package Rubrik;


import java.util.Arrays;

public class CandyCrush_723_Medium {

	public static void main(String[] args) {

		int[][] board = 
				{	
					{110,5,112,113,114},
					{210,211,5,213,214},
					{310,311,3,313,314},
					{410,411,412,5,414},
					{5,1,512,3,3},
					{610,4,1,613,614},
					{710,1,2,713,714},
					{810,1,2,1,1},
					{1,1,2,2,2},
					{4,1,4,4,1014}
				};
		
		int[][] res = candyCrush(board);
		
		System.out.println(Arrays.deepToString(res));
		
//		int[][] res2 = candyCrush_RECURSE(board);
//		System.out.println(Arrays.deepToString(res2));

	}
	
	
	/* 
	 * For question and solution: 
	 * 		https://just4once.gitbooks.io/leetcode-notes/content/leetcode/two-pointers/723-candy-crush.html
	 * 		http://shibaili.blogspot.com/2019/01/723-candy-crush.html
	 * 
	 * Time complexity O((rc)^2), because it costs 3rc to scan the whole board then we call at most rc/3 times
	 * Space complexity O(1)
	 * 
	 * */
	
	public static int[][] candyCrush(int[][] board) {
		
		int r = board.length; 
		int c = board[0].length;
		boolean crush = true;
		
		while(crush) {
			crush = false;
			for (int i=0; i<r; i++) {
				for (int j=0; j<c; j++) {
					
					int v = Math.abs(board[i][j]);
					
					if(v == 0) continue;
					
					//check horizontal line
					if( j+2<c && v == Math.abs(board[i][j+1]) && v == Math.abs(board[i][j+2]) ) {
						crush = true;
						board[i][j] = board[i][j+1] = board[i][j+2] = -v;	
					}
					
					//check vertical line
					if( i+2<r && v == Math.abs(board[i+1][j]) && v == Math.abs(board[i+2][j]) ) {
						crush = true;
						board[i][j] = board[i+1][j] = board[i+2][j] = -v;
					}
				}
			}
			
			//crush candy
			System.out.println("1. "+Arrays.deepToString(board));
			for (int j=0; j<c; j++) {
				
				int id = r-1;
				
				for (int i=r-1; i>=0; i--) {
					if(board[i][j] > 0) {
//						board[id--][j] = board[i][j]; //works
						board[id][j] = board[i][j];
						id--;
						System.out.println("2. "+Arrays.deepToString(board));
					}
				}
				
//				while(id>=0) board[id--][j] = 0; //works
				while(id>=0) { 
					board[id][j] = 0;
					id--;
				}
			}
			System.out.println("2.2 "+Arrays.deepToString(board));
			crush=false;
		}
		
		//return crush ? candyCrush(board) : board; // this works too
		return board;
	}
	
	
	public static int[][] candyCrush_RECURSE(int[][] board) {
		
		int r = board.length; 
		int c = board[0].length;
		boolean crush = false;
		
		for (int i=0; i<r; i++) {
			for (int j=0; j<c; j++) {
				
				int v = Math.abs(board[i][j]);
				
				if(v == 0) continue;
				
				//check horizontal line
				if( j+2<c && v == Math.abs(board[i][j+1]) && v == Math.abs(board[i][j+2]) ) {
					crush = true;
					board[i][j] = board[i][j+1] = board[i][j+2] = -v;	
				}
				
				//check vertical line
				if( i+2<r && v == Math.abs(board[i+1][j]) && v == Math.abs(board[i+2][j]) ) {
					crush = true;
					board[i][j] = board[i+1][j] = board[i+2][j] = -v;
				}
			}
		}
		
		//crush candy
		for (int j=0; j<c; j++) {
			
			int id = r-1;
			
			for (int i=r-1; i>=0; i--) {
				if(board[i][j] > 0) {
					board[id--][j] = board[i][j];
				}
			}
			
			while(id>=0) board[id--][j] = 0;
		}
		
		return crush ? candyCrush(board) : board;
	}
	
}

