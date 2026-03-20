package Week3.Day3;

public class BankMain {

    public static void main(String[] args) {
        // ---------------- Test Case 1: Creating a Bank Account ----------------
        BankAccount account = new BankAccount(101, 500.0);
        System.out.println("Test Case 1:");
        System.out.println("Account Number: " + account.getAccountNumber());
        System.out.println("Balance: " + account.getBalance());

        // ---------------- Test Case 2: Deposit Money ----------------
        System.out.println("\nTest Case 2:");
        account.deposit(200.0);
        System.out.println("Balance after deposit: " + account.getBalance());
        account.displayBalance();

        // ---------------- Test Case 3: Withdraw Money (Sufficient Balance) ----------------
        System.out.println("\nTest Case 3:");
        account.withdraw(300.0);
        System.out.println("Balance after withdrawal: " + account.getBalance());
        account.displayBalance();

        // ---------------- Test Case 4: Withdraw Money (Insufficient Balance) ----------------
        System.out.println("\nTest Case 4:");
        account.withdraw(500.0); // Should show error
        System.out.println("Balance after failed withdrawal: " + account.getBalance());
        account.displayBalance();

        // ---------------- Test Case 5: Multiple Transactions ----------------
        System.out.println("\nTest Case 5:");
        account.deposit(100); // 400 + 100 = 500
        System.out.println("Balance: " + account.getBalance());

        account.withdraw(200); // 500 - 200 = 300
        System.out.println("Balance: " + account.getBalance());

        account.deposit(50); // 300 + 50 = 350
        System.out.println("Balance: " + account.getBalance());

        account.withdraw(400); // 350 < 400 → Error
        System.out.println("Balance: " + account.getBalance());
    }
}
