package Week2.day2gitrepo;

import java.util.Scanner; //Import Library Class

public class InputOutput {
	public static void main(String []agrs) {
		//input
		//prompt for user
		System.out.print("Enter your name : ");
		
		//Step-2
		//Declare an object of Library class
		Scanner sc=new Scanner(System.in);
		
		//Read value from keyboard
		String name = sc.nextLine();
		
		//output
		System.out.print("Your name is :"+name);

}
}