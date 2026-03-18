package com.gymtrack.controller;

import com.gymtrack.database.DatabaseConnection;
import com.gymtrack.main.Main;
import com.gymtrack.util.ValidationUtil;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class RegisterTrainerController implements Initializable {

    @FXML private TextField fullNameField;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private TextField emailField;
    @FXML private TextField phoneField;
    @FXML private DatePicker dobPicker;
    @FXML private ComboBox<String> specialityCombo;
    @FXML private ComboBox<String> experienceCombo;
    @FXML private RadioButton maleRadio;
    @FXML private RadioButton femaleRadio;
    @FXML private RadioButton otherRadio;
    @FXML private Label errorLabel;

    private ToggleGroup genderGroup;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        genderGroup = new ToggleGroup();
        maleRadio.setToggleGroup(genderGroup);
        femaleRadio.setToggleGroup(genderGroup);
        otherRadio.setToggleGroup(genderGroup);
        maleRadio.setSelected(true);

        specialityCombo.setItems(FXCollections.observableArrayList(
            "Yoga & Flexibility", "Strength Training", "Cardio & Endurance",
            "Weight Loss", "Body Building", "Nutrition & Diet", "CrossFit"
        ));
        experienceCombo.setItems(FXCollections.observableArrayList(
            "3", "4", "5", "6", "7", "8", "9", "10+"
        ));
    }

    @FXML
    private void handleRegister() {
        String fullName        = fullNameField.getText().trim();
        String username        = usernameField.getText().trim();
        String password        = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        String email           = emailField.getText().trim();
        String phone           = phoneField.getText().trim();
        String speciality      = specialityCombo.getValue();
        String experienceStr   = experienceCombo.getValue();

        // Basic validation
        if (!ValidationUtil.isNotEmpty(fullName) || !ValidationUtil.isNotEmpty(username) ||
            !ValidationUtil.isNotEmpty(password) || !ValidationUtil.isNotEmpty(email) ||
            !ValidationUtil.isNotEmpty(phone) || speciality == null || experienceStr == null) {
            errorLabel.setText("All fields are required!");
            return;
        }
        if (!ValidationUtil.isValidEmail(email)) {
            errorLabel.setText("Invalid email address!");
            return;
        }
        if (!ValidationUtil.isValidPhone(phone)) {
            errorLabel.setText("Phone must be exactly 10 digits!");
            return;
        }
        if (!ValidationUtil.passwordsMatch(password, confirmPassword)) {
            errorLabel.setText("Passwords do not match!");
            return;
        }
        if (usernameExists(username)) {
            errorLabel.setText("Username already taken!");
            return;
        }

        String gender = maleRadio.isSelected() ? "Male"
                      : femaleRadio.isSelected() ? "Female" : "Other";
        String dob    = dobPicker.getValue() != null
                      ? dobPicker.getValue().toString() : "";
        int experience = experienceStr.equals("10+") ? 10
                       : Integer.parseInt(experienceStr);

        boolean saved = saveTrainer(
            username, password, fullName, email,
            phone, dob, gender, speciality, experience
        );

        if (saved) {
            showAlert(Alert.AlertType.INFORMATION,
                "Registration Successful!",
                "Welcome, " + fullName + "! You can now login.");
            goBack();
        } else {
            errorLabel.setText("Registration failed. Please try again.");
        }
    }

    private boolean saveTrainer(
            String username, String password, String fullName,
            String email, String phone, String dob,
            String gender, String speciality, int experience) {

        Connection conn = DatabaseConnection.getConnection();
        try {
            // TRANSACTION: Both inserts succeed or both rollback
            conn.setAutoCommit(false);
            // Step 1: Insert into Users
            PreparedStatement psUser = conn.prepareStatement(
                "INSERT INTO Users (username, password, role) VALUES (?, ?, 'Trainer')",
                PreparedStatement.RETURN_GENERATED_KEYS
            );
            psUser.setString(1, username);
            psUser.setString(2, password);
            psUser.executeUpdate();
            ResultSet rsUser = psUser.getGeneratedKeys();
            int userId = rsUser.next() ? rsUser.getInt(1) : -1;
            if (userId == -1) throw new Exception("Failed to get user_id");

            // Step 2: Insert into Trainers
            PreparedStatement psTrainer = conn.prepareStatement(
                "INSERT INTO Trainers (user_id, full_name, email, phone, " +
                "dob, gender, speciality, experience) VALUES (?, ?, ?, ?, ?, ?, ?, ?)"
            );
            psTrainer.setInt(1, userId);
            psTrainer.setString(2, fullName);
            psTrainer.setString(3, email);
            psTrainer.setString(4, phone);
            psTrainer.setString(5, dob);
            psTrainer.setString(6, gender);
            psTrainer.setString(7, speciality);
            psTrainer.setInt(8, experience);
            psTrainer.executeUpdate();

            conn.commit(); // Both inserts successful
            System.out.println("✅ Trainer registered successfully!");
            return true;

        } catch (Exception e) {
            try { conn.rollback(); } catch (Exception ignore) {}
            System.out.println("❌ Trainer registration failed, rolled back: " + e.getMessage());
            return false;
        } finally {
            try { conn.setAutoCommit(true); } catch (Exception ignore) {}
        }
    }

    private boolean usernameExists(String username) {
        try {
            PreparedStatement ps = DatabaseConnection.getConnection()
                .prepareStatement("SELECT COUNT(*) FROM Users WHERE username = ?");
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (Exception e) { return false; }
    }

    @FXML
    private void goBack() {
        Stage stage = (Stage) fullNameField.getScene().getWindow();
        Main.switchScene(stage, "/com/gymtrack/view/Login.fxml");
    }

    private void showAlert(Alert.AlertType type, String title, String msg) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}