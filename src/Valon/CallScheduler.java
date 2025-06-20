package Valon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CallScheduler {

	/*
	 * We have data on how our servicing team members reach out to homeowners for
	 * resolving escalations, but we’ve found that how we schedule calls is not
	 * optimal.
	 * 
	 * We can schedule homeowner calls between the hours of 7am and 6pm, and we want
	 * to space out the time between the calls within a single day as much as
	 * possible for a single servicing team member to give them breaks between
	 * calls. We want the time between calls to be as equally spaced out as
	 * possible. If there are multiple options of equally spaced out calls, we want
	 * to schedule the longer calls and longer breaks towards the beginning of the
	 * day.
	 * 
	 * Given a list of tuples (day of week, call duration in hours) of calls,
	 * implement a method to print out the most optimal call schedule. To simplify
	 * things, you can assume that calls must start at the beginning of an hour, and
	 * they last in increments of an hour.
	 */
	
	public static void main(String[] args) {
		List<Call> calls = Arrays.asList(
				new Call("Mon", 2),
				new Call("Mon", 2),
				new Call("Mon", 2),
				new Call("Tue", 2),
				new Call("Tue", 2),
				new Call("Wed", 2),
				new Call("Wed", 2),
				new Call("Wed", 2)
			);
		
		scheduleCalls(calls);
		
		// Edge case: too many calls for a day
        List<Call> edgeCalls = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            edgeCalls.add(new Call("Thursday", 1));
        }
        scheduleCalls(edgeCalls);

	}
	
	/*
	Time:
		Grouping calls → O(N)
		Sorting calls per day → O(M log M) per day, where M = number of calls in that day
		Overall: O(N log N)
	Space:
		For storing grouped calls and scheduled results → O(N)
	 * */
	
	// Method to schedule and print optimal call times
	private static void scheduleCalls(List<Call> calls) {
		
		// Step 1: Group calls by day in a Map
		Map<String, List<Integer>> dayToCalls = new HashMap<>();
		for(Call call : calls) {
			/* computeIfAbsent does two things:
				1. If the key (call.day) doesn't exist, it creates a new entry 
					with that key and the value from the lambda (new ArrayList<>())

				2. If the key exists, it just returns the current value
			 * */
			dayToCalls.computeIfAbsent(call.day, k -> new ArrayList<>()).add(call.duration);
			
			// The equivalent code without computeIfAbsent would be:
			/* if (!dayToCalls.containsKey(call.day)) {
			    dayToCalls.put(call.day, new ArrayList<>());
			}
			dayToCalls.get(call.day).add(call.duration); */
		}
			
			
		// Step 2: Process each day's calls
		for(String day : dayToCalls.keySet()) {
			List<Integer> durations = dayToCalls.get(day);
			
			// Sort call durations in descending order (so longer calls scheduled earlier)
			Collections.sort(durations, Collections.reverseOrder());
			
			int numOfCalls = durations.size();
			int startHour = 7; // 7 AM
			int endHour = 18; // 6 PM
			int availableSlots = endHour - startHour; // 11
			
			// If more calls than slots, we cannot schedule all
			if(numOfCalls > availableSlots) {
				System.out.println("Too many calls. Can not schedule all calls on " + day + ".");
                continue;
			}
			
			// Calculate optimal spacing
			double interval = (double) availableSlots / numOfCalls;
			
			List<ScheduledCall> schedule = new ArrayList<>();
			
			/*
			 Schedule calls by picking start times at start = 7AM + i * interval 
			 (rounded down to integer hours)
			 * */
			for(int i=0; i<numOfCalls; i++) {
				int scheduledHour = startHour + (int) Math.floor(i * interval);
				schedule.add(new ScheduledCall(scheduledHour, durations.get(i)));
			}
			
			// Step 3: Print the schedule
            System.out.println("Schedule for " + day + ":");
            for (ScheduledCall sc : schedule) {
            	System.out.println(sc.hour + ":00 - " + (sc.hour + sc.duration) + ":00 (" + sc.duration + " hr)");
            }
            System.out.println();
					
		}
		
		
		// Step 3: Print the schedule
	}

	// ScheduledCall class to hold scheduled results
    static class ScheduledCall {
        int hour;
        int duration;
        ScheduledCall(int hour, int duration) {
            this.hour = hour;
            this.duration = duration;
        }
    }
	
	// Call class to hold input
	private static class Call {
		String day;
		int duration;
		
		Call(String day, int duration) {
			this.day = day;
			this.duration = duration;
		}
	}

}
