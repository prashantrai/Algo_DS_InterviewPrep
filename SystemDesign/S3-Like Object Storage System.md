# S3-Like Object Storage System

### Interview Prep — Amazon SDE III / Staff Engineer (L6)

---

## 1. CLARIFY + ASSUMPTIONS — ~2 MIN

| Ask                                                                                                                                | Why It Matters                                             |
| ---------------------------------------------------------------------------------------------------------------------------------- | ---------------------------------------------------------- |
| “Should PUT/overwrite be strongly consistent, so a successful write is immediately visible to subsequent GET and LIST operations?” | Determines metadata commit and read semantics.             |
| “What object-size range should I design for, and do we need multipart uploads for very large objects?”                             | Determines streaming/chunking and upload-session design.   |
| “Are we designing within one region across multiple AZs, or is cross-region replication part of the core requirement?”             | Changes replication placement, latency, and failure model. |

### ASSUMPTIONS

* Strong read-after-write consistency for PUT, DELETE, and metadata operations.
* Objects range from KBs to multi-GB/TB; large objects use multipart upload.
* Core design is one region across 3 AZs.
* Objects are durable and effectively immutable once a version is committed; overwriting creates a new version internally.

### WHAT I SAY

> “I’ll focus on the regional object-storage data path: PUT, GET, DELETE, metadata lookup, durable placement across failure domains, and repair. I’ll treat cross-region replication and advanced lifecycle policies as extensions.”

The domain-specific center here is **metadata authority + object/chunk placement + replication/checksum/repair**, matching the storage-specific concerns in the provided interview framework. 

---

# 2. REQUIREMENTS + SCALE — ~2–3 MIN

## FUNCTIONAL REQUIREMENTS

| Priority  | Requirement                                                                                     |
| --------- | ----------------------------------------------------------------------------------------------- |
| CORE      | PUT/upload an object identified by `bucket + objectKey`.                                        |
| CORE      | GET/HEAD an object with low-latency metadata lookup and streamed data retrieval.                |
| CORE      | DELETE/overwrite objects while preserving correct visibility semantics.                         |
| CORE      | Replicate object data across failure domains and automatically recover lost/corrupted replicas. |
| FOLLOW-UP | Multipart upload, object versioning, lifecycle/tiering.                                         |
| FOLLOW-UP | Cross-region replication.                                                                       |

## NON-FUNCTIONAL

1. **Very high durability** — losing a machine/AZ must not lose committed objects.
2. **Horizontal scale** — billions of objects and very high aggregate bandwidth.
3. **Strong metadata consistency** — after PUT succeeds, subsequent reads resolve the new committed version.

## BACK-OF-THE-ENVELOPE

Assume:

```text
10B objects × 1 MB average
≈ 10 PB logical data

3 replicas
≈ 30 PB physical data

Metadata: 10B × ~500 bytes
≈ 5 TB metadata

20K PUT/s × 1 MB average
≈ 20 GB/s incoming write bandwidth
```

### ARCHITECTURE IMPLICATIONS

* 10B objects means object metadata must be **partitioned by bucket/key**, not stored on one relational node.
* 20 GB/s means application servers cannot persist object payloads themselves; they must **stream through or directly into distributed storage nodes**.
* Multi-PB storage requires placement across many nodes/AZs with automated **repair and rebalancing**.

### WHAT I SAY

> “The key observation is that metadata is small but latency-sensitive, while object bytes dominate capacity and bandwidth. So I separate the metadata plane from the data plane and scale them independently.”

---

# 3. MINIMAL API + CORE STATE — ~2 MIN

## 3A. CORE APIs

| Method / Operation | Endpoint / Event                  | Purpose                                |
| ------------------ | --------------------------------- | -------------------------------------- |
| `PUT`              | `/buckets/{bucket}/objects/{key}` | Create or replace an object            |
| `GET`              | `/buckets/{bucket}/objects/{key}` | Stream object contents                 |
| `HEAD`             | `/buckets/{bucket}/objects/{key}` | Return object metadata without payload |
| `DELETE`           | `/buckets/{bucket}/objects/{key}` | Delete current object/version          |

---

## 3B. SAMPLE WRITE

```http
PUT /buckets/photos/objects/2026/cat.jpg
Content-Length: 4194304
Content-MD5: abc123...
Idempotency-Key: req-8721
```

