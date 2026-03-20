package Week1.Day1;
import java.util.Scanner;
public class swap_numbers {
	    public static void main(String[] args) {
	        Scanner input = new Scanner(System.in);
	        System.out.print("Enter first number: ");
	        double num1 = input.nextDouble(); 
	        
	        System.out.print("Enter second number: "); 
	        double num2 = input.nextDouble(); 
	        
	        System.out.println("Before Swap: num1 = " + num1 + ", num2 = " + num2);
	        num1 = num1 + num2; 
	        num2 = num1 - num2; 
	        num1 = num1 - num2; 
	        
	        System.out.println("After Swap: num1 = " + num1 + ", num2 = " + num2);
	        
	        input.close(); 
	    }
	}