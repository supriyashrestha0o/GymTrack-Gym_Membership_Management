package Week2.Day3;

import java.util.Scanner;

public class AgeComparison {

    // Method to determine the older age
    public static int olderAge(int age1, int age2) {
        if (age1 > age2) {
            return age1;
        } else {
            return age2;
        }
    }

    // Main method
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter first age: ");
        int age1 = sc.nextInt();

        System.out.print("Enter second age: ");
        int age2 = sc.nextInt();

        int older = olderAge(age1, age2);

        System.out.println("The older age is: " + older);

        sc.close();
    }
}
