package Week2.day4gitrepo;

public class MyCalculatorTest {

	public static void main(String[] args) {
		
		MyCalculator mc2=new MyCalculator();
		mc2.num1=67;
		mc2.num2=9;
		mc2.calcSum();//Processing using method
		System.out.println(mc2.num1+", "+mc2.num2+", "+mc2.num3);
		
		/*
		//How to use MyClaculator?
		//1. Create an Object
		MyCalculator mc1;
		
		//2. Initialize an Object
		mc1=new MyCalculator();
		
		//3. Set/Get value(s) on object - Optional
		mc1.num1=15; //Set
		mc1.num2=2; //Get
		
		//4. Process value(s) - Optional
		mc1.num3=mc1.num1+mc1.num2;
		
		System.out.println(mc1.num3);
		
		//5. Destroy an Object
		//Automatically destroyed an object at the end of program
		//by Java Virtual Machine (JVM) - Garbage Collector
		
		*/
		
	}
}