# Design a Distributed Distributed Cache
### Interview Prep — Amazon SDE III / Staff Engineer (L6)

---


## 1. CLARIFY + ASSUMPTIONS — ~2 MIN

| Ask                                                                                                            | Why It Matters                                                                           |
| -------------------------------------------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------- |
| “Is this cache sitting in front of an authoritative datastore, or is the cache itself expected to be durable?” | Determines whether losing cached data is acceptable and whether persistence is required. |
| “Should I optimize primarily for read-heavy traffic with simple key-value GET/PUT operations?”                 | Determines API, partitioning, replication, and eviction strategy.                        |
| “Do we need strict read-after-write consistency, or is brief staleness acceptable?”                            | Determines replication and invalidation semantics.                                       |

### ASSUMPTIONS

* Cache is **not authoritative**; an external database/service owns durable data.
* Workload is read-heavy with very high GET QPS.
* Brief stale reads are acceptable.
* Cache entries support TTL and may be evicted before TTL when memory is constrained.

### WHAT I SAY

> “I’ll design this as a high-throughput distributed key-value cache in front of an authoritative datastore. I’ll optimize for low-latency reads, horizontal scaling, node failures, TTL/eviction, and avoiding hot-key/cache-stampede problems.”

---

# 2. REQUIREMENTS + SCALE — ~2–3 MIN

## FUNCTIONAL REQUIREMENTS

| Priority  | Requirement                                         |
| --------- | --------------------------------------------------- |
| CORE      | GET a value by key.                                 |
| CORE      | PUT/update a key with optional TTL.                 |
| CORE      | DELETE/invalidate a cached key.                     |
| CORE      | Automatically expire and evict entries when needed. |
| FOLLOW-UP | Multi-key operations.                               |
| FOLLOW-UP | Atomic counters / compare-and-set.                  |

## NON-FUNCTIONAL REQUIREMENTS

1. **Very low latency** — single-digit millisecond, ideally sub-millisecond inside the cache tier.
2. **High availability** — individual cache-node failure should not take down the cache.
3. **Horizontal scalability** — increase capacity by adding nodes.

## BACK-OF-THE-ENVELOPE

Assume:

```text
Peak requests        = 1,000,000 ops/sec
GET : writes         = 90 : 10
Avg cached value     = 1 KB

Read bandwidth:
900K * 1 KB ~= 900 MB/sec

100M cached entries * ~1 KB
~= 100 GB raw data
~= 200+ GB with replicas + metadata
```

## ARCHITECTURE IMPLICATIONS

* ~1M QPS → partition keys across many independent cache nodes.
* Hundreds of GB → memory distributed across shards rather than one machine.
* Read-heavy workload → replicas can absorb reads and improve availability.

### WHAT I SAY

> “The dominant requirement is high read throughput with very low latency. That pushes me toward an in-memory partitioned cache where routing is O(1), and both capacity and throughput scale by adding cache nodes.”

---

# 3. MINIMAL API + CORE STATE — ~2 MIN

## 3A. CORE APIs

| Method / Operation | Endpoint / Event        | Purpose                               |
| ------------------ | ----------------------- | ------------------------------------- |
| GET                | `GET /cache/{key}`      | Retrieve cached value.                |
| PUT                | `PUT /cache/{key}`      | Store/update value with optional TTL. |
| DELETE             | `DELETE /cache/{key}`   | Explicitly invalidate value.          |
| MGET               | `POST /cache/batch-get` | Retrieve multiple keys efficiently.   |

---

## 3B. SAMPLE API PAYLOAD

```json
PUT /cache/user:123

{
  "value": "...",
  "ttlSeconds": 600
}
```

Response:

```json
{
  "stored": true
}
```

GET:

```text
GET /cache/user:123

200 -> cached value
404 -> cache miss
```

---

# 3C. CORE STATE MODEL

## CACHE_ENTRY

