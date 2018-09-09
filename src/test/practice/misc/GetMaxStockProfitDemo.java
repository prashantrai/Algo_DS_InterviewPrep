package test.practice.misc;

public class GetMaxStockProfitDemo {

	public static void main(String[] args) {

//		int[] stockPricesYesterday = new int[] {10, 7, 5, 8, 11, 9};
		//int[] stockPricesYesterday = new int[] {10, 22, 5, 75, 65, 80};
		int[] stockPricesYesterday = new int[] {100, 180, 260, 310, 40, 535, 695};
		

		//getMaxProfit(stockPricesYesterday); 		// returns 6 (buying for $5 and selling for $11)
		
		int[] stockPricesYesterday2 = new int[] {10, 2, 3, 6, 5, 4};
//		int[] stockPricesYesterday2 = new int[] {100, 180, 260, 310, 40, 535, 695};
//		int[] stockPricesYesterday2 = new int[] {10, 7, 5, 8, 11, 9};
		getMaxProfit_2(stockPricesYesterday2); 		// returns 3 (buying for $10 and selling for $6) -- minimize the loss
		
	}

	private static void getMaxProfit(int[] stockPricesYesterday) {
		
		
		int boughtPrice = stockPricesYesterday[0];
		int currPrice = stockPricesYesterday[0];
		int prevPrice = boughtPrice;
		
		System.out.println("Bought @: "+boughtPrice);
		
		for(int i=1; i<stockPricesYesterday.length; i++) {
			currPrice = stockPricesYesterday[i];
			
			if(currPrice < boughtPrice) { 
				System.out.println("Sold @: "+prevPrice);
				System.out.println("Bought @: "+currPrice);
				boughtPrice = currPrice;
				prevPrice = boughtPrice;
				
			} else if(currPrice > boughtPrice) {
				if( (currPrice - boughtPrice) <  (prevPrice - boughtPrice)) {
					System.out.println("Sold @: "+prevPrice);
					System.out.println("Bought @: "+currPrice);
					boughtPrice = currPrice;
				}
				prevPrice = currPrice;
			}
		}
		
		if( (currPrice - boughtPrice) <  (prevPrice - boughtPrice)) {
			System.out.println("Sold @: "+prevPrice);
		} else {
			System.out.println("Sold @: "+currPrice);
		}
		
	}
	
	private static void getMaxProfit_2(int[] stockPricesYesterday) {
		
		
		int boughtPrice = stockPricesYesterday[0];
		int currPrice = stockPricesYesterday[0];
		int prevPrice = boughtPrice;
		int lowestPrice = 0;
		boolean isSold = false;
		
		System.out.println("Bought @: "+boughtPrice);
		
		for(int i=1; i<stockPricesYesterday.length; i++) {
			currPrice = stockPricesYesterday[i];
			
			if(currPrice < boughtPrice) { 
				
				if(isSold) { 
					boughtPrice = currPrice;
					prevPrice = boughtPrice;
					isSold = false;
				} 

				if (currPrice < prevPrice) {
					System.out.println("Sold @: "+prevPrice);
					System.out.println("Bought @: "+currPrice);
					
					isSold = true;
					boughtPrice = currPrice;
					prevPrice = boughtPrice;
				}

				if(currPrice >= lowestPrice)
					lowestPrice = currPrice;
				
			} else if(currPrice > boughtPrice) {
				if( (currPrice - boughtPrice) <  (prevPrice - boughtPrice)) {
					System.out.println("Sold @: "+prevPrice);
					System.out.println("Bought @: "+currPrice);
					boughtPrice = currPrice;
					isSold = true;
				}
				prevPrice = currPrice;
			}
		}
		if(currPrice < boughtPrice && currPrice < lowestPrice) {
			System.out.println("Sold @: "+lowestPrice);
		}
		else if( (currPrice - boughtPrice) <  (prevPrice - boughtPrice)) {
			System.out.println("Sold @: "+prevPrice);
		} else {
			System.out.println("Sold @: "+currPrice);
		}
		
	}

}
