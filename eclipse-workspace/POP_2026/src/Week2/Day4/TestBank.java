package Week2.Day4;

public class TestBank {
	    public static void main(String[] args) {
	        // Create the first object
	        BankAccount acc1 = new BankAccount();
	        acc1.accountNumber = "12345ABC";
	        acc1.holderName = "Alex Smith";
	        acc1.balance = 1500.75;

	        // Create the second object
	        BankAccount acc2 = new BankAccount();
	        acc2.accountNumber = "67890XYZ";
	        acc2.holderName = "Maria Garcia";
	        acc2.balance = 2800.50;

	        // Show balances
	        System.out.println("--- Bank Account Records ---");
	        acc1.displayBalance();
	        acc2.displayBalance();
	    }
}