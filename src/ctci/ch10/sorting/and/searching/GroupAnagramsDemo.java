package ctci.ch10.sorting.and.searching;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroupAnagramsDemo {

//	/aba, baa, aab, aabb, zzz, zzcc, cczz, c, c
	
	public static void main(String[] args) {

		String[] arr = {"aba", "baa", "aab", "aabb", "zzz", "zzcc", "cczz", "c", "c" };
		sort(arr);
		System.out.println(">> "+sort_2(arr));
	}
	
	public static void sort(String[] arr) {
		HashMap<String, List<String>> mapList = new HashMap<String, List<String>>();
		
		for(String s : arr) {
			String key = sort(s);
			
			if(!mapList.containsKey(key)) {
				ArrayList<String> list = new ArrayList<String>();
				list.add(s);
				mapList.put(key, list);
			} else {
				ArrayList<String> list = (ArrayList<String>) mapList.get(key);
				list.add(s);
			}
		}
		
		System.out.println("mapList:: "+mapList);
		System.out.println("mapList:: "+mapList.values());
	}
	
	
//	public static List<List<String>> sort_2(String[] arr) {
	public static Collection<List<String>> sort_2(String[] arr) {
		HashMap<String, List<String>> mapList = new HashMap<String, List<String>>();
		
		for(String s : arr) {
			String key = sort(s);
			
			if(!mapList.containsKey(key)) {
				List<String> list = new ArrayList<String>();
				list.add(s);
				mapList.put(key, list);
			} else {
				List<String> list = (List<String>) mapList.get(key);
				list.add(s);
			}
		}
		
		System.out.println("mapList:: "+mapList);
		System.out.println("mapList:: "+mapList.values());
		
		//List<String> res = new ArrayList<String> (mapList.values());
		//return res
		return mapList.values();
	}
	
	private static String sort(String s) {
		char[] cArr = s.toCharArray();
		Arrays.sort(cArr);
		return new String(cArr);
	}
	
	//leet code
	public List<List<String>> groupAnagrams(String[] strs) {
        
        Map<String, List<String>> map = new HashMap<String, List<String>>();
        
        for(String s : strs) {
            String key = sort(s);
            
            if(map.containsKey(key)) {
                List l = map.get(key);
                l.add(s);
                //map.put(key, l);
            } else {
                List l = new ArrayList<String>();
                l.add(s);
                map.put(key, l);
            }
        }
        
       return new ArrayList<List<String>> (map.values());
    }

}
