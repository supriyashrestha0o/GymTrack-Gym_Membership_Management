package Week4.Day4;

class Employee {
    int employeeID;
    String name;
    String designation;
    double salary;

    Employee(int employeeID, String name, String designation, double salary) {
        this.employeeID = employeeID;
        this.name = name;
        this.designation = designation;
        this.salary = salary;
    }

    void applyIncrement(double amount) {
        salary += amount;
    }

    void displayEmployeeDetails() {
        System.out.println("Employee ID: " + employeeID);
        System.out.println("Name: " + name);
        System.out.println("Designation: " + designation);
        System.out.println("Salary: " + salary);
    }
}

public class EmployeeMain {
    public static void main(String[] args) {
        Employee e1 = new Employee(201, "Ram", "Developer", 50000);
        e1.displayEmployeeDetails();

        e1.applyIncrement(5000);
        e1.displayEmployeeDetails();

        // Garbage collection
        e1 = null;
        System.gc();
    }
}
