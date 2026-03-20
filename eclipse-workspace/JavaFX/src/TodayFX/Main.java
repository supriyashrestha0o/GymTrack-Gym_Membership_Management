package TodayFX;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Student s = new Student();

        System.out.println("--- Student Result Console System ---");
        
        System.out.print("Enter SID: ");
        s.sid = sc.nextInt();

        System.out.print("Enter Name: ");
        s.name = sc.next();

        System.out.print("Enter POP marks: ");
        s.pop = sc.nextDouble();

        System.out.print("Enter CN marks: ");
        s.cn = sc.nextDouble();

        System.out.print("Enter DB marks: ");
        s.db = sc.nextDouble();

        ResultService service = new ResultServiceImpl();
        service.calculate(s);

        System.out.println("\nStudent Result Card");
        System.out.println("--------------------");
        System.out.println("SID: " + s.sid);
        System.out.println("Name: " + s.name);
        System.out.println("POP Marks: " + s.pop);
        System.out.println("CN Marks: " + s.cn);
        System.out.println("DB Marks: " + s.db);
        System.out.println("Total Marks: " + s.total);
        System.out.println("Average: " + String.format("%.2f", s.average));
        System.out.println("Result Status: " + s.result);

        System.out.println("\nAttempting to save to database...");
        StudentRepository repo = new StudentRepository();
        repo.save(s);
        
        sc.close();
    }
}
