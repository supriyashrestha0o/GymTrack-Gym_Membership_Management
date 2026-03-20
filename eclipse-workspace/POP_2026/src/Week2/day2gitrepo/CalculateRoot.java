package Week2.day2gitrepo;
import java.util.Scanner;

public class CalculateRoot {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter a number: ");
        double number = sc.nextDouble();

        double root = Math.sqrt(number);

        System.out.println("Square root = " + root);

        sc.close();
    }
}

