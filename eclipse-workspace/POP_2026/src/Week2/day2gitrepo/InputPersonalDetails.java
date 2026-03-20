package Week2.day2gitrepo;
import java.util.Scanner;

public class InputPersonalDetails {
	public static void main(String []args) {
		Scanner sc=new Scanner(System.in);
		System.out.print("Enter name : ");//Prompt
		String name = sc.nextLine(); //read line from keyboard
		System.out.print("Enter address : ");//Prompt
		String address = sc.nextLine(); //read line from keyboard
		
		//output
		System.out.println("NAME : "+name);
		System.out.println("ADDRESS : "+address);
		
	}
	//Accept name, address, email, telephone from user
	//Display name, address, email, telephone on screen
}