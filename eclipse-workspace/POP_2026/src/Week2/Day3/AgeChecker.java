package Week2.Day3;

public class AgeChecker {
	 public static void main(String[] args) {

	        int age = 18;
	        int minimumAge = 16;

	        boolean result = isAtLeastAge(age, minimumAge);
	        System.out.println("Is age at least the minimum required? " + result);
	    }

	    public static boolean isAtLeastAge(int age, int minimumAge) {
	        if (age >= minimumAge) {
	            return true;
	        } else {
	            return false;
	        }
	    }
	}