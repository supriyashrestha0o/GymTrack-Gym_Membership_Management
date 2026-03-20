package Week3.Day3;
class Person {
    private String name;
    private int age;

    // Constructor
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // Getter methods
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    // Display method
    public void displayPerson() {
        System.out.println("Name: " + name + ", Age: " + age);
    }
}

class Studentss extends Person {
    private int rollNumber;

    // Constructor
    public Studentss(String name, int age, int rollNumber) {
        super(name, age);
        this.rollNumber = rollNumber;
    }

    // Getter for roll number
    public int getRollNumber() {
        return rollNumber;
    }

    // Display method for student details
    public void displayStudent() {
        System.out.println("Roll Number: " + rollNumber);
    }
}

public class InheritanceMain {
    public static void main(String[] args) {
        // ---------------- Test Case 1 ----------------
        Studentss s1 = new Studentss("Ramesh", 20, 15);
        System.out.println("Test Case 1: Display Person and Student Details");
        s1.displayPerson();
        s1.displayStudent();

        // ---------------- Test Case 2 ----------------
        System.out.println("\nTest Case 2: Using Getter Methods");
        System.out.println("Name: " + s1.getName());
        System.out.println("Age: " + s1.getAge());
        System.out.println("Roll Number: " + s1.getRollNumber());

        // ---------------- Test Case 3 ----------------
        System.out.println("\nTest Case 3: Try Direct Access (will cause error if uncommented)");
        // s1.name = "Test";  // ❌ Error: name has private access
        // s1.age = 25;       // ❌ Error: age has private access
        System.out.println("Direct access to private variables is not allowed. Compiler shows error.");

        // ---------------- Test Case 4 ----------------
        Studentss s2 = new Studentss("Supriya", 17, 12);
        System.out.println("\nTest Case 4: Another Student Object");
        s2.displayPerson();
        s2.displayStudent();
    }
}
