package test.practice.misc;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class GenerateBinaryNumbersUsingQueue {

	//--https://www.acodersjourney.com/generate-binary-numbers-using-a-queue/
	public static void main(String[] args) {

		//List<String> testbinary0 = generateBinaryNumber(0);
        //List<String> testbinary1 = generateBinaryNumber(1);
        //List<String> testbinary3 = generateBinaryNumber(3);
        List<String> testbinary5 = generateBinaryNumber(5);
        System.out.println(testbinary5);
		
	}

	//--Runtime: O(n) Space: O(2n) = O(n) 
	private static List<String> generateBinaryNumber(int n) {

		List<String> result = new ArrayList<>();
		
		Queue<String> q = new LinkedList<>();
		
		q.offer("1");  //-- we can also use addLast() in lieu of offer
		
		while (n != 0) {
			String temp = q.poll(); //-- we can also use removefirst()
			
			q.offer(temp + "0");
			q.offer(temp + "1");
			result.add(temp);
			n--;
		}
		
		return result;
	}

}
