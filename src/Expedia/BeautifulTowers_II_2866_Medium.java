package Expedia;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class BeautifulTowers_II_2866_Medium {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	/** Monotonic Stack - DP version (less code and ideal for interview, only catch is, we should be able to explain the formula) */
	
	// comments as interview script
	public long maximumSumOfHeights(List<Integer> maxHeights) {

        int n = maxHeights.size();

        long[] left = new long[n];
        long[] right = new long[n];

        Deque<Integer> stack = new ArrayDeque<>();

        /*
         * left[i] = maximum sum of a valid mountain from 0...i
         * where i is the peak.
         *
         * I compute this using a monotonic increasing stack because
         * a smaller height acts as a boundary. Any height greater than
         * the current peak height must be reduced.
         */
        for (int i = 0; i < n; i++) {

            /*
             * Remove previous heights greater than current height.
             *
             * Example:
             *
             * 2 6 7 5
             *
             * If 5 is the peak, the mountain becomes:
             *
             * 2 5 5 5
             *
             * So 6 and 7 cannot be part of the solution.
             *
             * After popping, stack top is the previous smaller boundary.
             */
            while (!stack.isEmpty()
                    && maxHeights.get(stack.peek()) > maxHeights.get(i)) {

                stack.pop();
            }


            if (stack.isEmpty()) {

                /*
                 * No previous smaller element exists.
                 *
                 * This means every element in this prefix must be reduced
                 * to the current height.
                 *
                 * Example:
                 *
                 * 8 7 6 5
                 *
                 * becomes
                 *
                 * 5 5 5 5
                 *
                 * Contribution = height * number of elements
                 */
                left[i] = (long) maxHeights.get(i) * (i + 1);

            } else {

                int prev = stack.peek();

                /*
                 * Previous smaller element splits the prefix into two parts.
                 *
                 * Example:
                 *
                 * 2 | 6 7 5
                 *
                 *          ^
                 *        current
                 *
                 * The prefix before prev is already optimal:
                 *
                 * left[prev]
                 *
                 * The elements between prev+1 and i are >= current height,
                 * so they must all become current height.
                 *
                 * Number of those elements:
                 *
                 * i - prev
                 *
                 * New contribution:
                 *
                 * height * (i - prev)
                 *
                 * Therefore:
                 *
                 * left[i] =
                 * previous optimal prefix +
                 * newly adjusted section
                 */
                left[i] = left[prev]
                        + (long) maxHeights.get(i) * (i - prev);
            }

            /*
             * Store index because we need the position of the boundary
             * to calculate the distance later.
             */
            stack.push(i);
        }


        stack.clear();


        /*
         * Compute the same idea from right to left.
         *
         * right[i] = maximum sum of a valid mountain from i...n-1
         * where i is the peak.
         *
         * Here the monotonic stack helps us find the next smaller element.
         */
        for (int i = n - 1; i >= 0; i--) {


            /*
             * Remove heights that cannot stay taller than current height.
             *
             * After removing them, stack top is the next smaller boundary.
             */
            while (!stack.isEmpty()
                    && maxHeights.get(stack.peek()) > maxHeights.get(i)) {

                stack.pop();
            }


            if (stack.isEmpty()) {

                /*
                 * No next smaller boundary.
                 *
                 * Entire suffix becomes current height.
                 *
                 * Example:
                 *
                 * 5 6 7 8
                 *
                 * becomes
                 *
                 * 5 5 5 5
                 */
                right[i] = (long) maxHeights.get(i) * (n - i);

            } else {

                int next = stack.peek();

                /*
                 * Mirror of the left calculation.
                 *
                 * Everything after next is already optimal:
                 *
                 * right[next]
                 *
                 * Elements from i to next-1 must become current height.
                 *
                 * Count of elements:
                 *
                 * next - i
                 *
                 * Contribution:
                 *
                 * height * (next - i)
                 *
                 * Therefore:
                 *
                 * right[i] =
                 * existing optimal suffix +
                 * adjusted section
                 */
                right[i] = right[next]
                        + (long) maxHeights.get(i) * (next - i);
            }

            stack.push(i);
        }


        long answer = 0;

        /*
         * Try every index as the peak.
         *
         * left[i] gives the best mountain on the left side.
         * right[i] gives the best mountain on the right side.
         *
         * Peak height is counted twice, so subtract it once.
         */
        for (int i = 0; i < n; i++) {

            answer = Math.max(answer,
                    left[i] + right[i] - maxHeights.get(i));
        }

        return answer;
    }
	
	
	// comments are more like Stidy Notes
	public long maximumSumOfHeights3(List<Integer> maxHeights) {

        int n = maxHeights.size();

        long[] left = new long[n];
        long[] right = new long[n];

        Deque<Integer> stack = new ArrayDeque<>();

        /*
         * left[i] represents:
         *
         * Maximum possible sum from index 0 to i
         * assuming index i is the peak.
         *
         * Example:
         *
         * maxHeights = [5,3,4]
         *
         * For i = 2 (height = 4):
         *
         * Valid mountain:
         *
         * 3 3 4
         *
         * left[2] = 10
         */
        for (int i = 0; i < n; i++) {
            /*
             * Remove all previous heights greater than current height.
             *
             * Why?
             *
             * Suppose:
             *
             * [2,6,7] and current height = 5
             *
             * If 5 is the new peak:
             *
             * 2 6 7 5
             *
             * becomes
             *
             * 2 5 5 5
             *
             * Therefore heights 6 and 7 cannot remain.
             *
             * After popping, stack top becomes the previous smaller
             * element which acts as the boundary.
             */
            while (!stack.isEmpty()
                    && maxHeights.get(stack.peek()) > maxHeights.get(i)) {

                stack.pop();
            }

            if (stack.isEmpty()) {
                /*
                 * There is no previous smaller element.
                 *
                 * Example:
                 *
                 * [8,7,6,5]
                 *
                 * For height 5:
                 *
                 * Entire prefix must become:
                 *
                 * 5 5 5 5
                 *
                 * Number of elements = i + 1
                 *
                 * Contribution:
                 *
                 * height * count
                 *
                 * = 5 * 4
                 */
                left[i] = (long) maxHeights.get(i) * (i + 1);

            } else {
                int prev = stack.peek();
                /*
                 * Previous smaller element divides the prefix into two parts.
                 *
                 * Example:
                 *
                 * maxHeights = [2,6,7,5]
                 *
                 * Current index = 3
                 * Current height = 5
                 *
                 * Previous smaller element:
                 *
                 *          prev
                 *           |
                 *           v
                 * 2 | 6 7 5
                 *
                 * Everything before prev stays unchanged.
                 *
                 * left[prev] already contains the optimal sum:
                 *
                 * 2
                 *
                 *
                 * Everything between prev+1 and i becomes height 5:
                 *
                 * 5 5 5
                 *
                 * Number of elements:
                 *
                 * i - prev
                 *
                 * Contribution:
                 *
                 * height * (i - prev)
                 *
                 *
                 * Therefore:
                 *
                 * left[i]
                 *
                 * =
                 *
                 * previous optimal part
                 *
                 * +
                 *
                 * new flattened part
                 *
                 */
                left[i] = left[prev]
                        + (long) maxHeights.get(i) * (i - prev);
            }

            /*
             * Store index, not value.
             *
             * We need the index later to calculate:
             *
             * distance = i - prev
             */
            stack.push(i);
        }
        stack.clear();

        /*
         * Same idea from right side.
         *
         * right[i] represents:
         *
         * Maximum possible sum from index i to n-1
         * assuming index i is the peak.
         */
        for (int i = n - 1; i >= 0; i--) {
            /*
             * Remove all heights greater than current height
             * while moving from right to left.
             *
             * We are finding the next smaller element.
             */
            while (!stack.isEmpty()
                    && maxHeights.get(stack.peek()) > maxHeights.get(i)) {

                stack.pop();
            }
            if (stack.isEmpty()) {
                /* No next smaller element exists.
                 *
                 * Example:
                 *
                 * [5,6,7,8]
                 *
                 * For height 5:
                 *
                 * Entire suffix becomes:
                 *
                 * 5 5 5 5
                 *
                 * Number of elements:
                 *
                 * n-i
                 */
                right[i] = (long) maxHeights.get(i) * (n - i);


            } else {

                int next = stack.peek();

                /*
                 * Same logic as left side, but mirrored.
                 *
                 * Example:
                 *
                 * [5,7,8,3]
                 *
                 * Current height = 3
                 *
                 * Next smaller boundary:
                 *
                 * 5 7 8 | 3
                 *
                 * Everything after next smaller is already optimal.
                 *
                 * The section from i to next-1 becomes current height.
                 *
                 * Number of elements:
                 *
                 * next - i
                 *
                 * Contribution:
                 *
                 * height * (next - i)
                 *
                 *
                 * Therefore:
                 *
                 * right[i]
                 *
                 * =
                 *
                 * already calculated suffix
                 *
                 * +
                 *
                 * new flattened section
                 */
                right[i] = right[next]
                        + (long) maxHeights.get(i) * (next - i);
            }

            stack.push(i);
        }


        long answer = 0;


        /*
         * Assume every index is the peak once.
         *
         * left[i] contains:
         *
         *       /
         *      /
         *     i
         *
         * right[i] contains:
         *
         *     i
         *      \
         *       \
         *
         * The peak height is included in both arrays,
         * so subtract it once.
         */
        for (int i = 0; i < n; i++) {

            answer = Math.max(answer,
                    left[i] + right[i] - maxHeights.get(i));
        }

        return answer;
    }
	
	
	
	
	
	
	
	/** Segment Stack Version: Easy to understand but maybe little bigger to code in interview  */ 
	
	// Time and Space: O(N)
    // Represents a contiguous segment of towers having the same final height.
    // Example:
    // Towers: 3 3 3 5 5
    // Stack stores:
    // (3,3), (5,2)
    static class Segment {
        int height;
        int count;
        Segment(int height, int count) {
            this.height = height;
            this.count = count;
        }
    }

    // Using Segment Stack
    public static long maximumSumOfHeights2(List<Integer> maxHeights) {

        int n = maxHeights.size();

        // left[i] = maximum sum of prefix [0...i]
        // assuming i is the peak.
        long[] left = buildLeft(maxHeights);

        // right[i] = maximum sum of suffix [i...n-1]
        // assuming i is the peak.
        long[] right = buildRight(maxHeights);

        long answer = 0;

        // Combine left + right.
        // Peak gets counted twice,
        // so subtract it once.
        for (int i = 0; i < n; i++) {
            answer = Math.max(answer,
                    left[i] + right[i] - maxHeights.get(i));
        }

        return answer;
    }
    
    // Builds left[] using Segment Stack
    private static long[] buildLeft(List<Integer> maxHeights) {
        
        int n = maxHeights.size();
        long[] left = new long[n];
        Deque<Segment> stack = new ArrayDeque<>();

        // Sum represented by all segments currently in stack.
        long runningSum = 0;

        for (int i = 0; i < n; i++) {
            int currentHeight = maxHeights.get(i);
            // Current tower itself contributes one position.
            int mergedCount = 1;

            while (!stack.isEmpty()
                    && stack.peek().height > currentHeight) {

                Segment top = stack.pop();
                runningSum -= (long) top.height * top.count;
                mergedCount += top.count;
            }
            stack.push(new Segment(currentHeight, mergedCount));
            runningSum += (long) currentHeight * mergedCount;
            left[i] = runningSum;
        }

        return left;
    }
    // Exactly same logic from right side.
    private static long[] buildRight(List<Integer> maxHeights) {
        int n = maxHeights.size();
        long[] right = new long[n];
        Deque<Segment> stack = new ArrayDeque<>();

        long runningSum = 0;

        for (int i = n - 1; i >= 0; i--) {

            int currentHeight = maxHeights.get(i);
            int mergedCount = 1;

            while (!stack.isEmpty()
                    && stack.peek().height > currentHeight) {

                Segment top = stack.pop();

                // Remove contribution of taller segment.
                runningSum -= (long) top.height * top.count;

                // Those positions now become current height.
                mergedCount += top.count;
            }

            // Push merged segment.
            stack.push(new Segment(currentHeight, mergedCount));

            // Add contribution of merged segment.
            runningSum += (long) currentHeight * mergedCount;

            // Current suffix sum.
            right[i] = runningSum;
        }

        return right;
    }


    // Same as above but with detailed comments
    

    //----------------------------------------------------------
    // Builds left[] using Segment Stack
    //----------------------------------------------------------

    private static long[] buildLeft2(List<Integer> maxHeights) {

        int n = maxHeights.size();

        long[] left = new long[n];

        // Monotonic increasing stack of segments.
        Deque<Segment> stack = new ArrayDeque<>();

        // Sum represented by all segments currently in stack.
        long runningSum = 0;

        for (int i = 0; i < n; i++) {

            int currentHeight = maxHeights.get(i);

            // Current tower itself contributes one position.
            int mergedCount = 1;

            //--------------------------------------------------
            // Remove every segment taller than current height.
            //--------------------------------------------------
            //
            // Why?
            //
            // Suppose current height = 5
            //
            // Existing mountain:
            //
            // 2 6 7
            //
            // New peak = 5
            //
            // Final mountain must become
            //
            // 2 5 5 5
            //
            // Therefore every segment taller than 5
            // must be flattened to height 5.
            //--------------------------------------------------

            while (!stack.isEmpty()
                    && stack.peek().height > currentHeight) {

                Segment top = stack.pop();

                //--------------------------------------------------
                // Remove old contribution.
                //
                // Example:
                //
                // Segment = (7,2)
                //
                // represents
                //
                // 7 7
                //
                // Contribution = 14
                //
                // Since this segment disappears,
                // subtract it from running sum.
                //--------------------------------------------------

                runningSum -= (long) top.height * top.count;

                //--------------------------------------------------
                // These positions now become part of current height.
                //
                // Example:
                //
                // Current = 5
                //
                // Removed:
                //
                // 7 7
                //
                // They become
                //
                // 5 5
                //
                // Therefore merge their count.
                //--------------------------------------------------

                mergedCount += top.count;
            }

            //--------------------------------------------------
            // Push merged segment.
            //
            // Example:
            //
            // Removed:
            // (6,1)
            // (7,2)
            //
            // Current:
            // 5
            //
            // mergedCount = 4
            //
            // Push:
            //
            // (5,4)
            //--------------------------------------------------

            stack.push(new Segment(currentHeight, mergedCount));

            //--------------------------------------------------
            // Add contribution of new merged segment.
            //
            // Example:
            //
            // (5,4)
            //
            // contributes
            //
            // 5+5+5+5
            //
            // = 20
            //--------------------------------------------------

            runningSum += (long) currentHeight * mergedCount;

            //--------------------------------------------------
            // Current running sum is exactly
            // maximum possible prefix sum
            // if i is the peak.
            //--------------------------------------------------

            left[i] = runningSum;
        }

        return left;
    }

    //----------------------------------------------------------
    // Exactly same logic from right side.
    //----------------------------------------------------------

    private static long[] buildRight2(List<Integer> maxHeights) {

        int n = maxHeights.size();

        long[] right = new long[n];

        Deque<Segment> stack = new ArrayDeque<>();

        long runningSum = 0;

        for (int i = n - 1; i >= 0; i--) {

            int currentHeight = maxHeights.get(i);

            int mergedCount = 1;

            while (!stack.isEmpty()
                    && stack.peek().height > currentHeight) {

                Segment top = stack.pop();

                // Remove contribution of taller segment.
                runningSum -= (long) top.height * top.count;

                // Those positions now become current height.
                mergedCount += top.count;
            }

            // Push merged segment.
            stack.push(new Segment(currentHeight, mergedCount));

            // Add contribution of merged segment.
            runningSum += (long) currentHeight * mergedCount;

            // Current suffix sum.
            right[i] = runningSum;
        }

        return right;
    }

}


