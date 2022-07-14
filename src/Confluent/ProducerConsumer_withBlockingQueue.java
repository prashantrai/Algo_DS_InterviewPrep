package Confluent;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProducerConsumer_withBlockingQueue {

	private static int count=0;
	public static void main(String[] args) {

		final int BOUND = 10;
		final int N_PRODUCERS = 2;
		final int N_CONSUMERS = 4;

		/*
		 * https://www.baeldung.com/java-blocking-queue
		 * 
		 * We have used Bunded Queue here. It means that when a producer tries to add an
		 * element to an already full queue, depending on a method that was used to add
		 * it (offer(), add() or put()), it will block until space for inserting object
		 * becomes available. Otherwise, the operations will fail.
		 * 
		 * Using bounded queue is a good way to design concurrent programs because when
		 * we insert an element to an already full queue, that operations need to wait
		 * until consumers catch up and make some space available in the queue. It gives
		 * us throttling without any effort on our part.
		 * 
		 */

		// Creating shared object - Bounded Queue (i.e. with bound)
		BlockingQueue<Integer> sharedQueue = new LinkedBlockingQueue<>(BOUND);

		// Creating Producer and Consumer Thread
		
		/** Working - with ScheduledExecutorService */
		ScheduledExecutorService ses = Executors.newScheduledThreadPool(3);
		ses.scheduleWithFixedDelay(new Producer(sharedQueue), 100, 100, TimeUnit.MILLISECONDS);
		ses.scheduleWithFixedDelay(new Consumer(sharedQueue), 200, 100, TimeUnit.MILLISECONDS);
		ses.scheduleWithFixedDelay(new Consumer(sharedQueue), 200, 100, TimeUnit.MILLISECONDS);
		
		//shutdown ScheduledExecutorService
		
		// This method does not wait for previously submitted tasks to complete execution. 
		// Use awaitTermination to do that.
		// ses.shutdown();
		
		try {
			// Blocks until all tasks have completed execution after a shutdown request, 
			// or the timeout occurs, or the current thread is interrupted, whichever happens first.
			ses.awaitTermination(10, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
		/** Working - with Executors */
		final int threadPoolSize = 3;
		ExecutorService executorService = Executors.newFixedThreadPool(threadPoolSize);

		// execute has void return type whereas submit resturns Future object
//        executorService.execute(new Consumer(sharedQueue)); 
//        executorService.execute(new Consumer(sharedQueue));
//        
//        Future producerStatus = executorService.submit(new Producer(sharedQueue));
        
        // this will wait for the producer to finish its execution.
        //producerStatus.get();
            
//        executorService.shutdown();
        
        /* Blocks until all tasks have completed execution after a shutdown request, 
     	 * or the timeout occurs, or the current thread is interrupted, whichever happens first.
     	 */
        try {
			executorService.awaitTermination(10, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        
        
        
        /** Working - with multiple producer and consumer thread */
		
        /*
		for(int i=0; i<N_PRODUCERS; i++) {
			Thread prodThread = new Thread(new Producer(sharedQueue));
			prodThread.start();
		}
		for(int i=0; i<N_CONSUMERS; i++) {
			Thread consThread = new Thread(new Consumer(sharedQueue));
			consThread.start();
		}
		*/
		
        
		/** Working - with single producer and consumer thread */
//		Thread prodThread = new Thread(new Producer(sharedQueue));
//		Thread consThread = new Thread(new Consumer(sharedQueue));

		// Starting producer and Consumer thread
//		prodThread.start();
//		consThread.start();
		
        
        // Just a sample and not used anywhere in this code
        // Quick wat to create a Runnable Task
        
        Runnable task1 = () -> {
        	count++; // definded as instance variable
        	System.out.println(count);
        };
        

	}
	
	// java revisited
	//Producer Class in java
	static class Producer implements Runnable {

	    private final BlockingQueue sharedQueue;

	    public Producer(BlockingQueue sharedQueue) {
	        this.sharedQueue = sharedQueue;
	    }

	    @Override
	    public void run() {
	        for(int i=0; i<10; i++){
	            try {
	                System.out.println("Produced: " + i);
	                // Adding element
	                // put() – inserts the specified element into a queue, waiting for a free slot if necessary
	                sharedQueue.put(i);
	                //Thread.sleep(100); // in millis - add only when asked
	            } catch (InterruptedException ex) {
	                Logger.getLogger(Producer.class.getName()).log(Level.SEVERE, null, ex);
	            }
	        }
	    }

	}

	//Consumer Class in Java
	static class Consumer implements Runnable{

	    private final BlockingQueue sharedQueue;

	    public Consumer (BlockingQueue sharedQueue) {
	        this.sharedQueue = sharedQueue;
	    }
	  
	    @Override
	    public void run() {
	        while(true){
	            try {
	            	// Retrieving element
	            	// take() – waits for a head element of a queue and removes it. 
	            	//   If the queue is empty, it blocks and waits for an element to become available
	                System.out.println("Consumed: "+ sharedQueue.take());
	            } catch (InterruptedException ex) {
	                Logger.getLogger(Consumer.class.getName()).log(Level.SEVERE, null, ex);
	            }
	        }
	    }
	  
	}


}
