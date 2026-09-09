# Design a Distributed Message Queue
### Interview Prep — Amazon SDE III / Staff Engineer (L6)

---


# 1. CLARIFY + ASSUMPTIONS — ~2 MIN

| Ask                                                                                                           | Why It Matters                                                                                       |
| ------------------------------------------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------- |
| “Do we need ordering globally, or is ordering within a topic partition/key sufficient?”                       | Global ordering severely limits horizontal scalability; per-partition ordering lets us shard.        |
| “What delivery guarantee should I optimize for: at-most-once, at-least-once, or effectively-once processing?” | Determines acknowledgement, retry, deduplication, and consumer-offset design.                        |
| “Should consumers be able to replay old messages, or is this primarily a transient work queue?”               | Replay pushes us toward an append-only retained log rather than deleting messages after consumption. |

### ASSUMPTIONS

* Multiple producers and multiple consumer groups.
* Messages are durable and retained for a configurable period.
* **Ordering is guaranteed within a partition**, not globally.
* Primary delivery guarantee is **at-least-once**; consumer-side idempotency provides effectively-once processing where needed.

### WHAT I SAY

“I’ll design a durable Kafka-like distributed message queue: producers append messages to topics split into ordered partitions, multiple consumer groups can independently consume them, and the system provides at-least-once delivery with replay.”

---

# 2. REQUIREMENTS + SCALE — ~2–3 MIN

## FUNCTIONAL REQUIREMENTS

| Priority  | Requirement                                                                            |
| --------- | -------------------------------------------------------------------------------------- |
| CORE      | Producers publish messages to a topic, optionally with a key for ordering.             |
| CORE      | Consumers subscribe through independent consumer groups.                               |
| CORE      | Preserve message order within each partition and support acknowledgements/checkpoints. |
| CORE      | Retain messages so consumers can retry or replay from an earlier offset.               |
| FOLLOW-UP | Delayed delivery / dead-letter queues.                                                 |
| FOLLOW-UP | Cross-region replication.                                                              |

## NON-FUNCTIONAL REQUIREMENTS

1. **High throughput** — horizontally scale producers, brokers, and consumers.
2. **Durability** — acknowledged messages survive broker/node failures.
3. **Availability with defined consistency** — partition leader can fail over without corrupting log order.

## BACK-OF-THE-ENVELOPE

```text
Assume:
1M messages/sec peak
1 KB average message

Ingress ≈ 1 GB/sec
With replication factor 3 ≈ 3 GB/sec cluster write traffic

1 day raw retention:
1 GB/sec × 86,400 ≈ 86 TB/day
```

## ARCHITECTURE IMPLICATIONS

* 1M msg/sec → partition topics across many brokers rather than one centralized queue.
* ~86 TB/day → sequential append + segment files + cheap object storage for long retention if needed.
* Replication factor 3 → partition-level leader/follower replication for durability and failover.

### WHAT I SAY

“The main scaling unit is the partition. Each partition is an ordered append-only log, partitions are distributed across brokers, and replicas provide durability. Consumers independently track their position rather than messages being physically removed when one consumer reads them.”

---

# 3. MINIMAL API + CORE STATE — ~2 MIN

## 3A. CORE APIs

| Method / Operation | Endpoint / Event                            | Purpose                                                  |
| ------------------ | ------------------------------------------- | -------------------------------------------------------- |
| Produce            | `produce(topic, key, message)`              | Append a message to the appropriate partition.           |
| Fetch              | `fetch(topic, partition, offset, maxBytes)` | Read sequential messages starting from an offset.        |
| Commit Offset      | `commit(groupId, topic, partition, offset)` | Record consumer group's completed position.              |
| Subscribe          | `subscribe(groupId, topics)`                | Join a consumer group and receive partition assignments. |

---

## 3B. SAMPLE API PAYLOAD

```json
POST /topics/orders/messages

{
  "key": "customer-42",
  "producerId": "producer-17",
  "sequence": 105,
  "payload": {
    "orderId": "o-9001"
  }
}
```

Response:

```json
{
  "partition": 7,
  "offset": 1289341
}
```

`key` determines stable partition routing; `producerId + sequence` can support idempotent producer retries.

---

# 3C. CORE STATE MODEL

