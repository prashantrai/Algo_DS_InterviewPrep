package ctci.ch16.moderate;


//--Similar to connected islands prob
public class PondSizeDemo {

	public static void main(String[] args) {

		int m[][] = {
						{0, 2, 1, 0},
						{0, 1, 0, 1},
						{1, 1, 0, 1},
						{0, 1, 0, 1}
					};

		pondSize(m);
		
	}

	private static void pondSize(int[][] m) {
		
		if(m == null || m.length == 0) return;
		boolean[][] visited  = new boolean[m.length][m[0].length];
		for(int r=0; r<m.length; r++) {
			for(int c=0; c<m[0].length; c++) {
				if(m[r][c] == 0) {
					int counter = helper(m, r, c, visited);
					//int counter = helper2(m, r, c, visited);
					if(counter > 0)
						System.out.println("counter:: "+counter);
				}
			}
		}
		
	}

	
	private static int helper2(int[][] m, int r, int c, boolean[][] visited) {
		
		if(r < 0 || r >= m.length || c < 0 || c >= m[r].length 
				|| visited[r][c] || m[r][c] != 0) {
			return 0;
		}
		
		int count = 1;
		visited[r][c] = true;
		
		for(int dr = -1; dr<=1; dr++) {
			for(int dc = -1; dc<=1; dc++) {
				count += helper2(m, r+dr, c+dc, visited);
			}
		}
		
		return count;
	}
	
	private static int helper(int[][] m, int r, int c, boolean[][] visited) {
		
		if(r < 0 || r >= m.length || c < 0 || c >= m[0].length 
				|| visited[r][c] || m[r][c] != 0) {
			return 0;
		}
		
		int count = 1;
		//m[r][c] = 99;  //--if you don't like modifying the matrix
		visited[r][c] = true;
		
		count += helper(m, r-1, c, visited);
		count += helper(m, r+1, c, visited);
		count += helper(m, r, c-1, visited);
		count += helper(m, r, c+1, visited);
		
		//--diagonal
		count += helper(m, r-1, c-1, visited);
		count += helper(m, r-1, c+1, visited);
		count += helper(m, r+1, c-1, visited);
		count += helper(m, r+1, c+1, visited);
		
		return count;
	}

}
