import java.util.ArrayList;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User {
	
	// First name of the user
	private String firstName;
	
	// Last name of the user
	private String lastName;
	
	// ID number of the user
	private String uuid;
	
	// The MD5 hash of the user's pin number
	private byte pinHash[];
	
	// List of accounts for this user
	private ArrayList<Account> accounts;
	
	/**
	 * Create a new user
	 * @param firstName user's first name
	 * @param lastName	user's last name
	 * @param pin		user's account pin number
	 * @param theBank	bank object that the user is a customer of
	 */
	public User(String firstName, String lastName, String pin, Bank theBank) {
		
		// set user's name
		this.firstName = firstName;
		this.lastName = lastName;
		
		// store the pin's MD5 hash for security reasons using MessageDigest object
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			this.pinHash = md.digest(pin.getBytes());
		} catch (NoSuchAlgorithmException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}
		
		//get a new, unique universal ID for the user
		this.uuid = theBank.getNewUserUUID();
		
		// create empty list of accounts
		this.accounts = new ArrayList<Account>();
		
		// print log message
		System.out.printf("New user %s, %s with ID %s created.\n", lastName, firstName, this.uuid);
		
	}
	
	/**
	 * Get the first name of the user
	 * @return	firstName
	 */
	public String getFirstName() {
		return this.firstName;
	}
	
	/**
	 * Print summaries for the accounts of this user.
	 */
	public void printAccountSummary() {
		System.out.printf("\n\n%s's accounts summary", this.firstName);
		for (int a = 0; a < this.accounts.size(); a++) {
			System.out.printf("(%d) %s\n", a+1, this.accounts.get(a).getSummaryLine());
		}
		System.out.println();
	}
	/**
	 * Add an account for the user
	 * @param account	the account to add
	 */
	public void addAccount(Account account) {
		this.accounts.add(account);
	}
	
	/**
	 * Get the number of accounts of the user
	 * @return	number of accounts (array list)
	 */
	public int numAccounts() {
		return this.accounts.size();
	}
	
	/**
	 * Return the user's uuid
	 * @return		uuid
	 */
	public String getUUID() {
		return this.uuid;
	}
	
	/**
	 * Check whether a given pin matches the true User pin
	 * @param pin	the pin to check
	 * @return		whether the pin is valid or not
	 */
	public boolean validatePin(String pin) {
		
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			return MessageDigest.isEqual(md.digest(pin.getBytes()), this.pinHash);
		} catch (NoSuchAlgorithmException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}
		
		return false;
	}
	
}