## MESSAGE LOG / PARTITION

**Purpose:** Authoritative ordered sequence of messages belonging to one topic partition.

* `topicId` — identifies the logical stream the message belongs to.
* `partitionId` — identifies the shard responsible for maintaining this subset of messages in strict order.
* `offset` — monotonically increasing position assigned when the partition leader appends the message; consumers use it to read and resume.
* `key` — optional application key hashed to select a partition so messages for the same key remain ordered.
* `payload` — actual message contents.
* `producerSequence` — sequence supplied by an idempotent producer, allowing duplicate retries to be recognized rather than appended twice.

---

## PARTITION METADATA

**Purpose:** Records where every partition lives and which broker currently accepts authoritative writes.

* `topicId / partitionId` — identifies the partition being described.
* `leaderBrokerId` — broker currently allowed to assign offsets and accept writes for this partition.
* `replicaBrokerIds` — brokers maintaining replicated copies for durability.
* `leaderEpoch` — changes whenever leadership changes so requests from an obsolete leader can be rejected.
* `highWatermark` — highest replicated offset considered safely committed and therefore readable under normal consistency guarantees.

---

## CONSUMER OFFSET

**Purpose:** Stores each consumer group's durable progress independently so different groups can consume or replay the same log.

* `groupId` — identifies the independent subscriber application.
* `topicId / partitionId` — identifies which ordered partition this progress belongs to.
* `committedOffset` — next position the consumer should resume from after restart.
* `generationId` — identifies the current consumer-group membership generation so an old consumer cannot commit progress after losing its partition assignment.

---

## CONSUMER GROUP MEMBERSHIP

**Purpose:** Tracks active consumers and partitions assigned to each member so one partition is normally processed by only one member within a group.

* `groupId` — consumer group whose members cooperate.
* `consumerId` — specific active consumer instance.
* `assignedPartitions` — partitions this consumer currently owns.
* `generationId` — changes during rebalance; rejects actions from members using obsolete assignments.
* `sessionExpiresAt` — deadline after which a consumer with no heartbeat is considered dead and its partitions can be reassigned.

### AUTHORITATIVE

* Partition log is authoritative for message contents and ordering.
* Consumer offset store is authoritative for a group's durable progress.
* Partition metadata is authoritative for current leadership.

### DERIVED

Broker routing caches and producer metadata caches may be stale. Clients refresh them when the current broker rejects a request due to leadership/epoch mismatch.

---

# STATE MACHINE

### Partition leadership

```text
FOLLOWER
   |
   | elected
   v
LEADER
   |
   | failure / newer epoch
   v
FOLLOWER
```

### Consumer ownership

```text
UNASSIGNED → ASSIGNED → PROCESSING
                 |
          heartbeat lost
                 v
             UNASSIGNED
```

### CRITICAL TRANSITIONS

**Partition leadership:** only one valid leader epoch may assign new offsets to a partition.

**Consumer ownership:** after rebalance, consumers from an old generation must not commit offsets for partitions they no longer own.

### DRAW

Write only:

```text
Partition: Leader -> Followers
Consumer Group: Partition -> Consumer
```

Annotate `leaderEpoch` and `generationId`.

### WHAT I SAY

“The important ownership boundaries are partition leadership and consumer assignment. A single leader determines ordering for each partition, while generation numbers prevent stale consumers from committing progress after a rebalance.”

---

# 4. MAIN ARCHITECTURE — ~7–8 MIN

## 4A. DRAWING ORDER

### Step 1 — Producer and Routing

**DRAW:** Producer → Metadata/Routing → Broker Partition.

**MUST SAY:** Partitioning is the main scalability and ordering boundary.

**WHAT I SAY:**
“The producer gets partition metadata and hashes the message key to a partition. That gives us deterministic per-key ordering without requiring global ordering.”

**LIKELY PROBE:** Why not one queue?

**FAST ANSWER:** “A single ordered queue has one serialization point; partitions let independent ordered streams scale horizontally.”

---

### Step 2 — Partition Leader

**DRAW:** Broker containing `Partition Leader`.

Add arrows:

`(1) Producer → Partition Leader`
`(2) Leader → append log`

**MUST SAY:** Only the leader assigns offsets.

