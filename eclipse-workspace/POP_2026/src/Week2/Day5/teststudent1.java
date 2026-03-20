package Week2.Day5;

public class teststudent1 {
    public static void main(String[] args) {
        // Task 1: Creating 3 Student objects with different data
        // We use the new constructor: new Students(name, age, college)
        students1 s1 = new students1("Alice", 20, "MIT");
        students1 s2 = new students1("Bob", 22, "Stanford");
        students1 s3 = new students1("Charlie", 19, "Harvard");

        // Task 1: Call display() for each one
        System.out.println("--- Student Records ---");
        s1.display();
        s2.display();
        s3.display();
    }
}

