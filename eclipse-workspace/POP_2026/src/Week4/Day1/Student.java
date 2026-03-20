package Week4.Day1;

public class Student {
	    String name;

	    // 1. Default Parent Constructor
	    Student() {
	        System.out.println("Student: Default constructor called.");
	    }

	    // 2. Parameterized Parent Constructor
	    Student(String name) {
	        this.name = name;
	        System.out.println("Student: Parameterized constructor called for " + name);
	    }

	    void role() {
	        System.out.println("I am a student");
	    }
	}

	class CollegeStudent extends Student {
	    
	    // Using super() for Default Constructor
	    CollegeStudent() {
	        super(); // Calls Student()
	        System.out.println("CollegeStudent: Default constructor called.");
	    }

	    // Using super(parameters) for Parameterized Constructor
	    CollegeStudent(String name) {
	        super(name); // Calls Student(String name)
	        System.out.println("CollegeStudent: Parameterized constructor called.");
	    }

	    @Override
	    void role() {
	        System.out.println("I am a CollegeStudent named" + name);
	    }
	}