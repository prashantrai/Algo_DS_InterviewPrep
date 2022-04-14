package interview;
import java.util.Random;
import java.util.StringJoiner;


/** VmWare
	Write a function to generate random numbers between given range (5 to 55 inclusive) 
	using a given function "rand_0()" which returns whether '0' or '1'.
*/

public class RandomNumGeneratorDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		for(int i=0; i< 50; i++)
			System.out.print(" "+myRand() + " ");
	}
	
	//public static int myRand(int min, int max) {
	public static int myRand() {
		
		StringJoiner s = new StringJoiner("");
		while(true) {
			//s = new StringJoiner("");
			//for(int i=0; i<8; i++) {˙
			//s.add(String.valueOf(rand_0()));
			//}
			if(s.length() < 8) {
				s.add(String.valueOf(rand_0()));
				continue;
			}
				
			int v = Integer.parseInt(s.toString(), 2);
			
			if(v >= 5 && v<=55) {
				return v;
			} else {
				s = new StringJoiner("");
			}
		}
	}
	
	public static String getBinaryString() {
		String s = "";
		for(int i=0; i<8; i++) {
			s += rand_0();
		}
		System.out.println("--> "+s +" = "+ Integer.parseInt(s, 2));
		return s;
	}
	
	public static int rand_0 () {
		return getRandomNumberInRange (0, 1);
	}
	private static int getRandomNumberInRange (int min, int max) { 

		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}

}


/*
 * 25 5
 * 25-> 20 5
 * 20 -> 10 2
 * 10 -> 5 2
 * 
 6  10  31  40  26  10  36  
 6  6  41  10  30  26  40  
 35  15  36  10  36  36  6  
 6  30  25  6  16  5  15  5  
 25  40  40  11  36  26  26  
 15  11  25  41  15  35  6  
 26  26  11  11  15  30  35 
 * */
