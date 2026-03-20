package Week3.Day4;

public class Animal {
	void sound () {
		System.out.println("Some animal Sound");
	}
}
class Dog extends Animal {
		void sound() {
			System.out.println("BARK");		//overriding
		}
	}
	class Cat extends Animal {
		void sound() {
			System.out.println("MEOW");		//overriding
		}
	}