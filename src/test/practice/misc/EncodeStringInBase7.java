package test.practice.misc;

import java.util.Scanner;

public class EncodeStringInBase7 {

	//Atlassian
	public static void main(String[] args) {

        char [] b7={'0','a', 't', 'l', 's', 'i', 'n'};
        ///Scanner in = new Scanner(System.in);
        //int t = in.nextInt();
        int t = 7;

        int i=t;
        StringBuffer sb = new StringBuffer();
        while (i>0){
            int m=i%7;
            sb.append(b7[m]);
            i=i/7;
        }

        System.out.println(sb.reverse());
        //in.close();

    }

}
