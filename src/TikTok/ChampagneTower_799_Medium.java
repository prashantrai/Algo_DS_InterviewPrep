package TikTok;

public class ChampagneTower_799_Medium {

	public static void main(String[] args) {

		int poured = 1, query_row = 1, query_glass = 1;
		System.out.println("1. Expected: 0.00000, Actual: " + champagneTower(poured, query_row, query_glass));
		System.out.println("2. Expected: 0.00000, Actual: " + champagneTower2(poured, query_row, query_glass));
		
		poured = 2; 
		query_row = 1; 
		query_glass = 1;
		System.out.println("\n3. Expected: 0.50000, Actual: " + champagneTower(poured, query_row, query_glass));
		System.out.println("4. Expected: 0.50000, Actual: " + champagneTower2(poured, query_row, query_glass));
		
	}

	/*
    Time: O(R^2), where R is the number of rows. As this is fixed, we can consider this 
    complexity to be O(1).
    
    Space: O(R^2) or O(1) by the reasoning above.
    
    Note: For better space complexity solution refer 2nd solution
    */
    public static double champagneTower(int poured, int query_row, int query_glass) {
        // why 102?
        // refer: Refer: https://leetcode.com/problems/champagne-tower/solution/1310948
        // also find the explaination below
        double[][] A = new double[102][102];
        A[0][0] = (double) poured;
        
        for(int r=0; r <= query_row; r++) {
            for(int c = 0; c<=r; c++) {
                double q = (A[r][c] - 1.0) / 2.0;
                if(q > 0) {
                    A[r+1][c] += q;
                    A[r+1][c+1] += q;
                }
            }
        }
        return Math.min(1, A[query_row][query_glass]);
    }
    
    /*
    Time: O(R^2), where R is the number of rows. As this is fixed, we can consider this 
    complexity to be O(1).
    
    Space: O(R) or O(1) by the reasoning above.
    
    Refer: https://leetcode.com/problems/champagne-tower/solution/745535
    */
    public static double champagneTower2(int poured, int query_row, int query_glass) {
        
        if(poured == 0) return 0;
        
        double[] m = new double[101];
        m[0] = poured;
        
        for(int row=0; row<query_row; row++) {
            for(int col = row; col >= 0; col--) {
                
                // let us compute the next row
                if(m[col] > 1) {
                    // if current glass holds more than 1 cup of content
                    // we need to split the surplus with the left and right glass below.
                    double q = (m[col] - 1)/2;
                    m[col+1] += q;
                    m[col] = q;
                    
                } else {
                    // if the current glass holds less than or equal to 1 cup of content 
                    // initialize the left glass below to have no content.
                    //
                    // Note: we are iterating from the right, and as such, the right glass
                    // below would have been taken care of in the previous iteration.
                    
                    m[col] = 0;
                }
            }
        }
        return Math.min(1, m[query_glass]);
    }
}

/*
double[][] A = new double[102][102];
Why 102?
Refer: https://leetcode.com/problems/champagne-tower/solution/1310948

The +2 used in conjunction with range, has to do with accommodating columns, not rows.

Say you were maintaining a rectangular matrix, instead of the triangular matrix shared by @vj68
This could be initialized as
[[0]*(query_glass+1) for rows in range(query_row+1)]

Consider (query_row,query_glass) = (3,1)

          V
       V    V
    V    V    V
 V    V    V    V
(3,1) represents the 2nd glass from the left, in the bottom row; the right most glass in the previous row that would fill it would be (2,1); the rightmost glass that would will that glass, would be (1,1). So you never need to calculate flow through for any glasses right of query_glass

However, in the algorithm presented, we're not filling the cups as we traverse them via the loop; we add to the cups in the next row. And (2,1) fills not only (3,1) but (3,2) as well.
We could either add a check to make sure c+1 was within bounds

                if q > 0:
                    A[r+1][c] += q
                    A[r+1][c+1] += q
or, we could add one more column to A, to allow us to eliminate that check, like so:
[[0]*(query_glass+2) for rows in range(query_row+1)].
That's what the +2 accomplishes.

To your point, since query_glass must always be <= query_row.
this means [[0]*(i+1) for i in range(query_row+1)] should work
i.e. +1 should be sufficient, +2 is not needed in vj8's formulation

*/

