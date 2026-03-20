package Week3.Day3;

class Student {
    // Private variables (Encapsulation)
    private String name;
    private int rollNo;

    // Default Constructor (implicitly used)
    public Student() {
        // No initialization
    }

    // Parameterized Constructor
    public Student(String name, int rollNo) {
        this.name = name;
        this.rollNo = rollNo;
    }

    // Setter for Name
    public void setName(String name) {
        this.name = name;
    }

    // Getter for Roll Number
    public int getRollNo() {
        return rollNo;
    }

    // Setter for Roll Number
    public void setRollNo(int rollNo) {
        this.rollNo = rollNo;
    }

    // Method to display details
    public void display() {
        System.out.println("Name: " + name + ", Roll Number: " + rollNo);
    }
}

public class MainTest {
    public static void main(String[] args) {

        // -default constructor
        Student s1 = new Student();
        System.out.println("Test Case 1 Output:");
        s1.display();

        //Parameterized Constructor --------
        Student s2 = new Student("Amit", 101);
        System.out.println("\nTest Case 2 Output:");
        s2.display();

        // Using Setter Methods --------
        s1.setName("Sita");
        s1.setRollNo(5);
        System.out.println("\nTest Case 3 Output:");
        s1.display();

        //Another Student Object --------
        Student s3 = new Student("Supriya", 12);
        System.out.println("\nTest Case 4 Output:");
        s3.display();

//         -------- Test Case 5: Direct Access (ERROR Example) --------
//         s1.name = "Test";      // ❌ ERROR: name has private access
//         s1.rollNo = 10;        // ❌ ERROR: rollNo has private access
    }
}
