package Week7.Day1;

public class Main {

    public static void main(String[] args) {

        Payment esewa = new EsewaPayment();
        Payment khalti = new KhaltiPayment();
        Payment bank = new BankPayment();

        esewa.pay();
        khalti.pay();
        bank.pay();
    }
}
