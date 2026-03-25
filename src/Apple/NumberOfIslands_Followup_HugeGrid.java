package Apple;

import java.util.HashMap;
import java.util.Map;

public class NumberOfIslands_Followup_HugeGrid {


	public static void main(String[] args) {
        Solution sol = new Solution();

        // Regular mixed grid, multiple islands, some cross chunk boundary
        char[][] grid1 = {
                {'1','1','0','0'},
                {'1','0','0','1'},
                {'0','0','1','1'},
                {'0','0','0','0'}
        }; // expected: 2

        // All water
        char[][] grid2 = {
                {'0','0','0'},
                {'0','0','0'}
        }; // expected: 0

        // All land, one big island
        char[][] grid3 = {
                {'1','1','1'},
                {'1','1','1'},
                {'1','1','1'}
        }; // expected: 1

        // Empty grid
        char[][] grid4 = new char[0][0]; // expected: 0

        // Single row
        char[][] grid5 = {
                {'1','0','1','1'}
        }; // expected: 2

        // Tall vertical island crossing chunk boundaries
        char[][] grid6 = {
                {'0','1','0','0'},
                {'0','1','0','0'},
                {'0','1','0','0'},
                {'0','1','0','0'}
        }; // expected: 1

        System.out.println("grid1 -> " + sol.numIslandsHuge(grid1) + " (expected 2)");
        System.out.println("grid2 -> " + sol.numIslandsHuge(grid2) + " (expected 0)");
        System.out.println("grid3 -> " + sol.numIslandsHuge(grid3) + " (expected 1)");
        System.out.println("grid4 -> " + sol.numIslandsHuge(grid4) + " (expected 0)");
        System.out.println("grid5 -> " + sol.numIslandsHuge(grid5) + " (expected 2)");
        System.out.println("grid6 -> " + sol.numIslandsHuge(grid6) + " (expected 1)");
    }
	
	/* Follow-up: Handle extremely large grid by processing smaller chunks and merging borders.
	If they ask the follow-up again in the interview:
	
	- “If the grid is too large to fit in memory, I’d:
	  - Stream it in **horizontal strips** that fit in memory.
	  - For each strip, run the same DFS/BFS locally and assign island IDs.
	  - For islands touching the **bottom** of one strip and the **top** of the next, use a **Union-Find** to merge IDs (they’re actually one island).
	  - Total islands = sum of local counts – number of merges.
	  - Time stays `O(m * n)`, memory `O(width_of_strip)` plus small border metadata.”
	  
	  ### Mental model to carry into the interview

	- Within each chunk, **normal island-count DFS/BFS** + assign island IDs.  
	- Across chunks, look only at the **shared border**:
	  - Same column `c`, previous chunk’s bottom row is land with island `A`.
	  - Current chunk’s top row is land with island `B`.
	  - They are vertically adjacent → `A` and `B` are same real 
	  	island → `union(A, B)` and `totalIslands--`.
	- If no such matching `(column, land)` pairs exist between chunks,
	 	**no union** is performed and the total is just the sum of all local chunk island counts. 

	 * */
	
	/*
	 Time complexity: O(m * n)
		Let the full grid be m x n.
		Each cell is read and DFS-visited at most once across all chunks.
		Union operations are at most O(m * n) and are almost O(1) amortized.
		Overall time: O(m * n).

	  Space complexity: O(R * n + K)
		For the **chunked follow-up solution**, space looks like this:

		- Let the full grid be `m x n`.
		- Let each chunk have `R` rows (in code: `CHUNK_ROWS`).
		
		What we store at any moment:
		
		1. **Current chunk in memory**: `R x n`  
		   → `O(R * n)`
		
		2. **Border maps (top + bottom)**: at most one entry per column  
		   → `O(n)`
		
		3. **Union-Find parent map**: one entry per island ID ever created.  
		   Let `K` be the number of chunk-local islands (IDs) over the whole grid.  
		   → `O(K)` space.  
		   In the absolute worst case, `K` can be `O(m * n)` (every land cell isolated),  
		   so worst-case extra space is `O(m * n)`.
		
		So, formally:
		
		- **Overall space complexity**: `O(R * n + K)`  
		  - Worst case: `O(m * n)`  
		- But **peak grid storage at any time** is only `O(R * n)` (a strip of rows), 
			not the whole `m * n` grid, which is the *practical* benefit of this approach 
			for huge grids.
	 * */
	
	
	// Follow-up: Chunk-based solution for extremely large grids.

