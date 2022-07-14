package Confluent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FunctionLibrary_PhoneScreen_Leetcode_Discussion {

	public static void main(String[] args) {
		
		Function funA = new Function("FuncA", Arrays.asList("String", "Integer", "Integer"), false);
		Function funB = new Function("FuncB", Arrays.asList("String", "Integer"), true);
		Function funC = new Function("FuncC", Arrays.asList("Integer"), true);
		Function funD = new Function("FuncD", Arrays.asList("Integer", "Integer"), true);
		Function funE = new Function("FuncE", Arrays.asList("Integer", "Integer", "Integer"), false);
		Function funF = new Function("FuncF", Arrays.asList("String"), false);
		Function funG = new Function("FuncG", Arrays.asList("Integer"), false);
		
		Set<Function> functionSet = new HashSet<>(Arrays.asList(funA, funB, funC, funD, funE, funF, funG));
		
		FunctionLibrary fl = new FunctionLibrary();
		fl.register(functionSet);
		
		System.out.println("Expected: [FuncF], Actual: " + fl.findMatches(Arrays.asList("String")));
		System.out.println("Expected: [FuncC, FuncG], Actual: " + fl.findMatches(Arrays.asList("Integer")));
		System.out.println("Expected: [FuncC, FuncD], Actual: " + fl.findMatches(Arrays.asList("Integer", "Integer", "Integer", "Integer")));
		
		System.out.println("Expected: [FuncC, FuncD, FuncE], Actual: " + fl.findMatches(Arrays.asList("Integer", "Integer", "Integer")));

		System.out.println("Expected: [FuncB], Actual: " + fl.findMatches(Arrays.asList("String", "Integer", "Integer", "Integer")));

		System.out.println("Expected: [FuncA, FuncB], Actual: " + fl.findMatches(Arrays.asList("String", "Integer", "Integer")));
		
	}

	// Working
	// https://leetcode.com/discuss/interview-question/759611/confluent-senior-software-engineer-phone-interview
	// Refer the same for Trie implementation: https://leetcode.com/discuss/interview-question/759611/Confluent-or-Senior-Software-Engineer-or-Phone-interview/1353554
	
	static class FunctionLibrary {
	    Map<String, List<Function>> nonVariadic = new HashMap<>();
	    Map<String, List<Function>> variadic = new HashMap<>();
	    
	    public void register(Set<Function> functionSet)  {
	        for (Function f: functionSet) {
	            String key = appendArgs(f.argumentTypes, f.argumentTypes.size());
	            if (f.isVariadic) {
	                variadic.putIfAbsent(key, new LinkedList<Function>());
	                variadic.get(key).add(f);
	            } else {
	                nonVariadic.putIfAbsent(key, new LinkedList<Function>());
	                nonVariadic.get(key).add(f);
	            }
	        }
	    }

		public List<Function> findMatches(List<String> argumentTypes) {
		    List<Function> matches = new ArrayList<>();
		    String key = appendArgs(argumentTypes, argumentTypes.size());
	
		    if (nonVariadic.containsKey(key)) {
		        matches.addAll(new LinkedList<Function>(nonVariadic.get(key)));
		    }
		    if (variadic.containsKey(key)) {
		        matches.addAll(new LinkedList<Function>(variadic.get(key)));
		    }
	
		    int count = argumentTypes.size();
		    for (int i = argumentTypes.size()-2; i>=0; --i) {
		        if (argumentTypes.get(i).equals(argumentTypes.get(i+1))) {
		            --count;
		        } else {
		            break;
		        }
		        key = appendArgs(argumentTypes, count);
		        if (variadic.containsKey(key)) {
		            matches.addAll(new LinkedList<Function>(variadic.get(key)));
		        }
		    }
	
		    return matches;
		}
	
		public String appendArgs(List<String> argumentTypes, int n) {
		    StringBuilder sb = new StringBuilder();
		    for (int i = 0; i < n; ++i) {
		        String arg = argumentTypes.get(i);
		        sb.append(arg);
		        sb.append("+");
		    }
		    return sb.toString();
		}

	}

	
	static class Function {
		String name;
		List<String> argumentTypes;
		boolean isVariadic;
		
		Function(String name, List<String> argumentTypes, boolean isVariadic) {
			this.name = name;
			this.argumentTypes = argumentTypes;
			this.isVariadic = isVariadic;
		}
		
		public String toString() {
			return name;
		}
	}
}


/**
 * Taken from:
 * https://leetcode.com/discuss/interview-question/759611/confluent-senior-software-engineer-phone-interview
 * 
 * Suppose you are building a library and have following definition of a
 * function and two methods register and findMatches. 
 * 
 * Register method registers a function and its argumentTypes in the library and findMatches accepts an
 * input argument list and tries to find all the functions that match this input
 * argument list.
 * 
 * Note:
	If a function is marked as isVariadic=true, then the last argument can occur one or more number of times.
	
	Example:
	FuncA: [String, Integer, Integer]; isVariadic = false
	FuncB: [String, Integer]; isVariadic = true
	FuncC: [Integer]; isVariadic = true
	FuncD: [Integer, Integer]; isVariadic = true
	FuncE: [Integer, Integer, Integer]; isVariadic = false
	FuncF: [String]; isVariadic = false
	FuncG: [Integer]; isVariadic = false
	
	findMatches({String}) -> [FuncF]
	findMatches({Integer}) -> [FuncC, FuncG]
	findMatches({Integer, Integer, Integer, Integer}) -> [FuncC, FuncD]
	findMatches({Integer, Integer, Integer}) -> [FuncC, FuncD, FuncE]
	findMatches({String, Integer, Integer, Integer}) -> [FuncB]
	findMatches({String, Integer, Integer}) -> [FuncA, FuncB]
 

	static class Function {
		String name;
		List<String> argumentTypes;
		boolean isVariadic;
		
		Function(String name, List<String> argumentTypes, boolean isVariadic) {
			this.name = name;
			this.argumentTypes = argumentTypes;
			this.isVariadic = isVariadic;
		}
	}

	static class FunctionLibrary {
		public void register(Set<Function> functionSet)  {
			// implement this method.
		}
		
		public List<Function> findMatches(List<String> argumentTypes) {
			// implement this method.
			return null;
		}
	}

* 
*/
