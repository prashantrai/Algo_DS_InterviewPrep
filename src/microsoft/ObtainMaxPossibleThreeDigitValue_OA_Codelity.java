package microsoft;

public class ObtainMaxPossibleThreeDigitValue_OA_Codelity {

	public static void main(String[] args) {
		int result = count(512, 10);
        System.out.println("Expected: 972, Actual: " + result);
	}
	
	/*
	 [https://leetcode.com/discuss/interview-question/398023/Microsoft-Online-Assessment-Questions/1173914]
	
		Write a function solution that, given a three-digit integer N and an integer K, 
		returns the maximum possible three-digit value that can be obtained by performing at most K increases 
		by 1 of any digit in N.
		
		Examples:
		
		Given N = 512 and K =10, the function should return 972. The result can be obtained by increasing the first digit of N four times and the second digit six times.
		Given N = 191 and K = 4, the function should return 591. The result can be obtained by increasing the first digit of N four times.
		Given N = 285 and K = 20, the function should return 999. The result can be obtained by increasing the first digit of N seven times, the second digit once and the third digit four times.
		Assume that:
		• N is an integer within the range [100..999]
		• K is an integer within the range [0..30].
	
	 * */
	
	public static int count (int num, int k) {
        for (int i = 0; i < k; i++) {
            if (num == 999) {
                break;
            }
            if (num + 100 < 1000) {
                num += 100;
                continue;
            }
            if (num + 10 < 1000) {
                num += 10;
                continue;
            }
            if (num + 1 < 1000) {
                num += 1;
                continue;
            }
        }
        return num;
    }

}