**WHAT I SAY:**
“Within a partition, one leader serializes appends and assigns monotonically increasing offsets, which gives us a simple deterministic ordering guarantee.”

**LIKELY PROBE:** Can two brokers append simultaneously?

**FAST ANSWER:** “Not for the same partition epoch; only the elected leader may assign offsets.”

---

### Step 3 — Replication

**DRAW:** Leader → Replica Brokers.

Add `(3) replicate`.

**MUST SAY:** Acknowledgement policy determines durability.

**WHAT I SAY:**
“For durable writes, the leader replicates the record to followers and acknowledges after the required replica quorum/in-sync replica policy is satisfied.”

**LIKELY PROBE:** What if leader crashes immediately?

**FAST ANSWER:** “A sufficiently caught-up replica is elected leader and continues from the committed high watermark.”

---

### Step 4 — Consumer Path

**DRAW:** Consumer Group → Fetch from partition.

Add `(4) fetch`.

**MUST SAY:** Consumers pull sequential batches.

**WHAT I SAY:**
“I prefer pull-based consumption because the consumer controls batching and backpressure and simply asks for data from its current offset.”

**LIKELY PROBE:** Why not push?

**FAST ANSWER:** “Push simplifies latency but makes broker-side flow control and slow-consumer handling significantly harder.”

---

### Step 5 — Offset Commit

**DRAW:** Consumer → Offset Store.

Add:

`(5) process`
`(6) commit offset`

**MUST SAY:** Commit after processing gives at-least-once semantics.

**WHAT I SAY:**
“The consumer processes the message and then commits its offset. If it crashes between those actions, the message is replayed, so handlers must tolerate duplicates.”

**LIKELY PROBE:** Exactly once?

**FAST ANSWER:** “The queue can suppress duplicate production, but exactly-once external business side effects generally require idempotency or a transaction with the consumer’s state.”

---

### Step 6 — Coordination / Failover

**DRAW:** Metadata Controller connected to brokers and consumer groups.

**MUST SAY:** It manages leadership and consumer-group membership, not message traffic.

**WHAT I SAY:**
“The controller handles partition leadership and membership changes, while the data path stays directly between clients and brokers.”

**LIKELY PROBE:** Is the controller a bottleneck?

**FAST ANSWER:** “Not on normal message traffic; it handles metadata and failure transitions rather than each produce/fetch.”

---

# 4B. FINAL WHITEBOARD

```text
                 CONTROL PLANE
        +---------------------------+
        | Metadata / Controller     |
        | leaders, replicas, groups |
        +-----------+---------------+
                    |
             metadata / failover
                    |
                    v

                 MESSAGE DATA PLANE

+----------+   (1)   +-------------------------+
| Producer | ------> | Partition Leader        |
+----------+         | Broker                  |
                     +-----------+-------------+
                                 |
                                (2) append
                                 v
                     +-------------------------+
                     | Ordered Partition Log   |
                     | AUTHORITATIVE           |
                     | offsets: ...100,101,102 |
                     +-----------+-------------+
                                 |
                                (3) replicate
                      +----------+----------+
                      |                     |
                      v                     v
               +-------------+       +-------------+
               | Replica B   |       | Replica C   |
               +-------------+       +-------------+


                 CONSUMPTION

+----------------+   (4) fetch   +-------------------------+
| Consumer Group | <-----------  | Partition Leader        |
| C1  C2  C3     |               +-------------------------+
+-------+--------+
        |
       (5) process
        |
        v
+-------------------+
| Application / DB  |
+-------------------+
        |
       (6) commit
        v
+----------------------------+
| Consumer Offset Store      |
| AUTHORITATIVE progress     |
+----------------------------+

Annotations:
- per-partition ordering
- leader epoch
- at-least-once consumption
```

---

# 4C. HAPPY-PATH WALKTHROUGH

| # | What Happens                                                                                                |
| - | ----------------------------------------------------------------------------------------------------------- |
| 1 | Producer hashes the message key, discovers the current leader, and sends the record there.                  |
| 2 | Leader serially appends it to the partition log and assigns the next offset.                                |
| 3 | Record is replicated to follower brokers before durable acknowledgement according to the configured policy. |
| 4 | Assigned consumer fetches a sequential batch beginning at its current offset.                               |
| 5 | Consumer processes the message and performs its application-side effect.                                    |
| 6 | Consumer commits the completed offset so it can resume from there after restart.                            |

