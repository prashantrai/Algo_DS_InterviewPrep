package test.practice.misc;

public class ExcelColumnTitleDemo {

	public static void main(String[] args) {

		//printColumnTitle(26);
		printColumnTitle(51);
		printColumnTitle(52);
		//printColumnTitle(705);
		
	}

	
	public static void printColumnTitle(int colNum) {
		
		StringBuilder sb = new StringBuilder();
		while(colNum > 0) {
			
			int rem = colNum % 26;
			
			if(rem == 0) {
				sb.append("Z");
				colNum = colNum/26 - 1;
			} else { //-- non zero
				sb.append((char)((rem-1) + 'A'));
				colNum = colNum/26;
			}
		}
		
		System.out.println(sb.reverse());
	}
	
}
