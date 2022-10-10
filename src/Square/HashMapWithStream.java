package Square;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HashMapWithStream {

	public static void main(String[] args) {
		
		streamMap();
		
		System.out.println("\n\n------- Local Date------");
		demo_LocalDate();
		System.out.println("\n\n------- Local Date Time------");
		
		demo_LocalDateTime();
		System.out.println("\n\n------- Local Time------");
		
		demo_LocalTime();

	}
	
	private static void streamMap() {
		Map<String, Integer> someMap = new HashMap<>();
		
		//We can obtain a set of key-value pairs:
		Set<Map.Entry<String, Integer>> entries = someMap.entrySet();
		
		//We can also get the key set associated with the Map:
		Set<String> keySet = someMap.keySet();
		
		// Or we could work directly with the set of values:
		Collection<Integer> values = someMap.values();
		
		// These each give us an entry point to process those collections by obtaining streams from them:
		Stream<Map.Entry<String, Integer>> entriesStream = entries.stream();
		Stream<Integer> valuesStream = values.stream();
		Stream<String> keysStream = keySet.stream();
		
		// https://www.baeldung.com/java-maps-streams
		//  finding the ISBN for the book titled “Effective Java.”
		Map<String, String> books = new HashMap<>();
		books.put("978-0201633610", "Design patterns");
		books.put("978-1617291999", "Java 8 in Action");
		books.put("978-0134685991", "Effective Java");
		
		
		Optional<String> optionalIsbn = books.entrySet().stream()
				  .filter(e -> "Effective Java".equals(e.getValue()))
				  .map(Map.Entry::getKey)
				  .findFirst(); // As we only want one result, we can apply the findFirst() terminal operation, which will provide the initial value in the Stream as an Optional object.

		System.out.println("isbn: " + optionalIsbn.get());
		
		// Retrieving Multiple Results
		// To have multiple results returned, let's add the following book to our Map:

		books.put("978-0321356680", "Effective Java: Second Edition");
		
		// So now if we look for all books that start with “Effective Java,” 
		// we'll get more than one result back:
		
		List<String> isbnCodes = books.entrySet().stream()
				.filter(e -> "Effective Java".equals(e.getValue()))
				.map(Map.Entry::getKey)
				.collect(Collectors.toList());
		
		System.out.println("isbnCodes: "+isbnCodes);
		
		
		//Instead of obtaining ISBNs based on the titles, we'll try and get titles based on the ISBNs.
		//Let's use the original Map. We want to find titles with an ISBN starting with “978-0”.
		List<String> titles = books.entrySet().stream()
				  .filter(e -> e.getKey().startsWith("978-0"))
				  .map(Map.Entry::getValue)
				  .collect(Collectors.toList());
		
		System.out.println("titles: "+titles);
		
	}

	// https://www.digitalocean.com/community/tutorials/java-8-date-localdate-localdatetime-instant
	
	private static void demo_LocalDate() {
		
		//Current Date
		LocalDate today = LocalDate.now();
		System.out.println("Current Date="+today);
		
		// specific date
		LocalDate ld = LocalDate.of(2022, 10, 9); // year, month, dayOfMonth
		System.out.println("1. local date: " + ld + ", year:" + ld.getYear() + ", month: " + ld.getMonth()
				+ ", dayOfMonth:" + ld.getDayOfMonth() + ", dayOfYear:" + ld.getDayOfYear());
		
		ld = LocalDate.of(2022, Month.OCTOBER, 9); // year, month, dayOfMonth
		System.out.println("2. local date: " + ld + ", year:" + ld.getYear() + ", month: " + ld.getMonth()
				+ ", dayOfMonth:" + ld.getDayOfMonth() + ", dayOfYear:" + ld.getDayOfYear());

		
		// Create a LocalDate by Parsing a String
		ld = LocalDate.parse("2020-10-15");
		System.out.println("3. local date: " + ld + ", year:" + ld.getYear() + ", month: " + ld.getMonth()
				+ ", dayOfMonth:" + ld.getDayOfMonth() + ", dayOfYear:" + ld.getDayOfYear());
		
		ld = LocalDate.parse("16-Oct-2022", DateTimeFormatter.ofPattern("d-MMM-yyyy"));
		System.out.println("4. local date: " + ld + ", year:" + ld.getYear() + ", month: " + ld.getMonth()
				+ ", dayOfMonth:" + ld.getDayOfMonth() + ", dayOfYear:" + ld.getDayOfYear());
		
		
	}
	
	private static void demo_LocalDateTime() {
		//Current Date
		LocalDateTime today = LocalDateTime.now();
		System.out.println("Current DateTime="+today);
		
		//Current Date using LocalDate and LocalTime
		today = LocalDateTime.of(LocalDate.now(), LocalTime.now());
		System.out.println("Current DateTime="+today);
		
		//Creating LocalDateTime by providing input arguments
		// year, month, dayOfMonth, hour, minute, second
		LocalDateTime specificDate = LocalDateTime.of(2014, Month.JANUARY, 1, 10, 10, 30); // year, month, dayOfMonth, hour, minute, second
		System.out.println("Specific Date="+specificDate);
		
		
	}
	
	private static void demo_LocalTime() {
		//Current Time
		LocalTime time = LocalTime.now();
		System.out.println("Current Time="+time);
		
		//Creating LocalTime by providing input arguments
		// LocalTime java.time.LocalTime.of(int hour, int minute, int second, int nanoOfSecond)
		LocalTime specificTime = LocalTime.of(12,20,25,40); // hour, min, sec, nanoSec
		System.out.println("Specific Time of Day="+specificTime);
	}

}
