package interview;

class BitGoPascalTriangle {

	public static void main(String[] args) {
		//printTriangle(7);
		printTriangleWithLinearSpace(7);
	}

	public static void printTriangle(int n) {

		// http://www.geeksforgeeks.org/pascal-triangle/
		int arr[][] = new int[n][n];  //Space O(n^2)

		for (int line = 0; line < n; line++) { //--Runtime O(n^2)

			for (int i = 0; i <= line; i++) {
				if (line == i || i == 0)
					arr[line][i] = 1;

				else
					arr[line][i] = arr[line - 1][i - 1] + arr[line - 1][i];

				System.out.print(arr[line][i] + " ");

			}
			System.out.println();

		}

	}
	
	
	//with O(n) space
	public static void printTriangleWithLinearSpace(int n) { 
		// http://www.geeksforgeeks.org/pascal-triangle/
		int old[] = new int[n]; 
		int a[] = new int[n]; 
		
		for (int line = 0; line < n; line++) { 
			
			for (int i = 0; i <= line; i++) {
				if(line == i || i == 0) {
					a[i] = 1;
				} else {
					a[i] = old[i] + old[i-1];
				}
				System.out.print(a[i] + " ");
			}
			System.out.println();
			
			for(int j=0;j<a.length; j++) {
				old[j] = a[j];
				a[j] = 0;
			}
		}
		
	}

}
