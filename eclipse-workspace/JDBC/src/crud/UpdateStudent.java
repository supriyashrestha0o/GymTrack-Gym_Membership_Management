package crud;
import java.sql.*;
import java.util.Scanner;

public class UpdateStudent {
    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter ID: ");
        int id = sc.nextInt();

        System.out.print("Enter new age: ");
        int age = sc.nextInt();

        Connection conn = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/","root","POP.MySQL33#");

        Statement stmt = conn.createStatement();

        String query = "UPDATE student SET age=" + age + " WHERE id=" + id;

        stmt.executeUpdate(query);

        System.out.println("Student updated");

        conn.close();
    }
}
