package Week2.Day3;

public class PasswordValidation {
	public static void main(String[] args) {

        String password = "MyPass123";
        int minLength = 6;
        int maxLength = 12;

        boolean result = isValidPasswordLength(password, minLength, maxLength);
        System.out.println("Is the password length valid? " + result);
    }

    public static boolean isValidPasswordLength(String password, int minLength, int maxLength) {
        if (password.length() >= minLength && password.length() <= maxLength) {
            return true;
        } else {
            return false;
        }
    }

}