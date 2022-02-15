import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class Twilio_OA_HackerRank_DeepakSethy {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	/* This solution was giving TLE.
	 * 
	 * Better approach should be to 
	 * 	1. make call with any range or date and then (include a page number starting 1)
	 *  2. iterate the result and print each result where date is in input date range
	 *  3. repeat step 1 with the next page till you land on a page where first data's date OR last data's date is out of range  
	 * 
	 * */
	
	static void openAndClosePrices(String firstDate, String lastDate) {
        if(firstDate == null || firstDate.isEmpty() || lastDate == null || lastDate.isEmpty()) {
            System.err.println("Error: Invalid input.");
            return;
        }
        StringBuilder sb = new StringBuilder();
        List<String> results = new ArrayList<>();
        List<LocalDate> dates = getAllDatesBetweenRangeInclusive(firstDate, lastDate);
        
        List<Data> datas = new ArrayList<>();
        
        try {
            for(LocalDate date : dates) {
                String dateStr =  formatLocalDate(date);
                
                URL dataEntry 
                    = new URL("https://jsonmock.hackerrank.com/api/stocks/?date="+dateStr);
                    
                InputStreamReader reader = new InputStreamReader(dataEntry.openStream());
                Result result = new Gson().fromJson(reader, Result.class);
                
                if ( result.total > 0) {
                    datas.addAll(result.data);
                }
            }
        }
        catch(Exception e) {
            System.out.println(e);
        }
        
        for (Data data : datas) {
            sb.append(data.date);
            sb.append(" ");
            sb.append(data.open);
            sb.append(" ");
            sb.append(data.close);
            System.out.println(sb);
        }
    }
	
	private static String formatLocalDate(LocalDate localDate) {
		return localDate.format(DateTimeFormatter.ofPattern("dd-MMMM-yyyy"));
	}

	
	public static List<LocalDate> getAllDatesBetweenRangeInclusive(String start, String end) { 
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MMMM-yyyy");

		LocalDate startDate = LocalDate.parse(start, formatter);
		LocalDate endDate = LocalDate.parse(end, formatter);
		endDate = endDate.plusDays(1);
			 
	    long numOfDaysBetween = ChronoUnit.DAYS.between(startDate, endDate); 
	    return IntStream.iterate(0, i -> i + 1)
	      .limit(numOfDaysBetween)
	      .mapToObj(i -> startDate.plusDays(i))
	      .collect(Collectors.toList()); 
	}
	
	
	class Result {
        int page;
        int per_page;
        int total;
        int total_pages;
        List<Data> data;
        //TODO: Avoiding setters getters in interest of time 
    }

    class Data {
        String date;
        float open;
        float close;
        float high;
        float low;
        //TODO: Avoiding setters getters in interest of time
    }

}
