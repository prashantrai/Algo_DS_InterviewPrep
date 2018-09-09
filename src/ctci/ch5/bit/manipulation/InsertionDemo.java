package ctci.ch5.bit.manipulation;

public class InsertionDemo {

	public static void main(String[] args) {

		int result = updateBits(1024, 19, 2, 6);
		System.out.println(">>>result:: "+result + "; " + Integer.toString(result, 2));
		
	}
	
	
	public static int updateBits (int n, int m, int i, int j) {
		
		System.out.println("n="+Integer.toBinaryString(n)+", m="+Integer.toBinaryString(m));
		
		/* Clear the bits in j through i in n : 
		 * EXAMPLE: i = 2, j = 4. 
		 * Result should be 11100011. For simplicity, we'll use just 8 bits for the example. */
		
		int allOnes = ~0;  //sequance of all 1s
		
		//System.out.println("allOnes: "+allOnes + ", binary: "+Integer.toBinaryString(allOnes));
		
		//--1s before the position j then 0: apply left shift
		int left = allOnes << (j+1);
		System.out.println("left: "+Integer.toBinaryString(left));
		//System.out.println("left: "+Integer.toString(left, 2));
		
		int right = ((1 << i)-1);
		System.out.println("right: "+Integer.toBinaryString(right));
		
		int mask  = left | right;
		
		System.out.println("mask: "+Integer.toBinaryString(mask));
		
		//clear bit j through i and then put m in
		int n_cleared = n & mask;
		int m_shifted = m << i; //move m to correct position i.e. i
		
		//OR them
		
		return n_cleared | m_shifted;
		
	}

}
