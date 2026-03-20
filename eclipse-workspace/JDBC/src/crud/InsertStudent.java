package crud;

import java.sql.Connection;
import java.sql.Statement;

public class InsertStudent {

    public static void main(String[] args) {

        try {

            Connection conn = DatabaseConnector.getConnection();
            Statement stmt = conn.createStatement();

            // Student 1
            int id = 1;
            String name = "Ali";
            int age = 20;

            String query = "INSERT INTO student VALUES(" + id + ",'" + name + "'," + age + ")";
            stmt.executeUpdate(query);
            System.out.println(id + " " + name + " " + age + " inserted successfully");

            // Student 2
            int id2 = 2;
            String name2 = "Odesha";
            int age2 = 18;

            String query2 = "INSERT INTO student VALUES(" + id2 + ",'" + name2 + "'," + age2 + ")";
            stmt.executeUpdate(query2);
            System.out.println(id2 + " " + name2 + " " + age2 + " inserted successfully");

        } catch(Exception e) {
            System.out.println(e);
        }

    }
}
