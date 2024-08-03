package microsoft;

public class PalindromicNumberWithLlargestPossibleDecimalValue_OA_Cedlity_During_My_Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	/*
	 * For Solutions: 
	 * https://leetcode.com/discuss/interview-question/398023/Microsoft-Online-Assessment-Questions/1266522
	 */

	public static String bp(String s) {
        StringBuilder sb = new StringBuilder();
        int[] count = new int[10];
        for(char c : s.toCharArray()) {
            count[c - '0']++;
        }
        for(int i = 0; i < count.length; i++) {
            while(count[i] > 1) {
                sb.insert(0, (char)('0' + i));
                sb.append((char)('0' + i));
                count[i] -= 2;
            }
            if(count[i] == 1 && sb.length() % 2 == 0) {
                sb.insert(sb.length() / 2, (char)('0' + i));
                count[i]--;
            } else if(count[i] == 1 && sb.length() % 2 == 1) {
                sb.setCharAt(sb.length() / 2, (char)('0' + i));
                count[i]--;
            }
        }
        while(sb.length() > 1 && sb.charAt(0) == '0') {
            sb.deleteCharAt(0);
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.length() > 0 ? sb.toString() : "0";
    }
}
