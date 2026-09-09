# Payment System — 30-Minute Staff 

### Interview Prep — Amazon SDE III / Staff Engineer (L6)

---

## 1. CLARIFY + ASSUMPTIONS — ~2 MIN

| Ask                                                                                                                                                                     | Why It Matters                                                                           |
| ----------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------- |
| “Should I focus on card payments where our system orchestrates a payment through an external payment processor, or are we also expected to build the processor itself?” | Determines whether PSP interaction is an external failure boundary or part of our scope. |
| “Do we need authorization and capture as separate operations, or can I model a simple charge/payment first?”                                                            | Changes the payment state machine and API surface.                                       |
| “When the PSP times out, is it acceptable to return a pending result and resolve it asynchronously?”                                                                    | Determines how we handle the most important distributed-systems ambiguity.               |

### ASSUMPTIONS

* We are building a merchant-facing payment orchestration platform similar to Stripe's payment layer, using external PSPs/card networks.
* Start with one-step card payment; authorization/capture can be added later.
* Clients may retry aggressively because of network failures.
* PSPs support a provider-side idempotency/reference key and status lookup.

### WHAT I SAY

> “I’ll focus on the payment orchestration layer. The key challenge isn't routing an HTTP request—it's ensuring retries don't double-charge, handling cases where the PSP outcome is unknown, and keeping our payment state and financial ledger correct.”

---

# 2. REQUIREMENTS + SCALE — ~2–3 MIN

## FUNCTIONAL REQUIREMENTS

| Priority  | Requirement                                                  |
| --------- | ------------------------------------------------------------ |
| CORE      | Create and execute a payment against an external PSP.        |
| CORE      | Safely retry the same payment without charging twice.        |
| CORE      | Query the current payment status.                            |
| CORE      | Reconcile payments whose PSP outcome is temporarily unknown. |
| FOLLOW-UP | Refund a completed payment.                                  |
| FOLLOW-UP | Route between multiple PSPs based on availability/cost.      |

## NON-FUNCTIONAL REQUIREMENTS

1. **Financial correctness:** never create two logical charges for the same payment request.
2. **Durability:** confirmed payment and ledger state must survive failures.
3. **High availability:** a PSP timeout should not make our entire payment API unavailable.

## BACK-OF-THE-ENVELOPE

Assume:

```text
100M payments/day
100M / 86,400 ≈ 1,160 avg payments/sec

10x peak ≈ 12K payment requests/sec

~2 KB payment + attempt + ledger metadata
100M × 2 KB ≈ 200 GB/day before replication/index overhead
```

## ARCHITECTURE IMPLICATIONS

* ~12K peak writes/sec requires horizontally scalable stateless payment services and a database strategy capable of handling high transactional write volume.
* Financial state is relatively small but correctness-sensitive → prefer transactional storage over eventually consistent state mutation.
* PSP latency/failure is outside our control → payment processing needs an explicit **UNKNOWN/PENDING** state plus reconciliation.

### WHAT I SAY

> “The scale is meaningful but not the hardest part. The correctness boundary with the external PSP is much more important: after a timeout, I may not know whether money moved, so I must persist that ambiguity rather than guessing.”

---

# 3. MINIMAL API + CORE STATE — ~2 MIN

## 3A. CORE APIs

| Method / Operation | Endpoint / Event                | Purpose                                                       |
| ------------------ | ------------------------------- | ------------------------------------------------------------- |
| POST               | `/payments`                     | Create and execute a payment idempotently.                    |
| GET                | `/payments/{paymentId}`         | Return authoritative payment status.                          |
| POST               | `/payments/{paymentId}/refunds` | Refund a successful payment.                                  |
| Inbound event      | `PSP webhook`                   | Receive asynchronous confirmation or status changes from PSP. |

---

## 3B. SAMPLE API PAYLOAD

### Create Payment

```json
POST /payments
Idempotency-Key: checkout-98342

{
  "merchantId": "m-42",
  "orderId": "order-901",
  "amount": 12500,
  "currency": "USD",
  "paymentMethodToken": "pm_abc"
}
```

Successful response:

```json
{
  "paymentId": "pay-701",
  "status": "SUCCEEDED"
}
```

Ambiguous PSP timeout:

```json
{
  "paymentId": "pay-701",
  "status": "PENDING"
}
```

**Important:** a PSP timeout must **not** become `FAILED` automatically.

---

# 3C. CORE STATE MODEL

## PAYMENT

