package Week5.Day1;

public class Testemployees {

    public static void main(String[] args) {

        // Runtime Polymorphism
        employee e;

        e = new FullTimeEmployee(50000);
        System.out.println("Full-Time Salary: " + e.calculateSalary());

        e = new PartTimeEmployee(40, 500);
        System.out.println("Part-Time Salary: " + e.calculateSalary());

        // Array + Loop
        employee[] employees = new employee[2];

        employees[0] = new FullTimeEmployee(60000);
        employees[1] = new PartTimeEmployee(30, 400);

        System.out.println("\nUsing Loop:");
        for (employee emp : employees) {
            System.out.println("Salary: " + emp.calculateSalary());
        }
    }
}
