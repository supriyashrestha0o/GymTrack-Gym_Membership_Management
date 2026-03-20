package Week5.Day2;

public class Employee {

	    static int employeeCount = 0; // static variable
	    String name;

	    public Employee(String name) {
	        this.name = name;
	        employeeCount++;
	    }

	    public static void main(String[] args) {
	        Employee e1 = new Employee("Ram");
	        Employee e2 = new Employee("Sita");
	        Employee e3 = new Employee("Hari");

	        System.out.println("Total Employees: " + Employee.employeeCount);
	    }
	}