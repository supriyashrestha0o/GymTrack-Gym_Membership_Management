package Week1.Day1;
import java.util.Scanner; 

public class simple_interest {
	    public static void main(String[] args) {
	        Scanner input = new Scanner(System.in);
	        
	        System.out.print("Enter Principal amount: ");
	        double principal = input.nextDouble();

	        System.out.print("Enter Annual Rate of Interest (in %): ");
	        double rate = input.nextDouble();

	        System.out.print("Enter Time period (in years): ");
	        double time = input.nextDouble();

	        double interest = (principal * rate * time) / 100;
	        
	        System.out.println("Simple Interest: " + interest);
	       
	        input.close();
	    }
	}
