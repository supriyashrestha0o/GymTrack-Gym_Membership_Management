package com.gymtrack.controller;

import com.gymtrack.database.DatabaseConnection;
import com.gymtrack.main.Main;
import com.gymtrack.model.User;
import com.gymtrack.util.SessionManager;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;
import java.util.ResourceBundle;

public class MemberDashboardController implements Initializable {

    // Profile
    @FXML private Label welcomeLabel;
    @FXML private Label subtitleLabel;
    @FXML private Label profileName;
    @FXML private Label profilePlan;
    @FXML private Label profilePhone;
    @FXML private Label profileEmail;
    @FXML private Label profileStatus;

    // Trainer
    @FXML private Label trainerName;
    @FXML private Label trainerSpeciality;
    @FXML private Label trainerExperience;
    @FXML private Label trainerPhone;

    // Chart
    @FXML private BarChart<String, Number> crowdChart;

    // Payment History Table
    @FXML private TableView<PaymentRow>            paymentHistoryTable;
    @FXML private TableColumn<PaymentRow, Integer> colMyPayId;
    @FXML private TableColumn<PaymentRow, Double>  colMyPayAmount;
    @FXML private TableColumn<PaymentRow, String>  colMyPayDate;
    @FXML private TableColumn<PaymentRow, String>  colMyPayStatus;

    // inner class
    public static class PaymentRow {
        private final SimpleIntegerProperty paymentId;
        private final SimpleDoubleProperty  amount;
        private final SimpleStringProperty  paymentDate, status;

        public PaymentRow(int paymentId, double amount,
                          String paymentDate, String status) {
            this.paymentId   = new SimpleIntegerProperty(paymentId);
            this.amount      = new SimpleDoubleProperty(amount);
            this.paymentDate = new SimpleStringProperty(paymentDate);
            this.status      = new SimpleStringProperty(status);
        }
        public SimpleIntegerProperty paymentIdProperty()   { return paymentId;   }
        public SimpleDoubleProperty  amountProperty()      { return amount;      }
        public SimpleStringProperty  paymentDateProperty() { return paymentDate; }
        public SimpleStringProperty  statusProperty()      { return status;      }
    }

    // initialize
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        User user = SessionManager.getCurrentUser();
        if (user == null) return;

