package Week5.Day1;

// Parent Class
public class employee {

    public double calculateSalary() {
        return 0;   // Will be overridden
    }
}

// Full-Time Employee
class FullTimeEmployee extends employee {

    double monthlySalary;

    public FullTimeEmployee(double monthlySalary) {
        this.monthlySalary = monthlySalary;
    }

    @Override
    public double calculateSalary() {
        return monthlySalary;
    }
}

// Part-Time Employee
class PartTimeEmployee extends employee {

    int hoursWorked;
    double ratePerHour;

    public PartTimeEmployee(int hoursWorked, double ratePerHour) {
        this.hoursWorked = hoursWorked;
        this.ratePerHour = ratePerHour;
    }

    @Override
    public double calculateSalary() {
        return hoursWorked * ratePerHour;
    }
}
