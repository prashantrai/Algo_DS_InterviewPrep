package com.interview.questions;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

/*

# Estimated Date of Delivery

Create a function which given a number of business days (Monday - Friday) as an
integer, a start date as a string, and a list of holidays returns a date by
which a package will be delivered. The start date will be a business day and the
array of holidays will be all the holidays of the entire year.

*/

public class Narvar {

	//--Working solutions
	public static void main(String[] args) throws Exception {
		String result = findEstimatedDateOfDelivery(new Options());
		assert result.equals("2018-03-14");
		
		
	}
	
	static class Holiday {
		public String month;
		public String day;

		Holiday(String m, String d) {
			month = m;
			day = d;
		}
	}
	
	static class Options {
		public String shipDate = "2018-01-02";  //yyyy-mm-dd
		public int deliveryEstimateInBusinessDays = 69;
		public Holiday[] holidays = { 
				new Holiday("01", "01"), // New Year's Day
				new Holiday("01", "15"), // MLK Day
				new Holiday("05", "28"), // Memorial Day
				new Holiday("07", "04"), // Independence Day
				new Holiday("09", "03"), // Labor Day
				new Holiday("11", "12"), // Veterans Day
				new Holiday("11", "22"), // Thanksgiving
				new Holiday("12", "25"), // Xmas Day
		};
	}

	public static String findEstimatedDateOfDelivery(Options options) throws Exception {
		
		int count = 0;
		String shipDate = options.shipDate;
		Map<String, String> map = getHolidayMap(new Options().holidays); //-- O(1) : can argue for constant as holidays are variable (at least that frequent)
		
		while (count <= options.deliveryEstimateInBusinessDays) { // O(n)
			shipDate = addOneDay_Java8(shipDate); 
			if(isHoliday(shipDate, map))
				continue;
			
			count++;
		}
		System.out.println("deliveryDate = "+shipDate);
		
		return shipDate;
	}
	//--Not in use
	public static Date addOneDay(String date) throws Exception {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(sdf.parse(date));
		cal.add(Calendar.DATE, 1);
		return cal.getTime();
		
	}
	
	public static String addOneDay_Java8 (String date) throws Exception {
		return LocalDate.parse(date).plusDays(1).toString();
	}
	
	public static Map<String, String> getHolidayMap(Holiday[] holidays) {
		
		Map<String, String> map = new HashMap<>();
		for(Holiday holiday : holidays) {
			map.put(holiday.day, holiday.month);
		}
		return map;
	}
	
	public static boolean isHoliday(String shipDate, Map<String, String> map) {
		
		String[] dt = shipDate.split("-");
		String m = dt[1];
		String d = dt[2];

		if(map.containsKey(d) && map.get(d).equals(m)) 
			return true;
		
		
		return false;
		
	}

}