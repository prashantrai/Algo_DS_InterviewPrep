package Facebook;

public class NextGreaterElement_III_556_Medium {

	public static void main(String[] args) {

	}
	
	/*
    Algo: 
    1. Convert the n to char array
    2. Iterate the array backward starting from end to 
       Find the first decreasing element.
       
    3. Once decrease element found, this is at index i.
    4. Again, start from the end of the array and find the smallest digit that is 
       larger than nums[i]. This digit is at index j.
       
    5. Swap nums[i] and nums[j]: Swap these two digits to make the 
       number just a little bit larger.
    6. Finally, reverse the sequence of digits after index i 
       to get the smallest possible number.
    7. Convert string to int.
      
      
    Time complexity : O(n). In worst case, only two scans of 
        the whole array are needed. Here, n refers to the number 
        of digits in the given number.

    Space complexity : O(n). An array a of size n is used, 
        where n is the number of digits in the given number.  
      
    */
    public int nextGreaterElement(int n) {
        char[] a = (""+n).toCharArray();
        int i = a.length-2;
        while (i>=0 && a[i] >= a[i+1]) {
            i--;
        }
        if(i<0) return -1;
        
        int j = a.length-1;
        while(j>=0 && a[j] <= a[i]) {
            j--;
        }
        // next greater number for i is found at j.
        // Swap nums[i] and nums[j]
        swap(a, i, j);
        reverse(a, i+1);
        
        // Convert string to int.
        try {
            return Integer.parseInt(new String(a));
        } catch (Exception e){
            return -1;
        }
    }
    
    private void reverse(char[] a, int start) {
        int i = start;
        int j = a.length -1;
        while (i<j) {
            swap(a, i, j);
            i++;
            j--;
        }
    }
    
    private void swap(char[] a, int i, int j) {
        char temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

}
