package Week5.Day2;

public class MathHelper {

    public static int square(int number) {
        return number * number;
    }

    public static int max(int a, int b) {
        return (a > b) ? a : b;
    }

    public static void main(String[] args) {
        System.out.println("Square of 5: " + MathHelper.square(5));
        System.out.println("Max of 10 and 20: " + MathHelper.max(10, 20));
    }
}