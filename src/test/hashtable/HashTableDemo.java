package test.hashtable;

public class HashTableDemo {

	public static void main(String[] args) {

		MyHashTable ht = new MyHashTable();
		ht.put("a", "AAA");
		ht.put("b", "BBB");
		ht.put("c", "CCC");
		ht.put("c", "CCCC");
		ht.put("d", "DDD");
		ht.put("e", "EEE");
		ht.put("f", "FFF");
		ht.put("Aa", "AaAa");  //--to test collison : http://stackoverflow.com/questions/12925988/how-to-generate-strings-that-share-the-same-hashcode-in-java
//		ht.put("BB", ">>BBBB");
		
		
		System.out.println(ht.get("c"));
		System.out.println(">>> ht = "+ht); 
		System.out.println(">>> SIZE = "+ht.size); 
		
		MyHashTable ht2 = new MyHashTable(20);
		
		System.out.println(">>> increased SIZE = "+ht2.size); 
		
	}

}


class MyHashTable {
	
	public int size;
	
	//--Declare arrays of lists i.e. of Entry type
	Entry[] buckets;
	
	
	public MyHashTable() {
		size = 10;
		buckets = new Entry[size];
	}
	public MyHashTable(int size) {
		this.size = size;
		buckets = new Entry[size];
	}
	
	private int getBucketLocation(int hashCode) {
		
		return hashCode % size;
	}
	
	public void put(Object k, Object v) {
		
		//--Get bucket location
		int index = getBucketLocation(k.hashCode());
		
		//--Get the Entry oject at above bucket location
		Entry element = buckets[index]; 
		
		//--Interate the list at that index
		while(element != null) {
			
			//--Duplicate
			if(element.key.equals(k)) {
				//--Duplicate key found replace the value...
				System.out.println(">>Duplicate key "+k+" found replcaing the value with new value.");
				element.value=v;
				return; 
			} else {  //--Collision : same bucket location for for a new key
				System.out.println(">>Collision detected, adding the value at same bucket.");
			}
			
			element = element.next;
		}
		
		//--If new element i.e. no duplicate or collision occured
		//--Create new element
		Entry newElement = new Entry(k, v);

		//--adding element to list
		newElement.next = buckets[index];
		
		//insert element at bucket location
		buckets[index] = newElement;
		
	}
	
	public Object get(Object k) {
		
		int index = getBucketLocation(k.hashCode());
		
		Entry element = buckets[index];
		
		while(element != null) {
			
			if(element.key.equals(k)) {
				return element.value;
			}
			
			element = element.next;
		}
		
		System.err.println(">>Value not found.");
		
		return null;
	}
	
	public String toString() {
		String entries = "";
		for (Entry entry : buckets) {
			if(entry != null) {
				entries += entry.toString();
			}
		}
		return "{"+entries+"}";
	}
	
	
}




class Entry {
	
	Object key;
	Object value;
	Entry next;
	
	public Entry(Object key, Object value) {
		this.key=key;
		this.value=value;
	}
	
	public String toString() {
		
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(key);
		stringBuilder.append("=");
		stringBuilder.append(value);
		stringBuilder.append(", ");
		return stringBuilder.toString();
	}
	
}


