package ctci.ch5.bit.manipulation;

public class NextNumberDemo {

	public static void main(String[] args) {

		
		int a  = 1 << 7;
		int b = a-1;
		int mask = ~b; 
		System.out.println(Integer.toBinaryString(a));
		System.out.println(Integer.toBinaryString(1));
		System.out.println(Integer.toBinaryString(b));
		System.out.println(Integer.toBinaryString(mask));
		
		a = 1 << 3;
		b = a-1;
		int c = b << 4;
		System.out.println(Integer.toBinaryString(a));
		System.out.println(Integer.toBinaryString(b));
		System.out.println(Integer.toBinaryString(c));
		
		System.out.println(Integer.toBinaryString(-1));
		
		
	}

}
