import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class HashMapComparator {

	//https://www.java67.com/2015/01/how-to-sort-hashmap-in-java-based-on.html
	//https://beginnersbook.com/2013/12/how-to-sort-hashmap-in-java-by-keys-and-values/
	
	public static void main(String[] args) {

		HashMap<String, String> codenames = new HashMap<String, String>();
		codenames.put("JDK 1.1.4", "Sparkler");
		codenames.put("J2SE 1.2", "Playground");
		codenames.put("J2SE 1.3", "Kestrel");
		codenames.put("J2SE 1.4", "Merlin");
		codenames.put("J2SE 5.0", "Tiger");
		codenames.put("Java SE 6", "Mustang");
		codenames.put("Java SE 7", "Dolphin");

		
		List<Entry<String, String>> entries = new ArrayList<>(codenames.entrySet());
		
		Comparator<Entry<String, String>> mapComparator = new Comparator<Entry<String, String>>() {
			@Override
			public int compare(Entry<String, String> e1, Entry<String, String> e2) {
				String v1 = e1.getValue();
				String v2 = e2.getValue();
				
				return v1.compareTo(v2);
			}
		};
		
		Collections.sort(entries, mapComparator);
		
		LinkedHashMap<String, String> sortedByValue = new LinkedHashMap<>();
		for(Entry<String, String> entry : entries) {
			sortedByValue.put(entry.getKey(), entry.getValue());
		}
		
		System.out.println("HashMap after sorting entries by values ");
		System.out.println(sortedByValue);
		
		
		//2nd approach
		HashMap<String, Integer> hm = new HashMap<String, Integer>(); 
		  
        // enter data into hashmap 
        hm.put("Math", 98); 
        hm.put("Data Structure", 85); 
        hm.put("Database", 91); 
        hm.put("Java", 95); 
        hm.put("Operating System", 79); 
        hm.put("Networking", 80); 
		
		// Sort the list 
        List<Map.Entry<String, Integer> > list = 
                new LinkedList<Map.Entry<String, Integer> >(hm.entrySet()); 

        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() { 
            public int compare(Map.Entry<String, Integer> o1,  
                               Map.Entry<String, Integer> o2) 
            { 
                return (o1.getValue()).compareTo(o2.getValue()); 
            } 
        }); 
		
        HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>(); 
        for (Map.Entry<String, Integer> aa : list) { 
            temp.put(aa.getKey(), aa.getValue()); 
        }
        
        System.out.println(temp);
		
	}

}
