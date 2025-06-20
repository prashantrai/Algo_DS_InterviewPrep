package Square;

public class CashApp_PhoneScreen_15July2024 {

	public static void main(String[] args) {
		int[] a = { 2, 1, 0, 1, 2, 4, 6 };
		
		System.out.println("Part 1\n");
		printGraph(a);
		
		System.out.println("\nPart 2\n");
		printVerticalGraph(a);
		
		//int[] a = { 3, 2, 0, 8, 12, 4, 5 };
		
		System.out.println("\nPart 3\n");
		printBarGraph(a);
	}

	// Part 1
	/*
	 Time Complexity: O(n * m)
		Where n is the length of the input array and 
		m is the maximum value in the array.
		The outer loop runs n times, and the inner 
		loop can run up to m times in the worst case.
	 Space Complexity: O(1)
		It uses only a constant amount of extra space, regardless of input size.
	 * */
	public static void printGraph(int[] a) {
		if (a == null || a.length == 0)
			return;

		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i]; j++) {
				System.out.print("-");
			}
			System.out.println();
		}

	}

	// Part 2
	/*
	 Time Complexity: O(n * m)
	 	Where n is the length of the input array and 
	 	m is the maximum value in the array.
		The first loop to find max is O(n).
		The nested loops run m times for the outer 
		loop and n times for the inner loop.
	 Space Complexity: O(1)
		It uses only a constant amount of extra space.
	 * */
	public static void printVerticalGraph(int[] a) {

		int max = 0;

		for (int value : a) {
			max = Math.max(value, max);
		}

		for (int i = max; i > 0; i--) {
			for (int value : a) {
				if (value >= i) {
					System.out.print("|");
				} else {
					System.out.print(" ");
				}
			}
			System.out.println();
		}

	}

	// Part 3
	/*
	 Time Complexity: O(n * m)
		Where n is the length of the input array 
		and m is the maximum value in the array.
		The first loop to find max is O(n).
		The nested loops run m times for the outer 
		loop and n times for the inner loop.
		The final loop runs n times.
	 
	 Space Complexity: O(1)
		It uses only a constant amount of extra space.
	 * */
	public static void printBarGraph(int[] a) {
		int max = 0;

		for (int value : a) {
			max = Math.max(value, max);
		}
		for (int i = max; i > 0; i--) {
			System.out.print("│  ");
			for (int value : a) {
				if (value >= i) {

					if (i == value) {
						System.out.print("┌─┐ ");
					} else {
						System.out.print("│ │ ");
					}
				} else {
					System.out.print("    "); // 4 spaces
				}
			}
			System.out.println(" ");
		}
		System.out.print("└──");
		for (int i = 0; i < a.length; i++) {
			if (a[i] != 0)
				System.out.print("┴─┴─");
			else
				System.out.print("────");

		}
		System.out.println("─");

	}

}


/* Question: 
 * 
TEXT BAR GRAPHS
You are working in a system that (like CoderPad) only allows for monospaced text output. 
Users need to share numbers that are better understood graphically. Create code to help them 
translate numerical data into visual bar graphs.


PART 1: HORIZONTAL BAR GRAPH
Given an array of integers, create a horizontal bar graph where each entry translates into a row 
of dashes of that length (from top to bottom). 

For example, dataset [2, 1, 0, 1, 2, 4, 6] renders as:

--
-

-
--
----
------

Note that the third output line is blank to represent the 0 value.



PART 2: VERTICAL BAR GRAPH
Your users prefer vertical bar graphs, where each integer entry is shown as a column 
of |s (from left to right). Implement that so the dataset from Part 1 renders as:

      |
      |
     ||
     ||
|   |||
|| ||||

 


PART 3: USE BOX-DRAWING CHARACTERS
The system supports unicode box-drawing characters (└ ┌ ┴ ┐ ─ │). The designer wants you to use these 
to create a more polished looking graph. The dataset now renders as:

│                          ┌─┐  
│                          │ │  
│                      ┌─┐ │ │  
│                      │ │ │ │  
│  ┌─┐             ┌─┐ │ │ │ │  
│  │ │ ┌─┐     ┌─┐ │ │ │ │ │ │  
└──┴─┴─┴─┴─────┴─┴─┴─┴─┴─┴─┴─┴──

Upgrade your code to implement this design. 

Tip 1: The last line provides both the bottom of the bars ┴─┴ and the bottom border ───.
Tip 2: You can copy the necessary characters from the example graph.
Tip 3: To check your output, copy it from the "Program Output" to under the design. Ideally your output should be character identical, including axes and column spacing.

*/
