package test.practice.atlassian;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class GetSubsetsDemo {

	public static void main(String... args) {
		
		//List<String> itemList = (Arrays.asList("apple","banana","lemon","berry","orange"));
		List<String> itemList = (Arrays.asList("a","b","c"));
		LinkedList<String> list = new LinkedList<String>(itemList);
		
		List<List<String>> subSetList = new ArrayList<List<String>>();
		
		getSubsets(list, subSetList);
		
		System.out.println("subSetList= "+subSetList);
	}
	
	
	public static void getSubsets(LinkedList<String> list, List<List<String>> subSetList) {
		
		if(list.size() == 0) {
			return;
		}

		getSubsets(list, 0, subSetList);
		
		list.removeLast();
		
		getSubsets(list, subSetList); //--recursing with reduced size
		
		//System.out.println("subSetList= "+subSetList);
		
		return;
	}
	
	public static void getSubsets(LinkedList<String> list, int index, List<List<String>> subSetList) {
		
		List<String> subSets = null;
		
		if(index > list.size()-1) {
			//subSets = new ArrayList<String>();
			//subSetList.add(subSets);
			return;
		}
		
		getSubsets(list, index+1, subSetList);
		
		subSets = new ArrayList<String>();
		
		for(int i=index; i<list.size(); i++) {
			subSets.add(list.get(i));
		}

		subSetList.add(subSets);
		
		return;
	}

}