**Purpose:** Authoritative business record representing the merchant's logical payment and its current outcome.

* `paymentId` — stable identifier used by clients and internal components to refer to this payment.
* `merchantId` — identifies the merchant owning the payment and scopes queries and idempotency.
* `amount/currency` — exact monetary value the payment is allowed to move; immutable after processing begins.
* `status` — current lifecycle state such as CREATED, PROCESSING, PENDING, SUCCEEDED, or FAILED.
* `idempotencyKey` — merchant-supplied retry identity; a unique constraint on `(merchantId, idempotencyKey)` ensures repeated requests resolve to the same payment.
* `requestHash` — fingerprint of amount, currency, order, and payment method; prevents someone from reusing the same idempotency key with different payment parameters.

---

## PAYMENT_ATTEMPT

**Purpose:** Records every interaction with a PSP so uncertain or failed provider operations can later be investigated and reconciled.

* `attemptId` — identifies one PSP interaction for this payment.
* `paymentId` — links this provider operation to the logical payment.
* `provider` — PSP chosen for this attempt.
* `providerRequestId` — stable identifier sent to the PSP and reused during safe retries/status queries.
* `providerPaymentId` — PSP's identifier once known; used for webhook correlation and reconciliation.
* `status` — records whether this attempt is IN_PROGRESS, CONFIRMED, DECLINED, or UNKNOWN.

---

## LEDGER_ENTRY

**Purpose:** Immutable double-entry accounting record describing confirmed movement of money.

* `transactionId` — groups all debit/credit entries belonging to one financial event.
* `paymentId` — links the financial movement to its originating payment.
* `accountId` — ledger account being debited or credited, such as merchant payable or processor clearing.
* `amount` — signed or debit/credit amount applied to this account.
* `currency` — prevents balances from different currencies being mixed.
* `entryType` — identifies PAYMENT, REFUND, FEE, or another financial movement.

---

## RECONCILIATION_ITEM

**Purpose:** Tracks payments whose final external PSP outcome is not yet known and therefore requires later verification.

* `paymentId` — payment whose provider outcome needs resolution.
* `attemptId` — exact PSP interaction being investigated.
* `nextCheckAt` — when reconciliation should query the PSP again instead of repeatedly polling immediately.
* `attemptCount` — controls retry/backoff and escalation.
* `status` — OPEN, RESOLVED, or MANUAL_REVIEW.

---

### AUTHORITATIVE

**Payment DB + Ledger DB records are authoritative correctness state.**

I would initially keep PAYMENT, PAYMENT_ATTEMPT, and LEDGER_ENTRY in the **same transactional PostgreSQL/Aurora cluster** so successful payment state and ledger entries can commit atomically.

### DERIVED

Operational dashboards, Redis caches, analytics streams, and merchant reporting views may be derived.

They never determine whether a payment succeeded.

---

# PAYMENT STATE MACHINE

```text
CREATED
   |
   v
PROCESSING ---------> FAILED
   |
   +---------> PENDING
   |              |
   |              +---- reconciliation ----+
   |                                      |
   v                                      v
SUCCEEDED <--------------------------- SUCCEEDED
                                          |
                                      or FAILED
```

For authorization/capture later:

```text
AUTHORIZED → CAPTURED
```

### CRITICAL TRANSITIONS

**PROCESSING → SUCCEEDED** must atomically persist the confirmed payment result and corresponding ledger entries.

**PENDING → SUCCEEDED / FAILED** must be based on PSP-confirmed evidence, never just a retry timeout.

### DRAW

Write only:

```text
CREATED → PROCESSING → SUCCEEDED
              |  \
              |   → FAILED
              v
           PENDING
              |
        reconciliation
```

### WHAT I SAY

> “The unusual state here is PENDING. A PSP timeout doesn't mean failure—it means I don't know whether money moved. I persist that uncertainty and let reconciliation establish the final state.”

---

# 4. MAIN ARCHITECTURE — ~7–8 MIN

## 4A. DRAWING ORDER

### Step 1 — Entry

**DRAW:** Client → Payment API `(1)`

**MUST SAY:** Payment requests carry an idempotency key.

**WHAT I SAY:**

> “Every logical checkout operation gets a stable idempotency key so client retries don't create a second logical payment.”

**LIKELY PROBE:** What if the same key has different amount?

**FAST ANSWER:** Reject it by comparing the stored `requestHash`.

---

### Step 2 — Establish Durable Intent

**DRAW:** Payment Service → Payment Store `(2)`

**MUST SAY:** Persist before calling PSP.

