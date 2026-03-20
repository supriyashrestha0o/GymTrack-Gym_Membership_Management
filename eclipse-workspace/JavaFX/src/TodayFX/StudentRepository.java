package TodayFX;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class StudentRepository {

    public void save(Student s) {
        try {
            // Using common default credentials for learning purposes
            // Suggesting user to check if 'college' database and 'student' table exists
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/college",
                    "root",
                    "password"); // change if needed

            String sql = "INSERT INTO student VALUES(?,?,?,?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, s.sid);
            ps.setString(2, s.name);
            ps.setDouble(3, s.pop);
            ps.setDouble(4, s.cn);
            ps.setDouble(5, s.db);
            ps.setDouble(6, s.total);
            ps.setDouble(7, s.average);
            ps.setString(8, s.result);

            ps.executeUpdate();
            conn.close();
            System.out.println("Student saved to database successfully.");
        } catch (Exception e) {
            System.err.println("Database Error: " + e.getMessage());
        }
    }
}
