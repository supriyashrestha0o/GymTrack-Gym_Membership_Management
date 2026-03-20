package Week3.Day4;

public class std {
	int id = 100;

}
class Collegestd extends std {
	int id = 200;
	 
	void show() {
		System.out.println(id);
		System.out.println(super.id);
	}
}