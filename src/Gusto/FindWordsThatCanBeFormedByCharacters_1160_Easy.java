package Gusto;

public class FindWordsThatCanBeFormedByCharacters_1160_Easy {

	public static void main(String[] args) {
		String[] words = {"cat","bt","hat","tree"};
		String chars = "atach";
		System.out.println("Expected: 6 Actual: " + countCharacters(words, chars));
	}

	public static int countCharacters(String[] words, String chars) {
        int count=0;
        int[] freq = new int[26];
        //chars.chars().forEach(c -> freq[c - 'a']++);
        for(char c : chars.toCharArray()) {
            freq[c-'a']++;
        }
        
        for(String word : words) {
            int[] tempFrq = freq.clone();
            int tCount = 0;
            for(char c : word.toCharArray()) {
                if(tempFrq[c - 'a'] > 0) {
                    tempFrq[c - 'a']--;
                    tCount++;
                }
            }
            if(tCount == word.length()) 
                count += tCount;
        }
        return count;
    }
	
}
