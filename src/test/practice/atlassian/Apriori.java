package test.practice.atlassian;

//This is the text editor interface. 
//Anything you type or change here will be seen by the other person in real time.

/*

Apriori Problem (~45 min)

Given a list of transactions, How can we calculate the frequency
counts of all possible item-sets?

For example,

[INPUT] list of transactions
| -- | -----------------------------|
| ID | Purchased items              |
| -- | -----------------------------|
| 1  | apple, banana, lemon         |
| 2  | banana, berry, lemon, orange |
| 3  | banana, berry, lemon         |
| -- | -----------------------------|


[OUTPUT] frequency counts of all possible item-sets. Note: some
outputs are omitted for brevity.
| ---------------------------- | --------- |
| Itemset                      | Frequency |
| ---------------------------- | --------- |
| apple, banana                | 1         |
| apple, lemon                 | 1         |
| banana, berry                | 2         |
| banana, lemon                | 3         |
| ...                                      |
| apple, banana, lemon         | 1         |
| banana, berry, lemon         | 2         |
| ...                                      |
| banana, berry, lemon, orange | 1         |
| ...                                      |
| ---------------------------- | --------- |

*/

import java.util.*;
import java.util.Map.Entry;

public class Apriori {

	public static void main(String[] args){
		
		Map<String, List<String>> txn = new HashMap<String, List<String>>();
		txn.put("1", Arrays.asList("apple","banana","lemon"));
		txn.put("2", Arrays.asList("banana","berry","lemon", "orange"));
		txn.put("3", Arrays.asList("banana","berry","lemon"));
		
		createFreqTableForSingleItem(txn);
		
		List<List<String>> txnList = new ArrayList<List<String>>();
		txnList.add(Arrays.asList("apple","banana","lemon"));
		txnList.add(Arrays.asList("banana","berry","lemon","orange"));
		txnList.add(Arrays.asList("banana","berry","lemon"));
		
		Set<String> set = getListOfItems(txnList);
		
		createFreqTableForTwoItems(set, txnList);
		
	}

	
	/*
	 >>>> Items:: [
	 					[name=[banana, orange], freq=1], 
	 					[name=[orange, orange], freq=1], 
	 					[name=[apple, orange], freq=1], 
	 					[name=[lemon, orange], freq=1], 
	 					[name=[berry, orange], freq=1]] 
	 * */
	
	public static void createFreqTableForTwoItems(Set<String> set, List<List<String>> txnList) {
		
		List<Item> items = new ArrayList<Item>();
		
		List<String> itemsList = new ArrayList<String>(set);
		
		Item item;
		for (int i=0; i<itemsList.size()-1; i++) {
			for (int j=i+1; j<itemsList.size(); j++) {
				
				List<String> pair = new ArrayList<String>();
				pair.add(itemsList.get(i));
				pair.add(itemsList.get(j));
				
				int freq = getFrequency(pair, txnList);
				item = new Item(freq, pair);
				items.add(item);
				
				System.out.println(">>>> ["+itemsList.get(i)+"-"+itemsList.get(j)+"]");
			}
		}
		
		System.out.println(">>>> Items:: "+items);
	}
	
	/*	txnList.add(Arrays.asList("apple","banana","lemon"));
		txnList.add(Arrays.asList("banana","berry","lemon","orange"));
		txnList.add(Arrays.asList("banana","berry","lemon"));
		
		
		Items:: [
					[name=[banana, orange], freq=1], 
					[name=[banana, apple], freq=1], 
					[name=[banana, lemon], freq=3], 
					[name=[banana, berry], freq=2], 
					
					[name=[orange, apple], freq=0], 
					[name=[orange, lemon], freq=1], 
					[name=[orange, berry], freq=1], 
					
					[name=[apple, lemon], freq=1], 
					[name=[apple, berry], freq=0], 
					
					[name=[lemon, berry], freq=2]
				]
	
		
*/
	
	
	
	public static int getFrequency(List<String> pair, List<List<String>> txnList) {
		
		int freq = 0;
		boolean isAvail = false;
		
		for (List<String> list : txnList) {
			for (String itemName : pair) {
				if(list.contains(itemName)) {
					isAvail = true;
				} else {
					isAvail = false;
					break;
				}
			}
			if(isAvail) {
				freq++;
			}
		}
		
		return freq;
	}
	
	
	public static Set<String> getListOfItems(List<List<String>> txnList) {
		
		//List<String> items = new ArrayList();
		Set<String> set = new HashSet<String>();
		for (List<String> list : txnList) {
			set.addAll(list);
		}
		System.out.println(">>> set: "+set);
		
		return set;
	}
	
	
	/*>>>singleItemFreqMap :: 
	 * {
	 * banana=[name=[banana], freq=3], 
	 * orange=[name=[orange], freq=1], 
	 * apple=[name=[apple], freq=1], 
	 * lemon=[name=[lemon], freq=3], 
	 * berry=[name=[berry], freq=2]
	 * }
	 */
	
	public static void createFreqTableForSingleItem(Map<String, List<String>> txn) {
		
		Map<String, Item> singleItemFreqMap = new HashMap<String, Item>();
		
		Collection<List<String>> v = txn.values();
		
		for (List<String> list : v) {
			
			for (int i=0; i<list.size(); i++) {
				
				String key = list.get(i);
				
				if(singleItemFreqMap.containsKey(key)) {
					Item item = singleItemFreqMap.get(key);
					item.incrementFreqByOne();
					
				} else {
					singleItemFreqMap.put(key, new Item(1, list.get(i)));
				}
			}
				
		}
		
		System.out.println(">>>singleItemFreqMap :: "+singleItemFreqMap);
		
	}
	
	static class Item {
		List<String> name;
		int freq;
		
		public Item (int freq, String... name) {
			this.name = Arrays.asList(name);
			this.freq = freq;
		}
		
		public Item (int freq, List<String> name) {
			this.name = name;
			this.freq = freq;
		}
		
		public void setFreq(int freq) {
			this.freq = freq;
		}
		
		public void incrementFreqByOne() {
			this.freq++;
		}
		
		public String toString() {
			return "[name=" + name + ", freq=" + freq + "]";
		}
	}
	
	
}

/*public class Apriori {
 public static void main(String[] args){

     List<List<String>> txnList = new ArrayList<List<String>>();
     List<String> tx = new ArrayList<String>();
     tx.add("apple");
     tx.add("banana");
     tx.add("lemon");
     txnList.add(tx);
     // txnList.add("apple, banana, lemon");
     // txnList.add("banana, berry, lemon, orange");
     // txnList.add("banana, berry, lemon");
     
     itemsets(tx);

 } 
 public static List<List<String>> itemsets(List<String> transaction) {
     
     List<List<String>> itemsets = new ArrayList<ArrayList<String>>();
     
     List<String> itemSet = null;
     for(int i=0; i<transaction.size(); i++) {
         itemSet = new ArrayList<String>();
         itemSet.add(transaction.get(i));
         
         for(int j=i+1; j<transaction.size(); j++) { 
             itemSet.add(transaction.get(j));
         }
         itemsets.add(itemSet);
     }
     
     [a, b, l] []
     a  [b, l] [a]
     b  [l]    [ab, b, a]
     l  []     [abl, bl, al, l, ab, b, a]
     
 }
 public static Map<String, String> frequency(List<List<String>> itemSets) {
     
 }
 
 public static void createItemSetMapping(String item1, String item2) {
     
     Map<String, String> map =  new HashMap<String, String>();
     map.put("apple", "banana"); //--TODO: replae with var
 
         
      
 }  */  
 