Conceptual response:

```json
{
  "versionId": "v9812",
  "etag": "ab92ef...",
  "size": 4194304
}
```

The response is sent **only after the required durable replicas are written and metadata is committed**.

---

# 3C. CORE STATE MODEL

## OBJECT_METADATA

**Purpose:** Authoritative namespace record telling the system which committed version of a bucket/key clients should currently see.

* `bucketId + objectKey` — identifies the logical object and forms the lookup/routing key for GET, PUT, and DELETE.
* `currentVersionId` — points readers to the exact committed version currently visible.
* `size` — logical object size used for range validation and responses.
* `etag/checksum` — verifies object integrity and lets clients detect whether contents changed.
* `state` — distinguishes a visible object from a delete marker or other non-readable state.
* `version` — used in conditional metadata updates so concurrent overwrites cannot silently replace each other incorrectly.

---

## OBJECT_VERSION

**Purpose:** Immutable description of one successfully created version of an object.

* `versionId` — uniquely identifies this exact object contents, even if the same key is later overwritten.
* `objectKey` — associates the immutable version with its logical bucket/key.
* `chunkIds` — ordered references identifying the chunks that reconstruct this version.
* `size` — total bytes represented by the version.
* `checksum` — end-to-end integrity value validated after upload/download.

---

## CHUNK_PLACEMENT

**Purpose:** Authoritative mapping from a chunk to the physical replicas that currently contain its bytes.

* `chunkId` — stable identifier derived/assigned to one stored data chunk.
* `replicas` — storage node IDs currently expected to hold valid copies.
* `checksum` — expected content checksum used to detect corruption.
* `generation` — prevents stale repair/rebalance operations from overwriting newer placement information.
* `state` — tracks whether the chunk is healthy, under-replicated, or being repaired.

---

## UPLOAD_SESSION

**Purpose:** Tracks an unfinished upload until it can safely become visible as a committed object version.

* `uploadId` — identifies one upload attempt or multipart upload.
* `bucketId + objectKey` — identifies the object that will be published if the upload succeeds.
* `chunkIds` — records chunks successfully written so far.
* `state` — distinguishes WRITING, COMMITTING, COMMITTED, and ABORTED uploads.
* `expiresAt` — allows incomplete uploads and their orphaned chunks to eventually be reclaimed.
* `idempotencyKey` — returns the same result when a client retries after losing the PUT response instead of creating another logical write.

### AUTHORITATIVE

**Metadata Store** is authoritative for namespace, versions, upload state, and placement.

The **storage nodes are authoritative for the actual bytes**, with multiple durable copies.

### DERIVED

In-memory routing information such as healthy-node capacity/load can be stale; the final write is validated by actual storage-node acknowledgment and committed placement metadata.

---

# STATE MACHINE

```text
UPLOAD

INITIATED → WRITING → COMMITTING → COMMITTED
                |
                +----------------→ ABORTED / EXPIRED


OBJECT VERSION

not visible ──COMMIT──> ACTIVE ──DELETE/overwrite──> old version
```

### CRITICAL TRANSITION

**COMMITTING → COMMITTED** must happen only after the required replicas are durable; otherwise metadata could expose an object whose bytes do not safely exist.

A concurrent overwrite of the same `bucket + key` also uses a conditional metadata update so the visible version changes atomically.

### DRAW

Write only:

```text
WRITING → COMMITTED
           ^
   replicas durable first
```

### WHAT I SAY

> “Uploads are initially invisible. We first persist the bytes with sufficient redundancy, and only then atomically publish the new version through metadata; that commit point defines when the PUT has succeeded.”

---

# 4. MAIN ARCHITECTURE — ~7–8 MIN

## 4A. DRAWING ORDER

### Step 1 — Front Door

**DRAW:** Client → Object API `(1)`

**MUST SAY:** Object APIs terminate requests but don't own persistent object state.

**WHAT I SAY:**

> “I'll start with stateless Object API servers that authenticate, rate-limit, and stream PUT/GET requests.”

**LIKELY PROBE:** Aren't API servers a bandwidth bottleneck?

**FAST ANSWER:** Horizontally scale them; for very large multipart uploads we can later issue direct upload targets.

---

### Step 2 — Metadata Authority

**DRAW:** Object API → Metadata Service → Metadata Store `(2)`

