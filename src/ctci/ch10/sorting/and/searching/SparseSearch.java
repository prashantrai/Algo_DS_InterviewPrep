package ctci.ch10.sorting.and.searching;

public class SparseSearch {

	public static void main(String[] args) {

		String[] arr = {"at","", "", "","ball", "", "", "car", "","", "dad", "", ""};
		String s = "ball";
		int index = search(arr, s);
		System.out.println(">>Result :: "+index);
	}
	
	public static int search(String[] arr, String s) {
		
		if(arr == null || arr.length == 0 || s == null || s.length() == 0) 
			return -1;
		
		return search(arr, s, 0, arr.length-1);
	}

	private static int search(String[] arr, String s, int low, int high) {
		
		int mid = (low+high)/2;
		int left = mid-1;
		int right = mid+1;
		
		while(arr[mid].isEmpty()) {
			
			if(left < low && right > high) {
				return -1;
			}
			if(left >= low && !arr[left].isEmpty()) {
				mid = left;
				break;
			} else if(right <= high && !arr[right].isEmpty()) { 
				mid = right;
				break;
			} 
			left--;
			right++;
		}
		
		if(left > right) {
			return -1;
		}
		
 		if(arr[mid].equals(s)) {
 			return mid;
 		}
 		else if(arr[mid].compareTo(s) < 0) { //--search right : Strings are sorted in lexo-graphical order
			return search(arr, s, mid+1, high);
		}
 		else {
 			return search(arr, s, low, mid-1);
 		}
		
	}

}
