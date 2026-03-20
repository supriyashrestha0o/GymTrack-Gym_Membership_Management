package Week1.Day1;
import java.util.Scanner; // Required for user input

	public class conversion_hours_seconds {
	    public static void main(String[] args) {
	        Scanner input = new Scanner(System.in);
	        System.out.print("Enter the number of hours you want to convert into seconds: ");
	        double hours = input.nextDouble(); 
	        double seconds = hours * 3600;
	        System.out.println(hours + " hour: " + seconds + " seconds.");
	        input.close();
	    }
	
}