**MUST SAY:** GET/PUT correctness is fundamentally determined by object metadata.

**WHAT I SAY:**

> “Metadata maps a bucket/key to an immutable version and its chunks. This store provides the strongly consistent namespace.”

**LIKELY PROBE:** Why not store bytes in this database?

**FAST ANSWER:** Object payload throughput and capacity are orders of magnitude larger than metadata and need an independently scalable data plane.

---

### Step 3 — Placement

**DRAW:** Metadata/Object API → Placement Service `(3)`

**MUST SAY:** Placement chooses storage nodes across independent failure domains.

**WHAT I SAY:**

> “For each new chunk, placement selects healthy nodes based on AZ, capacity, utilization, and failure-domain separation.”

**LIKELY PROBE:** Does placement require a database lookup for every server?

**FAST ANSWER:** No, it uses a derived in-memory cluster-capacity map and validates successful placement from storage acknowledgments.

---

### Step 4 — Store Bytes

**DRAW:** Object API → Storage Nodes `(4)` and replica writes `(5)`.

**MUST SAY:** The object is not committed until the configured durability threshold succeeds.

**WHAT I SAY:**

> “The API streams chunks to selected nodes, which checksum and durably persist them; replicas are deliberately spread across AZs.”

**LIKELY PROBE:** What happens if one node fails?

**FAST ANSWER:** The PUT can succeed once the required durability policy is met, and repair restores the missing redundancy asynchronously.

---

### Step 5 — Publish Object

**DRAW:** Storage acknowledgment → Metadata Service → Metadata Store `(6)` and Metadata → API → Client `(7)`.

**MUST SAY:** Metadata publication is the visibility boundary.

**WHAT I SAY:**

> “Only after the data is durable do I atomically publish the new version. From then on GET resolves to exactly that committed version.”

**LIKELY PROBE:** What if the response is lost?

**FAST ANSWER:** The client's idempotency key lets a retry discover and return the already committed version.

---

### Step 6 — Repair

**DRAW:** Repair/Rebalancer ↔ Metadata Store and Storage Nodes.

**MUST SAY:** Replica loss after commit must not become permanent durability degradation.

**WHAT I SAY:**

> “A background repair service continuously reacts to node failures, checksum errors, and under-replication and reconstructs replicas onto healthy nodes.”

**LIKELY PROBE:** Can repair race with deletion?

**FAST ANSWER:** Repair updates placement conditionally using the current chunk generation so stale repair work is rejected.

---

# 4B. FINAL WHITEBOARD

```text
                            METADATA PLANE
 +--------+   (1)   +-------------+   (2)   +----------------+
 | Client | ------> | Object API  | ------> | Metadata       |
 +---+----+         | stateless   |         | Service        |
     ^              +------+------+         +-------+--------+
     |                     |                        |
     |                    (3)                       | (6) commit
     |                     v                        v
     |              +-------------+       +--------------------+
     |              | Placement   |       | Metadata Store     |
     |              | Service     |       | AUTHORITATIVE      |
     |              | derived map |       | namespace/version  |
     |              +------+------+       | placement          |
     |                     |              +----------+---------+
     |                     |
     |                     | choose nodes across AZs
     |                     v
     |                 DATA PLANE
     |
     |              (4) stream chunks
     |       +--------------------------------+
     |       v                                |
     |  +------------+    (5) replicate   +------------+
     |  | Storage    | <----------------> | Storage    |
     |  | Nodes AZ-A |                    | Nodes B/C  |
     |  | checksum   |                    | checksum   |
     |  +------+-----+                    +------+-----+
     |         ^                                 ^
     |         |                                 |
     |         +-----------+   +-----------------+
     |                     |   |
     |               +-----+---+------+
     |               | Repair /       |
     |               | Rebalancer     |
     |               +-------+--------+
     |                       |
     |             metadata / health
     |                       |
     +-----------------------+
             (7) PUT success


 Correctness:
 • publish metadata only after durable write threshold
 • replicas span failure domains
 • checksum + generation-aware repair

 GET path:
 Client → Object API → Metadata → nearest/healthy replica → Client
```

---

# 4C. HAPPY-PATH WALKTHROUGH

