package Motive;

public class MergeSortedArray_88_Easy {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	// Time: O(n+m)
    // Space: O(1)
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int p1 = m-1;
        int p2 = n-1;
        int p3 = m+n-1;

        for(int i=p3; i>=0; i--) {
            if(p2 < 0) break;
            if(p1 >= 0 && nums1[p1] > nums2[p2]) {
                nums1[i] = nums1[p1--];
            } else {
                nums1[i] = nums2[p2--];
            }
        }

    }

}
