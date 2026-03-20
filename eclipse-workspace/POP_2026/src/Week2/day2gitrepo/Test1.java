package Week2.day2gitrepo;

public class Test1 {
	public static void main(String[] args) {
		//Create an object of MyClass1
		MyClass1 p1;//Declare an Object of MyClass1 Class
		MyClass1 p19;
		
		p1=new MyClass1();
		p19=new MyClass1();
		//Store Value
		p1.name="Alex";
		p1.address="Sanepa";
		
		p19.name="Dipendra";
		p19.address="Koteshor";
		
		//Access Value
		System.out.println(p1.name);
		System.out.println(p1.address);
		
	}
}