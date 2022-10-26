package Bloomberg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InvalidTransactions_1169_Medium {

	public static void main(String[] args) {
		String[] transactions = {"alice,20,800,mtv","alice,50,100,beijing"};
		System.out.println("Expected: [alice,20,800,mtv,alice,50,100,beijing], Actual: " + invalidTransactions(transactions));
		
		String[] transactions2 = {"alice,20,800,mtv","alice,50,1200,mtv"};
		System.out.println("Expected: [alice,50,1200,mtv], Actual: " + invalidTransactions(transactions2));
		
	}

	/*
	 * Time: O(transactions.length^2), worst case is when all transactions have the
	 * same name and amounts are <=1000, for each we iterate through every
	 * transaction 
	 * 
	 * Space: O(transactions.length), worst case is when all
	 * transactions have a unique name so each of them has a separate entry in the
	 * map
	 */
	public static List<String> invalidTransactions(String[] transactions) {
		// map transaction name to all transactions with that name
		Map<String, List<String[]>> map = new HashMap<>();

		for (String txn : transactions) {
			String[] arr = txn.split(",");
			map.putIfAbsent(arr[0], new ArrayList<>());
			map.get(arr[0]).add(arr);
		}

		List<String> res = new ArrayList<>();
		for (String txn : transactions) {
			String[] arr = txn.split(",");

			// amount exceeds $1000
			if (Integer.valueOf(arr[2]) > 1000) {
				res.add(txn);
			} else {
				// occurs within 60 minutes of another transaction
				// with the same name in a different city.

				for (String[] curr : map.get(arr[0])) {

					int splitTxnTime = Integer.valueOf(arr[1]);
					int currTxnTime = Integer.valueOf(curr[1]);

					if (Math.abs(splitTxnTime - currTxnTime) <= 60 && !arr[3].equals(curr[3])) {

						res.add(txn);
						break;

					}
				}
			}
		}

		return res;
	}
}
