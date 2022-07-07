package Box;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class TopKElementsFromFilesStoredInDiffrentFolders {

	public static void main(String[] args) {

	}

	/**
	 * 
	 * https://stackoverflow.com/questions/53755715/find-100-largest-numbers-from-all-the-files-present-in-different-folders
	 * 
	 * There are lot of files in all the folders and their sub folders. Each file
	 * will have lot of numbers in each line. Given a root folder, find 100 largest
	 * number from all those files.
	 * 
	 * 
	 * For top 100 int follow: KthLargestElementInAStream_703_Easy,
	 * TopKFrequentElements_347_Medium
	 * 
	 * 
	 * Time: O(NlogN), because for PriorityQueue/Heap
     * Space: O(N)
	 * 
	 */
	
	private static Queue<Integer> minHeap = new PriorityQueue<>();
	private static int k = 100;
	
	private static LinkedList<Integer> findTopKFromFiles(String rootDir) {
		LinkedList<Integer> res = new LinkedList<>();
		if (rootDir == null || rootDir.isEmpty()) {
			return res;
		}
		
		helper(rootDir);
		
		// adding in reverse order in Linked List (no need to perform reverse)
		while(!minHeap.isEmpty()) {
            res.addFirst(minHeap.poll());
        }
		
		return res;
	}
	
	private static void helper(String rootDir) {
		
		File file = new File(rootDir);
		for(File entry : file.listFiles()) {
			if(entry.isDirectory()) {
				findTopKFromFiles(entry.getName());
			} else {
				try (BufferedReader br = new BufferedReader(new FileReader(entry))) {
					String line;
					while ((line = br.readLine()) != null) {
						
						add(Integer.parseInt(line));
					}
				} catch (NumberFormatException | IOException e) { e.printStackTrace(); }
			}
		}
	}
	
	private static void add(int val) {
		minHeap.offer(val);

		if (minHeap.size() > k)
			minHeap.poll();
		
	}
	

}