| # | What Happens                                                                              |
| - | ----------------------------------------------------------------------------------------- |
| 1 | Client sends PUT for `bucket + objectKey` to a stateless Object API server.               |
| 2 | API creates/resolves the upload and existing key state through the metadata service.      |
| 3 | Placement selects storage nodes across independent AZ/failure domains.                    |
| 4 | Object API streams chunks to selected storage nodes.                                      |
| 5 | Chunks are checksummed and durably replicated; storage nodes acknowledge persistence.     |
| 6 | Metadata service atomically publishes the new immutable object version and its placement. |
| 7 | API returns the committed version/ETag to the client.                                     |

### WHAT I SAY

> “The main PUT path is steps 1 through 7. Metadata first establishes the upload, placement chooses independent failure domains, and the bytes are streamed and durably replicated. The critical point is step 6: I don't make the version visible until the storage durability requirement has succeeded. Once metadata commits, GET can resolve the immutable version and stream from any healthy replica.”

---

# 4D. COMPONENT → TECHNOLOGY MAP

| Component         | Responsibility                                   | Concrete Technology                            | Why                                                                                     |
| ----------------- | ------------------------------------------------ | ---------------------------------------------- | --------------------------------------------------------------------------------------- |
| Object API        | Stateless PUT/GET/DELETE frontend and streaming  | Java service on ECS/EKS                        | Easy horizontal scaling while keeping storage state elsewhere.                          |
| Metadata Service  | Namespace/version consistency                    | Java service                                   | Centralizes object lifecycle rules and conditional metadata transitions.                |
| Metadata Store    | Strongly consistent metadata authority           | DynamoDB                                       | Partitioned KV access and conditional updates fit bucket/key metadata at massive scale. |
| Placement Service | Select healthy failure-separated storage targets | Custom in-memory placement service             | Fast scoring over node capacity, AZ, and utilization information.                       |
| Storage Nodes     | Persist object chunks                            | Custom blob servers using local/attached disks | Optimized for sequential high-bandwidth object IO rather than metadata queries.         |
| Repair/Rebalancer | Restore redundancy and redistribute data         | Distributed background workers                 | Parallel reconstruction naturally scales with storage fleet size.                       |

---

# 5. TOP 2 CORRECTNESS + FAILURE HARD PARTS

## Hard Part 1 — Never expose an incompletely stored object

**INVARIANT:** A committed object version must always have the required durable redundancy.

**RACE / FAILURE:** Storage nodes can fail midway through PUT while metadata publication is occurring.

**SOLUTION:**

* Write and checksum chunks first.
* Wait for the required durable acknowledgments.
* Atomically publish only the successfully stored version.

**CONSISTENCY:** Strong consistency is required for the metadata commit that changes the currently visible version.

**RETRY / IDEMPOTENCY:** A retried PUT with the same idempotency key resolves to the already-created upload/version.

**RECOVERY:** Uncommitted chunks from failed uploads are reclaimed after the upload expires.

**WHAT I SAY**

> “The metadata commit is my visibility boundary. Bytes may exist before metadata, but metadata must never point to bytes that haven't satisfied the durability requirement.”

---

## Hard Part 2 — Replica loss and silent corruption

**INVARIANT:** Every committed chunk must retain enough healthy copies to satisfy its durability policy.

**RACE / FAILURE:** Disks, servers, racks, or AZ connectivity can fail, and stored bytes can also become corrupt.

**SOLUTION:**

* Store checksums with every chunk.
* Detect node loss/under-replication and choose new repair destinations.
* Repair from healthy replicas and conditionally update placement generation.

**CONSISTENCY:** Replica repair can be asynchronous; publishing the repaired placement requires a conditional write against the current generation.

**RETRY / IDEMPOTENCY:** Re-copying the same immutable chunk is safe.

**RECOVERY:** Repair continues until the desired replication factor is restored.

### DELIVERY / SIDE-EFFECT SEMANTICS

* Physical chunk writes can happen **at least once**.
* Chunk IDs/checksums make duplicate physical writes safe.
* Logical object visibility is **effectively once** because only one committed metadata version is exposed.

---

# 6. SCALE + OVERLOAD — ~2 MIN

## Bottleneck 1 — Metadata QPS

**PROBLEM:** Billions of keys plus high GET/PUT rates can hotspot metadata partitions.

**SCALE STRATEGY:**

* Partition by hashed `bucketId + objectKey`.
* Horizontally distribute metadata storage/services.

