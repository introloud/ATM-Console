import java.util.ArrayList;

public class Account {
	
	// Name of the account
	private String name;
	
	// Current balance of the account
	private double balance;
	
	// Account ID number
	private String uuid;
	
	// User object that owns this account
	private User holder;
	
	// List of transactions for this account
	private ArrayList<Transaction> transactions;

}
