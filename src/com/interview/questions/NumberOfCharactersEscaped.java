package com.interview.questions;
public class NumberOfCharactersEscaped {
	public static void main(String[] args) {
		System.out.println(numberOfCharactersEscaped(
				"#uv!#!lcr#f!#d!!v!##nwy!t!#i!vpz!kaxc!oj#!#!!d!!k!e#!!qs!rivzj#sqjlf#pe#v!cd!!!nst#je!##qthb#t!!ty#v"));
	}

	
	// https://leetcode.com/discuss/interview-question/428240/Audible-Onlie-Assessment-for-New-Graduate-Number-of-characters-escaped
	public static int numberOfCharactersEscaped(String expression) {
		int begin = 0;
		int length = expression.length();
		int end = 0;

		int escapeCount = 0;
		int numberSignCount = 0;

		while (end < length) {
			if (expression.charAt(end) == '#') {
				numberSignCount++;

				if (numberSignCount == 1) {
					begin = end;
				}
			}

			if (numberSignCount == 2) {
				escapeCount += getEscapeCount(begin, end, expression);
				begin = end;
				numberSignCount = 0;
			}

			end++;
		}

		return escapeCount;
	}

	private static int getEscapeCount(int start, int end, String expression) {
		int count = 0;
		while (start <= end) {
			if (expression.charAt(start) == '!' && Character.isLowerCase(expression.charAt(start + 1))) {
				count++;
			}
			start++;
		}

		return count;
	}
}