	private static class Solution {

	    // For demo; in a real system choose this based on memory limits
	    private static final int CHUNK_ROWS = 2;

	    // Public API that simulates "huge grid" processing using chunking
	    public int numIslandsHuge(char[][] grid) {
	        if (grid == null || grid.length == 0 || grid[0].length == 0) return 0;
	        GridChunkReader reader = new ArrayGridChunkReader(grid, CHUNK_ROWS);
	        BigGridIslands engine = new BigGridIslands();
	        return engine.numIslandsHuge(reader);
	    }

	    // Follow-up: abstraction to read the grid in small chunks (bands of rows)
	    interface GridChunkReader {
	        char[][] nextChunk();  // returns next sub-grid, or null when done
	    }

	    // Follow-up: simple in-memory implementation of GridChunkReader for practice
	    static class ArrayGridChunkReader implements GridChunkReader {
	        private final char[][] grid;
	        private final int chunkRows;
	        private int currentRow = 0;

	        ArrayGridChunkReader(char[][] grid, int chunkRows) {
	            this.grid = grid;
	            this.chunkRows = chunkRows;
	        }

	        @Override
	        public char[][] nextChunk() {
	            if (currentRow >= grid.length) return null;

	            int endRow = Math.min(currentRow + chunkRows, grid.length);
	            int rows = endRow - currentRow;
	            int cols = grid[0].length;

	            char[][] chunk = new char[rows][cols];
	            for (int r = 0; r < rows; r++) {
	                System.arraycopy(grid[currentRow + r], 0, chunk[r], 0, cols);
	            }

	            currentRow = endRow;
	            return chunk;
	        }
	    }

	    // Follow-up: core engine that counts islands across chunks using Union-Find
	    static class BigGridIslands {
	        private final UnionFind uf = new UnionFind();
	        private int nextIslandId = 0;  // global island ID counter

	        public int numIslandsHuge(GridChunkReader reader) {
	            int totalIslands = 0;
	            Map<Integer, Integer> prevBottomBorder = new HashMap<>();

	            char[][] chunk;
	            while ((chunk = reader.nextChunk()) != null) {
	                ChunkResult res = processChunk(chunk);

	                // add islands discovered in this chunk
	                totalIslands += res.localIslandCount;

	                // merge islands that touch across the boundary between chunks
	                for (Map.Entry<Integer, Integer> entry : res.topBorder.entrySet()) {
	                    int col = entry.getKey();
	                    int currId = entry.getValue();
	                    Integer prevId = prevBottomBorder.get(col);
	                    if (prevId != null) {
	                        // If union succeeds, we discovered that two IDs are same island
	                        if (uf.union(prevId, currId)) {
	                            totalIslands--;
	                        }
	                    }
	                }

	                prevBottomBorder = res.bottomBorder;
	            }

	            return totalIslands;
	        }

	        // Follow-up: process a single chunk fully in-memory (use your usual DFS/BFS inside here).
	        // 1) Run normal DFS/BFS on this small grid.
	        // 2) Assign a local island id (e.g., 0,1,2,...) to each discovered island.
	        // 3) For cells on the first and last row of the chunk:
	        //      - record col -> globalIslandId in topBorder / bottomBorder maps.
	        // 4) Return localIslandCount and these two maps.
	        private ChunkResult processChunk(char[][] chunk) {
	            int rows = chunk.length;
	            int cols = chunk[0].length;

	            ChunkResult res = new ChunkResult();

	            for (int r = 0; r < rows; r++) {
	                for (int c = 0; c < cols; c++) {
	                    if (chunk[r][c] == '1') {
	                        int islandId = nextIslandId++;
	                        res.localIslandCount++;
	                        dfsChunk(chunk, r, c, rows, cols, islandId,
	                                 res.topBorder, res.bottomBorder);
	                    }
	                }
	            }

	            return res;
	        }

	        // Follow-up: DFS inside a chunk, recording which island touches which borders
	        private void dfsChunk(char[][] chunk,
	                              int r,
	                              int c,
	                              int rows,
	                              int cols,
	                              int islandId,
	                              Map<Integer, Integer> topBorder,
	                              Map<Integer, Integer> bottomBorder) {
	            if (r < 0 || r >= rows || c < 0 || c >= cols || chunk[r][c] != '1') {
	                return;
	            }

	            // mark visited
	            chunk[r][c] = '0';

	            // record if cell is on top or bottom row of this chunk
	            if (r == 0) {
	                topBorder.put(c, islandId);
	            }
	            if (r == rows - 1) {
	                bottomBorder.put(c, islandId);
	            }

	            dfsChunk(chunk, r + 1, c, rows, cols, islandId, topBorder, bottomBorder);
	            dfsChunk(chunk, r - 1, c, rows, cols, islandId, topBorder, bottomBorder);
	            dfsChunk(chunk, r, c + 1, rows, cols, islandId, topBorder, bottomBorder);
	            dfsChunk(chunk, r, c - 1, rows, cols, islandId, topBorder, bottomBorder);
	        }

