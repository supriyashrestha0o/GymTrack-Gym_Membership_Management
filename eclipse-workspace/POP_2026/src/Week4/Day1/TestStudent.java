package Week4.Day1;

public class TestStudent {
	    public static void main(String[] args) {
	        System.out.println("--- Creating Object 1 ---");
	        CollegeStudent s1 = new CollegeStudent();
	        
	        System.out.println("\n--- Creating Object 2 ---");
	        CollegeStudent s2 = new CollegeStudent("Suman");
	        s2.role();
	    }
	}