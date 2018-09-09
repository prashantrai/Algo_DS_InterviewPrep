package com.interview.questions;

import java.util.HashMap;

public class AliceTech {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//int arr[] = {1, 8, 30, 40, 100};
		int arr[] = {1, 5, 3, 4, 2};
        int n = 2;
        //findPair(arr,n);
        //findPair_2(arr,n);
        //maxCupCakes();
        chutesAndLadders();
	}
	
	public static void chutesAndLadders() {
		int size = 5;
		int dice = 2;
		int[] board = {0,0,1,1,0}; 	//-1
//		int[] board = {0,0,0,1,0}; 	//22
//		int[] board = {0,0,1,0,0};  //221
		
		int currIndx = 0;
		int prevIndx = -1;
		String result = "";
		int tempDice = dice;

		while(true) {
			if(currIndx+dice < size) { 
				result += " "+dice;
			}
			if(prevIndx == currIndx ) {
				result = "" + -1;
				break;
			}
			
			prevIndx = currIndx; // 0, 1
			currIndx += dice; // 2, 3
			
			while(currIndx > size-1 && tempDice >= 1) {
				currIndx--;
				tempDice--;
			}
			
			if(board[currIndx] != 0) {
				currIndx = board[currIndx]; //value=0, index=1
			}
			if (board[currIndx] == 0 && currIndx == size-1) {
				if(tempDice < dice) {
					result = result + " " + tempDice;
				}
				break;
			}
		}
		System.out.println(">>Result: "+result);
	}
	
	
	public static void maxCupCakes() {
		//https://www.hackerrank.com/challenges/chocolate-feast/problem
		//for(int i = 0; i < t; i++){
            System.out.println(f(4, 1, 2));
        //}
		
	}
	
	private static long f(int n, int a, int b){
        return n/a + g(n/a, b);
    }
    
    private static long g(int a, int b){
        if(a < b) return 0;
        else return a/b + g(a-a/b*b + a/b, b);
    }
	
	public static void findPair_2(int[] arr, int n) {
		
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
		
		for(int i=0;i<arr.length;i++) {
			 map.put(arr[i], arr[i]);
		}
		int count=0;
		for(int j=0;j<arr.length;j++) {

		  if(map.containsKey(arr[j]+n)) {
		     count++;
		     System.out.println("Count = "+count);
		     System.out.println("Pair: "+ arr[j] +", "+ (arr[j]+n));
		  }
		}
	}
	
	static boolean findPair(int arr[],int n)
    {
        int size = arr.length;
 
        // Initialize positions of two elements
        int i = 0, j = 1;
 
        // Search for a pair
        while (i < size && j < size)
        {
            if (i != j && arr[j]-arr[i] == n)
            {
                System.out.print("Pair Found: "+
                                 "( "+arr[i]+", "+ arr[j]+" )");
                return true;
            }
            else if (arr[j] - arr[i] < n)
                j++;
            else
                i++;
        }
 
        System.out.print("No such pair");
        return false;
    }

}
