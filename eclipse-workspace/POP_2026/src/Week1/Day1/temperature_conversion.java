package Week1.Day1;
import java.util.Scanner;

public class temperature_conversion {
    public static void main(String[] args) {
    	Scanner input = new Scanner (System.in);
    	System.out.print("Enter temperature in Fahrenheit:");
        double fahrenheit = input.nextDouble(); 
        double celsius = (fahrenheit - 32) * 5.0 / 9.0;
        
        System.out.println(fahrenheit + "°F is equal to " + celsius + "°C");
        input.close();
    }
}