package Week2.Day3;

public class MaxNumber {
	public static void main(String[] args) {
		int num1 = 10;
		int num2 = 25;

		int max = findMax(num1, num2);
		System.out.println("Maximum number is: " + max);
	}

	public static int findMax(int num1, int num2) {
		if (num1 > num2) {
			return num1;
		} else {
        	return num2;
    }
}

}
