package Week2.Day3;

import java.util.Scanner;

public class PersonnelScenario {

    // Method based on personnel scenario
    // Checks if an employee is eligible for a bonus
    public static boolean isEligibleForBonus(int yearsOfExperience) {
        if (yearsOfExperience > 5) {
            return true;
        } else {
            return false;
        }
    }

    // Main method
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter employee's years of experience: ");
        int experience = sc.nextInt();

        boolean eligible = isEligibleForBonus(experience);

        if (eligible) {
            System.out.println("Employee is eligible for a bonus.");
        } else {
            System.out.println("Employee is not eligible for a bonus.");
        }

        sc.close();
    }
}
