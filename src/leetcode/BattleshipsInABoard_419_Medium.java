package leetcode;

public class BattleshipsInABoard_419_Medium {

	public static void main(String[] args) {
		
		char[][] board = {
							{'X','.','.','X'},
							{'.','.','.','X'},
							{'.','.','.','X'}
						};

		int res = countBattleships(board);
		System.out.println(">>res:: " + res);
		
	}
	
	/**
	 *	One-pass, using only O(1) extra memory 
	 */
	
	//--https://leetcode.com/problems/battleships-in-a-board/
	//--https://www.youtube.com/watch?v=wBG6078g1gE
	public static int countBattleships(char[][] board) {
        int numOfBattleShips = 0;
        
        for(int i=0; i < board.length; i++) {
            for(int j=0; j < board[0].length; j ++) {
                if(board[i][j] == '.') {
                    continue;
                }
                if(i > 0 && board[i-1][j] == 'X') {
                    continue;
                }
                if(j > 0 && board[i][j-1] == 'X') {
                    continue;
                }
                numOfBattleShips++;
            }
        }
        return numOfBattleShips;
    }

}
