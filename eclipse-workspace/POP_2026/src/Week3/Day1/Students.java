package Week3.Day1;

public class Students {
	// private variables (data hiding)
	private String name;
	private int age;
	// setter method (to set values with control)
	void setName(String n) {
		name = n;
	}
	void setAge(int a) {
		if (a > 0) {          // validation
			age = a;
		}
	}
	// getter methods (to read values)
	String getName() {
		return name;
	}
	int getAge() {
		return age;
	}
}
