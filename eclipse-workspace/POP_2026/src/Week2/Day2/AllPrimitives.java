package Week2.Day2;

public class AllPrimitives {
    public static void main(String[] args) {
        // 1. Integer types
        byte age = 25;
        short year = 2026;
        int population = 1500000;
        long stars = 10000000000L; 

        // 2. Floating-point types
        float temp = 36.5f; 
        double precisePi = 3.1415926535;

        // 3. Logics
        char grade = 'A';
        boolean isLearning = true;

        System.out.println("byte: " + age);
        System.out.println("short: " + year);
        System.out.println("int: " + population);
        System.out.println("long: " + stars);

        System.out.println("Float: " + temp);
        System.out.println("Double" + precisePi);

        System.out.println("Character: " + grade);
        System.out.println("Logic: " + isLearning);
    }
}