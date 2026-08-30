package Google;

public class SmallestRectangleEnclosingBlackPixels_302_Hard {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public int minArea(char[][] image, int x, int y) {
        return minArea_BinarySearch(image, x, y); // working
        //return minArea_DFS(image, x, y);  // working
        
        //char[][][] image_3D = new char[][][]
        //return minVolume_BinarySearch_3D(image_3D, x, y, z); // working
        
    }

	/*
	 * Interview Explanation Before Coding
	 * 
	 * “I’ll use the given black pixel as a point inside the rectangle and binary
	 * search for its four boundaries.
	 * 
	 * Because all black pixels are connected, every row between the topmost and
	 * bottommost black pixel must contain at least one black pixel. The same
	 * applies to columns.
	 * 
	 * So for the top boundary, I binary search between row 0 and x for the first
	 * row containing black. For the bottom, I search after x for the first row that
	 * no longer contains black.
	 * 
	 * I do the same thing for the left and right columns.
	 * 
	 * Checking whether a row contains black takes O(n), and checking a column takes
	 * O(m).
	 * 
	 * Once I have the four boundaries, the answer is simply (bottom - top) * (right
	 * - left).”
	 */
	
	/* Step-by-Step Algorithm: 
		1. Binary search rows [0, x] for the first row containing black → top.
		2. Binary search rows [x + 1, m] for the first row not containing black → bottom.
		3. Binary search columns [0, y] for the first column containing black → left.
		4. Binary search columns [y + 1, n] for the first column not containing black → right.
		5. Return: (bottom - top) * (right - left)
	 * */
	
	/*
	 Let: m = number of rows,  n = number of columns
		Time Complexity : O(n log m + m log n)

		Why?
		Binary searching rows takes O(log m) iterations.
		Each row check scans up to n columns.
		We search two row boundaries.
		
		So: O(n log m)
		Similarly, searching columns costs: O(m log n)
		
		Total: O(n log m + m log n)
		
		Space Complexity:  O(1)
	 * */
	
	public static int minArea_BinarySearch(char[][] image, int x, int y) {
        int m = image.length;
        int n = image[0].length;
        
        // Find first row containing a black pixel.
        // `true` here for `boolean findBlack`, dictates 
        // what we are searching for true for black(1) or false for white (0)
        int top = searchRows(image, 0, x, true); 
        
        // Find first row after the black region.
        int bottom = searchRows(image, x+1, m, false);
        
        // Find first column containing a black pixel.
        int left  = searchCols(image, 0, y, true);
        
        // Find first column after the black region.
        int right = searchCols(image, y+1, n, false); 
        
        return (bottom - top) * (right - left);
    }

	private static int searchRows(char[][] image, int low, int high, boolean findBlack) {
		
		while (low < high) {
			int mid = low + (high - low) / 2;
			boolean hasBlack = false;
			
			// Check whether this row contains a black pixel.
			for(int col=0; col<image[0].length; col++) {
				if(image[mid][col] == '1') {
					hasBlack = true;
					break;
				}
			}
			
			// Looking for the first position matching findBlack.
			if(hasBlack == findBlack) {
				high = mid;
			} else {
				low = mid + 1;
			}
		}
		
		return low;
	}
	
	private static int searchCols(char[][] image, int low, int high, boolean findBlack) {
		while (low < high) {
			int mid = low + (high - low) / 2;
			boolean hasBlack = false;
			
			// Check whether this column contains a black pixel.
			for(int row=0; row<image.length; row++) {
				if(image[row][mid] == '1') {
					hasBlack = true;
					break;
				}
			}
			
			// Looking for the first position matching findBlack.
			if(hasBlack == findBlack) {
				high = mid;
			} else {
				low = mid + 1;
			}
		}
		
		return low;
	}
	
	
	/** Follow-up: 🔴 Very High: What if the image is a 3D grid? */
	/* Interview script
		“The same idea generalizes naturally. In 2D we're finding two boundaries 
		along each of two dimensions. In 3D I'd find two boundaries along each of 
		three dimensions, giving six total boundaries. Connectivity gives us contiguous 
		projections along each axis, so we can binary search each one.”
	*/
		
