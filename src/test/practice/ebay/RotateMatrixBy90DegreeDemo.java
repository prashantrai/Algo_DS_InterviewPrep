package test.practice.ebay;

public class RotateMatrixBy90DegreeDemo {

	public static void main(String[] args) {
		Integer[][] matrix_4x4 = { 
										{ 1, 2, 3, 4 }, 
										{ 5, 6, 7, 8 }, 
										{ 9, 10, 11, 12 }, 
										{ 14, 15, 16, 17 },

								};

		System.out.println("matrix.lenght = " + matrix_4x4.length);

		// rotate(matrix_4x4);
		rotateBy90(matrix_4x4);
	}
	
	public static boolean rotateBy90 (Integer[][] mat) {
		
		if(mat.length == 0 || mat.length != mat[0].length) 
			return false;
		
		int len = mat.length; 
		
		for(int layer=0; layer<len/2; layer++) {
			
			int first = layer; 
			int last = len-1-layer; // for first layer offset=4-1-0=3 and for second offset=4-1-1=2
			
			for(int i=first; i<last; i++) {
				
				//int top = mat[]
			}
			
		}
		
	}

}
