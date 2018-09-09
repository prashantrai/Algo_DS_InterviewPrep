package test.practice.ebay;

public class RankFromStreamDemo {

	private static RankNode root;
	public static void main(String[] args) {
		
		int[] arr = {20, 15, 10, 5, 13, 25, 23, 24};
		for (int d : arr) {
			track(d);
		}
		System.out.println(getRankOfNumber(24));
		
	}

	public static void track(int num) {
		
		if(root == null) {
			root = new RankNode(num);
		} else {
			root.insert(num);
		}
	}
	
	public static int getRankOfNumber(int num) {
		return root.getRank(num);
	}
	
	static class RankNode {
		
		int left_size;
		int data;
		RankNode left, right;
		
 		public RankNode(int d) {
 			this.data= d;
 		}
 		
 		public void insert(int d) {
 			
 			if(d <= data) {
 				if(left == null) {
 					left = new RankNode(d);
 				} else {
 					left.insert(d);
 				}
  				left_size++;
 			} else {
 				if(right == null) {
 					right = new RankNode(d);
 				} else {
 					right.insert(d);
 				}
 			}
 		}
 		
 		public int getRank(int num) {
 			
 			if(num == data) {
 				return left_size;
 			} 
 			if(num <= data) {
 				if(left == null) {
 					return -1;
 				} else {
 					return left.getRank(num);
 				}
 			} else {
 				if(right == null) {
 					return -1;
 				} else {
 					int rank = right.getRank(num);
 					
 					if(rank == -1) return rank;
 					
 					return rank + 1 + left_size;
 							
 				}
 			}
 		}
		
	}
	
}
