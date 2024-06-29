package Facebook;

public class CapacityToShipPackagesWithin_D_Days_1011_Medium {

	public static void main(String[] args) {
		int[] weights = {1,2,3,4,5,6,7,8,9,10}; 
		int days = 5;
		int res = shipWithinDays(weights, days);
		System.out.println("Expected: 15, Actual: " + res);
	}

	/*
    Time: O(n long(n*W))
            - it takes O(n) to iterate 'weights' 
              to compute 'maxLoad' and 'totalLoad'.
              
            - O(log(nW)) for binary search. 
              The binary search is performed on the range 
              from 'maxLoad' to 'totalLoad'. The range size 
              is O(n*W) where W is the average weight in the 'weights' 
              array. The binary search reduces the range by half each iteration,
              leading to a time complexity of O(log(totalLoad−maxLoad)). 
              Given that 'totalLoad' can be as large as 'n*W' and 'maxLoad' can be as 
              large as W, the binary search has a time complexity of O(log(nW)).
              
            - The feasibleWeight function iterates through the weights 
              array once for each call, so its time complexity is O(n).

         Combining these components:
           - The outer binary search loop runs O(log(n*W)) times.
           - Each iteration of the binary search calls the feasibleWeight function, 
           which runs in O(n) time.
           
        Thus, the overall time complexity is O(nlog(n*W)).


    Space: O(1), since it uses a constant amount of extra space 
    
    */
    
    public static int shipWithinDays(int[] weights, int days) {
        // this will contains the sum all weights in the weights[]
        int totalLoad = 0; 
        // this will contain maxLoad in weights[]
        int maxLoad = 0;
        // O(n)
        for(int weight : weights) {
            totalLoad += weight;
            maxLoad = Math.max(maxLoad, weight);
        }
        
        // now with above we have start and end range to 
        // start a binary search on the range and find the
        // fieasible weight to ship all the pkg
        
        int l = maxLoad;
        int r = totalLoad;
        
        while(l < r) {
            int mid = l + (r-l)/2; // to avoid overflow
            
            // pass mid as capacity to see if it's a feasible weight
            if(feasibleWeight(weights, mid, days)) {
                r = mid;
            } else {
                l = mid + 1;
            }
        }
        return l;
    }
    
    // check if packages can be shipped in less than "days" days with
    // "c" is capacity
    private static boolean feasibleWeight(int[] weights, int c, int days) {
        int daysNeeded = 1;
        int currentLoad = 0;
        
        for(int weight : weights) {
            currentLoad += weight;
            if(currentLoad > c) {
                // when currentLoad is more than ship capacity 
                // we can't ship the pkg in 1 day and we need more days
                daysNeeded++;   
                
                // update current load to the current to start for next day
                currentLoad = weight;
            }
        }
        return daysNeeded <= days;
    }
}
