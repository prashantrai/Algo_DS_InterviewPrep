package com.interview.questions;

public class YelpHackerRank {

	public static void main(String[] args) {

		System.out.println(reduceString("asdheeeeskaeeeleee"));
		
	}
	
	
	/**
	   *  Given a string of characters change all occurrences of
	   *  two or more consecutive "e"s with a single "e".
	   *  Example:
	   *  Input: 'asdheeeeskaeeeleee'
	   *  Output: 'asdheskaele'
	   *
	   * @param inputString String
	   *
	   * @return String
	   */
	  public static String reduceString(String is) {
	      
	      StringBuilder sb = new StringBuilder();
	      sb.append(is.charAt(0));
	      for(int i=1; i<is.length(); i++) {
	          
	          if(is.charAt(i) != is.charAt(i-1)) {
	              sb.append(is.charAt(i));
	          }
	      }
	      return sb.toString();
	  }


}
