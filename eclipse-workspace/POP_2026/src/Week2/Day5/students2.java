package Week2.Day5;

public class students2 {
    String name;
    int age;
    String collegeName;

    // Task 3: Default Constructor (No arguments)
    public students2() {
        this.name = "Unknown"; 
        this.age = 0;          
        this.collegeName = "Not Assigned";
    }

    // Task 2: Parameterized Constructor (From previous task)
    public students2(String n, int a, String c) {
        this.name = n;
        this.age = a;
        this.collegeName = c;
    }

    void display() {
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("College: " + collegeName);

    }
}