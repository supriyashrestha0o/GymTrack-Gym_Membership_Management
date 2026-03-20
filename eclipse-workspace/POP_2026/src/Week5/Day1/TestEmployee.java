package Week5.Day1;

class Employee {
    void Salary() { //method overriding
        System.out.println("Both manager and developer has a salary");
    }
}

class Manager extends Employee { //inheritance
    @Override
    void Salary() { //method overriding
        System.out.println("Manager has high salary");
    }
}

class Developer extends Employee { //inheritance
    @Override
    void Salary() { //method overriding
        System.out.println("Developer has very high salary");
    }
}

public class TestEmployee {
    public static void main(String[] args) {

        Employee e1 = new Manager();
        Employee e2 = new Developer();
        Employee e3 = new Employee();
        //Reference type = Employee
        //Object type = Manager / Developer

        e1.Salary();
        e2.Salary();
        e3.Salary();
    }
}
