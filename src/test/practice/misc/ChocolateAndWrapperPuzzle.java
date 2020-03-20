package test.practice.misc;

public class ChocolateAndWrapperPuzzle {

	public static void main(String[] args) {

		// total money 
        int money = 15; 
          
        // cost of each candy 
        int price = 1;  
          
        // no of wrappers needs to be 
        int wrap = 3 ;  
          
        // exchanged for one chocolate. 
        System.out.println(countMaxChoco(money, price, wrap)); 
        
	}
	
	//--Quantcast
	//--https://www.geeksforgeeks.org/program-chocolate-wrapper-puzzle/
	// Returns maximum number of chocolates  
    // we can eat with given money, price  
    // of chocolate and number of wrapprices 
    // required to get a chocolate. 
    static int countMaxChoco(int money, int price, int wrap) { 
          
        // Corner case 
        if (money < price) 
            return 0; 
      
        // First find number of chocolates  
        // that can be purchased with the 
        // given amount 
        int choc = money / price; 
      
        // Now just add number of chocolates 
        // with the chocolates gained by 
        // wrappers price 
        choc = choc + (choc - 1) / (wrap - 1); 
        return choc; 
    }

}
