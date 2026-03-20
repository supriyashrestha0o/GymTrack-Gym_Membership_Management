package Week2.day2gitrepo;

public class isAtLeastAge {

    // Task 1
    public static boolean isAtLeastAge(int age, int minAge) {
        if (age >= minAge) {
            return true;
        } else {
            return false;
        }
        // shorter version: return age >= minAge;
    }
}