package Rippling;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class OrgHierarchy_Json {

	public static void main(String[] args) {

		List<Employee> empList = buildAndGetOrgHierarchy();
		Map<Employee, Set<Employee>> empListGroupedByMgr = groupByManager(empList);
		String res = empWithMaxAvgScore(empListGroupedByMgr);
		System.out.println("1. Emp with max avg: "+ res);
		
		Map<Employee, Set<Employee>> empListGroupedByMgr_withoutLambda = groupByManager_withoutLambda(empList);
		res = empWithMaxAvgScore(empListGroupedByMgr_withoutLambda);
		System.out.println("2. Emp with max avg: "+ res);
	}
	
	/* find the employee/manager with the highest average score
	 {
	 	e1=[e3, e1, e2], 
	 	e2=[e2_2, e2_1, e2_3],
	 	e3_1=[e3_1_2, e3_1_1, e3_1_3], 
	 	e3=[e3_1, e3_2], 
	 	e4=[e4_2, e4_1]
	 }
	 * */
	
	
	public static String empWithMaxAvgScore(Map<Employee, Set<Employee>> map) {
		
		String name = "";
		double maxAvg = 0;
		
		for(Employee e : map.keySet()) {
			double avg = getAvgScore(map.get(e));
			if(avg > maxAvg) {
				name = e.name;
				maxAvg = avg;
			}
		}
		
		System.out.println("name: " + name + ", maxAvg: " + maxAvg);
		return name;
	}
	
	public static double getAvgScore(Set<Employee> empSet) {
		double total = 0;
		for(Employee e : empSet) {
			total += e.reviewScore;
		}
		return total/empSet.size();
	}
	
	public static Map<Employee, Set<Employee>> groupByManager(List<Employee> empList) {
		Map<Employee, Set<Employee>> empListGroupedByMgr =
			    empList.stream()
			    .collect(Collectors.groupingBy(e -> e.mgr == null ? e : e.mgr, Collectors.toSet()));
		//empList.stream().collect(Collectors.groupingBy(e -> e.mgr, Collectors.toSet()));
		
		//		working
//		Map<Employee, List<Employee>> empListGroupedByMgr =
//			    empList.stream().collect(Collectors.groupingBy(w -> w.mgr == null ? w : w.mgr));
		
		/* working
		 * 
		Map<Employee, List<Employee>> empListGroupedByMgr =
				Optional.ofNullable(empList)
				.orElse(new ArrayList<Employee>())
			    .stream().collect(Collectors.groupingBy(w -> w.mgr == null ? w : w.mgr));
		*/
		
		System.out.println("empListGroupedByMgr:: "+empListGroupedByMgr);
		
		return empListGroupedByMgr;
	}
	
	// without StreamLambda - working
	public static Map<Employee, Set<Employee>> groupByManager_withoutLambda(List<Employee> empList) {
		Map<Employee, Set<Employee>> map = new HashMap<>();
		
		for(Employee emp : empList) {
			if (!map.containsKey(emp.mgr)) {
//			    List<Employee> list = new ArrayList<>();
			    Set<Employee> set = new HashSet<>();
			    set.add(emp);
	
			    map.put(emp.mgr, set);
			} else {
				map.get(emp.mgr).add(emp);
			}
		}

		System.out.println("map without lambda:: "+map);
		
		return map;
	}
	
	
	public static List<Employee> buildAndGetOrgHierarchy(){
		
		Employee e1 = new Employee("e1", 5, "A", null); // root/CEO
		e1.mgr= e1;
		
		Employee e2 = new Employee("e2", 5, "A", e1);
		Employee e3 = new Employee("e3", 6, "B", e1);
		Employee e4 = new Employee("e4", 7, "C", e1);
		
		Employee e2_1 = new Employee("e2_1", 8, "A", e2);
		Employee e2_2 = new Employee("e2_2", 8, "A", e2);
		Employee e2_3 = new Employee("e2_3", 9, "A", e2);
		
		Employee e3_1 = new Employee("e3_1", 4, "B", e3);
		Employee e3_2 = new Employee("e3_2", 7, "B", e3);
		
		Employee e4_1 = new Employee("e4_1", 9, "C", e4);
		Employee e4_2 = new Employee("e4_2", 3, "C", e4);
		
		Employee e3_1_1 = new Employee("e3_1_1", 8, "B", e3_1);
		Employee e3_1_2 = new Employee("e3_1_2", 8, "B", e3_1);
		Employee e3_1_3 = new Employee("e3_1_3", 19, "B", e3_1);
		
		return Arrays.asList(e1, e2, e3, e2_1, e2_2, e2_3, e3_1, e3_2, e4_1, e4_2, e3_1_1, e3_1_2, e3_1_3);
	}
	
}

class Employee {
	public String name;
	public int reviewScore;
	public String org; // org/dept
	public Employee mgr; // parent node
	
	public Employee(String name, int score, String org, Employee mgr) {
		this.name = name;
		this.reviewScore = score;
		this.org = org;
		this.mgr = mgr;
	}
	
	public String toString() {
		return name;
	}
}
