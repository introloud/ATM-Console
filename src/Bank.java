import java.util.ArrayList;
import java.util.Random;

public class Bank {
	
	private String name;
	
	private ArrayList<User> users;
	
	private ArrayList<Account> accounts;

	/**
	 * Generate a new universally unique ID for a user
	 * @return	uuid
	 */
	public String getNewUserUUID() {
		
		String uuid;
		Random range = new Random();
		int len = 6;
		boolean notUnique;
		
		// continue looping until we get a unique ID
		do {
			
			uuid = "";
			for (int c = 0; c < len; c++) {
				//uuid += ((Integer)rng.nextInt(10)).toString();
				uuid = ((Integer)(range.nextInt(999_999) + 100_000)).toString();
			}
			
			//check to make sure it is unique
			notUnique = false;
			for (User u : this.users) {
				if (uuid.compareTo(u.getUUID()) == 0) {
					notUnique = true;
					break;
				}
			}
		} while(notUnique);
		
		return uuid;
	}
	
	/**
	 * Generate a new universally unique ID for an account
	 * @return
	 */
	public String getNewAccountUUID() {
		String uuid;
		Random range = new Random();
		
		int len = 9;
		boolean notUnique;
		
		// continue looping until we get a unique ID
		do {
			
			uuid = "";
			for (int c = 0; c < len; c++) {
				//uuid += ((Integer)rng.nextInt(10)).toString();
				uuid = ((Integer)(range.nextInt(999_999_999) + 100_000_000)).toString();
			}
			
			//check to make sure it is unique
			notUnique = false;
			for (Account a : this.accounts) {
				if (uuid.compareTo(a.getUUID()) == 0) {
					notUnique = true;
					break;
				}
			}
		} while(notUnique);
		
		return uuid;
	}
	}
	
	/**
	 * Add an account
	 * @param account		the account to add
	 */
	public void addAccount (Account account) {
		this.accounts.add(account);
	}
}