**Purpose:** In-memory representation of one cached key/value and the metadata needed for expiration and eviction.

* `key` — unique lookup key whose hash determines which cache shard stores the entry.
* `value` — cached application data returned to readers.
* `expiresAt` — time after which the entry must no longer be returned, even if it remains physically in memory.
* `lastAccess` — tracks recent usage when the eviction policy needs to identify colder entries.
* `sizeBytes` — lets the node account for memory usage and avoid allowing a few large objects to exhaust the shard.

---

## CACHE_NODE

**Purpose:** Represents one cache server and the key ranges/partitions currently assigned to it.

* `nodeId` — unique identity used by routers and membership management.
* `address` — network location used to send cache operations to this node.
* `state` — ACTIVE, DRAINING, or DOWN so routing avoids unhealthy nodes.
* `weight` — allows larger nodes to own proportionally more cache partitions.
* `zone` — used to place replicas in different failure domains.

---

## PARTITION_ASSIGNMENT

**Purpose:** Derived routing metadata mapping logical hash partitions to cache nodes so clients do not scan the cluster to locate a key.

* `partitionId` — logical bucket produced from hashing the cache key.
* `primaryNode` — node responsible for writes for that partition.
* `replicaNodes` — additional nodes holding copies for availability/read scaling.
* `version` — identifies the membership/routing configuration; routers reject or refresh stale topology after rebalancing.

### AUTHORITATIVE

The **external backing datastore** is authoritative for application data.

The cache does **not** determine durable correctness.

### DERIVED

* Cache entries
* Partition/routing metadata
* Replicas

All can be reconstructed.

If the cache is stale or unavailable, correctness falls back to the backing datastore.

---

# STATE MACHINE

For an individual entry:

```text
      PUT
       |
       v
    PRESENT
    /     \
 TTL       memory pressure
  |             |
  v             v
EXPIRED       EVICTED
    \           /
       absent
```

Explicit invalidation:

```text
PRESENT -- DELETE --> ABSENT
```

### CRITICAL TRANSITION

**PRESENT → EXPIRED** must ensure expired data is never returned after its TTL even if physical deletion has not happened yet.

### DRAW

Only write:

```text
ABSENT -> PRESENT -> EXPIRED / EVICTED
              |
            DELETE
              v
            ABSENT
```

### WHAT I SAY

> “An entry is usable only while it exists and its TTL is valid. Physical cleanup can be lazy, but GET must always check expiration, so an expired entry can never be returned merely because the cleanup worker hasn’t removed it yet.”

---

# 4. MAIN ARCHITECTURE — ~7–8 MIN

## 4A. DRAWING ORDER

### Step 1 — Client + Cache API

**DRAW:** Client → Cache Router

**MUST SAY:** Router decides which cache shard owns a key.

**WHAT I SAY:**

> “I’ll keep routing stateless. We hash the cache key to a logical partition and then map that partition to a cache node.”

**LIKELY PROBE:** Why not one centralized cache node?

**FAST ANSWER:** Because it limits both memory capacity and request throughput and becomes a single failure domain.

---

### Step 2 — Partition Routing

**DRAW:** Cache Router → Partition Map / Membership

**MUST SAY:** Routing metadata is small and replicated.

**WHAT I SAY:**

> “The router uses a partition map rather than querying every node. I’ll use fixed logical partitions so adding a physical node moves only selected partitions.”

**LIKELY PROBE:** Why not simple `hash(key) % N`?

**FAST ANSWER:** Changing N remaps most keys, causing a huge cold-cache event.

---

### Step 3 — Cache Shards

**DRAW:** Router `(2)` → Cache Node Primary `(3)`.

**MUST SAY:** Each cache node owns independent partitions.

**WHAT I SAY:**

> “Each shard keeps its entries entirely in memory, with a hash table for O(1) key lookup and additional eviction metadata.”

**LIKELY PROBE:** How do we evict?

