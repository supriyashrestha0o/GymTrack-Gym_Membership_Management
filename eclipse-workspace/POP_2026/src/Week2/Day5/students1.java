package Week2.Day5;

public class students1 {
    String name;
    int age;
    String collegeName; // Task 2: Adding the new variable

    // Task 2: Constructor to accept name, age, and college
    public students1(String n, int a, String c) {
        name = n;
        age = a;
        collegeName = c;
    }

    // Task 1 & 2: Display all properties
    void display() {
        System.out.println("Name: " + name + " | Age: " + age + " | College: " + collegeName);
    }
}
