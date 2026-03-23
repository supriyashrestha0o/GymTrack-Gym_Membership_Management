package com.gymtrack.main;

import com.gymtrack.database.DatabaseConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        DatabaseConnection.initializeDatabase();
        Parent root = FXMLLoader.load(
            getClass().getResource("/com/gymtrack/view/Login.fxml")
        );
        primaryStage.setTitle("GymTrack — Gym Membership Management System");
        primaryStage.setScene(new Scene(root, 780, 600));
        primaryStage.setResizable(true);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    public static void switchScene(Stage stage, String fxmlPath) {
        try {
            Parent root = FXMLLoader.load(Main.class.getResource(fxmlPath));
            stage.getScene().setRoot(root);
        } catch (Exception e) {
            System.err.println("❌ Failed to switch to: " + fxmlPath);
            e.printStackTrace();
        }
    }

    public static Stage openNewWindow(String fxmlPath, String title) {
        try {
            Parent root = FXMLLoader.load(Main.class.getResource(fxmlPath));
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.show();
            return stage;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}