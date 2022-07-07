package Box;
public class HammingDistance {

	public static void main(String[] args) {
        int x = 1;
        int y = 2;
        System.out.println("x="+x+", y="+y+"  Hamming distance: " + getDistance(x,y));
        x = 4;
        y = 5;
        System.out.println("x="+x+", y="+y+"  Hamming distance: " + getDistance(x,y));
        x = 7;
        y = 10;
        System.out.println("x="+x+", y="+y+"  Hamming distance: " + getDistance(x,y));
    }
	
   /**
	Approach:

		Do x XOR y. (Since in XOR, the bit is set only when both bits are different 
		so doing x XOR y will set the bits at the places where bits are different.)

		Now just count the number of set bits, this will be the hamming distance between x and y
		
		NOTE: In XOR if input bits are the same, then the output will be false(0) else true(1).
		
		To understand arithmetic left shift refer below: 
		https://open4tech.com/logical-vs-arithmetic-shift/#:~:text=A%20Right%20Arithmetic%20Shift%20of,position%20to%20the%20right)%20MSB.
	
	*/
	public static int getDistance(int x, int y){

        //first get the XOR of x and y
        int xor = x^y;

        System.out.println("x=" + get8BitFormat(Integer.toBinaryString(x)));
        System.out.println("y=" + get8BitFormat(Integer.toBinaryString(y)));
        System.out.println("xor=" + get8BitFormat(Integer.toBinaryString(xor)));
        
        //now count all set bits in it
        int bitCount = 0;
        while(xor>0){
            if((xor&1)==1)
                bitCount++;
            xor >>= 1;
        }
        return bitCount;
    }

    private static String get8BitFormat(String binary) {
    	return String.format("%8s", binary).replaceAll(" ", "0");
    }
    
    
}

/**
Hamming Distance: Hamming distance between two integers is the number of positions at which the 
bits are different.

Example:

X = 2, Y = 4
Hamming distance: 2
2 = 0 1 0
4 = 1 0 0
There are 2 positions at which bits are different.

X = 4, Y = 5
Hamming distance: 1
4 = 1 0 0
5 = 1 0 1
There is only 1 position at which bit is different.

X = 7, Y = 10
Hamming distance: 3
10 = 1 0 1 0
7 =  0 1 1 1
There are 3 positions at which bits are different. 




 */

