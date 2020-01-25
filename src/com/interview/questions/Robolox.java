package com.interview.questions;

import java.io.*;
import java.util.*;

/*
 * Imagine we have an image. We’ll represent this image as a simple 2D array where every pixel is a 1 or a 0. 
The image you get is known to have a single rectangle of 0s on a background of 1s. 

Write a function that takes in the image and returns the coordinates of the rectangle of 0’s 
-- either top-left and bottom-right; or top-left, width, and height.

Sample output:
	x: 3, y: 2, width: 3, height: 2
	2,3 3,5
	3,3 5,3 -- it’s ok to reverse columns/rows as long as you’re consistent


	int[][] image = {
		{1, 1, 1, 1, 1, 1, 1},
		{1, 1, 1, 1, 1, 1, 1},
		{1, 1, 1, 0, 0, 0, 1},
		{1, 1, 1, 0, 0, 0, 1},
		{1, 1, 1, 1, 1, 1, 1}
	};

*/


public class Robolox {
	public static void main(String[] args) {
		
		int[][] image = { 
							{ 1, 1, 0, 0, 0, 0, 0 }, 
							{ 1, 1, 0, 0, 0, 0, 0 }, 
							{ 1, 1, 0, 0, 0, 0, 0 },
							{ 1, 1, 0, 0, 0, 0, 0 }, 
							{ 1, 1, 0, 0, 0, 0, 0 } 
						};

		findCoordinates(image);
	}

	public static void findCoordinates(int[][] m) {

		List<Point> points = new ArrayList<>();
		if (m.length == 0)
			return;

		// --r=3, c=0
		for (int r = 0; r < m.length; r++) {
			for (int c = 0; c < m[0].length; c++) {

				if (m[r][c] == 0 && points.size() == 0) { // --for top left
					points.add(new Point(r, c));
					break;
				}

				else if (m[r][c] == 0) {
					if (r < m.length - 1 && m[r + 1][c] == 0) { // skip current
																// row

						break;
					}

					else if (r < m.length - 1 && c < m[0].length - 1 && m[r + 1][c] != 0 && m[r][c + 1] == 0) {
						continue;
					} else if (c < m[0].length - 1 && m[r][c + 1] != 0) {
						points.add(new Point(r, c));// bottom right
						System.out.println(points);
						break;

					} else {
						points.add(new Point(r, c));// bottom right

						System.out.println(points);
						break;
					}

				}

			}
		}

	}

	public static boolean isInBound(int[][] m, int r, int c) {

		// System.out.println("r="+r+", c="+c);

		if (r < m.length - 1 && c < m[0].length - 1) {
			return true;
		}
		return false;
	}

}

class Point {
	public int x;
	public int y;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public String toString() {
		return "r=" + x + ", c=" + y;
	}
}
