package Week3.Day3;

public class BankAccount {

    // Private variables
    private int accountNumber;
    private double balance;

    // Parameterized Constructor
    public BankAccount(int accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    // Getter Methods
    public int getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    // Deposit Method
    public void deposit(double amount) {
        balance += amount;
        System.out.println("Deposited: " + amount);
    }

    // Withdraw Method
    public void withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            System.out.println("Withdrawn: " + amount);
        } else {
            System.out.println("Error: Insufficient balance");
        }
    }

    // Display Balance Method
    public void displayBalance() {
        System.out.println("Current Balance: " + balance);
    }
}
