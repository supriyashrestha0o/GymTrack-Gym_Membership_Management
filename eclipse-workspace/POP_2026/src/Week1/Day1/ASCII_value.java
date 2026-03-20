package Week1.Day1;
import java.util.Scanner;

public class ASCII_value {

	    public static void main(String[] args) {
	        Scanner input = new Scanner(System.in);
	        System.out.print("Enter a character: ");
	        char myChar = input.next().charAt(0);
	        
	        int asciiValue = (int) myChar;
	        
	        System.out.println("The ASCII value of '" + myChar + "' is: " + asciiValue);
	        input.close();
	    }
	}