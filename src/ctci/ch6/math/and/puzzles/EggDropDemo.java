package ctci.ch6.math.and.puzzles;

public class EggDropDemo {

	public static void main(String[] args) {

		System.out.println(findBreakingPoint(100));
		
	}
	
	static int countDrops = 0;
	static int breakingPoint = 99; 
	
	public static boolean drop(int floor) {
		countDrops++;
		return floor >= breakingPoint;
	}
	
	public static int findBreakingPoint (int floors) {
		
		int interval = 14;
		int prevFloor = 0;
		int egg1 = interval;
		
		//--drop egg1 at decreasing interval
		while(!drop(egg1) && egg1 <= floors) {
			interval -= 1;
			prevFloor = egg1;
			egg1 += interval;
			
		}
		
		//--drop egg2 at 1 unit increment
		int egg2 = prevFloor + 1;
		while(egg2 < egg1 && egg2 <= floors && !drop(egg2)) {
			egg2 += 1;
		}
		
		//--if didn't break return -1
		return egg2 > floors ? -1 : egg2;
		
	}

}
