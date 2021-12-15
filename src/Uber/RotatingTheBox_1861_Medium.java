package Uber;

public class RotatingTheBox_1861_Medium {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	// https://leetcode.com/problems/rotating-the-box/discuss/1210113/simplest-explanation-with-java-solution
	// Look at this for explanation:  https://leetcode.com/problems/rotating-the-box/discuss/1388005/Two-simple-JAVA-solutions
	
	// Time and Space: O(N^2)
	
	
	// First we move the stones to its furthest right (if there are empty spaces on the right), then rotate the altered matrix.
	//Check here: https://leetcode.com/problems/rotating-the-box/discuss/1388005/Two-simple-JAVA-solutions
	public static char[][] rotateTheBox(char[][] box) {
        int r = box.length, c = box[0].length;
        char[][] box2 = new char[c][r];
        
        for(int i = 0; i<r; ++i){
            int empty = c-1;
            for(int j = c-1; j>=0; --j){
                if(box[i][j] == '*'){
                    empty = j-1;
                }
                if(box[i][j] == '#'){
                    box[i][j] = '.';
                    box[i][empty] = '#';
                    --empty;
                }
            }
        }
        
        for(int i = 0; i<r; ++i){
            for(int j = c-1; j>=0; --j)
                box2[j][r-i-1] = box[i][j];
        }
        
        return box2;
    }

}