        welcomeLabel.setText("Member · " + user.getUsername());
        loadMemberProfile(user.getUserId());
        loadCrowdChart();
        setupPaymentTable();
        loadPaymentHistory(user.getUserId());
    }

    //  Member Profile 
    private void loadMemberProfile(int userId) {
        try {
            Connection conn = DatabaseConnection.getConnection();

            // Member details + plan
            PreparedStatement ps = conn.prepareStatement(
                "SELECT m.full_name, m.email, m.phone, mp.plan_type " +
                "FROM Members m " +
                "LEFT JOIN MembershipPlans mp ON m.member_id = mp.member_id " +
                "WHERE m.user_id = ? ORDER BY mp.plan_id DESC LIMIT 1"
            );
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String name = rs.getString("full_name");
                subtitleLabel.setText("Welcome back, " + name + "!");
                profileName.setText(name);
                profilePlan.setText(rs.getString("plan_type") != null
                    ? rs.getString("plan_type") : "N/A");
                profilePhone.setText(rs.getString("phone"));
                profileEmail.setText(rs.getString("email"));
            }

            // Assigned trainer
            PreparedStatement psT = conn.prepareStatement(
                "SELECT t.full_name, t.speciality, t.experience, t.phone " +
                "FROM TrainerAssignments ta " +
                "JOIN Members m ON ta.member_id = m.member_id " +
                "JOIN Trainers t ON ta.trainer_id = t.trainer_id " +
                "WHERE m.user_id = ? ORDER BY ta.assignment_id DESC LIMIT 1"
            );
            psT.setInt(1, userId);
            ResultSet rsT = psT.executeQuery();
            if (rsT.next()) {
                trainerName.setText(rsT.getString("full_name"));
                trainerSpeciality.setText(rsT.getString("speciality"));
                trainerExperience.setText(rsT.getInt("experience") + " years");
                trainerPhone.setText(rsT.getString("phone"));
            } else {
                trainerName.setText("Not assigned yet");
                trainerSpeciality.setText("—");
                trainerExperience.setText("—");
                trainerPhone.setText("—");
            }

        } catch (Exception e) { e.printStackTrace(); }
    }

    // Crowd Chart 
    private void loadCrowdChart() {
        int[] crowdValues = {75, 60, 43, 14, 9, 12, 11, 20, 24, 36, 50, 72, 78};
        String[] hours    = {"6am","7am","8am","9am","10am","11am",
                             "12pm","1pm","2pm","3pm","4pm","5pm","6pm"};

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Crowd %");
        for (int i = 0; i < hours.length; i++) {
            series.getData().add(new XYChart.Data<>(hours[i], crowdValues[i]));
        }
        crowdChart.getData().add(series);
        crowdChart.setLegendVisible(false);
    }

    // Payment History 
    private void setupPaymentTable() {
        colMyPayId.setCellValueFactory(d -> d.getValue().paymentIdProperty().asObject());
        colMyPayAmount.setCellValueFactory(d -> d.getValue().amountProperty().asObject());
        colMyPayDate.setCellValueFactory(d -> d.getValue().paymentDateProperty());
        colMyPayStatus.setCellValueFactory(d -> d.getValue().statusProperty());
    }

    private void loadPaymentHistory(int userId) {
        try {
            ObservableList<PaymentRow> payments = FXCollections.observableArrayList();

            // Get member_id from user_id
            PreparedStatement psM = DatabaseConnection.getConnection().prepareStatement(
                "SELECT member_id FROM Members WHERE user_id = ?"
            );
            psM.setInt(1, userId);
            ResultSet rsM = psM.executeQuery();
            if (!rsM.next()) return;
            int memberId = rsM.getInt("member_id");

            // Load payment history
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(
                "SELECT payment_id, amount, payment_date, status " +
                "FROM Payments WHERE member_id = ? ORDER BY payment_id DESC"
            );
            ps.setInt(1, memberId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                payments.add(new PaymentRow(
                    rs.getInt("payment_id"),
                    rs.getDouble("amount"),
                    rs.getString("payment_date"),
                    rs.getString("status")
                ));
            }
            paymentHistoryTable.setItems(payments);

        } catch (Exception e) { e.printStackTrace(); }
    }

    //  Leave Dialog 
    @FXML
    private void openLeaveDialog() {
        User user = SessionManager.getCurrentUser();

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Take a Leave");
        dialog.setHeaderText("Submit a Leave Request");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        TextField reasonField  = new TextField();
        reasonField.setPromptText("Reason for leave");
        DatePicker startPicker = new DatePicker();
        DatePicker endPicker   = new DatePicker();

        javafx.scene.layout.GridPane grid = new javafx.scene.layout.GridPane();
        grid.setHgap(12); grid.setVgap(12);
        grid.setPadding(new javafx.geometry.Insets(20));
        grid.add(new Label("Username:"),   0, 0);
        grid.add(new TextField(user.getUsername()) {{ setEditable(false); }}, 1, 0);
        grid.add(new Label("Reason:"),     0, 1); grid.add(reasonField,  1, 1);
        grid.add(new Label("Start Date:"), 0, 2); grid.add(startPicker, 1, 2);
        grid.add(new Label("End Date:"),   0, 3); grid.add(endPicker,   1, 3);
        dialog.getDialogPane().setContent(grid);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            String reason = reasonField.getText().trim();
            if (reason.isEmpty() || startPicker.getValue() == null
                    || endPicker.getValue() == null) {
                showAlert(Alert.AlertType.WARNING, "All leave fields are required.");
                return;
            }
            saveLeave(user.getUserId(), reason,
                startPicker.getValue().toString(),
                endPicker.getValue().toString());
        }
    }

    private void saveLeave(int userId, String reason,
                           String startDate, String endDate) {
        try {
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(
                "INSERT INTO Leaves (member_id, reason, start_date, end_date) " +
                "SELECT m.member_id, ?, ?, ? FROM Members m WHERE m.user_id = ?"
            );
            ps.setString(1, reason);
            ps.setString(2, startDate);
            ps.setString(3, endDate);
            ps.setInt(4, userId);
            ps.executeUpdate();
            showAlert(Alert.AlertType.INFORMATION, "Leave request submitted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Failed to submit leave: " + e.getMessage());
        }
    }

    // Logout
    @FXML
    private void handleLogout() {
        SessionManager.clearSession();
        Stage stage = (Stage) welcomeLabel.getScene().getWindow();
        Main.switchScene(stage, "/com/gymtrack/view/Login.fxml");
    }

    private void showAlert(Alert.AlertType type, String msg) {
        Alert alert = new Alert(type);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}



