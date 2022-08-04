package Confluent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FunctionLibrary_PhoneScreen_7_14_22 {

	public static void main(String[] args) {

		Function funA = new Function("FuncA", Arrays.asList("Boolean", "Integer"), false);
		Function funB = new Function("FuncB", Arrays.asList("Integer"), false);
		Function funC = new Function("FuncC", Arrays.asList("Integer"), true);
		Function funD = new Function("FuncD", Arrays.asList("String", "Integer", "Integer", "Integer"), true);
		Function funE = new Function("FuncE", Arrays.asList("String", "Integer", "Integer"), false);
		
		Set<Function> set = new HashSet<>(Arrays.asList(funA, funB, funC, funD, funE));
		FunctionLibrary fl = new FunctionLibrary();
		fl.register(set);
		
		System.out.println("Expected: [FunA], Actual: " + fl.findMatches(Arrays.asList("Boolean", "Integer")));
		System.out.println("Expected: [FunB, FunC], Actual: " + fl.findMatches(Arrays.asList("Integer")));
		System.out.println("Expected: [FunC], Actual: " + fl.findMatches(Arrays.asList("Integer", "Integer", "Integer")));
		System.out.println("Expected: [FunD, FunE], Actual: " + fl.findMatches(Arrays.asList("String", "Integer", "Integer")));
		System.out.println("Expected: [FunD], Actual: " + fl.findMatches(Arrays.asList("String", "Integer", "Integer", "Integer", "Integer")));
		
		// Test which is not working - not sure if it's valid
		System.out.println("Expected: [], Actual: " + fl.findMatches(Arrays.asList("String", "Integer")));
		
		
	}
	
	
	/*
	* register[{ 
	*    funA:{["Boolean", "Integer"], isVariadic:false},
	*    funB:{["Integer"], isVariadic:false},
	*    funC:{["Integer"], isVariadic:true}

	* })
	*
	* findMatches(["Boolean", "Integer"])            -> [funA]
	* findMatches(["Integer"])                       -> [funB, funC]
	* findMatches(["Integer", "Integer", "Integer"]) -> [funC]
	*/

	/*
	* More Examples:
	*
	*    funD:{["String", "Integer", "Integer", "Integer"], isVariadic:true},
	*    funE:{["String", "Integer", "Integer"], isVariadic:false}
	*
	* findMatches(["String", "Integer"])             -> [] // I this use case is wrong it should be funD in stead of empty
	* findMatches(["String", "Integer", "Integer"])  -> [funE, funD] # funD due to the supporting 0 variadics
	* findMatches(["String", "Integer", "Integer", "Integer", "Integer"])  -> [funD]
	*/

	
	
	// Mine: Working solution for all the cases except 1 which seems invalid
	
	static class Function {
		public final List<String> argumentTypes; // e.g. ["Integer", "String", "PersonClass"]
		public final String name;
		public final boolean isVariadic;

		Function(String name, List<String> argumentTypes, boolean isVariadic) {
			this.name = name;
			this.argumentTypes = argumentTypes;
			this.isVariadic = isVariadic;
		}

		public String toString() {
			return this.name;
		}
	}
	
	/* Working solution but see the one with single map in this file
	 * 
	 * This implementation uses 2 Maps
	 
	static class FunctionLibrary2 {
		
		Map<String, List<Function>> nonVariadic = new HashMap<>();
		Map<String, List<Function>> variadic = new HashMap<>();
		
		void register(Set<Function> functions) {
			for (Function f : functions) {

				if (f.isVariadic) {
					String key = getKeyForVariadic(f.argumentTypes);
					variadic.putIfAbsent(key, new LinkedList<Function>());
					variadic.get(key).add(f);
				} else {
					String key = getKeyForNonVariadic(f.argumentTypes);
					nonVariadic.putIfAbsent(key, new LinkedList<Function>());
					nonVariadic.get(key).add(f);
				}
			}
		}
		
		List<Function> findMatches(List<String> argumentTypes) {

			List<Function> matches = new ArrayList<>();

			
			String key_Variadic = getKeyForVariadic(argumentTypes);
			String key_NonVariadic = getKeyForNonVariadic(argumentTypes);

			if (nonVariadic.containsKey(key_NonVariadic)) {
				matches.addAll(new LinkedList<Function>(nonVariadic.get(key_NonVariadic)));
			}
			if (variadic.containsKey(key_Variadic)) {
				matches.addAll(new LinkedList<Function>(variadic.get(key_Variadic)));
			}

			return matches;
		}
		
	*/
	
	// with single map
	static class FunctionLibrary {
		
		Map<String, List<Function>> map = new HashMap<>();
		
		void register(Set<Function> functions) {
			for (Function f : functions) {
				String key;
				
				if (f.isVariadic) {
					key = getKeyForVariadic(f.argumentTypes);
				} else {
					key = getKeyForNonVariadic(f.argumentTypes);
				}
				
				map.putIfAbsent(key, new LinkedList<Function>());
				map.get(key).add(f);
			}
		}
		
		List<Function> findMatches(List<String> argumentTypes) {

			List<Function> matches = new ArrayList<>();
			
			String key_Variadic = getKeyForVariadic(argumentTypes);
			String key_NonVariadic = getKeyForNonVariadic(argumentTypes);
			
			boolean isSameKey = key_Variadic.equals(key_NonVariadic);
			
			if(isSameKey) {
				matches.addAll(new LinkedList<Function>(map.get(key_NonVariadic))); //any key as they are same
			} else {
				if (map.containsKey(key_NonVariadic)) {
					matches.addAll(new LinkedList<Function>(map.get(key_NonVariadic)));
				}
				if (map.containsKey(key_Variadic)) {
					matches.addAll(new LinkedList<Function>(map.get(key_Variadic)));
				}
			}

			return matches;
		}
		
		private String getKeyForNonVariadic(List<String> argumentTypes) {
			int n = argumentTypes.size();
		    StringBuilder sb = new StringBuilder();
		    for (int i = 0; i < n; ++i) {
		        String arg = argumentTypes.get(i);
		        if(sb.length() == 0)
		        	sb.append("#");
		        sb.append(arg);
		        sb.append("#");
		    }
		    return sb.toString();
		}
		
		/*
		 * "String", "Integer"
		 * "String", "Integer", "Integer"
		 * "String", "Integer", "Integer", "Integer"
		 * "String", "Integer", "Integer", "Integer", "Integer"
		 * 
		 * All these should be group under key #String#Integer#
		 */
		public String getKeyForVariadic(List<String> args) {
			int n = args.size();
			StringBuilder sb = new StringBuilder();
			
			String arg = args.get(n-1); // last index value
			int count = 0;
			
			/*
			 * Find the index before where variable arg startes: 
			 * 		Loop till you find the index with different parameter than arg
			 * 		and assign that index in count and break
			 */
		    for (int i = n-2; i >= 0; i--) {
		    	String curr = args.get(i);
		    	count = i;
		    	if(curr.equals(arg)) continue;
		    	
		    	break;
		    }
		    /*
		     * Loop till the count index and prepare the key
		     */
		    for(int i=0; i<=count; i++) {
		    	String curr = args.get(i);
		    	if(sb.length() == 0)
		    		sb.append("#");
		    	sb.append(curr);
		    	sb.append("#");
		    	
		    }
		    sb.append(arg);
		    sb.append("#");
		    
		    //System.out.println("Key= " + sb);
		    
		    return sb.toString();
		}
		
	}
	
	
	public static void test_VeriadicAndNonVeriadicKey() {
		FunctionLibrary fl = new FunctionLibrary();
		fl.getKeyForVariadic(Arrays.asList("String", "Integer"));
		fl.getKeyForVariadic(Arrays.asList("String", "Integer", "Integer"));
		fl.getKeyForVariadic(Arrays.asList("String", "Integer", "Integer", "Integer"));
		fl.getKeyForVariadic(Arrays.asList("String", "Integer", "Integer", "Integer", "Integer"));
		
		fl.getKeyForVariadic(Arrays.asList("String", "Integer", "Boolean", "Integer", "Integer"));
		fl.getKeyForVariadic(Arrays.asList("String", "Integer", "String", "Integer", "String"));
	}
	
}
