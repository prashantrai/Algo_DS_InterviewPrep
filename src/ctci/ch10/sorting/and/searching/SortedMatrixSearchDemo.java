package ctci.ch10.sorting.and.searching;

public class SortedMatrixSearchDemo {

	public static void main(String[] args) {

		int[][] arr  ={	{15, 20, 40, 85},
						{20, 35, 80, 95},
						{30, 55, 95, 105},
						{40, 80, 100, 120}
					};
		
		System.out.println(">>>Result:: "+findElement(arr, 56));
		
	}
	
	public static boolean findElement(int[][] arr, int v) {

		int r=0;
		int c = arr[0].length-1;
		
		while(r < arr.length) {
			
			if(arr[r][c] == v) {
				return true;
			}
			if(arr[r][c] > v) {
				c--;
			} else{
				r++;
			}
			
		} 
		
		return false;
	} 

}
