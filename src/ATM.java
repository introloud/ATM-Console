/*
 * Coder: Shashwat Malla
 * Date: 10-18-2022
 * Program: ATM.java
 * Purpose: Main class of the ATM console machine.
 */

import java.util.Scanner;

public class ATM {
	
	public static void main(String[] args) {
		
		Scanner scr = new Scanner(System.in);
		
		Bank theBank = new Bank("Bank of London");
		
		// add a user, which also creates a savings account
		User user1 = theBank.addUser("Shas", "Malla", "1234");
		
		// add a checking account for our user
		Account newAccount = new Account("Checking", user1, theBank);
		user1.addAccount(newAccount);
		theBank.addAccount(newAccount);
		
		User currentUser;
		while (true) {
			
			// stay in the login prompt until successful login
			currentUser = ATM.mainMenuPrompt(theBank, scr);
			
			//stay in main menu until user quits
			ATM.printUserMenu(currentUser, scr);
			
		}
	}
	
	/**
	 * Print the ATM's login menu
	 * @param theBank	the Bank object
	 * @param scr		the Scanner object to use for user input
	 * @return			authenticated User object
	 */
	public static User mainMenuPrompt(Bank theBank, Scanner scr) {
		String userId;
		String pin;
		User authUser;
		
		// prompt the user for user ID/pin combo until a correct one is reached
		do {
			System.out.printf("\n\nWelcome to %s\n\n", theBank.getName());
			System.out.print("Enter user ID: ");
			userId = scr.nextLine();
			System.out.print("Enter pin: ");
			pin = scr.nextLine();
			
			//try to get the user object corresponding to the ID and pin combo
			authUser = theBank.userLogin(userId, pin);
			if (authUser == null) {
				System.out.println("Incorrect user ID/pin combination. " +
									"Please try again.");
			}
		} while( authUser == null);
		
		return authUser;
	}
	
	public static void printUserMenu(User theUser, Scanner scr) {
		
		// print a summary of the user's accounts
		theUser.printAccountSummary();
		
		int choice;
		
		// user menu
		do {
			System.out.printf("Welcome %s, what would you like to do?\n", theUser.getFirstName());
			System.out.println("  1) Show account transaction history");
			System.out.println("  2) Withdraw");
			System.out.println("  3) Deposit");
			System.out.println("  4) Transfer");
			System.out.println("  5) Log-out");
			System.out.println();
			System.out.print("Enter choice: ");
			choice = scr.nextInt();
			
			if(choice < 1 || choice > 5) {
				System.out.println("Invalid choice. Please choose 1-5");
			}
		} while(choice < 1 || choice > 5);
		
		//choices
		switch (choice) {
		case 1:
			ATM.showTransHistory(theUser, scr);
			break;
		case 2:
			ATM.withdrawFunds(theUser, scr);
			break;
		case 3:
			ATM.depositFunds(theUser, scr);
			break;
		case 4:
			ATM.transferFunds(theUser, scr);
			break;
		case 5:
			scr.nextLine();
			break;
		}
		
		//redisplay this menu unless the user wants to quit
		if (choice != 5) {
			ATM.printUserMenu(theUser, scr);
		}
	}
	
	/**
	 * Show the transaction history for an account
	 * @param theUser	the logged-in User object
	 * @param scr		the Scanner object used for user input
	 */
	public static void showTransHistory (User theUser, Scanner scr) {
		
		int theAcc;
		
		// get account whose transaction history to look at
		do {
			System.out.printf("Enter the number (1-%d) of the account\n" +
					"whose transactions you want to see: ", theUser.numAccounts());
			theAcc = scr.nextInt() - 1;
			if (theAcc < 0 || theAcc >= theUser.numAccounts()) {
				System.out.println("Invalid account. Please try again");
			}
		} while (theAcc < 0 || theAcc >= theUser.numAccounts());
		
		// print the transaction history
		theUser.printAccTransHistory(theAcc);
	}
	
