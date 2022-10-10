package Square;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetWheater {

	public static void main(String[] args) {
		
		String data = "Year,Month,Day,High,Low\n2019,1,1,8.5,-8.3\n2019,1,2,-1.8,-11.6\n2019,1,3,-0.8,-2.5\n2018,1,1,-9.8,-26.9\n2018,1,2,-9.7,-16.0\n2018,1,3,-8.7,-16.2\n2017,1,1,0.2,-11.5\n2017,1,2,3.8,-9.4\n2017,1,3,3.0,1.0";
		getWeather(2018, data);
	}
	
	// working
	private static void getWeather(int year, String data) {
		
		Map<Integer, List<Weather>> dataMap = buildAndGetDataMap(data);
		
		System.out.println("|Year/Month/Day/Low Temp/High Temp|");
		for(Weather w : dataMap.get(year)) {
			System.out.println(getFormattedResult(w));
		}
		
	}
	
	private static String getFormattedResult(Weather w) {
		StringBuilder sb = new StringBuilder();
		sb.append("|");
		
		sb.append(w.year);
		sb.append("/");
		sb.append(w.month);
		sb.append("/");
		sb.append(w.day);
		sb.append("/");
		sb.append(w.low);
		sb.append("/");
		sb.append(w.high);
		
		sb.append("|");
		
		return sb.toString();
		
	}
	
	private static Map<Integer, List<Weather>> buildAndGetDataMap(String data) {
		String[] arr = data.split("\n");
		
		Map<Integer, List<Weather>> map = new HashMap<>();
		
		for(int i=1; i<arr.length; i++) {
			Weather w = buildAndGetWeatherObj(arr[i]);
			
			if(!map.containsKey(w.year)) {
				map.put(w.year, new ArrayList<Weather>());
			}
			
			map.get(w.year).add(w);
			
		}
		return map;
	}
	
	private static Weather buildAndGetWeatherObj(String weather) {
		
		//Year, Month, Day, High, Low
		//2019, 1, 1, 8.5, -8.3
		String[] sArr = weather.split(",");
		
		int year = Integer.valueOf(sArr[0]);
		int month = Integer.valueOf(sArr[1]);
		int day = Integer.valueOf(sArr[2]);
		double high = Double.valueOf(sArr[3]);
		double low = Double.valueOf(sArr[4]);
		
		return new Weather(year, month, day, high, low);
	}

	private static class Weather {
		int year, month, day;
		double high, low;
		
		public Weather(int year, int month, int day, double high, double low) {
			this.year = year;
			this.month = month;
			this.day = day;
			this.high = high;
			this.low = low;
		}
	}

}

/*
 [Nov2021] https://leetcode.com/discuss/interview-experience/1605678/Cash-or-Phone-or-Weather-App/1167024
	
	Write a function to return weather data for any given input year. (e.g. getWeather(2018) )
	
	Raw input weather data is given as a serialized String as below:
	
	"Year,Month,Day,High,Low\n2019,1,1,8.5,-8.3\n2019,1,2,-1.8,-11.6\n2019,1,3,-0.8,-2.5\n2018,1,1,-9.8,-26.9\n2018,1,2,-9.7,-16.0\n2018,1,3,-8.7,-16.2\n2017,1,1,0.2,-11.5\n2017,1,2,3.8,-9.4\n2017,1,3,3.0,1.0"
	
	Input : 2019
	Result:
	
	| Year / Month / Day / Low Temp / High Temp |
	
	| 2018 / 1 / 1 / -26.9 / -9.8 |
	| 2018 / 1 / 2 / -16.0 / -9.7 |
	| 2018 / 1 / 3 / -16.2 / -8.7 |
	
	Follow-up:
	
	Instead of complete data, just return selected fields (e.g. year and lowTemp)
 */
