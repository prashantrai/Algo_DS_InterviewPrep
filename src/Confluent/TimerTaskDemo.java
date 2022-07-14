package Confluent;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Timer;
import java.util.TimerTask;

public class TimerTaskDemo {

	public static void main(String[] args) {
		repeatedTask();
		repeatedTask2();

	}

	private static void repeatedTask() {
		
		TimerTask repeatedTask = new TimerTask() {
	        public void run() {
	        	
	            System.out.println("Task performed on " 
	            + LocalTime.now().getMinute() + ":" + LocalTime.now().getSecond());
	        }
	    };
	    
	    Timer timer = new Timer("Timer");
	    long delay = 500L;
	    long period = 1000L; // period of successive task of execution
	    timer.scheduleAtFixedRate(repeatedTask, delay, period);
		
	}
	
	private static void repeatedTask2() {
		new Timer().schedule(new NewsletterTask(), 0, 1000);

		for (int i = 0; i < 3; i++) {
		    try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	// Using a class 
	static class NewsletterTask extends TimerTask {
	    @Override
	    public void run() {
	        System.out.println("Email sent at: " 
	          + LocalDateTime.ofInstant(Instant.ofEpochMilli(scheduledExecutionTime()), 
	          ZoneId.systemDefault()));
	    }
	}
}
