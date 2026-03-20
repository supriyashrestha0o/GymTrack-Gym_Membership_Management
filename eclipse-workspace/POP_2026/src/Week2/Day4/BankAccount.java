package Week2.Day4;

public class BankAccount {
    String accountNumber;
    String holderName;
    double balance;

    public void displayBalance() {
        System.out.println("Account: " + accountNumber + " | Holder: " + holderName + " | Balance: $" + balance);
    }
}