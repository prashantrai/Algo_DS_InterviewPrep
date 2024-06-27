package CART;

import java.util.*;

public class BankingSystemJava {

	public static void main(String[] args) {

	}

	static class Account {
		String accountId;
		int balance;
		int totalOutgoing;

		public Account(String accountId) {
			this.accountId = accountId;
			this.balance = 0;
			this.totalOutgoing = 0;
		}
	}

	private Map<String, Account> accounts;

	public BankingSystemJava() {
		accounts = new HashMap<>();
	}

	public boolean createAccount(int timestamp, String accountId) {

		if (accounts.containsKey(accountId)) {
			return false;
		}
		accounts.put(accountId, new Account(accountId));
		return true;
	}

	public Optional<Integer> deposit(int timestamp, String accountId, int amount) {
		Account account = accounts.get(accountId);
		if (account == null) {
			return Optional.empty();
		}

		account.balance += amount;
		return Optional.of(account.balance);
	}

	public Optional<Integer> transfer(int timestamp, String sourceAccountId, String targetAccountId, int amount) {

		Account sourceAccount = accounts.get(sourceAccountId);
		Account targetAccount = accounts.get(targetAccountId);

		if (sourceAccount == null || targetAccount == null) {
			return Optional.empty();
		}
		if (sourceAccountId.equals(targetAccountId)) {
			return Optional.empty();
		}
		if (sourceAccount.balance < amount) {
			return Optional.empty();
		}

		sourceAccount.balance -= amount;
		targetAccount.balance += amount;
		sourceAccount.totalOutgoing += amount;

		return Optional.of(sourceAccount.balance);
	}

	public List<String> topSpenders(int timestamp, int n) {

		List<Account> accountList = new ArrayList<>(accounts.values());

		accountList.sort((a, b) -> {
			if (b.totalOutgoing == a.totalOutgoing) {
				return a.accountId.compareTo(b.accountId);
			}
			return Integer.compare(b.totalOutgoing, a.totalOutgoing);
		});

		// create result list
		List<String> result = new ArrayList<>();
		for (int i = 0; i < Math.min(n, accountList.size()); i++) {
			Account account = accountList.get(i);
			result.add(account.accountId + "(" + account.totalOutgoing + ")");
		}

		return result;

	}

}
