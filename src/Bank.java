import java.util.ArrayList;

public class Bank {
	
	private String name;
	
	private ArrayList<User> users;
	
	private ArrayList<Account> accounts;
	
	public String getNewUserUUID() {
		
	}
	
	public String getNewAccountUUID() {
		
	}
	
	/**
	 * Add an account
	 * @param account		the account to add
	 */
	public void addAccount (Account account) {
		this.accounts.add(account);
	}
}
