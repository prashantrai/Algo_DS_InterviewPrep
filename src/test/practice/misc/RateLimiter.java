package test.practice.misc;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class RateLimiter {
    public static void main(String args[] ) throws Exception {
        
        RateLimiter rateLimiter = new RateLimiter();
        System.out.println("1>>"+rateLimiter.rateLimit("test2"));
        System.out.println("2>>"+rateLimiter.rateLimit("test2"));
        System.out.println("3>>"+rateLimiter.rateLimit("test2"));
        System.out.println("4>>"+rateLimiter.rateLimit("test2"));
        System.out.println("5>>"+rateLimiter.rateLimit("test2"));
        
        
        try {
            java.lang.Thread.sleep(TIME_LIMIT);
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        
        for(int i = 0; i<1000; i++) {
            if(rateLimiter.rateLimit("test2"))
                System.out.println("**>>"+rateLimiter.rateLimit("test2"));
        }
        
    }
    
    /**
     *  Rate limiter to limit the number of request from one user i.e.
     *  in our case a user (e.g. test2) can not call API more than the 
     *  defined REQ_LIMIT
     *  
     *  e.g. In this implementation if the user 'test2' is requesting/calling
     *  API more than 3 times then after 3rd time user will receive FALSE
     *  that is no API call is made as user has reached the threshold (i.e. 3)
     *  and has to wait for sometime (i.e. TIME_LIMIT which is 1000ms in our case) 
     *  and after that user should be able to call API successfully.
     *  
     *  
     *  Code : https://leetcode.com/playground/wGHWEekd
     *  Discussion Link: https://leetcode.com/discuss/interview-question/system-design/124558/uber-rate-limiter
     *   
     * */
    
    private Map<String, HitCounter> map = new HashMap<>();
    boolean rateLimit(String customerId) {
        
        HitCounter h = map.get(customerId);
        long curTime = System.currentTimeMillis();
        
        if(h == null) {
            h = new HitCounter();
            map.put(customerId, h);
        }
        return h.hit(curTime);
    }
    final static int REQ_LIMIT = 3;
    final static long TIME_LIMIT = 1000L;  //--age of a request sit in a queue/or wait time for a new request/API call
    
    public static class HitCounter {
        Queue<Long> q = null;
        
        public HitCounter() {
            q = new LinkedList<>();
        }
        
        public boolean hit(long timestamp) {
            
        	/* If q is not empty and 
        	 * 
        	 * timestamp-q.peek() >= TIME_LIMIT :: Means the top element in the queue is older the TIME_LIMIT i.e. 1000ms 
        	 * then we can remove the oldest element from q i.e. q.poll() 
        	 * and add the new element on top for the user
        	 * 
        	 * Note : Below approach of removing the element from the Q is calles 'Sliding Window Algo'
        	 * Look 'Grokking the System Design Interview' - Designing an API Rate Limiter 
        	 * 
        	 * */
        	
            while (!q.isEmpty() 
            		&& timestamp-q.peek() >= TIME_LIMIT) {
                q.poll();
            }
            if(q.size() < REQ_LIMIT) {
                q.offer(timestamp);                
                return true;
            }            
            return false;
        }
         
    }
}
