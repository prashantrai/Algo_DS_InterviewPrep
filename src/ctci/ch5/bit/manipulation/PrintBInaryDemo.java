package ctci.ch5.bit.manipulation;

public class PrintBInaryDemo {

	public static void main(String[] args) {

		System.out.println(printBinary(.55));
		System.out.println(">> "+printBinary_2(.55));
		
		System.out.println("Integer.BYTES:: "+Integer.BYTES);
		
	}
	
	public static String printBinary_2 (double num) {
		
		if(num >= 1 && num <= 0) return "ERROR";
		
		StringBuilder binary = new StringBuilder();
		binary.append(".");
		
		double frac = 0.50;
		
		while(num > 0) {
			
			//if(binary.length() >= 32) return "ERROR_1";
			
			if(num >= frac) {
				binary.append(1);
				num = num - frac;
			} else {
				binary.append(0);
			}
 			frac = frac/2;
		}
		
		return binary.toString();
		
	}
	
	public static String printBinary(double num) {
		
		if(num >=1 && num <=0) return "ERROR";
		
		StringBuilder binary = new StringBuilder();
		binary.append(".");
		
		while(num > 0) {
			
			/*if(binary.length() >= 32) {
				System.out.println(">> "+binary);
				return "ERROR_1";
			}*/
			
			double r = num * 2;
			
			if(r >= 1) {
				binary.append(1);
				num = r - 1;
			} else {
				binary.append(0);
				num = r;
			}
			
		}
		
		return binary.toString();
		
	}

}
