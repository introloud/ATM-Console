import java.util.ArrayList;

public class Account {
	
	// Name of the account
	private String name;
	
	// Account ID number
	private String uuid;
	
	// User object that owns this account
	private User holder;
	
	// List of transactions for this account
	private ArrayList<Transaction> transactions;
	
	/**
	 * Create a new Account
	 * @param name		name of the account
	 * @param holder	user object that holds this account
	 * @param theBank	bank that issues the account 
	 */
	public Account(String name, User holder, Bank theBank) {
		
		// set the account name and holder
		this.name = name;
		this.holder = holder;
		
		// get new account UUID
		this.uuid = theBank.getNewAccountUUID();
		
		// init transactions
		this.transactions = new ArrayList<Transaction>();
	}
	
	/**
	 * Get the account ID
	 * @return	uuid
	 */
	public String getUUID() {
		return this.uuid;
	}
	
	/**
	 * Get summary line for the account
	 * @return	String summary
	 */
	public String getSummaryLine() {
		
		// get the account's balance 
		double balance = this.getBalance();
		
		// format the summary line, depending on the whether the balance is negative
		if (balance >= 0) {
			return String.format("%s : $%.02f : %s", this.uuid, balance, this.name);
		} else {
			return String.format("%s : $(%.02f) : %s", this.uuid, balance, this.name);
		}
	}
	
	public double getBalance() {
		
		double balance = 0;
		for (Transaction t : this.transactions) {
			balance += t.getAmount();
		}
		return balance;
	}
	
	
}
