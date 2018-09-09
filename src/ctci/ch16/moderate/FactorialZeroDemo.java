package ctci.ch16.moderate;

public class FactorialZeroDemo {

	public static void main(String[] args) {
		
		int count = countFactZero (25); 
		System.out.println(count);

	}
	
	public static int countFactZero (int num) {
		
		if(num < 0) return -1;
		
		int count = 0;
		
		for (int i=5; num/i > 0; i*=5) {
			count += num/i;
		}
		return count;
	}

}