	        // Follow-up: result for a single chunk
	        static class ChunkResult {
	            int localIslandCount = 0;
	            Map<Integer, Integer> topBorder = new HashMap<>();
	            Map<Integer, Integer> bottomBorder = new HashMap<>();
	        }

	        // Follow-up: Union-Find to merge island IDs across chunks
	        static class UnionFind {
	            private final Map<Integer, Integer> parent = new HashMap<>();

	            int find(int x) {
	                if (!parent.containsKey(x)) {
	                    parent.put(x, x);
	                }
	                if (parent.get(x) != x) {
	                    parent.put(x, find(parent.get(x)));
	                }
	                return parent.get(x);
	            }

	            boolean union(int a, int b) {
	                int pa = find(a);
	                int pb = find(b);
	                if (pa == pb) return false;
	                parent.put(pa, pb);
	                return true;
	            }
	        }
	    }
	}
    

}

/*
 I’ll walk through **two concrete examples** using the “process in chunks + union/merge borders” idea:

1. One where **no merge is needed**.  
2. One where **a merge is needed** (same island spans two chunks).

Assume we split the grid **horizontally** into bands of 2 rows each (because only 2 rows fit in memory at a time).

For each chunk we do:

- Run normal DFS/BFS to find islands in that small chunk.  
- Assign each found island a **(global) island ID**: 1, 2, 3, …  
- For the **top row** of this chunk: record `col → islandID` in `topBorder`.  
- For the **bottom row**: record `col → islandID` in `bottomBorder`.  
- Keep a running `totalIslands` (sum of all new island IDs created so far).  
- When processing the next chunk, compare:
  - previous chunk’s `bottomBorder`
  - current chunk’s `topBorder`  
  If both have land at the same column, those island IDs are **merged** via Union-Find and `totalIslands--` (because we double-counted one island).

---

## Example 1 – no merge needed

Grid (4 rows × 4 cols), `1 = land`, `0 = water`:

Row indices on the left:

```
r0: 1 0 0 0
r1: 0 0 0 0
r2: 0 1 0 0
r3: 0 1 0 0
```

We split into 2 chunks:

- **Chunk 1:** rows `r0–r1`
- **Chunk 2:** rows `r2–r3`

### Chunk 1 (rows 0–1)

Sub-grid:

```
r0: 1 0 0 0
r1: 0 0 0 0
```

Step-by-step:

1. Start `totalIslands = 0`.
2. Run DFS/BFS inside this 2×4 chunk:
   - At `(0,0)` we see `1` → new island with ID **1**.
   - Its neighbors: `(1,0)`, `(0,1)` etc. are water or out of land → island is just the single cell `(0,0)`.
   - After exploring, `localIslandCount = 1`.  
3. Record **chunk borders**:
   - `topBorder` (row 0):
     - Column 0 has land → `topBorder[0] = 1`.
   - `bottomBorder` (row 1):
     - All zeros → `bottomBorder` is **empty**.
4. Update global info:
   - `totalIslands += localIslandCount = 0 + 1 = 1`.
   - `prevBottomBorder = bottomBorder` of this chunk = `{}` (empty).

So after Chunk 1:

- `totalIslands = 1`
- `prevBottomBorder = {}` (no land touching the bottom of the chunk)

### Chunk 2 (rows 2–3)

Sub-grid:

```
r2: 0 1 0 0
r3: 0 1 0 0
```

Step-by-step:

1. Start this chunk with `localIslandCount = 0`.
2. Run DFS/BFS:
   - At `(2,1)` we see `1` → new island with ID **2** (we continue global ID numbering).
   - DFS from `(2,1)` discovers `(3,1)` is also `1`. So island with ID 2 is actually `{(2,1), (3,1)}`.
   - No other land → `localIslandCount = 1`.
3. Record borders:
   - `topBorder` (row 2):
     - `col 1` has land → `topBorder[1] = 2`.
   - `bottomBorder` (row 3):
     - `col 1` has land → `bottomBorder[1] = 2`.
4. Merge with **previous chunk**:
   - `prevBottomBorder` from chunk 1 is `{}` → there is **no column** where both chunks have land in `bottom(previous)` and `top(current)`.
   - So we perform **no unions**, `totalIslands` unchanged.
5. Update global info:
   - `totalIslands += localIslandCount = 1 + 1 = 2`.
   - `prevBottomBorder = current bottomBorder = { 1 → 2 }`.

Final result:

- `totalIslands = 2`  
- Which matches the actual islands:
  - Island A: `(0,0)`  
  - Island B: `(2,1),(3,1)`  
- And notice: they are **not connected vertically** across the chunk boundary (row 1 vs row 2), so no merge was needed.

---

## Example 2 – merge/union is needed

Now a grid where one island spans both chunks:

```
r0: 0 1 0 0
r1: 0 1 0 0
r2: 0 1 0 0
r3: 0 1 0 0
```

This is visually one vertical island in column 1.

Again:

- **Chunk 1:** rows `r0–r1`
- **Chunk 2:** rows `r2–r3`

### Chunk 1 (rows 0–1)

Sub-grid:

```
r0: 0 1 0 0
r1: 0 1 0 0
```

Step-by-step:

1. `totalIslands` starts at 0.
2. Run DFS/BFS in this chunk:
   - First land cell we hit is `(0,1)` → new island, ID **1**.
   - DFS explores `(1,1)` which is also land; after that, all neighbors are water or already visited.
   - So island with ID 1 covers `{(0,1), (1,1)}`.
   - `localIslandCount = 1`.
3. Record borders:
   - `topBorder` (row 0):
     - `col 1` is land → `topBorder[1] = 1`.
   - `bottomBorder` (row 1):
     - `col 1` is land → `bottomBorder[1] = 1`.
4. Update global info:
   - `totalIslands += localIslandCount = 0 + 1 = 1`.
   - `prevBottomBorder = { 1 → 1 }`.

So after Chunk 1:

- We *think* we have 1 island so far.
- There is land at column 1 touching the bottom border.

### Chunk 2 (rows 2–3)

Sub-grid:

```
r2: 0 1 0 0
r3: 0 1 0 0
```

Step-by-step:

1. Start this chunk: `localIslandCount = 0`.
2. Run DFS/BFS:
   - First land we see is `(2,1)` → new island, ID **2** (global counter).
   - DFS explores `(3,1)` as well; together they form island `{(2,1), (3,1)}`.
   - `localIslandCount = 1`.
3. Record borders:
   - `topBorder` (row 2):
     - `col 1` is land → `topBorder[1] = 2`.
   - `bottomBorder` (row 3):
     - `col 1` is land → `bottomBorder[1] = 2`.
4. **Merge with previous chunk**:
   - We now compare:
     - `prevBottomBorder = { 1 → 1 }`
     - `currTopBorder = { 1 → 2 }`
   - Look at column 1:
     - Previous chunk bottom has island ID 1 at column 1.
     - Current chunk top has island ID 2 at column 1.
     - Since these two land cells are vertical neighbors (row 1 and row 2, same column),
       they must be part of the **same global island**.
   - So we call `union(1, 2)` in Union-Find:
     - If `1` and `2` were separate sets, union returns “merged”.
     - We then **decrement `totalIslands` by 1** because we had counted them separately.
5. Update global info:
   - First we add local islands:  
     `totalIslands += localIslandCount = 1 + 1 = 2`  
     (temporarily we think islands with ID 1 and 2 are distinct.)
   - After union(1,2) succeeds, we do:  
     `totalIslands--` → `2 - 1 = 1`.
   - `prevBottomBorder = current bottomBorder = { 1 → 2 }` (or its representative after union; logically it’s the same island).

Final result:

- `totalIslands = 1`
- That correctly reflects that the entire column of `1`s (rows 0–3, col 1) is **one single island**, even though we processed it as two separate chunks.

---

### Mental model to carry into the interview

- Within each chunk, **normal island-count DFS/BFS** + assign island IDs.  
- Across chunks, look only at the **shared border**:
  - Same column `c`, previous chunk’s bottom row is land with island `A`.
  - Current chunk’s top row is land with island `B`.
  - They are vertically adjacent → `A` and `B` are same real island → `union(A, B)` and `totalIslands--`.
- If no such matching `(column, land)` pairs exist between chunks, **no union** is performed and the total is just the sum of all local chunk island counts. 
 * */
