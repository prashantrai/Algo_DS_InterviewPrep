package Confluent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import Confluent.FunctionLibrary_PhoneScreen_Leetcode_Discussion.Function;


public class FunctionLibrary_With_Trie {

	public static void main(String[] args) {
		
		Function funA = new Function("FuncA", Arrays.asList("String", "Integer", "Integer"), false);
		Function funB = new Function("FuncB", Arrays.asList("String", "Integer"), true);
		Function funC = new Function("FuncC", Arrays.asList("Integer"), true);
		Function funD = new Function("FuncD", Arrays.asList("Integer", "Integer"), true);
		Function funE = new Function("FuncE", Arrays.asList("Integer", "Integer", "Integer"), false);
		Function funF = new Function("FuncF", Arrays.asList("String"), false);
		Function funG = new Function("FuncG", Arrays.asList("Integer"), false);
		
		// For these output is not correct
		Function funH = new Function("FuncH", Arrays.asList("String", "Integer", "Integer", "Integer"), true);
		Function funI = new Function("FuncI", Arrays.asList("String", "Integer", "Integer"), false);
		Function funJ = new Function("FuncJ", Arrays.asList("String", "Integer"), false);
		
		//Set<Function> functionSet = new HashSet<>(Arrays.asList(funA, funB, funC, funD, funE, funF, funG, funH, funI, funJ));
		Set<Function> functionSet = new HashSet<>(Arrays.asList(funH, funI));
		
		FunctionLibrary fl = new FunctionLibrary();
		fl.register(functionSet);
		
		/*
		System.out.println("Expected: [FuncF], Actual: " + fl.findMatches(Arrays.asList("String")));
		System.out.println("Expected: [FuncC, FuncG], Actual: " + fl.findMatches(Arrays.asList("Integer")));
		System.out.println("Expected: [FuncC, FuncD], Actual: " + fl.findMatches(Arrays.asList("Integer", "Integer", "Integer", "Integer")));
		System.out.println("Expected: [FuncC, FuncD, FuncE], Actual: " + fl.findMatches(Arrays.asList("Integer", "Integer", "Integer")));
		System.out.println("Expected: [FuncB], Actual: " + fl.findMatches(Arrays.asList("String", "Integer", "Integer", "Integer")));
		System.out.println("Expected: [FuncA, FuncB], Actual: " + fl.findMatches(Arrays.asList("String", "Integer", "Integer")));
		*/
		// Test which are not working
		System.out.println("Expected: [], Actual: " + fl.findMatches(Arrays.asList("String", "Integer")));
		System.out.println("Expected: [FunH, FunI], Actual: " + fl.findMatches(Arrays.asList("String", "Integer", "Integer")));

		System.out.println("Expected: [FunH], Actual: " 
				+ fl.findMatches(Arrays.asList("String", "Integer", "Integer", "Integer", "Integer")));
				
		
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
			//return name + ": " + argumentTypes + "; isVariadic =" + isVariadic;
			return name;
		}
	}

	static class FunctionLibrary {
		Trie trie;

		public FunctionLibrary() {
			trie = new Trie();
		}

		public void register(Set<Function> functionSet) {
			for (Function function : functionSet) {
				register(function);
			}
		}

		private void register(Function function) {
			trie.register(function);
		}

		public List<Function> findMatches(List<String> argumentTypes) {
			// implement this method.
			return trie.findMatches(argumentTypes);
		}
	}

	static class TrieNode {
		String argumentType;
		Map<String, TrieNode> childrenMap;
		List<Function> variadicMatches;
		List<Function> absoluteMatches;

		public TrieNode(String argumentType) {
			this.argumentType = argumentType;
			this.variadicMatches = new ArrayList<>();
			this.absoluteMatches = new ArrayList<>();
			this.childrenMap = new HashMap<>();
		}

		public String toString() {
			return argumentType + " " + childrenMap.keySet() + " " + variadicMatches + " " + absoluteMatches;
		}
	}

	static class Trie {
		TrieNode root;

		public Trie() {
			root = new TrieNode("root");
		}

		public void register(Function function) {
			TrieNode curr = root;
			List<String> argumentTypes = function.argumentTypes;
			for (String argumentType : argumentTypes) {
				if (!curr.childrenMap.containsKey(argumentType)) {
					curr.childrenMap.put(argumentType, new TrieNode(argumentType));
				}
				curr = curr.childrenMap.get(argumentType);
			}

			if (function.isVariadic) {
				curr.variadicMatches.add(function);
			} else {
				curr.absoluteMatches.add(function);
			}
		}

		public List<Function> findMatches(List<String> argumentTypes) {
			TrieNode curr = root;
			Set<Function> matches = new HashSet<>();
			String prevArgumentType = null;
			boolean onlyVariadic = false;
			for (String argumentType : argumentTypes) {
				if (prevArgumentType != null && !prevArgumentType.equals(argumentType)) {
					matches.clear();
				}

				if (onlyVariadic) {
					continue;
				}

				if (!curr.childrenMap.containsKey(argumentType)) {
					curr = null;
					onlyVariadic = true;
					continue;
				}

				curr = curr.childrenMap.get(argumentType);

				for (Function variadicMatch : curr.variadicMatches) {
					matches.add(variadicMatch);
				}

				prevArgumentType = argumentType;
			}

			if (curr != null) {
				matches.addAll(curr.absoluteMatches);
			}
			return new ArrayList<>(matches);
		}
	}

}
