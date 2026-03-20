package Week2.Day3;
import java.util.Scanner;
public class Discount {
	    public static void main(String[] args) {
	        Scanner sc = new Scanner(System.in);

	        System.out.print("Enter membership type (Gold/Silver/Bronze): ");
	        String membership = sc.nextLine();

	        int discount = calculateDiscount(membership);
	        System.out.println("Discount: " + discount + "%");

	        sc.close();
	    }

	    // Method to calculate discount
	    public static int calculateDiscount(String membership) {
	        if (membership.equals("Gold")) {
	            return 20;
	        } else if (membership.equals("Silver")) {
	            return 10;
	        } else if (membership.equals("Bronze")) {
	            return 5;
	        } else {
	            return 0; // if membership is invalid
	        }
	    }
	}