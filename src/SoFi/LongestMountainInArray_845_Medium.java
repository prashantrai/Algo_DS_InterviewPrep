package SoFi;

public class LongestMountainInArray_845_Medium {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	// Time: O(N), Space: O(1)
    public int longestMountain(int[] A) {
        int res = 0, up = 0, down = 0;

        for (int i = 1; i < A.length; ++i) {

            // Reset when:
            // We are already going down (down > 0) and suddenly go up again
            // (A[i-1] < A[i]): start of a new possible mountain.
            // Or we see a plateau (A[i-1] == A[i]): not allowed in a mountain.
            if (down > 0 && A[i - 1] < A[i] || A[i - 1] == A[i])
                up = down = 0;
            if (A[i - 1] < A[i])
                up++;
            if (A[i - 1] > A[i])
                down++;
            if (up > 0 && down > 0 && up + down + 1 > res)
                res = up + down + 1;
        }
        return res;
    }
}

/* Example Walkthrough: 
	A = [2, 1, 4, 7, 3, 2, 5]
	
	Start: res=0, up=0, down=0.
	
	i = 1, compare (2,1)
	
	reset? (down>0 && 2<1) false, 2==1 false → no reset
	2<1? no → up stays 0
	2>1? yes → down=1
	mountain? up>0 && down>0? 0 && 1 → no
	i = 2, compare (1,4)
	
	reset? down>0 (1) and 1<4 → true → up=0, down=0
	1<4? yes → up=1
	1>4? no
	mountain? up>0 && down>0? 1 && 0 → no
	i = 3, compare (4,7)
	
	reset? down>0? 0 → no; equal? no
	4<7? yes → up=2
	4>7? no
	mountain? 2 && 0 → no
	i = 4, compare (7,3)
	
	reset? down>0? 0 → no; equal? no
	7<3? no
	7>3? yes → down=1
	mountain? up>0 && down>0 → 2 && 1 true
	length = up + down + 1 = 2 + 1 + 1 = 4 → res = 4
	(mountain: [1,4,7,3])
	i = 5, compare (3,2)
	
	reset? down>0? 1 but 3<2? no; equal? no
	3<2? no
	3>2? yes → down=2
	mountain? up>0 && down>0 → 2 && 2 true
	length = 2 + 2 + 1 = 5 → res = 5
	(mountain: [1,4,7,3,2])
	i = 6, compare (2,5)
	
	reset? down>0 (2) and 2<5 → true → up=0, down=0
	2<5? yes → up=1
	2>5? no
	mountain? 1 && 0 → no
	Loop ends, return res = 5.
	
	So the longest mountain [1,4,7,3,2] has length 5. 
	The code tracks “up steps” and “down steps” in one pass, 
	resetting only when the mountain pattern is broken.
*/
