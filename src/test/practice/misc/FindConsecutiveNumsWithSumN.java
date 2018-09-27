package test.practice.misc;

public class FindConsecutiveNumsWithSumN {

	public static void main(String[] args) {
//		findConsecutives(9);
//		findConsecutives(10);
//		findConsecutives(100);
//		findConsecutives(125);
//		findConsecutives(10);
		findConsecutives(28);
		
		System.out.println(">>"+countConsecutive(10));
	}

	
	public static void findConsecutives(int N) {
		
		int start = 1;
		int end = 1;
		int sum = 1;
		int count = 0;
		
		System.out.println("N="+N);
		
		while (start <= N/2) {
			
			if(sum < N) {
				end += 1;
				sum += end;
				
			} else if(sum > N) {
				sum -= start;
				start += 1;
			}
			
			else if(sum == N) {
				count++;
				for(int i=start; i<=end; i++) {
					System.out.print(i+" ");
				}
				System.out.println();
				
				sum -= start;
				start += 1;
				
			}
		}
		
		System.out.println("Count = "+count);
		
	}
	
	public static int countConsecutive(int num)
    {
        // constraint on values of L gives us the 
        // time Complexity as O(N^0.5)
        int count = 0;
        for (int L = 1; L * (L + 1) < 2 * num; L++)
        {
            float a = (float) ((1.0 * num-(L * (L + 1)) / 2) / (L + 1));
            if (a-(int)a == 0.0) 
                count++;        
        }
        return count;
    }
}
