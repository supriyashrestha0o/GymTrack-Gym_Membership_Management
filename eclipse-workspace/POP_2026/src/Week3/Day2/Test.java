//package Week3.Day2;
//
//class Student {
//    String name;
//    int age;
//
//    void display() {
//        System.out.println(name);
//        System.out.println(age);
//    }
//}
//
//class CollegeStudent extends Student {
//    int rollNo;
//
//    void showRollNo() {
//        System.out.println(rollNo);
//    }
//}
//
//
//public class Test {
//    public static void main(String[] args) {
//
//        CollegeStudent cs = new CollegeStudent();
//
//        cs.name = "Ram";
//        cs.age = 20;
//        cs.rollNo = 101;
//
//        cs.display();
//        cs.showRollNo();
//    }
//}
//
//


package Week3.Day2;

class Student {
    // 1. Private fields for encapsulation
    private String name;
    private int age;

    // 2. Encapsulated Setter for name
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            System.out.println("Error: Name cannot be empty.");
        } else {
            this.name = name;
        }
    }

    // 3. Encapsulated Setter for age
    public void setAge(int age) {
        if (age < 0) {
            System.out.println("Error: Age cannot be negative.");
        } else {
            this.age = age;
        }
    }

    public void display() {
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
    }
} // <--- Make sure Student class ends here

// 4. CollegeStudent should be its own class, NOT inside Student
class CollegeStudent extends Student {
    int rollNo;

    void showRollNo() {
        System.out.println("Roll No: " + rollNo);
    }
} // <--- Make sure CollegeStudent class ends here

public class Test {
    public static void main(String[] args) {
        CollegeStudent cs = new CollegeStudent();

        // Use the encapsulated setters
        cs.setName(" ");
        cs.setAge(-20);
        cs.rollNo = 101;

        cs.display();
        cs.showRollNo();
    }
}