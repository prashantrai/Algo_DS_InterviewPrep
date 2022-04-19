package leetcode;

public class ExcelColumnTitle_168_Easy {

	public static void main(String[] args) {

		//printColumnTitle(26);
		printColumnTitle(51);
		printColumnTitle(52);
		//printColumnTitle(705);
		
	}


	//Time Complexity: O(N) for reverse + O(Log N); where Log has base 26
	//Space Complexity: O(k) ; where k = constant = no. of variables
	public static void printColumnTitle(int colNum) {
		
		StringBuilder sb = new StringBuilder();
		
		// log N
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
		
		System.out.println(sb.reverse()); // O(N)
	}
	
	/*
    Reference: https://leetcode.com/problems/excel-sheet-column-title/discuss/51399/Accepted-Java-solution
    
    Time and Space: O(N)
    */
    public String convertToTitle(int n) {
        StringBuilder sb = new StringBuilder();
        
        while (n > 0) {
            /* why n--?             
            Because we are appending 'A' (in the next line) i.e. we always use 
            'A' + n%26, 'A' is virtually stand for 1 at here, 
            we have to deduct 1 before doing this operation.
            
            eg. if n%26 == 1 now, we definitely need to append A at current iteration,
            however, 'A' + 1 will give us 'B', so we need 'A' + 0 to get correct result.
            */
            n--;
            
            sb.append((char) ('A' + n%26));
            
            n = n/26;
        }
        
        return sb.reverse().toString(); // O(N)
    }
	
}
