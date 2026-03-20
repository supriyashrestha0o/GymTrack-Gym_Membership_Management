package Week5.Day2;

public class Person {

    public Person() {
        System.out.println("Person is created");
    }
}

class Student extends Person {

    public Student() {
        super();
        System.out.println("Student is created");
    }
}