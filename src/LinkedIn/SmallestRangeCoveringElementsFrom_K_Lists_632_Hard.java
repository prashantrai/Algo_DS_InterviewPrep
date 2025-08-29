package LinkedIn;

import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class SmallestRangeCoveringElementsFrom_K_Lists_632_Hard {


	/*
    Algorithm Explanation: 
    This problem asks us to find the smallest range that includes 
    at least one number from each of the k sorted lists.

    Step-by-Step Approach:
    1. Merge with Tracking: We need to process elements from all lists 
    while keeping track of which list each element comes from.
    
    2. Sliding Window Technique: Use a sliding window approach where:
    - We expand the window by adding elements until we have at 
      least one element from each list.
    - Once we have coverage of all lists, we try to shrink the window 
      from the left while maintaining coverage.
    - Track the smallest valid range found.

    3. Data Structures:
    - A min-heap (priority queue) to get the next smallest element 
      across all lists.
    - A frequency map to track how many elements we have from each 
      list in our current window.
    - Variables to track the current min and max values in our window.

    4. Process:
    - Start by adding the first element from each list to the heap
    - Keep track of the current maximum value among these initial elements
    - Use sliding window to find the optimal range.
    */

    /*
    Time Complexity: O(N log K) where N is the total number of 
    elements across all lists, and K is the number of lists.

    Space Complexity: O(K) for the heap and frequency map.
    */

    // Inner class to store value and its list index
    static class Element {
        int value;
        int listIndex;
        int elementIndex; // position in the original list
        
        public Element(int value, int listIndex, int elementIndex) {
            this.value = value;
            this.listIndex = listIndex;
            this.elementIndex = elementIndex;
        }
    }
    
    public static int[] smallestRange(List<List<Integer>> nums) {
        // Priority queue to get the smallest element (min-heap)
        PriorityQueue<Element> minHeap = new PriorityQueue<>(
            (a, b) -> Integer.compare(a.value, b.value)
        );
        
        // Track how many elements we have from each list
        int[] listCount = new int[nums.size()];
        int listsCovered = 0; // Number of distinct lists represented
        
        // Add first element from each list to the heap
        int currentMax = Integer.MIN_VALUE;
        for (int i = 0; i < nums.size(); i++) {
            if (nums.get(i) != null && !nums.get(i).isEmpty()) {
                Element element = new Element(nums.get(i).get(0), i, 0);
                minHeap.offer(element);
                currentMax = Math.max(currentMax, nums.get(i).get(0));
            }
        }
        
        // Initialize result range with maximum possible values
        int[] result = {0, Integer.MAX_VALUE};
        
        // Sliding window approach
        while (minHeap.size() == nums.size()) {
            // Get the smallest element (current left boundary)
            Element minElement = minHeap.poll();
            
            // Check if current range is smaller
            if (currentMax - minElement.value < result[1] - result[0]) {
                result[0] = minElement.value;
                result[1] = currentMax;
            }
            
            // Remove this element from our count
            listCount[minElement.listIndex]--;
            if (listCount[minElement.listIndex] == 0) {
                listsCovered--;
            }
            
            // Add next element from the same list if available
            minElement.elementIndex++;
            if (minElement.elementIndex < nums.get(minElement.listIndex).size()) {
                minElement.value = nums.get(minElement.listIndex).get(minElement.elementIndex);
                minHeap.offer(minElement);
                
                // Update current max if needed
                currentMax = Math.max(currentMax, minElement.value);
                
                // Add to count for this list
                listCount[minElement.listIndex]++;
                if (listCount[minElement.listIndex] == 1) {
                    listsCovered++;
                }
            }
        }
        
        return result;
    }
    
    
public static void main(String[] args) {
        
        // Test Case 1: Example from problem
        List<List<Integer>> nums1 = Arrays.asList(
            Arrays.asList(4, 10, 15, 24, 26),
            Arrays.asList(0, 9, 12, 20),
            Arrays.asList(5, 18, 22, 30)
        );
        System.out.println("Test 1: " + Arrays.toString(smallestRange(nums1))); // Expected: [20, 24]
        
        // Test Case 2: Example from problem
        List<List<Integer>> nums2 = Arrays.asList(
            Arrays.asList(1, 2, 3),
            Arrays.asList(1, 2, 3),
            Arrays.asList(1, 2, 3)
        );
        System.out.println("Test 2: " + Arrays.toString(smallestRange(nums2))); // Expected: [1, 1]
        
        // Test Case 3: Single element in each list
        List<List<Integer>> nums3 = Arrays.asList(
            Arrays.asList(1),
            Arrays.asList(2),
            Arrays.asList(3)
        );
        System.out.println("Test 3: " + Arrays.toString(smallestRange(nums3))); // Expected: [1, 3]
        
        // Test Case 4: Two lists with overlapping ranges
        List<List<Integer>> nums4 = Arrays.asList(
            Arrays.asList(1, 3, 5),
            Arrays.asList(2, 4, 6)
        );
        System.out.println("Test 4: " + Arrays.toString(smallestRange(nums4))); // Expected: [2, 3]
        
        // Test Case 5: Edge case with one list having single element
        List<List<Integer>> nums5 = Arrays.asList(
            Arrays.asList(10, 15, 20),
            Arrays.asList(5, 10, 15),
            Arrays.asList(7)
        );
        System.out.println("Test 5: " + Arrays.toString(smallestRange(nums5))); // Expected: [7, 10]
        
        // Test Case 6: Large gap between some elements
        List<List<Integer>> nums6 = Arrays.asList(
            Arrays.asList(1, 100),
            Arrays.asList(2, 50),
            Arrays.asList(3, 25)
        );
        System.out.println("Test 6: " + Arrays.toString(smallestRange(nums6))); // Expected: [1, 3]
    }
}
