package asana;

public class HungryRabbit {

	/* Problem: https://github.com/vildevev/Carrot_Search
	 * 
	 * Hungry Rabbit
		This is a pretty interesting problem, but it takes a bit longer than most interviews would allow. It is not a difficult algorithm, but it is a great question for determining code hygiene, since there is a lot of opportunity for code deduplication and that sort of thing.
		
		Problem Statement:
		A very hungry rabbit is placed in the center of of a garden, represented by a rectangular N x M 2D matrix.
		
		The values of the matrix will represent numbers of carrots available to the rabbit in each square of the garden. If the garden does not have an exact center, the rabbit should start in the square closest to the center with the highest carrot count.
		
		On a given turn, the rabbit will eat the carrots available on the square that it is on, and then move up, down, left, or right, choosing the the square that has the most carrots. If there are no carrots left on any of the adjacent squares, the rabbit will go to sleep. You may assume that the rabbit will never have to choose between two squares with the same number of carrots.
		
		Write a function which takes a garden matrix and returns the number of carrots the rabbit eats eg.
		
		    [[5, 7, 8, 6, 3],
		     [0, 0, 7, 0, 4],
		     [4, 6, 3, 4, 9],
		     [3, 1, 0, 5, 8]]
		Should return 27
		
		Key components:
		Deduplicates code for enumerating possible moves for both starting and next moves.
		is able to parse the matrix
		has an intuitive approach for dealing with the problem.

	 * 
	 */
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
