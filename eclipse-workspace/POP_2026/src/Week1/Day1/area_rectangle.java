package Week1.Day1;
import java.util.Scanner;
public class area_rectangle {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		System.out.print("Enter length:");
		double length = input.nextDouble();
		System.out.print("Enter breadth:");
		double breadth = input.nextDouble();
		double area = length * breadth;
		System.out.print("The area of triangle is:" + area);
		input.close();
	}
}
