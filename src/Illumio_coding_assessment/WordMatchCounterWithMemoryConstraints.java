package Illumio_coding_assessment;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class WordMatchCounterWithMemoryConstraints {
	public static void main(String[] args) {
		String predefinedWordsFile = "files/predefined_words.txt";
    	String inputFile = "files/input_file.txt";
        
        // Create a temporary file to store the lowercase versions of predefined words
        File tempPredefinedWordsFile = createTempPredefinedWordsFile(predefinedWordsFile);
        
        // Count matches by processing the input file line by line
        Map<String, Integer> matchCounts = countMatches(inputFile, tempPredefinedWordsFile);
        
        // Print the results
        printMatchCounts(matchCounts, new File(predefinedWordsFile));
        
        // Delete the temporary file
        tempPredefinedWordsFile.delete();
    }

    /**
     * Creates a temporary file containing lowercase versions of predefined words.
     * 
     * @param predefinedWordsFile Path to the file containing predefined words.
     * @return The temporary file containing lowercase versions of predefined words.
     */
    private static File createTempPredefinedWordsFile(String predefinedWordsFile) {
        File tempFile = null;
        try {
            tempFile = File.createTempFile("predefinedWords", ".tmp"); //creates file in a tmp folder which gets deleted by calling tempPredefinedWordsFile.delete(); 
            System.out.println("tempFile.getAbsolutePath():: "+ tempFile.getAbsolutePath());
            
            try (BufferedReader reader = Files.newBufferedReader(Paths.get(predefinedWordsFile));
                 BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    writer.write(line.toLowerCase());
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tempFile;
    }

    /**
     * Counts matches of predefined words in the input file by processing it line by line.
     * This method ensures that memory usage is kept low by only holding a single line in memory at a time.
     * @param inputFile Path to the input file.
     * @param tempPredefinedWordsFile The temporary file containing lowercase versions of predefined words.
     * @return A map containing the match counts for each predefined word in lowercase.
     */
    private static Map<String, Integer> countMatches2(String inputFile, File tempPredefinedWordsFile) {
        Map<String, Integer> matchCounts = new HashMap<>();
        Pattern wordPattern = Pattern.compile("\\b\\w+\\b");

        try (Stream<String> lines = Files.lines(Paths.get(inputFile))) {
            lines.forEach(line -> {
                Matcher matcher = wordPattern.matcher(line);
                while (matcher.find()) {
                    String word = matcher.group().toLowerCase();
                    if (isWordInTempFile(word, tempPredefinedWordsFile)) {
                        matchCounts.put(word, matchCounts.getOrDefault(word, 0) + 1);
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return matchCounts;
    }
    
    
	private static Map<String, Integer> countMatches(String inputFile, File tempPredefinedWordsFile) {
        Map<String, Integer> matchCounts = new HashMap<>();
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(inputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
            	
                String[] words = line.split("\\W+"); // Split by non-word characters
                for (String word : words) {
                    String lowerCaseWord = word.toLowerCase();
                    if (isWordInTempFile(word, tempPredefinedWordsFile)) {
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
     * Checks if a word exists in the temporary file containing predefined words.
     * @param word The word to check.
     * @param tempPredefinedWordsFile The temporary file containing lowercase versions of predefined words.
     * @return True if the word exists in the temporary file, false otherwise.
     */
    private static boolean isWordInTempFile(String word, File tempPredefinedWordsFile) {
        try (BufferedReader reader = new BufferedReader(new FileReader(tempPredefinedWordsFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.equals(word)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Prints the match counts in the specified format using the original case of the predefined words.
     * @param matchCounts A map containing the match counts for each predefined word in lowercase.
     * @param tempPredefinedWordsFile The temporary file containing lowercase versions of predefined words.
     */
    /*private static void printMatchCounts(Map<String, Integer> matchCounts, File predefinedWordsFile, File tempPredefinedWordsFile) {
        System.out.printf("%-20s %s%n", "Predefined word", "Match count");
        
        // Create a map of lowercase to original case words
        Map<String, String> originalCaseMap = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(tempPredefinedWordsFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                originalCaseMap.put(line.toLowerCase(), line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        for (Map.Entry<String, Integer> entry : matchCounts.entrySet()) {
            String originalCaseWord = originalCaseMap.get(entry.getKey());
            System.out.printf("%-20s %d%n", originalCaseWord, entry.getValue());
        }
    }*/
    
    private static void printMatchCounts(Map<String, Integer> matchCounts, File predefinedWordsFile) {
        System.out.printf("%-20s %s%n", "Predefined word", "Match count");
        
        // Create a map of lowercase to original case words
        Map<String, String> originalCaseMap = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(predefinedWordsFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                originalCaseMap.put(line.toLowerCase(), line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        for (Map.Entry<String, Integer> entry : matchCounts.entrySet()) {
            String originalCaseWord = originalCaseMap.get(entry.getKey());
            System.out.printf("%-20s %d%n", originalCaseWord, entry.getValue());
        }
    }
    
    
    /////// - Wihout Temp File, i.e. reading directly from predefinedWordsFile
    
  private static Map<String, Integer> countMatchesWithPredfinedFile(String inputFile, String predefinedWordsFile) {
      Map<String, Integer> matchCounts = new HashMap<>();
      try (BufferedReader reader = Files.newBufferedReader(Paths.get(inputFile))) {
          String line;
          while ((line = reader.readLine()) != null) {
          	
              String[] words = line.split("\\W+"); // Split by non-word characters
              for (String word : words) {
                  String lowerCaseWord = word.toLowerCase();
                  if (isWordInPredefinedFile(word, predefinedWordsFile)) {
                      matchCounts.put(lowerCaseWord, matchCounts.getOrDefault(lowerCaseWord, 0) + 1);
                  }
              }
          }
      } catch (IOException e) {
          e.printStackTrace();
      }
      return matchCounts;
  }
  
  private static boolean isWordInPredefinedFile(String word, String predefinedWordsFile) {

  	try (BufferedReader reader = Files.newBufferedReader(Paths.get(predefinedWordsFile))) {
          String line;
          while ((line = reader.readLine()) != null) {
              if (line.toLowerCase().equals(word)) {
                  return true;
              }
          }
      } catch (IOException e) {
          e.printStackTrace();
      }
      return false;
  }
  
  private static void printMatchCountsWithPredefinedFile(Map<String, Integer> matchCounts, String predefinedWordsFile) {
      System.out.printf("%-20s %s%n", "Predefined word", "Match count");
      
      // Create a map of lowercase to original case words
      Map<String, String> originalCaseMap = new HashMap<>();
      try (BufferedReader reader = Files.newBufferedReader(Paths.get(predefinedWordsFile))) {
          String line;
          while ((line = reader.readLine()) != null) {
              originalCaseMap.put(line.toLowerCase(), line);
          }
      } catch (IOException e) {
          e.printStackTrace();
      }
      
      for (Map.Entry<String, Integer> entry : matchCounts.entrySet()) {
          String originalCaseWord = originalCaseMap.get(entry.getKey());
          System.out.printf("%-20s %d%n", originalCaseWord, entry.getValue());
      }
  }
  
  //////
    
    
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