**FAST ANSWER:** Use approximate LRU/LFU plus TTL, avoiding expensive globally exact ordering.

---

### Step 4 — Replication

**DRAW:** Primary → Replica.

**MUST SAY:** Replication is for cache availability, not durable correctness.

**WHAT I SAY:**

> “I replicate each partition to one or more nodes so a node failure doesn’t cause every key on that shard to become unavailable.”

**LIKELY PROBE:** Synchronous replication?

**FAST ANSWER:** Usually asynchronous because brief cache staleness is acceptable and write latency matters more.

---

### Step 5 — Cache Miss

**DRAW:** Cache miss `(4)` → Application/Loader → authoritative DB `(5)` → fill cache `(6)`.

**MUST SAY:** Database remains authoritative.

**WHAT I SAY:**

> “On a miss, the application loads from the authoritative datastore and repopulates the cache. For hot keys I’ll coalesce concurrent misses so one DB lookup populates the cache instead of thousands.”

**LIKELY PROBE:** What is that problem called?

**FAST ANSWER:** Cache stampede or thundering herd.

---

### Step 6 — Membership / Rebalancing

**DRAW:** Membership Service → Partition Map and cache nodes.

**MUST SAY:** It manages topology, not individual requests.

**WHAT I SAY:**

> “Membership detects joins and failures and produces a versioned partition assignment. Routers then refresh topology and send traffic to the new owners.”

**LIKELY PROBE:** What happens during redistribution?

**FAST ANSWER:** Move partitions gradually and rate-limit migration so rebalancing itself doesn’t overload the cache or database.

---

# 4B. FINAL WHITEBOARD

```text
                     REQUEST / ROUTING

 +--------+   (1)    +----------------+
 | Client | -------> | Cache Router   |
 +--------+          | hash(key)      |
                     +-------+--------+
                             |
                            (2)
                             v
                     +----------------+
                     | Partition Map  |
                     | derived        |
                     +-------+--------+
                             |
                            (3)
                             v

                     CACHE DATA PLANE

              +---------------------+
              | Cache Primary       |
              | HashMap + TTL +     |
              | LRU/LFU eviction    |
              +----------+----------+
                         |
                    async replica
                         v
              +---------------------+
              | Cache Replica       |
              +---------------------+

                         |
                     MISS (4)
                         v

                     MISS / FILL PATH

              +---------------------+
              | App / Loader        |
              | single-flight       |
              +----------+----------+
                         |
                        (5)
                         v
              +---------------------+
              | Backing Database    |
              | AUTHORITATIVE       |
              +----------+----------+
                         |
                        (6)
                         |
                         +--------> refill Cache Primary


                     CONTROL PLANE

              +---------------------+
              | Membership /        |
              | Rebalancer          |
              +----------+----------+
                         |
                  update topology
                         v
                  Partition Map


Annotations:
- fixed logical partitions / consistent hashing
- async replication
- TTL + approximate LRU/LFU
```

---

# 4C. HAPPY-PATH WALKTHROUGH

| # | What Happens                                                              |
| - | ------------------------------------------------------------------------- |
| 1 | Client sends GET/PUT for a cache key.                                     |
| 2 | Router hashes the key and consults local partition metadata.              |
| 3 | Request goes directly to the primary cache shard owning that partition.   |
| 4 | If the key is missing or expired, the request enters the cache-miss path. |
| 5 | Loader fetches the authoritative value from the backing datastore.        |
| 6 | Retrieved value is returned and repopulated into the cache.               |

### WHAT I SAY

> “The main path is steps 1 through 3. The client sends the key, the router hashes it to a logical partition, and the request goes directly to that shard for an O(1) in-memory lookup. If it’s a miss at step 4, the application loads the authoritative value from the database at step 5 and writes it back into the cache at step 6. Cache replicas improve availability, while membership and rebalancing are off the request path.”

---

# 4D. COMPONENT → TECHNOLOGY MAP

