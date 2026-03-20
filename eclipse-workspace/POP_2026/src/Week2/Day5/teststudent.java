package Week2.Day5;

public class teststudent {
	    public static void main(String[] args) {
	        // 1. Create 3 Student objects
	        students s1 = new students();
	        students s2 = new students();
	        students s3 = new students();

	        // 2. Assign different names and ages
	        s1.name = "Alex";
	        s1.age = 18;

	        s2.name = "Priya";
	        s2.age = 21;

	        s3.name = "Jordan";
	        s3.age = 19;

	        // 3. Call display() for each one
	        System.out.println("--- Student List ---");
	        s1.display();
	        s2.display();
	        s3.display();
	    }
	}
