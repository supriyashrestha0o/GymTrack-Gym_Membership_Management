package Week3.Day4;

public class Student {
	void role() {
		System.out.println("From Parent: HI");
		
	}

}

class CollegeStudent extends Student {
	void role() {
		super.role();
		System.out.println("From Child: Hello");
		
	}

}




