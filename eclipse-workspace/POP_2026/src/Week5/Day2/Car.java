package Week5.Day2;

public class Car {

	    String model;               // Instance variable
	    static int totalCarsSold;   // Static variable

	    public Car(String model) {
	        this.model = model;
	        totalCarsSold++;
	    }

	    public void display() {
	        System.out.println("Model: " + model);
	        System.out.println("Total Cars Sold: " + totalCarsSold);
	        System.out.println("--------------------");
	    }

	    public static void main(String[] args) {
	        Car c1 = new Car("Toyota");
	        Car c2 = new Car("Honda");
	        Car c3 = new Car("Ford");

	        c1.display();
	        c2.display();
	        c3.display();
	    }
	}