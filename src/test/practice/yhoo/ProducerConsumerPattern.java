package test.practice.yhoo;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ProducerConsumerPattern {

	public static void main(String[] args) {

		BlockingQueue sharedQ = new LinkedBlockingQueue();
		
		Producer p = new Producer(sharedQ);
		Consumer c = new Consumer(sharedQ);
		
		Thread pt = new Thread(p);
		Thread ct = new Thread(c);
		
		pt.start();
		ct.start();
	}

}


class Producer implements Runnable {
	
	BlockingQueue sharedQ = null;
	
	public Producer(BlockingQueue sharedQ) {
		this.sharedQ = sharedQ;
	}
	
	public void  run() {
		
		for(int i=0; i<=10; i++) {
			try {
				System.out.println("Produced : "+i);
				sharedQ.put(i);
			
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
}

class Consumer implements Runnable {
	
	BlockingQueue sharedQ = null;
	
	public Consumer(BlockingQueue sharedQ) {
		this.sharedQ = sharedQ;
	}
	
	public void run() {
		
		while(true) {
			try {
				System.out.println("Consumed : "+ sharedQ.take());
			
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}