**PARTITIONING:** Hash the complete object identity rather than bucket alone so one huge bucket does not create one hot partition.

**TRADE-OFF:** Hash partitioning makes lexicographical LIST more expensive than simple range partitioning.

---

## Bottleneck 2 — Storage bandwidth

**PROBLEM:** Large uploads/downloads can consume enormous network and disk bandwidth.

**SCALE STRATEGY:**

* Add storage/API nodes horizontally.
* Stream/chunk large objects rather than buffering whole objects.

**PARTITIONING:** Placement spreads objects across thousands of storage servers.

**TRADE-OFF:** More distributed placement increases repair and metadata complexity.

---

## Bottleneck 3 — Repair storms

**PROBLEM:** A rack/AZ failure may suddenly make enormous quantities of data under-replicated.

**SCALE STRATEGY:**

* Rate-limit repair.
* Prioritize chunks closest to losing durability.

**PARTITIONING:** Repair ownership is divided by chunk ranges/node groups.

**TRADE-OFF:** Aggressive repair improves durability faster but competes with foreground traffic.

---

## BACKPRESSURE

Overload first appears at API/network or storage-node capacity.

* Bound concurrent uploads per API/storage node.
* Rate-limit per bucket/account.
* Reject or throttle PUTs before saturating storage.
* Prioritize foreground GET/PUT traffic over low-priority rebalancing.
* Use per-tenant quotas so one customer cannot consume all ingress bandwidth.

### WHAT I SAY

> “Metadata scales by hashing object keys, while data throughput scales by adding independent storage nodes. Admission control protects foreground traffic during bursts, and background repair/rebalancing is explicitly throttled so it can't cause a cascading overload.”

---

# 7. TWO KEY DESIGN TRADE-OFFS

## Replication vs Erasure Coding

**CHOSEN:** Start with 3-way replication.

**WHY:** Simple reads/writes, straightforward repair, and fast recovery make the core interview design easier and lower-latency.

**ALTERNATIVE:** Erasure coding.

**WHEN ALTERNATIVE WINS:** At multi-PB scale, especially for colder data where storage efficiency outweighs additional CPU/network/reconstruction cost.

---

## Central Metadata vs Metadata Embedded in Storage Nodes

**CHOSEN:** Separate strongly consistent metadata plane.

**WHY:** Namespace operations, overwrite consistency, versions, and placement lookup remain small and independently scalable from object bytes.

**ALTERNATIVE:** Derive metadata by querying storage nodes.

**WHEN ALTERNATIVE WINS:** Only for much simpler content-addressed blob storage where user-visible namespace semantics are unnecessary.

---

# 8. FAST PROBES — ONE-LINE LOOKUP

**Q1. What happens if PUT succeeds internally but the client never receives the response?**
→ Retry with the same idempotency key returns the already committed version instead of creating another logical write.

**Q2. What happens if one replica fails immediately after PUT?**
→ The object remains readable from surviving replicas while repair asynchronously restores redundancy.

**Q3. How do you prevent a stale repair worker from restoring a deleted/obsolete chunk?**
→ Its conditional placement update must match the current chunk generation/version.

**Q4. How do you support huge objects such as 100 GB?**
→ Multipart upload writes independently validated chunks and atomically publishes the completed manifest.

**Q5. Would you really keep 3 copies of every object at S3 scale?**
→ Not necessarily; hot data can use replication while colder data migrates to erasure coding for much better storage efficiency.

---

# 9. FINAL 30–45 SECOND SUMMARY

> “The main path is essentially steps 1 through 7 on the board. I separate the strongly consistent metadata plane from the high-bandwidth data plane: metadata resolves bucket/key to an immutable version, placement selects storage nodes across failure domains, and the API streams chunks to those nodes. The critical correctness rule is that I publish a version only after its required durable copies have succeeded, so metadata never exposes incomplete data. Metadata scales through key-based partitioning and the data plane scales by adding storage nodes. Checksums detect corruption, and an asynchronous repair/rebalancing service restores redundancy after node or disk failures.”

---

# IF I AM RUNNING OUT OF TIME

### NEVER SKIP

1. Metadata vs data-plane separation.
2. **Store replicas first → publish metadata second.**
3. Replication/checksum/repair story.

### SKIP FIRST

1. Multipart details.
2. Erasure coding.
3. Lifecycle/cross-region replication.
