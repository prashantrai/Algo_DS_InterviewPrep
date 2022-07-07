package Box;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class SynchronizedCounter {

	public static void main(String[] args) {
//		usingSynchronizedBlock();  // working
		usingConcurrentLock();     // working
//		usingSemaphore();		// working
		
//		usingAtomicInteger(); // working
	}
	
	/**
	 * Reference: https://tharakamd-12.medium.com/4-ways-to-implement-a-synchronized-counter-in-java-5540452fb4f7
	 * */
	
	
	// Using a synchronized block
	
	private static void usingSynchronizedBlock() {
		final int threadPoolSize = 5;
		ExecutorService executorService = Executors.newFixedThreadPool(threadPoolSize);
        for (int i = 0; i < threadPoolSize; i++) {
            executorService.submit(new Counter_Synchronized_Block());
        }
        executorService.shutdown();
	}
	
	static class Counter_Synchronized_Block implements Runnable {
	    private static int counter = 0;
	    private static final int limit = 100;
	    private static final Object lock = new Object();

	    @Override
	    public void run() {
	        while (counter < limit) {
	            increaseCounter();
	        }
	    }

	    private void increaseCounter() {
	        synchronized (lock) {
	        //synchronized (Counter_Synchronized_Block.class) {
	            System.out.println(Thread.currentThread().getName() + " : " + counter);
	            counter++;
	        }
	    }
	}
	
	
	// Using Concurrent Lock
	// https://stackoverflow.com/questions/29883719/java-multithreading-threadsafe-counter
	public static void usingConcurrentLock() {
		final int threadPoolSize = 5;
        ReentrantLock sharedLock = new ReentrantLock(true); // enable fairness policy

        ExecutorService executorService = Executors.newFixedThreadPool(threadPoolSize);
        for (int i = 0; i < threadPoolSize; i++) {
            executorService.submit(new Counter_Concurrent_Lock(sharedLock));
        }
        executorService.shutdown();
    }
	
	static class Counter_Concurrent_Lock implements Runnable {
	    private ReentrantLock lock;
	    private static int counter = 0;
	    private static final int limit = 100;
	    

	    public Counter_Concurrent_Lock(ReentrantLock lock) {
	        this.lock = lock;
	    }

	    @Override
	    public void run() {
	        while (counter < limit) {
	            increaseCounter();
	        }
	    }

	    private void increaseCounter() {
	        lock.lock();
	        try {
	            System.out.println(Thread.currentThread().getName() + " : " + counter);
	            counter++;
	        } finally {
	            lock.unlock();
	        }
	    }
	}
	
	
	// Using a Semaphore 
	public static void usingSemaphore() {
		final int threadPoolSize = 5;
        Semaphore sharedSemaphore = new Semaphore(1);
        ExecutorService executorService = Executors.newFixedThreadPool(threadPoolSize);
        for (int i = 0; i < threadPoolSize; i++) {
            executorService.submit(new Counter_Using_Semaphore(sharedSemaphore));
        }
        executorService.shutdown();
    }
	
	static class Counter_Using_Semaphore implements Runnable {
	    Semaphore semaphore;
	    private static int counter = 0;
	    private static final int limit = 100;

	    public Counter_Using_Semaphore(Semaphore semaphore) {
	        this.semaphore = semaphore;
	    }

	    @Override
	    public void run() {
	        while (counter < limit) {
	            increaseCounter();
	        }
	    }

	    private void increaseCounter() {
	        try {
	            semaphore.acquire();
	            System.out.println(Thread.currentThread().getName() + " : " + counter);
	            counter++;
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        } finally {
	            semaphore.release();
	        }
	    }
	}
	
	
	
	// Using atomic integer -- working
	public static void usingAtomicInteger() {
		final int threadPoolSize = 5;
        ExecutorService executorService = Executors.newFixedThreadPool(threadPoolSize);
        for (int i = 0; i < threadPoolSize; i++) {
            executorService.submit(new Counter_Atomic_Integer());
        }
        executorService.shutdown();
    }
	
	static class Counter_Atomic_Integer implements Runnable {
	    private static AtomicInteger counter = new AtomicInteger(1);
	    private static final int limit = 100;

	    @Override
	    public void run() {
	        while (counter.get() < limit) {
	            increaseCounter();
	        }
	    }

	    private void increaseCounter() {
	        System.out.println(Thread.currentThread().getName() + " : " + counter.getAndIncrement());
	    }
	}

}
