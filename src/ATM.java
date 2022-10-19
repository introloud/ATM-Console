import java.util.Scanner;

public class ATM {
	
	public static void main(String[] args) {
		
		Scanner scr = new Scanner(System.in);
		
		Bank theBank = new Bank("Bank of London");
		
		// add a user, which also creates a savings account
		User user1 = theBank.addUser("Jack", "Vincent", "1234");
		
		// add a checking account for our user
		Account newAccount = new Account("Checking", user1, theBank);
		user1.addAccount(newAccount);
		theBank.addAccount(newAccount);
		
		User currentUser;
		while (true) {
			
			// stay in the login prompt until successful login
			currentUser = ATM.mainMenuPrompt(theBank, scr);
			
			//stay in main menu until user quits
			//ATM.printUserMenu(currentUser, scr);
			
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
}
