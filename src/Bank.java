/*
 * Coder: Shashwat Malla
 * Date: 10-18-2022
 * Program: Bank.java
 * Purpose: Holds the data members and methods belonging to a particular bank
 */

import java.util.ArrayList;
import java.util.Random;

public class Bank {
	
	private String name;
	
	private ArrayList<User> users;
	
	private ArrayList<Account> accounts;
	
	/**
	 * Create a new Bank object
	 * @param name	name of the bank
	 */
	public Bank(String name) {
		this.name = name;
		this.users = new ArrayList<User>();
		this.accounts = new ArrayList<Account>();
	}

	/**
	 * Get the name of the bank
	 * @return	name
	 */
	public String getName() {
		return this.name;
	}
	
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
	
	/**
	 * Add an account
	 * @param account		the account to add
	 */
	public void addAccount (Account account) {
		this.accounts.add(account);
	}
	
	/**
	 * Create a new user of the bank
	 * @param firstName		user's first name
	 * @param lastName		user's last name
	 * @param pin			user's pin
	 * @return				new User object
	 */
	public User addUser(String firstName, String lastName, String pin) {
		
		//create a new User object and add it to our list
		User newUser = new User(firstName, lastName, pin, this);
		this.users.add(newUser);
		
		//create a savings account for the user
		Account newAccount = new Account("Savings", newUser, this);
		
		newUser.addAccount(newAccount);
		this.addAccount(newAccount);
		
		return newUser;
	}
	
	/**
	 * Get the User object associated with a particular userID and pin, if they are valid
	 * @param userId		UUID of the user to log in
	 * @param pin			pin of the user
	 * @return				the User object, if the login is successful, or null, if it is not
	 */
	public User userLogin(String userId, String pin) {
		
		//search through list of users
		for (User u : this.users) {
			
			// check if userID is correct
			if (u.getUUID().compareTo(userId) == 0 && u.validatePin(pin)) {
				return u;
			}
		}
		
		//if we don't find the user or have an incorrect pin
		return null;
	}
	
}
