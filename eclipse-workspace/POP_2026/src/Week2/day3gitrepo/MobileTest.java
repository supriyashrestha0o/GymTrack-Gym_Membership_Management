package Week2.day3gitrepo;

public class MobileTest {
	public static void main(String[] args) {
		//1. Declare an Object of Mobile Class
		Mobile m1; //Stack
		
		//2. Initialize an object
		//Allocate memory to object - Heap
		m1=new Mobile();
		
		//Set Values
		m1.mfgBy="Colors India PVt. Ltd";
		m1.mfgDate="2020";
		m1.model="C0256";
		
		//Get Values
		System.out.println(m1.mfgBy);
	}
}