**WHAT I SAY:**

> “Before touching the provider, I create the payment and attempt record transactionally, so after a crash I know exactly what external operation I intended to perform.”

**LIKELY PROBE:** Why not call the PSP first?

**FAST ANSWER:** A crash after PSP success but before local persistence could leave us with an untraceable charge.

---

### Step 3 — External Payment

**DRAW:** Payment Service → PSP Adapter → PSP `(3)` and return `(4)`.

**MUST SAY:** Stable provider request ID.

**WHAT I SAY:**

> “The adapter sends a stable provider request ID so even retries against the PSP can remain idempotent.”

**LIKELY PROBE:** Why adapter?

**FAST ANSWER:** It isolates PSP-specific APIs and lets the payment state machine remain provider-independent.

---

### Step 4 — Finalize Financial State

**DRAW:** Payment Service → Payment + Ledger Store `(5)`.

**MUST SAY:** Atomic finalization.

**WHAT I SAY:**

> “If the PSP confirms success, I atomically mark the payment successful and append the corresponding double-entry ledger records.”

**LIKELY PROBE:** Why transaction?

**FAST ANSWER:** Payment state must never say SUCCEEDED without the matching accounting entry.

---

### Step 5 — Return Result

**DRAW:** Payment API → Client `(6)`.

**MUST SAY:** PENDING is valid.

**WHAT I SAY:**

> “Confirmed outcomes return immediately; if the PSP result is ambiguous I return PENDING rather than incorrectly retrying or failing the charge.”

**LIKELY PROBE:** What happens after PENDING?

**FAST ANSWER:** Reconciliation or a PSP webhook determines the eventual final state.

---

### Step 6 — Recovery

**DRAW:** PSP → Webhook Ingest → Payment Service and Reconciliation Worker → PSP.

**MUST SAY:** Two independent resolution paths.

**WHAT I SAY:**

> “Unknown payments are resolved through provider webhooks when available and periodic reconciliation queries when they aren't.”

**LIKELY PROBE:** What if webhook and reconciliation race?

**FAST ANSWER:** Both perform idempotent conditional transitions against the same authoritative payment state.

---

# 4B. FINAL WHITEBOARD

```text
                    REQUEST / IDEMPOTENCY

 +----------+   (1)   +---------------+
 | Merchant | ------> | Payment API   |
 | / Client |         +-------+-------+
 +----------+                 |
                              | (2) persist intent
                              v
                     +--------------------+
                     | Payment Service    |
                     | state machine      |
                     +----+----------+----+
                          |          |
                 (3)      |          | (5) finalize
                          v          v
                  +-------------+   +----------------------+
                  | PSP Adapter |   | Payment + Ledger DB  |
                  +------+------+   | AUTHORITATIVE        |
                         |          |                      |
                      (3)|          | PAYMENT              |
                         v          | PAYMENT_ATTEMPT      |
                  +-------------+   | LEDGER_ENTRY         |
                  | External PSP|   +----------------------+
                  +------+------+        ^
                         |               |
                      (4)| result        |
                         +---------------+
                              |
                              | (6)
                              v
                           Client


                    AMBIGUOUS-RESULT RECOVERY

 +--------------+                     +------------------+
 | PSP Webhooks | ------------------> | Webhook Ingest   |
 +--------------+                     +--------+---------+
                                               |
                                               v
                                        Payment Service
                                               ^
                                               |
 +---------------------+      status query    |
 | Reconciliation     | --------------------> PSP
 | Worker              |
 +----------+----------+
            |
            +-------> reconcile PENDING payments


 Correctness:
 • UNIQUE(merchantId, idempotencyKey)
 • stable providerRequestId
 • SUCCEEDED + ledger entries commit atomically
```

---

# 4C. HAPPY-PATH WALKTHROUGH

| # | What Happens                                                                                      |
| - | ------------------------------------------------------------------------------------------------- |
| 1 | Merchant sends payment with an idempotency key.                                                   |
| 2 | Payment Service creates PAYMENT + PAYMENT_ATTEMPT before external side effects.                   |
| 3 | PSP Adapter submits payment using a stable provider request ID.                                   |
| 4 | PSP returns confirmed success/failure or an ambiguous timeout.                                    |
| 5 | Confirmed success atomically updates PAYMENT and appends ledger entries; timeout becomes PENDING. |
| 6 | API returns the authoritative current state to the client.                                        |

### WHAT I SAY