### WHAT I SAY

“The main path is steps one through six. The producer selects a partition and writes directly to its leader; that leader is the ordering authority and assigns the offset. We replicate before acknowledging according to the durability policy. Consumers then pull sequential batches, process them, and commit progress separately. Because processing happens before committing the offset, a crash can replay a message, so the base semantic is at-least-once.”

---

# 4D. COMPONENT → TECHNOLOGY MAP

| Component            | Responsibility                             | Concrete Technology                               | Why                                                                 |
| -------------------- | ------------------------------------------ | ------------------------------------------------- | ------------------------------------------------------------------- |
| Producer Client      | Partition routing, batching, retry         | Java client library                               | Maintains metadata and batches records efficiently.                 |
| Broker               | Host partition logs and serve reads/writes | Custom Java/C++ service                           | Sequential disk/network workload with explicit log semantics.       |
| Partition Log        | Durable ordered message storage            | Append-only segment files + filesystem/page cache | Sequential writes provide very high throughput.                     |
| Replica Brokers      | Protect partitions from node loss          | Replicated broker nodes                           | Maintain redundant ordered log copies.                              |
| Metadata Controller  | Leadership and cluster metadata            | Raft-backed controller                            | Consensus provides single authoritative metadata decisions.         |
| Consumer Coordinator | Group membership/rebalancing               | Broker-side coordinator                           | Keeps partition assignment close to queue metadata.                 |
| Offset Store         | Consumer progress                          | Internal replicated compacted topic               | Durable, partitionable, naturally represented as key→latest offset. |

---

# 5. TOP 2 CORRECTNESS + FAILURE HARD PARTS — ~2–3 MIN

## Hard Part 1 — Partition Leader Failure

**INVARIANT:**
Two leaders must never independently assign valid offsets for the same partition generation.

**RACE / FAILURE:**
A network-partitioned old leader may still believe it owns the partition after another replica has been elected.

**SOLUTION:**

* Consensus-backed controller assigns a new **leader epoch**.
* Brokers reject requests carrying obsolete epochs.
* Promote only a sufficiently synchronized replica and expose data only through the committed high watermark.

**CONSISTENCY:**
Leadership changes require strong consensus; normal message appends remain local to the partition leader plus replicas.

**RETRY / IDEMPOTENCY:**
Producer retries use producer identity + sequence to avoid duplicate append where idempotent production is enabled.

**RECOVERY:**
Elect an in-sync replica and redirect producers/consumers using refreshed metadata.

### WHAT I SAY

“The main broker-side correctness issue is split leadership. I solve that with epoch-based leadership backed by consensus, so a stale leader cannot continue making authoritative appends.”

---

## Hard Part 2 — Consumer Crash Around Offset Commit

**INVARIANT:**
A committed offset must never claim that processing completed when it did not.

**RACE / FAILURE:**
Consumer may finish processing and crash before committing, causing the same message to be delivered again.

**SOLUTION:**

* Process first, then commit offset.
* Accept replay and make consumers idempotent.
* For DB-backed applications, store business mutation and consumed offset in the same transaction when practical.

**CONSISTENCY:**
Offset commits need consistent per-group/per-partition ordering, but application processing need not involve queue-wide transactions.

**RETRY / IDEMPOTENCY:**
Duplicate delivery is safe when processing is keyed by a message/event ID or performed transactionally.

**RECOVERY:**
Replacement consumer resumes from the last committed offset.

### WHAT I SAY

“I prefer losing uniqueness rather than losing data: process first and then commit. That gives at-least-once delivery, with effectively-once application behavior achieved through idempotency or a local transaction.”

### DELIVERY / SIDE-EFFECT SEMANTICS

* **Queue consumption:** at-least-once.
* **Producer append:** can be made idempotent using producer ID + sequence.
* True exactly-once against arbitrary external systems is not generally possible without cooperation from that external system.

---

# 6. SCALE + OVERLOAD — ~2 MIN

## Bottleneck 1 — Hot Partition

**PROBLEM:**
A popular key or poorly distributed partition key can overload one leader even when cluster capacity is available.

**SCALE STRATEGY:**

