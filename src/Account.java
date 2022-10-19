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
		
		// add to holder and bank lists
		holder.addAccount(this);
		theBank.addAccount(this);
	}
	
	/**
	 * Get the account ID
	 * @return	uuid
	 */
	public String getUUID() {
		return this.uuid;
	}
}