> “The main path is steps 1 through 6. I first deduplicate using the merchant's idempotency key and persist the intended payment before making any external side effect. Then I call the PSP with another stable provider request ID. On confirmed success, payment state and ledger entries commit atomically. If the provider times out, I don't guess—I persist PENDING and return that state. Webhooks or reconciliation later resolve it using the same authoritative payment record.”

---

# 4D. COMPONENT → TECHNOLOGY MAP

| Component             | Responsibility                              | Concrete Technology                | Why                                                                  |
| --------------------- | ------------------------------------------- | ---------------------------------- | -------------------------------------------------------------------- |
| Payment API           | Authentication, validation, request routing | Java service on ECS/EKS            | Stateless instances scale horizontally.                              |
| Payment Service       | Idempotency + state-machine coordination    | Java/Spring Boot                   | Strong fit for transactional orchestration and backend domain logic. |
| Payment Store         | Authoritative payment/attempt state         | Aurora PostgreSQL                  | Transactions, unique constraints, row locking/conditional updates.   |
| Ledger                | Immutable financial accounting              | PostgreSQL tables initially        | Can transactionally commit with payment finalization.                |
| PSP Adapter           | Normalize different PSP contracts           | Stateless Java service/module      | Hides provider-specific request and error semantics.                 |
| Reconciliation Worker | Resolve ambiguous transactions              | ECS workers + DB-driven work queue | Durable state already exists; no broker is required initially.       |
| Webhook Ingest        | Accept PSP async confirmations              | API Gateway + stateless service    | Horizontally scalable inbound event handling.                        |

**I would not introduce Kafka initially.** The payment workflow doesn't require a durable event log to establish correctness.

---

# 5. TOP 2 CORRECTNESS + FAILURE HARD PARTS

## Hard Part 1 — Duplicate Charges

### INVARIANT

One merchant idempotency key represents at most one logical payment.

### RACE / FAILURE

Client times out and retries while the original payment request is still processing.

### SOLUTION

* Unique constraint on `(merchantId, idempotencyKey)`.
* Store and compare `requestHash`.
* Reuse the same `providerRequestId` for safe PSP retry.

### CONSISTENCY

Creating the PAYMENT row requires a strongly consistent transaction / unique constraint.

### RETRY / IDEMPOTENCY

A retry returns the existing payment rather than creating another.

### RECOVERY

If local state says PROCESSING/PENDING, resume or reconcile that payment instead of creating a replacement.

### WHAT I SAY

> “Idempotency must exist at both boundaries. My API deduplicates logical merchant requests, and I also reuse a stable provider request ID because otherwise an internal retry could still double-charge at the PSP.”

---

## Hard Part 2 — PSP Timeout / Unknown Result

### INVARIANT

Never mark a payment failed—and retry the charge—unless we know the original PSP operation did not succeed.

### RACE / FAILURE

PSP charges the card successfully, but its response is lost.

```text
Our system ------ Charge ------> PSP
Our system <----- X response --- PSP
```

We cannot distinguish:

```text
A. PSP never charged
B. PSP charged, response lost
```

### SOLUTION

* Mark attempt/payment `PENDING`.
* Query provider using `providerRequestId` or `providerPaymentId`.
* Accept PSP webhook confirmation as another resolution path.

### CONSISTENCY

Final `PENDING → SUCCEEDED` update and ledger insertion happen transactionally.

### RETRY / IDEMPOTENCY

Never create a new PSP charge merely because the previous call timed out.

### RECOVERY

Backoff reconciliation until final resolution; eventually escalate unresolved cases to manual investigation.

### WHAT I SAY

> “This is the hardest failure boundary. A timeout gives me an unknown outcome, not a failed payment, so I persist PENDING and ask the PSP what happened before attempting any new financial side effect.”

---

## DELIVERY / SIDE-EFFECT SEMANTICS

* Internally we provide **effectively-once payment semantics** through idempotency.
* Network requests may occur more than once, but duplicate attempts use the same logical/provider identity.
* True exactly-once execution across our DB and an external PSP is impossible without cooperation from the PSP; provider idempotency plus reconciliation gives the practical guarantee.

---

# 6. SCALE + OVERLOAD — ~2 MIN

## Bottleneck 1 — Payment Database Writes

### PROBLEM

Every payment requires multiple correctness-critical writes.

### SCALE STRATEGY

* Partition/shard by `merchantId` when one primary cluster is no longer sufficient.
* Keep payment rows narrow and move analytics out of the OLTP path.

### PARTITIONING