	/*	Time complexity: 
			Let the dimensions be: X × Y × Z
			For an X binary search: O(log X) searches
			Each search scans a Y × Z plane 
			=> O(YZ log X)
			
			Similarly:
			Y boundaries → O(XZ log Y)
			Z boundaries → O(XY log Z)
			
			Overall:
			O(YZ log X + XZ log Y + XY log Z)
			
			For an n × n × n cube: O(n² log n)
			
		Space: O(1)	 
	* */
	
	/* Exactly same idea as 2D just extension of that.
	   We are calling the same binary search, total 3 for each axis X, Y, X, 
	   but during binary search we have 2 nested loops to scan each plane (say Y-Z).
	   
	   While searching/scanning inside binary search we follow below approach.
	   	search X → fix X, scan Y × Z
		search Y → fix Y, scan X × Z
		search Z → fix Z, scan X × Y
	*/
	public static int minVolume_BinarySearch_3D( 
	        char[][][] grid, int x, int y, int z) { // 3d grid

	    int xSize = grid.length; 
	    int ySize = grid[0].length; 
	    int zSize = grid[0][0].length; // 3d grid

	    // Find first X-plane containing a black voxel. 
	    int xMin = searchX(grid, 0, x, true); 

	    // Find first X-plane after the black region. 
	    int xMax = searchX(grid, x + 1, xSize, false); 

	    // Find first Y-plane containing a black voxel. 
	    int yMin = searchY(grid, 0, y, true); 

	    // Find first Y-plane after the black region. 
	    int yMax = searchY(grid, y + 1, ySize, false); 

	    // Find first Z-plane containing a black voxel. // 3d grid
	    int zMin = searchZ(grid, 0, z, true); // 3d grid

	    // Find first Z-plane after the black region. // 3d grid
	    int zMax = searchZ(grid, z + 1, zSize, false); // 3d grid

	    // Width * height * depth. // 3d grid
	    return (xMax - xMin) *
	           (yMax - yMin) *
	           (zMax - zMin); // 3d grid
	}

	// Search along X dimension. // 3d grid
	private static int searchX(
	        char[][][] grid, int low, int high, boolean findBlack) { // 3d grid

	    while (low < high) {
	        int mid = low + (high - low) / 2;
	        boolean hasBlack = false;

	        // Fix X = mid and scan the entire Y-Z plane. // 3d grid
	        for (int y = 0; y < grid[0].length && !hasBlack; y++) { // 3d grid
	            for (int z = 0; z < grid[0][0].length; z++) { // 3d grid
	            	//search X → fix X, scan Y × Z
	                if (grid[mid][y][z] == '1') { // 3d grid
	                    hasBlack = true;
	                    break;
	                }
	            }
	        }

	        // Same binary-search boundary logic.
	        if (hasBlack == findBlack) {
	            high = mid;
	        } else {
	            low = mid + 1;
	        }
	    }

	    return low;
	}
	
	// Search along Y dimension. // 3d grid
	private static int searchY(
	        char[][][] grid, int low, int high, boolean findBlack) { // 3d grid

	    while (low < high) {

	        int mid = low + (high - low) / 2;
	        boolean hasBlack = false;

	        // Fix Y = mid and scan the entire X-Z plane. // 3d grid
	        for (int x = 0; x < grid.length && !hasBlack; x++) { // 3d grid
	            for (int z = 0; z < grid[0][0].length; z++) { // 3d grid
	        		// search Y → fix Y, scan X × Z
	                if (grid[x][mid][z] == '1') { // 3d grid
	                    hasBlack = true;
	                    break;
	                }
	            }
	        }

	        if (hasBlack == findBlack) {
	            high = mid;
	        } else {
	            low = mid + 1;
	        }
	    }

	    return low;
	}

	// Search along Z dimension. // 3d grid
	private static int searchZ(
	        char[][][] grid, int low, int high, boolean findBlack) { // 3d grid

	    while (low < high) {
	        int mid = low + (high - low) / 2;
	        boolean hasBlack = false;

	        // Fix Z = mid and scan the entire X-Y plane. // 3d grid
	        for (int x = 0; x < grid.length && !hasBlack; x++) { // 3d grid
	            for (int y = 0; y < grid[0].length; y++) { // 3d grid
	            	// search Z → fix Z, scan X × Y
	                if (grid[x][y][mid] == '1') { // 3d grid
	                    hasBlack = true;
	                    break;
	                }
	            }
	        }

	        if (hasBlack == findBlack) {
	            high = mid;
	        } else {
	            low = mid + 1;
	        }
	    }
	    return low;
	}
	
	
	
