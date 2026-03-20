package Week4.Day4;

class Vehicle {
    String registrationNumber;
    String ownerName;
    String model;
    int year;

    // Default constructor
    Vehicle() {
        registrationNumber = "N/A";
        ownerName = "Unknown";
        model = "Unknown";
        year = 0;
    }

    // Parameterized constructor
    Vehicle(String registrationNumber, String ownerName, String model, int year) {
        this.registrationNumber = registrationNumber;
        this.ownerName = ownerName;
        this.model = model;
        this.year = year;
    }

    void changeOwnership(String newOwner) {
        ownerName = newOwner;
    }

    void displayVehicleDetails() {
        System.out.println("Registration Number: " + registrationNumber);
        System.out.println("Owner Name: " + ownerName);
        System.out.println("Model: " + model);
        System.out.println("Year: " + year);
    }
}

public class VehicleMain {
    public static void main(String[] args) {
        Vehicle v1 = new Vehicle("BA-1234", "Sita", "Toyota", 2022);
        v1.displayVehicleDetails();

        v1.changeOwnership("Gita");
        v1.displayVehicleDetails();
    }
}
