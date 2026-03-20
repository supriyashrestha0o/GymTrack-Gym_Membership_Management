package Week4.Day4;

class BankAccount {
    int accountNumber;
    String holderName;
    double balance;

    BankAccount(int accountNumber, String holderName, double balance) {
        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.balance = balance;
    }

    void transferMoney(BankAccount receiver, double amount) {
        if (balance >= amount) {
            balance -= amount;
            receiver.balance += amount;
            System.out.println("Transfer successful.");
        } else {
            System.out.println("Insufficient balance.");
        }
    }

    void displayAccountDetails() {
        System.out.println("Account No: " + accountNumber);
        System.out.println("Name: " + holderName);
        System.out.println("Balance: " + balance);
    }
}

public class BankMain {
    public static void main(String[] args) {
        BankAccount a1 = new BankAccount(1001, "Ram", 5000);
        BankAccount a2 = new BankAccount(1002, "Sita", 3000);

        a1.transferMoney(a2, 2000);

        a1.displayAccountDetails();
        a2.displayAccountDetails();
    }
}
