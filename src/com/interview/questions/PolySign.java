package com.interview.questions;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

//--Coding assignment (to be submitted in Python) :: Log analyzer

public class PolySign {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		String[] logs = {
							"2018-01-01	1	200",
							"2018-01-01	2	401", 
							"2018-01-03	1	200", 
							"2018-01-05	1	200"
						};
		
		
		LogAnalyzer analyzer = new LogAnalyzer();
		analyzer.createMatrics(logs);
		
	}

}

class LogAnalyzer {
	
	private int noOfReq;
	private int noOfErr;
	private Date stDate;
	private Date endDate;
	private Set<String> users;
	private Map<Date, Set<String>> dailyUserMap;
	
	
	public LogAnalyzer() {
		users = new HashSet<>();
		dailyUserMap = new HashMap<>();
	}

	public void createMatrics(String[] logs) throws Exception {
		
		
		for(int i=0; i<logs.length; i++) {
			
			String[] data = logs[i].split("\\t");
			Date date = new SimpleDateFormat("yyyy-mm-dd").parse(data[0]);
			
			noOfReq++; //--Increament request count as we are iterating (it could have been done by getting length of array but in actual problem we'll be reading this input from file one by one)
			if(!data[2].equals("200")) {
				noOfErr++;
			}
			
			//--Users
			users.add(data[1]);
			
			if(i==0) {
				stDate = date; 
			} else if (i == logs.length-1) {
				endDate = date;
			}
			
			//--DailyMap
			if(dailyUserMap.containsKey(date)) {
				dailyUserMap.get(date).add(data[1]);
			} else {
				Set<String> dailyUserSet = new HashSet<String>();
				dailyUserSet.add(data[1]);
				dailyUserMap.put(date, dailyUserSet);
			}
			
		}
		
		System.out.println("Summary Metrics:: ");
		System.out.println("Num of req: "+getNoOfReq());
		System.out.println("Num of err: "+getNoOfErr());
		System.out.println("Num of days:"+(getDateDiffInDays(stDate, endDate)+1)); //-- add 1 for inclusive
		System.out.println("Num of unique user: "+users.size());
		
		System.out.println("Daily Metrics:: ");
		/*for(Date d : dailyUserMap.keySet()) {
			System.out.println(d + "	" + dailyUserMap.get(d).size());
		}*/
		
		
		//--We need to iterate through all the dates whether they are in logs or not till end date
		Calendar start = Calendar.getInstance();
		start.setTime(stDate);
		Calendar end = Calendar.getInstance();
		end.setTime(endDate);
		
		for(Date d = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), d = start.getTime()) {
			System.out.println(d + "	" + (dailyUserMap.containsKey(d) ? dailyUserMap.get(d).size() : 0) );
		}
		
		
	}

	public int getNoOfReq() {
		return noOfReq;
	}

	public void setNoOfReq(int noOfReq) {
		this.noOfReq = noOfReq;
	}

	public int getNoOfErr() {
		return noOfErr;
	}

	public void setNoOfErr(int noOfErr) {
		this.noOfErr = noOfErr;
	}

	public Date getStDate() {
		return stDate;
	}

	public void setStDate(Date stDate) {
		this.stDate = stDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Set<String> getUsers() {
		return users;
	}

	public void setUsers(Set<String> users) {
		this.users = users;
	}

	public Map<Date, Set<String>> getDailyUserMap() {
		return dailyUserMap;
	}

	public void setDailyUserMap(Map<Date, Set<String>> dailyUserMap) {
		this.dailyUserMap = dailyUserMap;
	}
	
	public int getDateDiffInDays(Date startDate, Date endDate) {
		
		int diffInDays = (int) (endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24);
		return diffInDays;
	}
	
}