| Component     | Responsibility                    | Concrete Technology                        | Why                                                                            |
| ------------- | --------------------------------- | ------------------------------------------ | ------------------------------------------------------------------------------ |
| Cache Router  | Route key to correct shard        | Stateless Java/Go service                  | Hashing and partition lookup require little state.                             |
| Cache Nodes   | Hold hot data in memory           | Redis-style custom cluster / Redis Cluster | In-memory hash lookup and TTL/eviction are native cache operations.            |
| Partition Map | Map logical partitions to nodes   | In-memory versioned topology               | Tiny lookup structure can be copied to every router.                           |
| Membership    | Track healthy nodes and rebalance | etcd                                       | Consensus-backed membership/configuration prevents conflicting topology views. |
| Backing Store | Own durable application data      | Existing DB, e.g. DynamoDB/PostgreSQL      | Cache misses ultimately validate against the system of record.                 |
| Loader        | Fetch/cache missing values        | Application service                        | Keeps domain-specific DB loading outside generic cache nodes.                  |

---

# 5. TOP 2 CORRECTNESS + FAILURE HARD PARTS — ~2–3 MIN

## Hard Part 1 — Node Failure and Repartitioning

**INVARIANT:** Every cache key should have a well-defined current partition owner.

**RACE / FAILURE:** A router may use an old partition map while a node is failing or being replaced.

### SOLUTION

* Version partition assignments.
* Mark failed node unavailable and promote/use replica.
* Routers refresh their partition map when a request hits stale topology.

**CONSISTENCY:** Strong consistency is required for publishing cluster membership/topology, not for individual cached values.

**RETRY / IDEMPOTENCY:** GET can safely retry against the new owner; PUT uses last-write semantics or an application version if stronger semantics are required.

**RECOVERY:** Missing cache contents are reconstructed lazily from the backing DB.

**ALTERNATIVE:** Client-side consistent hashing can eliminate the proxy/router but pushes topology logic into every application.

### WHAT I SAY

> “The important correctness point is agreement on current ownership, not preserving every cached object. If a node dies, I promote or route to its replica, publish a newer topology version, and allow cold entries to refill from the authoritative datastore.”

---

## Hard Part 2 — Cache Stampede

**INVARIANT:** A hot expired key should not cause thousands of simultaneous requests to hammer the database.

**RACE / FAILURE:** A popular entry expires and many requests observe the miss simultaneously.

### SOLUTION

* Single-flight/coalesce concurrent loads per hot key.
* Add small TTL jitter so many keys do not expire simultaneously.
* Optionally serve stale data briefly while one request refreshes it.

**CONSISTENCY:** No strong cache-wide transaction is necessary.

**RETRY / IDEMPOTENCY:** Multiple fills of the same key are safe when values are versioned or last-write-wins is acceptable.

**RECOVERY:** If the loader fails, release the single-flight marker and allow a later request to retry.

### WHAT I SAY

> “The dangerous miss-path failure is a thundering herd. I coalesce refreshes by key so one request hits the database while the others wait or briefly receive stale data.”

---

## DELIVERY / SIDE-EFFECT SEMANTICS

* Cache writes can normally be **at-least-once** because repeating a PUT is harmless.
* A version can prevent an older delayed fill from overwriting newer cached data.
* Exactly-once cache mutation is unnecessary because cache contents are derived state.

---

# 6. SCALE + OVERLOAD — ~2 MIN

## Bottleneck 1 — Hot Keys

**PROBLEM:** A single popular key hashes to one partition and can overwhelm one node.

**SCALE STRATEGY:**

* Replicate very hot keys and distribute reads.
* Optionally keep a small router/local L1 cache for extreme hot spots.

**PARTITIONING:** Ordinary keys remain hash-partitioned; exceptional hot keys may be replicated beyond their normal factor.

**TRADE-OFF:** More replicas increase stale-read risk and invalidation work.

