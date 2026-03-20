package Week1.Day1;
import java.util.Scanner;
public class circle_parameters {
	public static void main(String[] args) {
		Scanner input = new Scanner (System.in);
		System.out.print("Enter radius:");
		double radius = input.nextDouble();
		double area = Math.PI * radius * radius ;
		double circumference = 2 * Math.PI * radius;
		System.out.println("The area of circle is:"+ area);
		System.out.println("The circumference of circle is:"+ circumference);
		input.close();
		}
	}

