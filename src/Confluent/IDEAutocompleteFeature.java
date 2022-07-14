package Confluent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IDEAutocompleteFeature {

	public static void main(String[] args) {
		List<String> classes = Arrays.asList("Container", "Panel", "AutoPanel", "ResumePanel", "RegularContainer", "RidePrinter");
	
//		System.out.println("Expected: [ResumePanel, RegularContainer, RidePrinter], Actual: " + autoCompleteIDE("R", classes));
		System.out.println("Expected: [ResumePanel, RegularContainer], Actual: " + autoCompleteIDE("Re", classes));
//		System.out.println("Expected: [ResumePanel, RidePrinter], Actual: " + autoCompleteIDE("RP", classes));
//		System.out.println("Expected: [RidePrinter], Actual: " + autoCompleteIDE("RPr", classes));
	}

	public static List<String> autoCompleteIDE(String input, List<String> classes) {
        List<String> results = new ArrayList<>();
        
        for (String className : classes) {
            int inputIdx = 0;
            int classIdx = 0;

            while (classIdx < className.length())
            {
                if (input.charAt(inputIdx) == className.charAt(classIdx))
                {
                    inputIdx++;
                    if (inputIdx == input.length()) {
                        results.add(className);
                        break;
                    }
                }
                else if (Character.isLowerCase(input.charAt(inputIdx)) 
                		&& input.charAt(inputIdx) == className.charAt(classIdx)) {
                    break;
                }
                classIdx++;
            }
        }

        return results;
    }
}

/**
 * You are asked to build the autocomplete feature for a programming IDE
		Lets say the classes available are :
		
		Container
		Panel
		AutoPanel
		RidePrinter
		ResumePanel
		RegularContainer
		
		The class names are camel case
		
		The upper case letters in the pattern match the different segments of the class names
		
		AutoComplete("R") - > ["ResumePanel", "RegularContainer", "RidePrinter"]
		AutoComplete("Re") - > ["ResumePanel", "RegularContainer"]
		AutoComplete("RP") - > ["ResumePanel", "RidePrinter"]
		AutoComplete("RPr") - > ["RidePrinter"]
	   
	   	Few implementations (along with question): 
	   		- https://leetcode.com/discuss/interview-question/algorithms/132310/ide-autocomplete-feature
	   		-[Python] https://leetcode.com/playground/MHs8BSFc
	   		- https://leetcode.com/discuss/interview-question/algorithms/132310/IDE-Autocomplete-Feature/1286087
*/
