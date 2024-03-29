import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Test2 {

	public static void main(String[] args) {
		
//		System.out.println("1>>> Expected: aabbaa: Actual: " + solution("?ab??a"));
//		System.out.println("2>>> Expected: NO: Actual: " + solution("bab??a"));
//		System.out.println("2>>> Expected: aaa: Actual: " + solution("?a?")); // "zaz" is also correct
		
		List<LocalDate> dates = getAllDatesBetweenRangeInclusive("5-January-2000", "10-January-2000");
		System.out.println("dates:: "+dates);
		
		System.out.println(">>>"+ formatLocalDate(LocalDate.now()));
		        
	}
	
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
	
	/* Deepak's Codeity Test: 
	 * Make and return palindrome by replacing '?' in the input string 
	 * with characters from a-z, return "NO" if not possible.
	 * */
	public static String solution(String S) {
        
        int i=0, j=S.length()-1;
        char[] arr = S.toCharArray();
        while (i < j) {
            char i_char = arr[i];
            char j_char = arr[j];
            
            // when char are not same
            if(i_char != '?' && j_char != '?' && i_char != j_char) {
                return "NO";
            }
            
            if(i_char != '?' || j_char != '?') {
                if(i_char == '?') {
                    arr[i] = arr[j];
                } else {
                    arr[j] = arr[i];
                }
            }
            else if (i_char == '?' && j_char == '?') {
                arr[i] = 'a';
                arr[j] = 'a';
            }
            
            i++;
            j--;
        }

     return new String (arr);
    }
	
}
