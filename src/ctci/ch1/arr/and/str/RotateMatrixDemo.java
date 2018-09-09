package ctci.ch1.arr.and.str;

import java.util.Arrays;

public class RotateMatrixDemo {

	public static void main(String[] args) {
		
		Integer [][] matrix = 	{
								{1, 2, 3},
								{4, 5, 6},
								{7, 8, 9}
							};
		
		
		/*
		 >> matrix :: 
		 				[14,  2,  3,  1], 
		 				[ 5,  6,  7,  8], 
		 				[ 9, 10, 11, 12], 
		 				[17, 15, 16,  4]
		 				
>> matrix :: [[14, 9, 3, 1], [5, 6, 7, 2], [16, 10, 11, 12], [17, 15, 8, 4]]
>> matrix :: [
						[14,  9,  5, 1], 
						[15,  6,  7, 2], 
						[16, 10, 11, 3], 
						[17, 12,  8, 4]]
		  
		  
		 * */
		
		
		Integer[][] matrix_4x4 =  {
									{ 1,  2,  3,  4},
									{ 5,  6,  7,  8},
									{ 9, 10, 11, 12},
									{14, 15, 16, 17},
				
								};

			
		System.out.println("matrix.lenght = "+matrix.length);
		
//		rotate(matrix_4x4);
		rotateBy90(matrix_4x4);
		
		//System.out.println(">>> "+ Arrays.deepToString(matrix));
		
	}
	
	/*
		 1  2  3  4
		 5  6  7  8
		 9 10 11 12
		14 15 16 17
		
		
>> matrix :: [	[7, 4, 1], 
				[8, 5, 2], 
				[9, 6, 3]
			]
		
for i = 0 to n
temp	 = top[i];
top[i] = left[i]
left[i] 	= bottom[i]
bottom[i] = right[i]
right[i]  = temp

lenght/2 = 3


  1  2  3  4  5  6	  -> layer = 0 to (length-1)-layer
  7  8  9 10 11 12    -> layer = 1 to (length-1)-layer 
 13 14 15 16 17 18
 19 20 21 22 23 24
 25 26 27 28 29 30
 31 32 33 34 35 36

{ 1,  2,  3,  4},
{ 5,  6,  7,  8},
{ 9, 10, 11, 12},
{14, 15, 16, 17},

[
	[14,  9,  5, 1], 
	[15,  6,  7, 2], 
	[16, 10, 11, 3], 
	[17, 12,  8, 4]
]
[
[14,  9,  5, 1], 
[15, 10,  6, 2], 
[16,  7, 11, 3], 
[17, 12,  8, 4]]

		
 */
	private static boolean rotateBy90(Integer[][] mat) {
		
		int len = mat.length;
		
		int first = 0;
		int last = len - 1;
				
		for (int layer=0; layer<len/2; layer++)	{
			
			first = layer;
			last = len-1-layer;
			
			System.out.println("\n\n\n---------layer = " + layer);
			System.out.println("\n\n\n---------last = " + last);
			
			for (int i=first; i< last; i++) { 
				
				int offset = i-first;
				
				System.out.println(">> mat[last-offset]  => "+ "mat["+(last-offset)+"]");
				System.out.println(">> mat[first]  => "+ "mat["+first+"]");
				System.out.println(">> mat[last]  => "+ "mat["+(last)+"]");
				System.out.println(">> mat[i]  => "+ "mat["+(i)+"]");
				
				//--store top
				int temp = mat[first][i];
				
				// top <- left 
				mat[first][i] = mat[last-offset][first]; //expected: 1,1=2,1 ; Result: 1,1=(1),1
				
				// left <- bottom
				mat[last-offset][first] = mat[last][last-offset]; //  
				
				// bottom <- right
				mat[last][last-offset] = mat[i][last]; //
				
				// right <- temp
				mat[i][last] = temp;
				
				System.out.println(">> matrix :: " + Arrays.deepToString(mat));
				
			}
		}
		
		System.out.println("*>> matrix :: " + Arrays.deepToString(mat));
		
		return true;
		
	}
	
	

	private static boolean rotate(Integer[][] matrix) {
		 
		if (matrix.length== 0 || matrix.length != matrix[0].length) return false;
		 
		 int n = matrix.length;
		 
		 System.out.println("n/2 = "+ (n/2));
		 
		 for (int layer = 0; layer < n / 2; layer++) {
			 
			 int first = layer;
			 int last = n - 1 - layer;
			 
			 System.out.println("layer = "+layer);
			 System.out.println("first = "+first);
			 System.out.println("last = "+last);
			 
			 /*
		 		1	2	3
		 		4	5	6
		 		7	8	9
		 		
for i = 0 to n
  temp	 = top[i];
  top[i] = left[i]
  left[i] 	= bottom[i]
  bottom[i] = right[i]
  right[i]  = temp
		 		
			 */
			 
			 for(int i = first; i < last; i++) {
				
				int offset = i - first;
				
				System.out.println("i = "+i);
				System.out.println("offset = "+offset);
				 
				int top= matrix[first][i]; // save top
				
				// left -> top
				matrix[first][i]  = matrix[last-offset][first];
				
				// bottom -> left
				matrix[last-offset][first] = matrix[last][last - offset]; 
				
				// right -> bottom
				matrix[last][last - offset] = matrix[i][last]; 
				
				// top -> right
				matrix[i][last] = top; // right<- saved top
				
				System.out.println(">>> "+ Arrays.deepToString(matrix));
				 
			 }
			 
			 System.out.println("*>>> "+ Arrays.deepToString(matrix));
		}
		
		return true;
		
	}
	
	
}

/*
 
 Matrix: 
 		1	2	3
 		4	5	6
 		7	8	9
 		
 		[7, 4, 1], 
 		[8, 5, 2], 
 		[9, 6, 3]
 		
 		
i=0
j 		
 		
     
>>>  [7, 4, 1], 
	 [8, 5, 2], 
	 [9, 6, 3]
	 
*>>> [7, 4, 1], 
	 [8, 5, 2], 
	 [9, 6, 3]
		
 		
 
 n=3
 n/2 = 1
 layer = 0, 1
 first = 0 
 last  = 1
 
 i=0, 1
 
 offset=0, 1
 top = 1
 
 last-offset = 0
 
 
 Result: 
 Matrix: 
 		4	1	_
 		5	2	_
 		_	_	_
 		
 		4	1	_
 		5	2	_
 		_	_	_
 
 
 
 
 
// bottom -> left
matrix[1][0] = matrix[1][1]

// right -> bottom
matrix[1][1] = matrix[0][1]

// top -> right
matrix[last][last - offset];
 
 
 * */
