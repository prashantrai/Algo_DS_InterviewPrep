package Illumio_coding_assessment;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WordMatchCounter2 {
    public static void main(String[] args) {
    	
    	// Use the relative path to read the file
//        String relativePath = "files/input_file.txt";
//        String absPath = "/Users/prashantrai/eclipse-workspace/assessment/files/input_file.txt";
//        Set<String> words = readPredefinedWords3(relativePath);
//        Set<String> words = readPredefinedWords3(absPath);
//        words.forEach(System.out::println);
    	
    	
    	// Use the relative path to read the file
        String predefinedWordsFile = "files/predefined_words.txt";
        String inputFile = "files/input_file.txt";
        
        // Read predefined words into a HashSet
        Set<String> predefinedWords = readPredefinedWords(predefinedWordsFile);
        
        // Count matches by processing the file line by line
        Map<String, Integer> matchCounts = countMatches(inputFile, predefinedWords);
        
        // Print the results
        printMatchCounts(matchCounts);
    }
    
    
    /**
     * Reads predefined words from a file and stores them in a HashSet for quick lookup.
     * 
     * @param predefinedWordsFile Path to the file containing predefined words.
     * @return A HashSet containing all predefined words in lowercase.
     */
    private static Set<String> readPredefinedWords2(String predefinedWordsFile) {
        Set<String> words = new HashSet<>();
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(predefinedWordsFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                words.add(line.toLowerCase());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return words;
    }
    
    
    // readPredefinedWords with Stream
    /*
	The readPredefinedWords method can be optimized in terms of code readability 
	and possibly performance by using Java Streams. While the overall complexity 
	remains the same, using Streams can make the code more concise and potentially 
	more efficient, as it can leverage internal optimizations.
     */
    private static Set<String> readPredefinedWords(String predefinedWordsFile) {
        try (Stream<String> lines = Files.lines(Paths.get(predefinedWordsFile))) {
            return lines.map(String::toLowerCase)
                        .collect(Collectors.toSet());
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptySet();
        }
    }

    
    
    

    /**
     * When memory usage is kept low
     * 
     * Counts matches of predefined words in the input file by processing it line by line.
     * This method ensures that memory usage is kept low by only holding a single line in memory at a time.
     * 
     * @param inputFile Path to the input file.
     * @param predefinedWords A set of predefined words to match against.
     * @return A map containing the match counts for each predefined word.
     */
    private static Map<String, Integer> countMatches(String inputFile, Set<String> predefinedWords) {
        Map<String, Integer> matchCounts = new HashMap<>();
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(inputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.split("\\W+"); // Split by non-word characters
                for (String word : words) {
                    String lowerCaseWord = word.toLowerCase();
                    if (predefinedWords.contains(lowerCaseWord)) {
                        matchCounts.put(lowerCaseWord, matchCounts.getOrDefault(lowerCaseWord, 0) + 1);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return matchCounts;
    }
    
    // count matches 2 with stream 
    /**
     * Counts matches of predefined words in the input file by processing it line by line.
     * This method ensures that memory usage is kept low by only holding a single line in memory at a time.
     * 
     * @param inputFile Path to the input file.
     * @param predefinedWords A set of predefined words to match against.
     * @return A map containing the match counts for each predefined word.
     */
    private static Map<String, Integer> countMatches2(String inputFile, Set<String> predefinedWords) {
        Map<String, Integer> matchCounts = new HashMap<>();
        Pattern wordPattern = Pattern.compile("\\b\\w+\\b");

        try (Stream<String> lines = Files.lines(Paths.get(inputFile))) {
            lines.forEach(line -> {
                Matcher matcher = wordPattern.matcher(line);
                while (matcher.find()) {
                    String word = matcher.group().toLowerCase();
                    if (predefinedWords.contains(word)) {
                        matchCounts.put(word, matchCounts.getOrDefault(word, 0) + 1);
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return matchCounts;
    }
    
    

    /**
     * Prints the match counts in the specified format.
     * @param matchCounts A map containing the match counts for each predefined word.
     */
    private static void printMatchCounts(Map<String, Integer> matchCounts) {
        System.out.printf("%-20s %s%n", "Predefined word", "Match count");
        for (Map.Entry<String, Integer> entry : matchCounts.entrySet()) {
            System.out.printf("%-20s %d%n", entry.getKey(), entry.getValue());
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