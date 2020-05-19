package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class DesignLogStorageSystem_635_Medium {

	public static void main(String[] args) {

		LogSystem obj = new LogSystem();
		// obj.put(id,timestamp);
		// List<Integer> param_2 = obj.retrieve(s,e,gra);
		/*
		 * obj.put(1, "2017:01:01:23:59:59"); obj.put(2, "2017:01:01:22:59:59");
		 * obj.put(3, "2016:01:01:00:00:00"); List<Integer> param_1 =
		 * obj.retrieve("2016:01:01:01:01:01","2017:01:01:23:00:00","Year"); // return
		 * [1,2,3], because you need to return all logs within 2016 and 2017.
		 * List<Integer> param_2 =
		 * obj.retrieve("2016:01:01:01:01:01","2017:01:01:23:00:00","Hour"); // return
		 * [1,2], because you need to return all logs start from 2016:01:01:01 to
		 * 2017:01:01:23, where log 3 is left outside the range.
		 * 
		 * System.out.println("param_1:: "+param_1);
		 * System.out.println("param_2:: "+param_2);
		 */

		// ["LogSystem","put","put","retrieve"]
		// [[],[1,"2017:01:01:23:59:59"],[2,"2017:01:02:23:59:59"],["2017:01:01:23:59:59","2017:01:02:23:59:59","Month"]]

		obj.put(1, "2017:01:01:23:59:59");
		obj.put(2, "2017:01:02:23:59:59");
		List<Integer> param_1 = obj.retrieve("2017:01:01:23:59:59", "2017:01:02:23:59:59", "Month");
		System.out.println("param_1:: " + param_1);

	}

	// --https://leetcode.com/problems/design-log-storage-system/discuss/105006/Java-range-query-using-TreeMap.subMap()

	/*
	 * Performance Analysis: 
	 * 
	 * The TreeMap is maintained internally as a Red-Black(balanced) tree. Thus, the
	 * put method takes O(log(n)) time to insert a new entry into
	 * the given set of logs. Here, n refers to the number of entries currently
	 * present in the given set of logs.
	 * 
	 * The retrieve method takes O(m_start) time to retrieve the logs
	 * in the required range. Determining the granularity takes O(1) time. To
	 * find the logs in the required range, we only need to iterate over those
	 * elements which already lie in the required range. 
	 * 
	 * Here, m_start refers to the number of entries in the current set of logs which have a
	 * timestamp greater than the current start value.
	 * 
	 */
	static class LogSystem {

		Map<String, Integer> graIndexMap; // granularity Index Map;
		TreeMap<String, List<Integer>> logs;
		static final String start = "2000:01:01:00:00:00";
		static final String end = "2017:12:31:23:59:59";

		public LogSystem() {
			logs = new TreeMap<>();
			graIndexMap = new HashMap<>();
			graIndexMap.put("Year", 4); // index of : in timestamp
			graIndexMap.put("Month", 7);
			graIndexMap.put("Day", 10);
			graIndexMap.put("Hour", 13);
			graIndexMap.put("Minute", 16);
			graIndexMap.put("Second", 19);
		}

		public void put(int id, String timestamp) {
			if (!logs.containsKey(timestamp))
				logs.put(timestamp, new ArrayList<Integer>());

			logs.get(timestamp).add(id);
		}

		public List<Integer> retrieve(String s, String e, String gra) {
			List<Integer> res = new ArrayList<>();
			// this will creata a range, we substring the start and time based on the index
			// of granularity and then append the start and time from final date range and
			// from the index till the lenght of the string.
			int idx = graIndexMap.get(gra);
			String st_sub = s.substring(0, idx) + start.substring(idx);
			String end_sub = e.substring(0, idx) + end.substring(idx);

			// submap returns Navigable map
			Map<String, List<Integer>> subMap = logs.subMap(st_sub, true, end_sub, true);
			for (String key : subMap.keySet()) {
				res.addAll(subMap.get(key));
			}

			return res;

		}
	}

	/**
	 * Your LogSystem object will be instantiated and called as such: LogSystem obj
	 * = new LogSystem(); obj.put(id,timestamp); List<Integer> param_2 =
	 * obj.retrieve(s,e,gra);
	 */
	
	
	//-- Article Solution
	static class LogSystem_article_Solution {
	    ArrayList < long[] > list;
	    public LogSystem_article_Solution() {
	        list = new ArrayList < long[] > ();
	    }

	    public void put(int id, String timestamp) {
	        int[] st = Arrays.stream(timestamp.split(":")).mapToInt(Integer::parseInt).toArray();
	        list.add(new long[] {convert(st), id});
	    }
	    public long convert(int[] st) {
	        st[1] = st[1] - (st[1] == 0 ? 0 : 1);
	        st[2] = st[2] - (st[2] == 0 ? 0 : 1);
	        return (st[0] - 1999L) * (31 * 12) * 24 * 60 * 60 + st[1] * 31 * 24 * 60 * 60 + st[2] * 24 * 60 * 60 + st[3] * 60 * 60 + st[4] * 60 + st[5];
	    }
	    public List < Integer > retrieve(String s, String e, String gra) {
	        ArrayList < Integer > res = new ArrayList();
	        long start = granularity(s, gra, false);
	        long end = granularity(e, gra, true);
	        for (int i = 0; i < list.size(); i++) {
	            if (list.get(i)[0] >= start && list.get(i)[0] < end)
	                res.add((int) list.get(i)[1]);
	        }
	        return res;
	    }

	    public long granularity(String s, String gra, boolean end) {
	        HashMap < String, Integer > h = new HashMap();
	        h.put("Year", 0);
	        h.put("Month", 1);
	        h.put("Day", 2);
	        h.put("Hour", 3);
	        h.put("Minute", 4);
	        h.put("Second", 5);
	        String[] res = new String[] {"1999", "00", "00", "00", "00", "00"};
	        String[] st = s.split(":");
	        for (int i = 0; i <= h.get(gra); i++) {
	            res[i] = st[i];
	        }
	        int[] t = Arrays.stream(res).mapToInt(Integer::parseInt).toArray();
	        if (end)
	            t[h.get(gra)]++;
	        return convert(t);
	    }
	}

}