* Increase partition count for independently orderable keys.
* Encourage high-cardinality routing keys or application-level key salting where strict per-key ordering is unnecessary.

**PARTITIONING:**
`hash(messageKey) % partitionCount`.

**TRADE-OFF:**
More partitions improve parallelism but increase metadata, file handles, replication work, and rebalance cost.

---

## Bottleneck 2 — Broker Disk / Network Throughput

**PROBLEM:**
Large aggregate append and replication traffic can saturate individual brokers.

**SCALE STRATEGY:**

* Batch records and use sequential append/sendfile-like zero-copy reads.
* Spread partition leaders across brokers.

**PARTITIONING:**
Balance both replicas and leaders across nodes.

**TRADE-OFF:**
Larger batches improve throughput but slightly increase latency.

---

## Bottleneck 3 — Slow Consumer

**PROBLEM:**
A consumer group may fall behind production indefinitely.

**SCALE STRATEGY:**

* Add consumers up to the number of partitions.
* Monitor consumer lag and scale processing independently.

**TRADE-OFF:**
Beyond partition count, adding consumers provides no additional parallelism.

---

## BACKPRESSURE

* Overload becomes visible as **producer latency**, broker queue depth, disk utilization, or consumer lag.
* Short bursts may wait in bounded broker/network buffers.
* Producer request queues and broker memory must remain bounded.
* Throttle producers or tenants exceeding quotas instead of allowing unbounded memory growth.
* Apply per-tenant/topic quotas to prevent noisy neighbors.

### WHAT I SAY

“The system scales primarily by adding partitions and spreading their leaders across brokers. Batching gives us efficient sequential IO, while quotas and bounded broker buffers prevent bursts from becoming memory failures. Consumer throughput scales independently up to roughly one active consumer per partition per group.”

---

# 7. TWO KEY DESIGN TRADE-OFFS — ~1 MIN

## Partitioned Ordering vs Global Ordering

**CHOSEN:**
Per-partition ordering.

**WHY:**
Independent partitions can be written and consumed concurrently across many brokers.

**ALTERNATIVE:**
Single globally ordered log.

**WHEN ALTERNATIVE WINS:**
When total scale is modest and strict global event order is truly required.

---

## Pull Consumers vs Push Delivery

**CHOSEN:**
Consumers pull batches using offsets.

**WHY:**
Consumers control their pace, batching, replay, and backpressure.

**ALTERNATIVE:**
Broker pushes messages to consumers.

**WHEN ALTERNATIVE WINS:**
Low-volume workloads where minimal delivery latency matters more than replay and flow-control simplicity.

---

# 8. FAST PROBES — ONE-LINE LOOKUP

**Q1. How do you preserve ordering?**
→ One leader serializes writes and assigns offsets within each partition; no global ordering is promised.

**Q2. What happens if a broker leader dies?**
→ The controller elects an in-sync replica with a higher epoch and clients refresh routing metadata.

**Q3. How do you avoid duplicate processing?**
→ The queue provides at-least-once delivery, while consumers use event IDs/idempotent writes or transactional processing.

**Q4. How do you handle a hot partition?**
→ Increase key cardinality or repartition where ordering permits; a single hot ordering key fundamentally limits parallelism.

**Q5. Why append-only log instead of deleting a message after consumption?**
→ Independent consumer groups need their own offsets and replay capability, so consumption should not mutate the underlying message log.

---

# 9. FINAL 30–45 SECOND SUMMARY

“The main path is essentially steps one through six on the board. Producers hash a key to a partition and write to that partition’s leader, which is the authority for ordering and assigns sequential offsets. Messages are replicated before durable acknowledgement, while consumer groups independently fetch and track their committed offsets. The core correctness problems are preventing split partition leadership through epochs and consensus, and safely handling consumer crashes using at-least-once delivery plus idempotent processing. We scale primarily through partitioning, batching, and distributing partition leaders across brokers, and recover broker or consumer failures by electing replicas or resuming from committed offsets.”

## NEVER SKIP

1. **Partition = scaling + ordering boundary**
2. **Leader + replicas = authoritative durable log**
3. **Process then commit = at-least-once semantics**

## SKIP FIRST

1. Cross-region replication
2. DLQ / delayed messages
3. Detailed observability/security