---

## Bottleneck 2 — Memory Capacity

**PROBLEM:** Working set exceeds aggregate RAM.

**SCALE STRATEGY:**

* Add cache nodes and rebalance logical partitions.
* Evict low-value entries using approximate LRU/LFU.

**PARTITIONING:** Use many more logical partitions than physical nodes.

**TRADE-OFF:** More aggressive eviction lowers hit rate.

---

## Bottleneck 3 — Rebalancing / Cache Cold Start

**PROBLEM:** Adding or losing nodes can suddenly move large numbers of keys and increase DB misses.

**SCALE STRATEGY:**

* Rebalance a bounded number of partitions at a time.
* Optionally transfer hot cache contents before switching ownership.

**TRADE-OFF:** Slower rebalance protects the database but leaves temporary imbalance longer.

---

## BACKPRESSURE

* Overload first appears as rising cache-node latency/CPU/network utilization.
* Misses may queue briefly in the loader.
* Per-key single-flight waiters and loader concurrency must be bounded.
* Reject/throttle excessive client QPS before the backing DB is overwhelmed.
* Apply per-tenant quotas/rate limits for shared caches.

### WHAT I SAY

> “The normal scale path is horizontal partitioning across cache nodes. Hot keys require replication rather than more ordinary shards, and the most important overload protection is on the miss path because an overloaded cache must not turn into an even larger database outage.”

---

# 7. TWO KEY DESIGN TRADE-OFFS — ~1 MIN

## Fixed Logical Partitions vs `hash(key) % N`

**CHOSEN:** Many fixed logical partitions mapped onto physical cache nodes.

**WHY:** Adding/removing nodes moves only selected partitions instead of remapping nearly every key.

**ALTERNATIVE:** `hash(key) % N`.

**WHEN ALTERNATIVE WINS:** Small fixed clusters where membership almost never changes.

---

## Async vs Synchronous Replication

**CHOSEN:** Asynchronous replication.

**WHY:** Cache data is reconstructable, so lower write latency is more valuable than strict replica consistency.

**ALTERNATIVE:** Synchronous replication.

**WHEN ALTERNATIVE WINS:** When applications require strong read-after-write guarantees even through immediate failover.

---

# 8. FAST PROBES — ONE-LINE LOOKUP

**Q1. How do you decide which node stores a key?**
→ Hash the key into a stable logical partition and use the versioned partition map to find that partition's owner.

**Q2. What happens when a cache node dies?**
→ Route to its replica/new owner and lazily reconstruct missing data from the authoritative datastore.

**Q3. How do you prevent cache stampede?**
→ Coalesce concurrent misses for the same key so only one request refreshes it from the DB.

**Q4. Why not use `hash(key) % numberOfNodes`?**
→ Changing the node count remaps most keys and creates a massive cold-cache event.

**Q5. How do you maintain consistency with the database?**
→ The database remains authoritative; cache entries use TTL plus explicit write-through/invalidation where fresher reads are required.

---

# 9. FINAL 30–45 SECOND SUMMARY

> “The main path is essentially steps 1 through 3 on the board: hash the key, map it to a logical partition, and perform an O(1) lookup on the owning in-memory cache shard. The backing database remains authoritative, so cache state and replicas are rebuildable. The domain-specific scaling problem is distributing both memory and traffic while handling membership changes without remapping the entire cache, so I use fixed logical partitions with versioned ownership. Node failures fall back to replicas and eventually the database, while single-flight loading protects the database from cache stampedes. Capacity scales by adding nodes and gradually rebalancing partitions.”

---

## IF I AM RUNNING OUT OF TIME

### NEVER SKIP

1. Hash/partitioning strategy.
2. Authoritative DB vs derived cache.
3. Node failure + cache stampede handling.

### SKIP FIRST

1. Multi-key APIs.
2. Detailed eviction implementation.
3. Advanced rebalance optimization.
