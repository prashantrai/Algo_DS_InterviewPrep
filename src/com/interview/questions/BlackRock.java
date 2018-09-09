package com.interview.questions;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class BlackRock {

	private static final String TEXT =  "I am a {0} account with {1,number,#} units of {2} currency";

    public static void main(String args[] ) throws Exception {

        List<BankAccount> accounts = new ArrayList<BankAccount>();
        accounts.add(new SavingsAccount("USD",3));//Savings
        accounts.add(new SavingsAccount("EUR",2));//Savings
        accounts.add(new CheckingAccount("HUF",100));//Checking
        accounts.add(new CheckingAccount("COP",10000));//Checking
        accounts.add(new BrokerageAccount("GBP",2));//Brokerage
        accounts.add(new BrokerageAccount("INR",600));//Brokerage
        
        accounts.stream().forEach(
                                    account -> System.out.println(
                                        MessageFormat.format(TEXT,
                                            new Object[]{
                                            account.getAccountType().getName(),//make this work
                                            account.getUnits(),//make this work
                                            account.getCurrency()//make this work
                                                           })));
    }

}

interface BankAccount {
	
	BankAccount getAccountType();
	String getName();
	Double getUnits();
	String getCurrency();
	
}

class SavingsAccount implements BankAccount {
	
	String currency; 
	double amt;
	static final String NAME = "SavingsAccount";
	
	public SavingsAccount(String currency, double amt) {
		this.currency = currency;
		this.amt = amt;
		
	}

	@Override
	public BankAccount getAccountType() {
		return this;
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public Double getUnits() {
		return amt;
	}

	@Override
	public String getCurrency() {
		return currency;
	}
	
	
}

class CheckingAccount implements BankAccount {
	
	String currency; 
	double amt;
	static final String NAME = "CheckingAccount";
	
	public CheckingAccount(String currency, double amt) {
		this.currency = currency;
		this.amt = amt;
		
	}
	@Override
	public BankAccount getAccountType() {
		return this;
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public Double getUnits() {
		return amt;
	}

	@Override
	public String getCurrency() {
		return currency;
	}
}

class BrokerageAccount implements BankAccount {
	
	String currency; 
	double amt;
	static final String NAME = "BrokerageAccount";
	
	public BrokerageAccount(String currency, double amt) {
		this.currency = currency;
		this.amt = amt;
		
	}
	@Override
	public BankAccount getAccountType() {
		return this;
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public Double getUnits() {
		return amt;
	}

	@Override
	public String getCurrency() {
		return currency;
	}
}


