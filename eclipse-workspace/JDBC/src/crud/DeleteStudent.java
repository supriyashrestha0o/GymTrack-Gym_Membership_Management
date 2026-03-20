package crud;

import java.sql.Connection;
import java.sql.Statement;

public class DeleteStudent {

    public static void main(String[] args) {

        try {

            Connection conn = DatabaseConnector.getConnection();

            String query = "DELETE FROM student WHERE id=1";

            Statement stmt = conn.createStatement();
            stmt.executeUpdate(query);

            System.out.println("Student deleted!");

        } catch(Exception e) {
            System.out.println(e);
        }

    }

}
