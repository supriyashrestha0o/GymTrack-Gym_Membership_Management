package com.gymtrack.controller;
import com.gymtrack.database.DatabaseConnection;
import com.gymtrack.main.Main;
import com.gymtrack.model.User;
import com.gymtrack.util.SessionManager;
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

public class LoginController implements Initializable {

    // @FXML fields — matched by fx:id in Login.fxml 
    @FXML private ComboBox<String> roleCombo;
    @FXML private TextField        usernameField;
    @FXML private PasswordField    passwordField;
    @FXML private TextField        passwordVisible;   // eye toggled
    @FXML private Label            errorLabel;

    private boolean showingPassword = false;

    // initialize() runs automatically after FXML is loaded 
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // role dropdown
        roleCombo.setItems(FXCollections.observableArrayList("Member", "Trainer", "Admin"));

        // passwordField and passwordVisible in sync
        passwordVisible.textProperty().bindBidirectional(passwordField.textProperty());
    }

    // Toggle password visibility
    @FXML
    private void togglePassword() {
        showingPassword = !showingPassword;
        passwordField.setVisible(!showingPassword);
        passwordField.setManaged(!showingPassword);
        passwordVisible.setVisible(showingPassword);
        passwordVisible.setManaged(showingPassword);
    }

    //  Handle Login button click
    @FXML
    private void handleLogin() {
        String role     = roleCombo.getValue();
        String username = usernameField.getText().trim();
        String password = passwordField.getText();

        // Basic validation
        if (role == null || role.isEmpty()) { showError("Please select a role."); return; }
        if (username.isEmpty())             { showError("Username is required."); return; }
        if (password.isEmpty())             { showError("Password is required."); return; }

        // Query the database
        User user = authenticateUser(username, password, role);

        if (user == null) {
            showError("Invalid username, password, or role.");
            return;
        }

        // Save user in session so other controllers can access it
        
        SessionManager.setCurrentUser(user);

        // Navigates to the correct dash board
        Stage stage = (Stage) usernameField.getScene().getWindow();

        switch (role) {
            case "Member":
                Main.switchScene(stage, "/com/gymtrack/view/MemberDashboard.fxml");
                break;
            case "Trainer":
                Main.switchScene(stage, "/com/gymtrack/view/TrainerDashboard.fxml");
                break;
            case "Admin":
                Main.switchScene(stage, "/com/gymtrack/view/AdminDashboard.fxml");
                break;
        }
    }

    private User authenticateUser(String username, String password, String role) {
        String sql = "SELECT * FROM Users WHERE username = ? AND password = ? AND role = ?";
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, role);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new User(
                    rs.getInt("user_id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("role")
                );
            }
        } catch (Exception e) {
            showError("Database error: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    // Open Member Registration window
    @FXML
    private void openMemberRegister() {
        Stage stage = (Stage) usernameField.getScene().getWindow();
        Main.switchScene(stage, "/com/gymtrack/view/RegisterMember.fxml");
    }

    //  Open Trainer Registration window 
    @FXML
    private void openTrainerRegister() {
        Stage stage = (Stage) usernameField.getScene().getWindow();
        Main.switchScene(stage, "/com/gymtrack/view/RegisterTrainer.fxml");
    }

    // Exit the application
    @FXML
    private void handleExit() {
        System.exit(0);
    }

    // Helper to show error messages
    private void showError(String message) {
        errorLabel.setText(message);
    }
}