	/**
	 * Process transferring funds from one account to another
	 * @param theUser	the logged-in User object
	 * @param scr		Scanner object used for user input
	 */
	public static void transferFunds(User theUser, Scanner scr) {
		
		int fromAcc;
		int toAcc;
		double amount;
		double accBal;
		
		// get the account to transfer from
		do {
			System.out.printf("Enter the number (1-%d) of the account\n" +
					"to transfer from: ", theUser.numAccounts());
			fromAcc = scr.nextInt() - 1;
			if (fromAcc < 0 || fromAcc >= theUser.numAccounts()) {
				System.out.println("Invalid account. Please try again");
			}
		}while(fromAcc < 0 || fromAcc >= theUser.numAccounts());
		
		accBal = theUser.getAccBalance(fromAcc);
		
		// get the account to transfer to
		do {
			System.out.printf("Enter the number (1-%d) of the account\n" +
					"to transfer to: ", theUser.numAccounts());
			toAcc = scr.nextInt() - 1;
			if (toAcc < 0 || toAcc >= theUser.numAccounts()) {
				System.out.println("Invalid account. Please try again");
			} else if (toAcc == fromAcc) {
				System.out.println("You can not transfer to the same account. Please choose another account");
			}
		}while(toAcc < 0 || toAcc >= theUser.numAccounts());
		
		// get the amount to transfer
		do {
			System.out.printf("Enter the amount to transfer (max $%.02f): $", accBal);
			amount = scr.nextDouble();
			if (amount < 0) {
				System.out.println("Amount must be greater than zero.");
			} else if (amount > accBal) {
				System.out.printf("Amount must not be greater than\n" +
						"balance of %.02f.\n", accBal);
			}
		} while (amount < 0 || amount > accBal);
			
		// finally, do the transfer
		theUser.addAccTransaction(fromAcc, -1 * amount, String.format(
				"Transfer to account %s", theUser.getAccUUID(toAcc)));
		theUser.addAccTransaction(toAcc, amount, String.format(
				"Transfer to account %s", theUser.getAccUUID(fromAcc)));
	}
	
	/**
	 * Process a fund withdraw from an account
	 * @param theUser	the logged-in User object
	 * @param scr		the Scanner object user for user input
	 */
	public static void withdrawFunds(User theUser, Scanner scr) {
		
		int fromAcc;
		double amount;
		double accBal;
		String memo;
		
		// get the account to withdraw from
		do {
			System.out.printf("Enter the number (1-%d) of the account\n" +
					"to withdraw from: ", theUser.numAccounts());
			fromAcc = scr.nextInt() - 1;
			if (fromAcc < 0 || fromAcc >= theUser.numAccounts()) {
				System.out.println("Invalid account. Please try again");
			}
		}while(fromAcc < 0 || fromAcc >= theUser.numAccounts());
		
		accBal = theUser.getAccBalance(fromAcc);
		
		// get the account to withdraw
		do {
			System.out.printf("Enter the amount to withdraw (max $%.02f): $", accBal);
			amount = scr.nextDouble();
			if (amount < 0) {
				System.out.println("Amount must be greater than zero.");
			} else if (amount > accBal) {
				System.out.printf("Amount must not be greater than\n" +
						"balance of %.02f.\n", accBal);
			}
		} while (amount < 0 || amount > accBal);
		
		// clear the stream
		scr.nextLine();
		
		// get a memo
		System.out.print("Enter a memo: ");
		memo = scr.nextLine();
		
		// do the withdrawal
		theUser.addAccTransaction(fromAcc, -1 * amount, memo);
	}
	
	/**
	 * Process a fund deposit to an account
	 * @param theUser	the logged-in User object
	 * @param scr		the Scanner object used for user input
	 */
	public static void depositFunds(User theUser, Scanner scr) {
		
		int toAcc;
		double amount;
		double accBal;
		String memo;
		
		// get the account to deposit
		do {
			System.out.printf("Enter the number (1-%d) of the account\n" +
					"to deposit to: ", theUser.numAccounts());
			toAcc = scr.nextInt() - 1;
			if (toAcc < 0 || toAcc >= theUser.numAccounts()) {
				System.out.println("Invalid account. Please try again");
			}
		}while(toAcc < 0 || toAcc >= theUser.numAccounts());
		
		accBal = theUser.getAccBalance(toAcc);
		
		// get the account to deposit
		do {
			System.out.printf("Enter the amount to deposit (max $%.2f): $", accBal);
			amount = scr.nextDouble();
			if (amount < 0) {
				System.out.println("Amount must be greater than zero.");
			}
		} while (amount < 0);
		
		// clear the stream
		scr.nextLine();
		
		// get a memo
		System.out.print("Enter a memo: ");
		memo = scr.nextLine();
		
		// do the deposit
		theUser.addAccTransaction(toAcc, amount, memo);
	}
	
}
