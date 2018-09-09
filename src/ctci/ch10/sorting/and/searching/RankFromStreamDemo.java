package ctci.ch10.sorting.and.searching;

public class RankFromStreamDemo {

	static RankNode root = null;
	
	public static void main(String[] args) {
		
		int[] arr = {20, 15, 10, 5, 13, 25, 23, 24};
		for (int d : arr) {
			track(d);
		}
		System.out.println(getRankOfNumber(24));
		
	}
	
	public static void track(int d) {\
		if(root == null) {
			root = new RankNode(d);
		} else {
			root.insert(d);
		}
	}
	
	public static int getRankOfNumber(int d) {
		return root.getRank(d);
	}

}
 
class RankNode {
	
	int leftSize = 0;
	int data;
	RankNode left, right;
	
	public RankNode(int d) { data = d; }
	
	public void insert(int d) {
		
		if(d <= data) {
			if(left == null) {
				left = new RankNode(d);
			} else {
				left.insert(d);
			}
			leftSize++;
		}
		else { //--- if(d > data)
			if(right == null) {
				right = new RankNode(d);
			} else {
				right.insert(d);
			}
		}
	}
	
	public int getRank(int d) {
		if(d == data) {
			return leftSize;
		}
		else if(d < data) {
			if(left == null) 
				return -1;
			else 
				return left.getRank(d);
		}
		else { //--if(d > data)
			int rightRank = 0;
			
			if(right == null) 
				return -1;
			else {
				rightRank = right.getRank(d);
			}
			
			return rightRank == -1 ?  rightRank : leftSize + 1 + rightRank;	
				
		}
	}
	
}
