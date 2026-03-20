package Week4.Day2;
import java.util.Scanner;

public class SumFinal {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Processing processor = new Processing();
        Store storage = new Store();

        System.out.println("SUM OF TWO NUMBERS");

        //UI
        System.out.print("Enter first number: ");
        int a = scanner.nextInt();
        
        System.out.print("Enter second number: ");
        int b = scanner.nextInt();

        //Processing
        int result = processor.calculateSum(a, b);

        //Store
        storage.save(result);

        //Output
        System.out.println("Sum: " + storage.getStoredSum());

        scanner.close();
    }
}