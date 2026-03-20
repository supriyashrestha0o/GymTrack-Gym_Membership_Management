package Week2.day2gitrepo;
import java.util.Scanner;

public class MyCalculator {
	//Enter first number : 9
	//Enter second number : 4
	//Sum: 13
	
	public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter first number: ");
        int num1 = sc.nextInt();

        System.out.print("Enter second number: ");
        int num2 = sc.nextInt();

        int sum = num1 + num2;
        //+ sum
        //- difference
        //* product
        // / divide
        // % rem
        
        //How to calculate root, and power in java program.
        
        System.out.println("Sum of the two numbers = " + sum);

        sc.close();
    }
}