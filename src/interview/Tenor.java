package interview;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//--Tenor.AI hacker Rank
public class Tenor {

	public static void main(String[] args) {

		Integer[] arr = {2,6,3,4,5};
		List<Integer> tickets = Arrays.asList(arr);
		//waitingTime2(tickets, 2);
		calculateTimeOptimized(tickets, 2);
		
	}
	
	
	//--Not a efficient solution, number of pass = number of elements in the array
	public static long waitingTime2 (List<Integer> tickets, int p) {
		System.out.println("Input: "+tickets);
		
		long time = 0;
		int i=0;
		
		while(true) {
			
			int v = tickets.get(i);
			if(v==0) {
				i++;
				continue;
			}
			tickets.set(i, v-1);
			
			if(i == tickets.size()-1) 
				i=0;
			else 
				i++;
			
			time++;
			
			if(tickets.get(p) == 0) break;
			
		}
		
		System.out.println(tickets);
		System.out.println(time);
		return time;
	}
	
	
	//https://stackoverflow.com/questions/49712041/how-to-optimise-algorithm-for-buying-show-tickets-when-using-nodejs
	
	
	private static Long calculateTimeTakenToGetAllTickets(int[] tickets, int p) {
        Long count = 0L;
        List<Integer> list = new ArrayList(Arrays.asList(tickets));
        for (int i = 1; i < tickets.length; i++) {
            list.add(tickets[i]);
        }
        final int size = list.size();
        boolean done = false;
        while (!done) {
            for (int j = 1; j < size; j++) {
                if (list.get(j) == 0) {
                    continue;
                }
                if (list.get(p + 1) == 0) {
                    done = true;
                    break;
                }
                list.set(j, list.get(j) - 1);
                count++;
            }
        }
        return count;
    }
	
	
	//--wrong : not all test case are passing
	

	public static long waitingTime(List<Integer> tickets, int p) {
        long times = 0;
    
        //int[] temp = Arrays.copyOf(tickets, tickets.size()); 
        ArrayList<Integer> temp = new ArrayList<>();
        temp.addAll(tickets);
        
        for(int i = 0; i < tickets.size(); i++ ) {
           temp.add(tickets.get(i) - tickets.get(p));
        }
        for(int i = 0; i < tickets.size(); i++ ) {
           if(temp.get(i) < 0) times += tickets.get(i);
           else {
              if(i <= p) times += tickets.get(i);
              else times += tickets.get(i) - 1;
           }
        }
        
        System.out.println(">>"+times);
        return times;

    }
	
	
	//----
	
	public static int calculateTimeOptimized(List<Integer> tickets, int p) {
	    // Minimum time I have to wait is the number of tickets I want to buy.
	    int waitingTime = tickets.get(p);
	    for (int i = 0; i < tickets.size(); i++) {
	      if (i == p) continue;
	      // I will see people -> minimum(how many tickets I need, how many tickets they need).
	      waitingTime += (Math.min(tickets.get(p), tickets.get(i)));
	      // Unless they were behind me, I got my ticket before them so I need to see them 1 time less.
	      if (i > p) {
	        waitingTime--;
	      }
	    }
	    System.out.println(">>waitingTime: "+waitingTime);
	    return waitingTime;
	  }

}
