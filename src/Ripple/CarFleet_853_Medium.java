package Ripple;

import java.util.Arrays;

public class CarFleet_853_Medium {

	public static void main(String[] args) {

        // Example test from LeetCode
        System.out.println(carFleet(12, new int[]{10,8,0,5,3}, new int[]{2,4,1,1,3})); // expected 3

        // Additional tests
        System.out.println(carFleet(10, new int[]{0,4,2}, new int[]{2,1,3})); // expected 1 (all catch up)
        System.out.println(carFleet(100, new int[]{1,2,3,4,5}, new int[]{5,4,3,2,1})); // expected 5 (none catch)
        System.out.println(carFleet(10, new int[]{7,7,7}, new int[]{1,1,1})); // expected 3 (same pos but independent)
    }

	/* 
    Time: O(N log N), where N is the number of cars. 
          The complexity is dominated by the sorting operation.
    Space: O(N), the space used to store information about the cars.
    */
    public static int carFleet(int target, int[] position, int[] speed) {
        int res = 0;
        int N = position.length;
        Car[] cars = new Car[N];

        /*
        For each car compute the time it needs to reach the target: 
        time = (target - position) / speed
        */
        for(int i=0; i<N; i++) {
            cars[i] = new Car(position[i], (double) (target - position[i])/speed[i]);
        }

        /*
        Sort cars by position ascending (left → right). We'll process from 
        the car closest to the target (largest position) backwards to the 
        start.
        */
        Arrays.sort(cars, (a, b) -> Double.compare(a.position, b.position));

        /* 
        A new fleet forms only when a car’s time to reach target is greater than 
        the current fleet’s time (cars[i][1] > cur).

        Why?
        If a car takes longer (cars[i][1] > cur), it means it cannot catch up to 
        the fleet ahead → new fleet formed.

        If a car’s time ≤ cur, it will catch up and join the fleet ahead → no new fleet.
        */
        double cur = 0;
        for(int i = N-1; i>=0; i--) {
            if(cars[i].time > cur) {
                cur = cars[i].time;
                res++;
            }
        }
        return res;

    }

    static class Car {
        int position; // current position of the car
        double time; // time it will take to reach the target
        public Car(int p, double t) {
            position = p;
            time = t;
        }
    }
	
}
