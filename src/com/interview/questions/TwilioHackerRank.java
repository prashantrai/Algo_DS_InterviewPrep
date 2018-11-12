package com.interview.questions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class TwilioHackerRank {

	public static void main(String[] args) {

		System.out.println("hi");

		// int[] arr = {4,5,6,5,4,3};
		List<Integer> arr = Arrays.asList(4, 5, 6, 5, 4, 3); 
		System.out.println("->"+arr);
		sortByFreq(arr);

	}

	

	// --sort num by freq and if the freq is same then by value
	public static void sortByFreq(List<Integer> arr) {
		Map<Integer, Integer> map = new TreeMap<>();

		/* Logic to place the elements to Map */
		for (int i = 0; i < arr.size(); i++) {
			if (map.get(arr.get(i)) == null) {
				map.put(arr.get(i), 1);
			} else {
				int frequency = map.get(arr.get(i));
				map.put(arr.get(i), frequency + 1);
			}
		}

		System.out.println(map);

		List list = new LinkedList(map.entrySet());

		/* Sort the list elements based on frequency */
		Collections.sort(list, new Comparator() {
			@Override
			public int compare(Object obj1, Object obj2) {
				return ((Comparable) ((Map.Entry) (obj1)).getValue()).compareTo(((Map.Entry) (obj2)).getValue());
			}
		});

		int count = 0;

		/* Place the elements in to the array based on frequency */
		for (Iterator it = list.iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();

			int key = (int) entry.getKey();
			int val = (int) entry.getValue();

			for (int i = 0; i < val; i++) {
				arr.add(count, key);
				count++;
			}
		}

		System.out.println(arr);
	}

	// -version 2 - not working
	public static void customSort(List<Integer> arr) {

		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();

		for (int i = 0; i < arr.size(); i++) {
			if (map.containsKey(arr.get(i))) {
				Integer c = map.get(arr.get(i)) + 1;
				map.put(arr.get(i), c);
			} else {
				map.put(arr.get(i), 1);
			}
		}
		System.out.println(">> " + map);

		List<Integer> result = new ArrayList<>();
		int index = 0;
		while (!map.isEmpty()) {
			int curMin = Integer.MAX_VALUE;
			int curNumberWithMinOccur = -1;
			for (Entry<Integer, Integer> entry : map.entrySet()) {
				if (entry.getValue() < curMin) {
					curMin = entry.getValue();
					curNumberWithMinOccur = entry.getKey();
				}
			}
			result.add(index, curNumberWithMinOccur);
			index++;
			map.remove(curNumberWithMinOccur);

		}
		for (int i = 0; i < result.size(); i++) {
			System.out.print(result.get(i) + " ");
		}

	}

	public static void fun() {

	}

}
