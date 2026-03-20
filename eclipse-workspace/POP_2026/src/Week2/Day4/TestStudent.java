package Week2.Day4;

public class TestStudent {

    public static void main(String[] args) {
        
        // --- Task: Create three Student objects ---
        Student s1 = new Student();
        s1.name = "Armita";
        s1.age = 20;

        Student s2 = new Student();
        s2.name = "Kevin";
        s2.age = 22;

        Student s3 = new Student();
        s3.name = "Dipendra";
        s3.age = 21;

        System.out.println("--- Initial Student Details ---");
        s1.display();
        s2.display();
        s3.display();

        // --- Task: Change age of one student and display again ---
        s1.age = 25; 
        System.out.println("--- After Updating Armita's Age ---");
        s1.display();

        // --- Task: Create object without values & observe output ---
        Student s4 = new Student();
        System.out.println("--- Empty Student Object (Default Values) ---");
        s4.display(); 
        // Observation: Name will be 'null' and Age will be '0'.
    }
}