/*
[
 [110,   5, 112, 113, 114], 
 [210, 211,   5, 213, 214], 
 [310, 311,   3, 313, 314], 
 [410, 411, 412,   5, 414], 
 [  5,   1, 512,   3,   3], 
 [610,   4,   1, 613, 614], 
 [710,  -1,  -2, 713, 714], 
 [810,  -1,  -2,   1,   1], 
 [  1,  -1,  -2,  -2,  -2], 
 [  4,  -1,   4,   4,1014]]


[
 [110,   5, 112, 113, 114], 
 [210, 211,   5, 213, 214], 
 [310, 311,   3, 313, 314], 
 [410, 411, 412,   5, 414], 
 [  5,   1, 512,   3,   3], 
 [610,   4,   1, 613, 614], 
 [710,  -1,  -2, 713, 714], 
 [810,  -1,  -2,   1,   1], 
 [  1,  -1,  -2,  -2,  -2], 
 [  4,  -1,   4,  4, 1014]]
		
[
 [110,   5, 112, 113, 114], 
 [210, 211,   5, 213, 214], 
 [310, 311,   3, 313, 314], 
 [410, 411, 412,   5, 414], 
 [  5,   1, 512,   3,   3], 
 [610,   4,   1, 613, 614], 
 [710,  -1,  -2, 713, 714], 
 [810,  -1,  -2,   1,   1], 
 [  1,  -1,  -2,  -2,  -2], 
 [  4,  -1,   4,   4, 1014]]


[
 [110,   5, 112, 113, 114], 
 [210, 211,   5, 213, 214], 
 [310, 311,   3, 313, 314], 
 [410, 411, 412,   5, 414], 
 [  5,   1, 512,   3,   3], 
 [610,   4,   1, 613, 614], 
 [710,  -1,  -2, 713, 714], 
 [810,  -1,  -2,   1,   1], 
 [  1,  -1,  -2,  -2,  -2], 
 [  4,   4,   4,   4, 1014]]


[
 [110,   5, 112, 113, 114], 
 [210, 211,   5, 213, 214], 
 [310, 311,   3, 313, 314], 
 [410, 411, 412,   5, 414], 
 [  5,   1, 512,   3,   3], 
 [610,   4,   1, 613, 614], 
 [710,  -1,  -2, 713, 714], 
 [810,  -1,  -2,   1,   1], 
 [  1,   1,  -2,  -2,  -2], 
 [  4,   4,   4,   4, 1014]]
 
 
 [
  [110,   5, 112, 113, 114], 
  [210, 211,   5, 213, 214], 
  [310, 311,   3, 313, 314], 
  [410, 411, 412,   5, 414], 
  [  5,   1, 512,   3,   3], 
  [610,   4,   1, 613, 614], 
  [710,  -1,  -2, 713, 714], 
  [810, 411,  -2,   1,   1], 
  [  1,   1,  -2,  -2,  -2], 
  [  4,   4,   4,   4, 1014]]

[
 [110,   5, 112, 113, 114], 
 [210, 211,   5, 213, 214], 
 [310, 311,   3, 313, 314], 
 [410, 411, 412,   5, 414], 
 [  5,   1, 512,   3,   3], 
 [610,   4,   1, 613, 614], 
 [710, 311,  -2, 713, 714], 
 [810, 411,  -2,   1,   1], 
 [  1,   1,  -2,  -2,  -2], 
 [  4,   4,   4,   4, 1014]]

[
 [110,   5, 112, 113, 114], 
 [210, 211,   5, 213, 214], 
 [310, 311,   3, 313, 314], 
 [410, 411, 412,   5, 414], 
 [  5,   1, 512,   3,   3], 
 [610, 211,   1, 613, 614], 
 [710, 311,  -2, 713, 714], 
 [810, 411,  -2,   1,   1], 
 [  1,   1,  -2,  -2,  -2], 
 [  4,   4,   4,   4, 1014]]

[
 [110,   0, 112, 113, 114], 
 [210,   0,   5, 213, 214], 
 [310,   0,   3, 313, 314], 
 [410,   0, 412,   5, 414], 
 [  5,   5, 512,   3,   3], 
 [610, 211,   1, 613, 614], 
 [710, 311,  -2, 713, 714], 
 [810, 411,  -2,   1,   1], 
 [  1,   1,  -2,  -2,  -2], 
 [  4,   4,   4,   4, 1014]]


2.2>>
[
 [110,   0,   0,   0,   0], 
 [210,   0,   0, 113, 114], 
 [310,   0,   0, 213, 214], 
 [410,   0, 112, 313, 314], 
 [  5,   5,   5,   5, 414], 
 [610, 211,   3,   3,   3], 
 [710, 311, 412, 613, 614], 
 [810, 411, 512, 713, 714], 
 [  1,   1,   1,   1,   1], 
 [  4,   4,   4,   4, 1014]]

*/

