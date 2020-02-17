public class LandOnZero_JumpGame {

	public static void main(String[] args) {
		
		int[] board = {1,2,1,0};
		int start_idx = 2;
		boolean res = hasLandOnZero(board, start_idx);
		
		System.out.println(res);
	}

	/**
	 * In a given arr you need to find ZERO by visiting left and/or right from the starting idx 
	 */
	
	private static boolean hasLandOnZero(int[] board, int start_idx) {
		boolean result = false;
		//HashSet<Integer> visited = new HashSet<>(); ///we can't use visited as it's possible that if we have start idx 2 then right might have ZERO in 2 steps but left not and using this will not let us move in other side i.e. if we have land on this while visting left then we can't go right from this node coz it's visited.
		result = helper(board, start_idx, true, false);
		
		return result;
	}

	private static boolean helper(int[] board, int start_idx, boolean go_left, boolean go_right) {
		boolean result = false;

		if(start_idx < 0 || start_idx > board.length-1) {
			return false;
		}
		
		/*
		 * Here we comparing value at start_idx with remaining left side length which will be
		 * current index value - 1 i.e. start_idx
		 * 
		 * e.g. {1,2,2,0} in this arr if we are at idx=2 so left side remaining size is 2 that is 2 elements
		 * */
		if(go_left && board[start_idx] > start_idx) {
			
			return false; //--out of bound
		}
		
		if(go_right && board[start_idx] > board.length -1 - start_idx) {
			
			return false; //--out of bound
		}
		
		if(board[start_idx] == 0) return true;
		
		if(go_left)
			start_idx -= board[start_idx];
		if(go_right)
			start_idx += board[start_idx];
		
		//-left
		result = helper(board, start_idx, true, false);
		
		if(!result) {
			result = helper(board, start_idx, false, true);
		}
		
		return result;
	}

}
