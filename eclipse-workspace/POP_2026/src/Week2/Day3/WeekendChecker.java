package Week2.Day3;

	import java.util.Scanner;

	public class WeekendChecker {

	    // Method to check if the given day is a weekend
	    public static boolean isWeekend(String day) {
	        if (day.equalsIgnoreCase("Saturday") || day.equalsIgnoreCase("Sunday")) {
	            return true;
	        } else {
	            return false;
	        }
	    }

	    // Main method (program starts here)
	    public static void main(String[] args) {

	        Scanner sc = new Scanner(System.in);

	        System.out.print("Enter a day of the week: ");
	        String day = sc.nextLine();

	        boolean result = isWeekend(day);

	        if (result) {
	            System.out.println(day + " is a weekend.");
	        } else {
	            System.out.println(day + " is a weekday.");
	        }

	        sc.close();
	    }
	}


