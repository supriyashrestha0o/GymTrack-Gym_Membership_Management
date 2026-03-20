package Week4.Day4;

class Student {
    int studentID;
    String name;
    String grade; // Changed to String to support grades like "A+"

    // Default constructor
    Student() {
        this.studentID = 0;
        this.name = "Unknown";
        this.grade = "N";
    }

    // Parameterized constructor
    Student(int studentID, String name, String grade) {
        this.studentID = studentID;
        this.name = name;
        this.grade = grade;
    }

    // Method to update the grade
    void updateGrade(String grade) {
        this.grade = grade;
    }

    // Method to display student information
    void displayStudentInfo() {
        System.out.println("ID: " + studentID);
        System.out.println("Name: " + name);
        System.out.println("Grade: " + grade);
        
    }
}

public class StudentMain {
    public static void main(String[] args) {
        // 1. Create an object using the Parameterized Constructor
        Student s1 = new Student(101, "Supriya", "A");
        System.out.println("Initial Info:");
        s1.displayStudentInfo();

        // 2. Update the grade (using double quotes for String)
        s1.updateGrade("A+");
        System.out.println("Updated Info:");
        s1.displayStudentInfo();

        // 3. (Optional) Create an object using the Default Constructor to see "Unknown"
        Student s2 = new Student();
        System.out.println("Default Constructor Info:");
        s2.displayStudentInfo();

        // 4. Demonstrating garbage collection
        s1 = null; 
        s2 = null;
        System.gc(); 
        System.out.println("Garbage Collection requested.");
    }
}