package interview;

import java.util.List;

public class KSubSeqTwilioHackerRank {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	//--worrking
	//http://crackingtechinterviews.blogspot.com/2016/02/12152016-consecutive-subsequence.html
	
	static long kSub(int k, List<Integer> nums) {
	    

	    int [] sum = new int[nums.size()];
	    long count = 0;    
	    sum[0] = nums.get(0);
	    
	    for(int i = 1; i < nums.size(); i++){
	        sum[i] = sum[i-1] + nums.get(i);
	    }
	    
	    int [] kVal = new int[k];
	    
	    for(int i = 0; i < sum.length; i++){       
	        int mod = sum[i] % k;

	        if(mod == 0)
	            count++;

	        count += kVal[mod];
	        kVal[mod] += 1;
	             
	    }
	    return count;
	}

}
