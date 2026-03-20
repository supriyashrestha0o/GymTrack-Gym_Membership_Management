package Week5.Day1;
class Vehicle {
    void run() {
        System.out.println("Vehicle is running");
    }
}

class Car extends Vehicle {
    void run() {
        System.out.println("Car is running fast");
    }
}

class Bike extends Vehicle {
    void run() {
        System.out.println("Bike is running smoothly");
    }
}

public class TestVehicle {
    public static void main(String[] args) {
        
        // Parent reference, child object
        Vehicle v1 = new Car();
        Vehicle v2 = new Bike();
        Vehicle v3 = new Vehicle();
        
        v1.run();   // Calls Car's run()
        v2.run();   // Calls Bike's run()
        v3.run();   // Calls Vehicle's run()
    }
}

//Why This Code Demonstrates Polymorphism
//Step 1️⃣ – There is Inheritance
//class Car extends Vehicle
//class Bike extends Vehicle
//
//
//Car and Bike are child classes
//
//They inherit from Vehicle
//
//👉 Polymorphism requires inheritance
//
//Step 2️⃣ – Method Overriding Exists
//
//All classes have the same method:
//
//void run()
//
//
//But:
//
//Vehicle → prints "Vehicle is running"
//
//Car → prints "Car is running fast"
//
//Bike → prints "Bike is running smoothly"
//
//👉 Same method name
//👉 Same parameters
//👉 Different behavior
//
//This is method overriding.
//
//Polymorphism requires overriding.
//
//Step 3️⃣ – Parent Reference Holds Child Object
//Vehicle v1 = new Car();
//Vehicle v2 = new Bike();
//
//
//Here:
//
//Reference type → Vehicle
//
//Object type → Car or Bike
//
//This is called upcasting.
//
//👉 This is the MOST IMPORTANT step for runtime polymorphism.
//
//Step 4️⃣ – Method Call is Decided at Runtime
//v1.run();
//
//
//Java checks:
//
//Is the method overridden? ✅ Yes
//
//What is the actual object? → Car
//
//So it runs:
//
//Car is running fast
//
//
//Even though reference type is Vehicle.
//
//This runtime decision is called:
//
//✅ Dynamic Method Dispatch
