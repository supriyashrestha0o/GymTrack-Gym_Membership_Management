package Week2.day2gitrepo;

import java.util.Scanner;

public class CalculatePower {
	public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter base number: ");
        double base = sc.nextDouble();

        System.out.print("Enter exponent: ");
        double exponent = sc.nextDouble();

        double result = Math.pow(base, exponent);

        System.out.println("Result = " + result);

        sc.close();
	}
}