Hash `merchantId` across database shards while retaining per-merchant idempotency locality.

### TRADE-OFF

Cross-merchant reporting becomes harder and belongs in an analytics pipeline.

---

## Bottleneck 2 — PSP Capacity

### PROBLEM

A provider may impose rate limits or become degraded while incoming traffic continues.

### SCALE STRATEGY

* Per-provider concurrency/rate limits.
* If multiple PSPs exist, route new payments to healthy providers before processing begins.

### TRADE-OFF

Failover routing must never switch providers blindly for an already-ambiguous payment.

---

## Bottleneck 3 — Reconciliation Backlog

### PROBLEM

A PSP incident can create millions of PENDING payments.

### SCALE STRATEGY

* Partition reconciliation by payment/provider.
* Exponential backoff + bounded worker concurrency.

### PARTITIONING

Workers claim ranges/buckets of reconciliation items so the work scales horizontally.

### TRADE-OFF

Resolution latency increases under a major provider outage, but this is safer than creating duplicate charges.

---

## BACKPRESSURE

* Overload first appears at the Payment API or PSP concurrency boundary.
* New requests may wait only in a **bounded** admission queue.
* Per-merchant and per-provider concurrency must be bounded.
* Throttle merchants exceeding quota; return `429` rather than allow unlimited internal buildup.
* Use merchant-level fairness so one large merchant can't starve others.

### WHAT I SAY

> “The API tier is easy to scale horizontally; the constrained resources are transactional database writes and external PSP capacity. I use per-merchant admission limits and provider-specific concurrency limits, and I deliberately slow reconciliation during incidents rather than retry financial operations aggressively.”

---

# 7. TWO KEY DESIGN TRADE-OFFS

## Transactional SQL vs DynamoDB for Core Payment State

**CHOSEN:** Aurora PostgreSQL initially.

**WHY:** Payment state and double-entry ledger updates benefit heavily from transactions, uniqueness constraints, and relational integrity.

**ALTERNATIVE:** DynamoDB.

**WHEN ALTERNATIVE WINS:** Extremely high scale with simpler single-item state transitions and a separately designed ledger architecture.

---

## Synchronous PSP Call vs Fully Async Payment Processing

**CHOSEN:** Synchronous provider attempt for the common case, with asynchronous reconciliation only for uncertain outcomes.

**WHY:** Merchants usually want immediate checkout confirmation, while we still isolate slow failure recovery.

**ALTERNATIVE:** Persist request and process every payment asynchronously.

**WHEN ALTERNATIVE WINS:** Workflows where seconds/minutes of confirmation latency are acceptable or the PSP itself is inherently asynchronous.

---

# 8. FAST PROBES — ONE-LINE LOOKUP

**Q1. What happens if the client retries while the original payment is still running?**
→ The unique idempotency key maps the retry to the existing payment, so no second logical charge is created.

**Q2. What if the PSP succeeds but our service crashes before updating our DB?**
→ The persisted PAYMENT_ATTEMPT remains unresolved and reconciliation queries the PSP using the stable provider request ID.

**Q3. How do you prevent payment status and ledger state from diverging?**
→ Commit the successful payment transition and ledger entries in one database transaction.

**Q4. Can I fail over from PSP A to PSP B after PSP A times out?**
→ Not until PSP A's outcome is proven unsuccessful; otherwise both PSPs could charge the customer.

**Q5. Why don't you need Kafka or an Outbox here?**
→ Neither is required for the correctness-critical payment path; I would add an Outbox only when committed payment state must reliably publish downstream events across a DB→broker boundary.

---

# 9. FINAL 30–45 SECOND SUMMARY

> “The main path is essentially steps 1 through 6 on the board. I persist an idempotent payment intent before calling the PSP, then execute the provider operation using a stable provider request ID. Confirmed success atomically updates the payment and double-entry ledger. The core distributed-systems problem is an ambiguous PSP response: if a timeout occurs, I persist PENDING rather than guessing or retrying a new charge, and webhooks or reconciliation establish the final outcome. Payment state and the ledger are authoritative, stateless API/service tiers scale horizontally, and database sharding plus provider-level admission control handle growth.”

---

## IF I AM RUNNING OUT OF TIME

### NEVER SKIP

1. Idempotency at client **and PSP** boundary.
2. `PENDING` state for ambiguous PSP result.
3. Payment success + ledger atomicity.

### SKIP FIRST

1. Multi-PSP routing.
2. Analytics/reporting.
3. Detailed webhook infrastructure.