	/** Approach 2: DFS or BFS for 2d grid/array */
    // Time: O(E) = O(B) = O(m*n) in worst case (especially when all pixels are black)
    // Here E is the number of edges in the traversed graph. B is the total number of black pixels. Since each pixel have four edges at most, O(E)=O(B). In the worst case, O(B)=O(mn).
    
    // Space: O(V)=O(B)= O(m*n).
    // The space complexity is O(V) where V is the number of vertices in the 
    // traversed graph. In this problem O(V)=O(B). 
    // Again, in the worst case, O(B)=O(mn).
    private static int top, bottom, left, right;
    public static int minArea_DFS(char[][] image, int x, int y) {
        if(image.length == 0 || image[0].length == 0) return 0;

        top = bottom = x;
        left = right = y;
        
        dfs(image, x, y);

        return (bottom - top) * (right - left);
    }

    private static void dfs(char[][] image, int x, int y){
        if(x < 0 || x >= image.length || 
           y < 0 || y >= image[0].length || 
           image[x][y] == '0') {
            return;
        }
        image[x][y] = '0'; // mark visited black pixel as white
        
        // Expand the rectangle boundary using this black pixel.
        top = Math.min(top, x);
        bottom = Math.max(bottom, x+1);
        left = Math.min(left, y);
        right = Math.max(right, y+1);

        // Visit all connected black neighbors.
        dfs(image, x+1, y); 
        dfs(image, x-1, y);
        dfs(image, x, y-1); 
        dfs(image, x, y+1);
    }
	
	
	
	
	//--------------------------------------------------------------------------------------------
	
	/** Possible Follow-ups */
	
	// 1. 🔴 Extremely High: What if black pixels are NOT connected?
	// This is the first one expected because it directly attacks the key 
	// assumption behind our binary search.
	
	/* Interview answer
		“The current binary-search solution relies on connectivity. Connectivity 
		guarantees that the rows containing black pixels form one continuous interval, 
		and the same is true for columns. If black pixels can be disconnected, that 
		monotonic property disappears, so I can't safely binary search the boundaries. 
		
		In that case, if I need a rectangle covering all black pixels, I'd scan the 
		whole matrix and track min/max row and column.”
		
		Complexity becomes:
		Time:  O(mn)
		Space: O(1)
	 * */
	
	// 2. 🔴 Very High: Could you solve this with DFS/BFS?
	/* Interview script
		“An alternative is DFS or BFS from the known black pixel. Since all black 
		pixels are connected, I can visit exactly that component and track the min 
		and max coordinates. That costs O(k), where k is the number of black pixels. 
		
		Binary search is attractive when I want the required O(n log m + m log n) 
		bound without traversing the component.”
	 * */
	
    // 3. 🔴 Very High: What if the image is a 3D grid?
    /*
     	Instead of: image[row][col]
		you now have: grid[x][y][z]
		
		We need a minimum 3D bounding box.

		In 2D we find four boundaries: top, bottom, left, right
		
		In 3D we find six: 
			minX, maxX, 
			minY, maxY 
			minZ, maxZ
		
		Then volume is:
		(maxX - minX) * (maxY - minY) * (maxZ - minZ)
		
		Interview script
		“The same idea generalizes naturally. In 2D we're finding two boundaries 
		along each of two dimensions. In 3D I'd find two boundaries along each of 
		three dimensions, giving six total boundaries. Connectivity gives us contiguous 
		projections along each axis, so we can binary search each one.”
		
		Time complexity: 
			Let the dimensions be: X × Y × Z
			For an X binary search: O(log X) searches
			Each search scans a Y × Z plane 
			=> O(YZ log X)
			
			Similarly:
			Y boundaries → O(XZ log Y)
			Z boundaries → O(XY log Z)
			
			Overall:
			O(YZ log X + XZ log Y + XY log Z)
			
			For an n × n × n cube: O(n² log n)
			
		Space: O(1)
		
     * */
}
