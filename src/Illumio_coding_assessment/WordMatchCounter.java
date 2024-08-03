package Illumio_coding_assessment;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class WordMatchCounter {
    public static void main(String[] args) {
    	
    	// Use the relative path to read the file
        String predefinedWordsFile = "predefined_words.txt";
        String inputFile = "input_file.txt";
        
        // Read predefined words into a HashSet
        Map<String, String> predefinedWordsMap = readPredefinedWords(predefinedWordsFile);

        // Count matches by processing the file line by line
        Map<String, Integer> matchCountsMap = countMatches(inputFile, predefinedWordsMap);
        
        // Print the results
        printMatchCounts(matchCountsMap, predefinedWordsMap);
        
    }
    
    
    /*
	Time Complexity: O(N*L), where N is the number of lines in the input file, 
		and  L is the average number of words per line. 
		
	Space Complexity:  O(W+P), where  W is the number of predefined words that 
		have at least one match, and P is the number of predefined words.
     * */
    
    /**
     * Reads predefined words from a file and stores them in a HashMap for O(1) lookup.
     * @param predefinedWordsFile Path to the file containing predefined words.
     * @return A HashMap containing all predefined words in lowercase as key and original 
     * case as value as defined in 'predefined_words.txt'.
     */
    private static Map<String, String> readPredefinedWords(String predefinedWordsFile) {
    	// to keep the exact case of predefined words, this will help print the output
        Map<String, String> wordsMap = new HashMap<>(); 
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(predefinedWordsFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                wordsMap.put(line.toLowerCase(), line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wordsMap;
    }
    

    /**
     * Counts matches of predefined words in the input file by processing it line by line.
     * This method ensures that memory usage is kept low by only holding a single line in memory at a time.
     * 
     * @param inputFile Path to the input file.
     * @param predefinedWords A map of predefined words to match against.
     * @return A map containing the match counts for each predefined word.
     */
    private static Map<String, Integer> countMatches(String inputFile, Map<String, String> predefinedWordsMap) {
        Map<String, Integer> matchCounts = new HashMap<>();
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(inputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
            	/* 
            	 * \\W+: Matches one or more consecutive non-word characters, 
            	 * effectively identifying separators between words.
            	 */
                String[] words = line.split("\\W+"); // Split by non-word characters
                for (String word : words) {
                    String lowerCaseWord = word.toLowerCase();
                    if (predefinedWordsMap.containsKey(lowerCaseWord)) {
                        matchCounts.put(lowerCaseWord, matchCounts.getOrDefault(lowerCaseWord, 0) + 1);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return matchCounts;
    }

    
    /**
     * Prints the match counts in the specified format.
     * @param matchCounts A map containing the match counts for each predefined word.
     * @param predefinedWordsMap A map containing predefined word. 
     * Mapping lowercase to it's original case as value as defined in 'predefined_words.txt'. 
     */
    private static void printMatchCounts(Map<String, Integer> matchCounts, Map<String, String> predefinedWordsMap) {
        System.out.printf("%-20s %s%n", "Predefined word", "Match count");
        for (Map.Entry<String, Integer> entry : matchCounts.entrySet()) {
        	String originalCaseKey = predefinedWordsMap.get(entry.getKey());
            System.out.printf("%-20s %d%n", originalCaseKey, entry.getValue());
        }
    }
}



/**

Coding Assessment 

Task 1: Description: 
Write a program that reads a file and finds matches against a predefined set of words.

There can be up to 10K entries in the list of predefined words. 
The output of the program should look something like this: 

Predefined word           Match count 
FirstName                 3500 
LastName                  2700 
Zipcode                   1601 

 

Requirement details:  

- Input file is a plain text (ascii) file, every record separated by a new line. 
- For this exercise, assume English words only 
- The file size can be up to 20 MB 
- The predefined words are defined in a text file, every word separated by a newline. 
- Use a sample file of your choice for the set of predefined keywords for the exercise. 
- Assume that the predefined words file doesn’t contain duplicates. 
- Maximum length of the word can be upto 256 

- Matches should be *case-insensitive* 

- The match should be word to word match, no substring matches.  


Consider a sample file with only the following two lines (defined in a text file): 

Line 1: Detecting first names is tricky to do even with AI. 

Line 2: how do you say a street name is not a first name?  

  

And a sample list of predefined words (defined in a text file): 

Name 

Detect 

AI 


The match should happen only for the word “AI” in the first line and the 
word “name” in the second line.  The word “Detect” should not match. 

If there is any aspect of the requirement or question is not clear, 
please make reasonable assumptions and document them in the README file 
to be submitted with the assignment. 
 
 * */