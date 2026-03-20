package com.gymtrack.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DatabaseConnection {

    public static Connection getConnection() {
        try {
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/gymtrack",
                "root",
                "POP.MySQL33#" 
            );
            return conn;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public static void initializeDatabase() {
        Connection conn = getConnection();
        if (conn == null) {
            System.out.println("❌ Database connection failed!");
            return;
        }

        try (Statement stmt = conn.createStatement()) {

            stmt.execute("CREATE TABLE IF NOT EXISTS Users (" +
                "user_id INT PRIMARY KEY AUTO_INCREMENT," +
                "username VARCHAR(50) NOT NULL UNIQUE," +
                "password VARCHAR(50) NOT NULL," +
                "role ENUM('Member','Trainer','Admin') NOT NULL)");

            stmt.execute("CREATE TABLE IF NOT EXISTS Members (" +
                "member_id INT PRIMARY KEY AUTO_INCREMENT," +
                "user_id INT NOT NULL," +
                "full_name VARCHAR(100) NOT NULL," +
                "email VARCHAR(100) NOT NULL," +
                "phone VARCHAR(15) NOT NULL," +
                "address VARCHAR(200)," +
                "dob VARCHAR(20)," +
                "gender VARCHAR(10)," +
                "goal TEXT," +
                "FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE)");

            stmt.execute("CREATE TABLE IF NOT EXISTS MembershipPlans (" +
                "plan_id INT PRIMARY KEY AUTO_INCREMENT," +
                "member_id INT NOT NULL," +
                "plan_type ENUM('Normal','Premium') NOT NULL," +
                "duration INT NOT NULL," +
                "start_date VARCHAR(20) NOT NULL," +
                "total_amount DECIMAL(10,2) NOT NULL," +
                "FOREIGN KEY (member_id) REFERENCES Members(member_id) ON DELETE CASCADE)");

            stmt.execute("CREATE TABLE IF NOT EXISTS Trainers (" +
                "trainer_id INT PRIMARY KEY AUTO_INCREMENT," +
                "user_id INT NOT NULL," +
                "full_name VARCHAR(100) NOT NULL," +
                "email VARCHAR(100) NOT NULL," +
                "phone VARCHAR(15) NOT NULL," +
                "dob VARCHAR(20)," +
                "gender VARCHAR(10)," +
                "speciality VARCHAR(100) NOT NULL," +
                "experience INT NOT NULL," +
                "FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE)");

            stmt.execute("CREATE TABLE IF NOT EXISTS TrainerAssignments (" +
                "assignment_id INT PRIMARY KEY AUTO_INCREMENT," +
                "member_id INT NOT NULL," +
                "trainer_id INT NOT NULL," +
                "assigned_date VARCHAR(20) NOT NULL," +
                "FOREIGN KEY (member_id) REFERENCES Members(member_id) ON DELETE CASCADE," +
                "FOREIGN KEY (trainer_id) REFERENCES Trainers(trainer_id) ON DELETE CASCADE)");

            stmt.execute("CREATE TABLE IF NOT EXISTS Attendance (" +
                "attendance_id INT PRIMARY KEY AUTO_INCREMENT," +
                "member_id INT NOT NULL," +
                "trainer_id INT NOT NULL," +
                "date VARCHAR(20) NOT NULL," +
                "status ENUM('Present','Absent') NOT NULL," +
                "FOREIGN KEY (member_id) REFERENCES Members(member_id) ON DELETE CASCADE)");

            stmt.execute("CREATE TABLE IF NOT EXISTS Leaves (" +
                "leave_id INT PRIMARY KEY AUTO_INCREMENT," +
                "member_id INT NOT NULL," +
                "reason TEXT NOT NULL," +
                "start_date VARCHAR(20) NOT NULL," +
                "end_date VARCHAR(20) NOT NULL," +
                "status ENUM('Pending','Approved','Rejected') DEFAULT 'Pending'," +
                "FOREIGN KEY (member_id) REFERENCES Members(member_id) ON DELETE CASCADE)");

            // NEW: Payments table
            stmt.execute("CREATE TABLE IF NOT EXISTS Payments (" +
                "payment_id INT PRIMARY KEY AUTO_INCREMENT," +
                "member_id INT NOT NULL," +
                "amount DECIMAL(10,2) NOT NULL CHECK (amount > 0)," +
                "payment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                "status ENUM('Paid','Refunded','Pending') DEFAULT 'Pending'," +
                "FOREIGN KEY (member_id) REFERENCES Members(member_id) ON DELETE CASCADE)");

            // NEW: PaymentLog table
            stmt.execute("CREATE TABLE IF NOT EXISTS PaymentLog (" +
                "log_id INT PRIMARY KEY AUTO_INCREMENT," +
                "payment_id INT," +
                "old_status VARCHAR(20)," +
                "new_status VARCHAR(20)," +
                "changed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)");

            stmt.execute("INSERT IGNORE INTO Users (username, password, role) " +
                "VALUES ('admin', 'admin123', 'Admin')");

            System.out.println("✅ Database Connected Successfully!");
            System.out.println("✅ All tables created/verified.");
            System.out.println("✅ Default admin ready!");

        } catch (Exception e) {
            System.out.println("❌ Error: " + e);
        }
    }
}