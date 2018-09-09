package com.interview.questions;


/*
 * Write program to search a movie title using screen keyboard with minimal traversal 
 * 
 *  input:  keyBoard : can have all the chars in any order
 *  		movie title : String
 *  		initial position of remote selection
 * */

//UP, DOWN, LEFT, RIGHT, OK

//A B C D E
//F G H I J
//K L M N O
//P Q R S T
//U V W X Y


//Initial: A
//Movie title: BHE
//Result: RIGHT, OK, RIGHT, DOWN, OK, RIGHT, RIGTH, UP, OK


//Result: RIGHT, RIGHT, LEFT, OK, RIGHT, DOWN, OK, RIGHT, RIGTH, UP, OK  (incorrect, not the shortest one)


public class Medallia {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}



//UP, DOWN, LEFT, RIGHT, OK

//A B C D E
//F G H I J
//K L M N O
//P Q R S T
//U V W X Y


//Initial: A
//Movie title: BHE
//Result: RIGHT, OK, RIGHT, DOWN, OK, RIGHT, RIGTH, UP, OK


//Result: RIGHT, RIGHT, LEFT, OK, RIGHT, DOWN, OK, RIGHT, RIGTH, UP, OK  (incorrect, not the shortest one)

/*arr[4][4] 
char[] movie BHE
char input = A

public String[] getRemoteAction(char[][] arr, char[] movie, char input) {
	
	 String remote = "";     
	 int m = 0;
	 boolean intitialiseLocaiton = false;
	 
	 for(int r=0; r< arr.length; r++) {
	 
	     for(int c=0; c< arr[r].length; c++) {
	         
	        if(arr[r][c] != input && !intitialiseLocaiton) { 
	            intitialiseLocaiton = true;
	            continue;
	        }
	        
	        if(arr[r][c] == movie[m]) {
	             String remote += "R, OK";
	            
	             input = arr[r][c];
	        }              
	     }
	 
	 }
 
 

}*/
