package crud;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnector {

    public static Connection getConnection() {

        try {
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/w3sql",
                "root",
                "POP.MySQL33#"
            );

            return conn;

        } catch (Exception e) {
            System.out.println(e);
        }

        return null;
    }

    public static void main(String[] args) {

        Connection conn = getConnection();

        if(conn != null) {
            System.out.println("Database Connected Successfully!");
        } else {
            System.out.println("Connection Failed");
        }
    }
}
