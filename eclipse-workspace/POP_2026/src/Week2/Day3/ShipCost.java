package Week2.Day3;

import java.util.Scanner;
public class ShipCost {
	    public static void main(String[] args) {
	        Scanner sc = new Scanner(System.in);

	        System.out.print("Enter weight (kg): ");
	        double weight = sc.nextDouble();

	        if (weight <= 5) {
	            System.out.println("Cost: $10");
	        } else if (weight <= 10) {
	            System.out.println("Cost: $20");
	        } else {
	            System.out.println("Cost: $30");
	        }

	        sc.close();
	    }
	}
