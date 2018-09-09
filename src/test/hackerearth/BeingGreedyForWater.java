package test.hackerearth;
/* IMPORTANT: Multiple classes and nested static classes are supported */

/*
 * uncomment this if you want to read input.
 */
//imports for BufferedReader
import java.io.BufferedReader;
import java.io.InputStreamReader;

//import for Scanner and other utility  classes
import java.util.*;


//--https://www.hackerearth.com/practice/algorithms/greedy/basics-of-greedy-algorithms/tutorial/
//--https://www.hackerearth.com/practice/algorithms/greedy/basics-of-greedy-algorithms/practice-problems/algorithm/rooms-1/description/

//--http://www.geeksforgeeks.org/minimum-number-platforms-required-railwaybus-station/
//--http://www.geeksforgeeks.org/minimum-number-platforms-required-railwaybus-station-set-2-map-based-approach/

class BeingGreedyForWater {
    public static void main(String args[] ) throws Exception {
        /*
         * Read input from stdin and provide input before running
         * Use either of these methods for input

        //BufferedReader
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line = br.readLine();
        int N = Integer.parseInt(line);

        //Scanner
        Scanner s = new Scanner(System.in);
        int N = s.nextInt();

        for (int i = 0; i < N; i++) {
            System.out.println("hello world");
        }
        */
        Scanner in = new Scanner(System.in);
        int t=1;	//in.nextInt();  //--no of test cases
    
        for(int i=0;i<t;i++) {
            int n=in.nextInt(); //--number of bottles
            int w=in.nextInt();  //--total amount of water
            int[] a = new int[n];
            
            for(int j=0;j<n;j++){
                a[j]=in.nextInt();
            }
            
            Arrays.sort(a);
            int count=0;
            for(int j=0;j<n;j++){
                if(a[j]<=w){
                	System.out.println(">>> a["+j+"]:: " + a[j]);
                    w -= a[j];
                    count++;
                }
                else
                break;
            }
            System.out.println(count);
        }
        System.out.println("Hello World!");
    }
}