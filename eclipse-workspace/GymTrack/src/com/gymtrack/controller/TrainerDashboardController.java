package com.gymtrack.controller;
import com.gymtrack.database.DatabaseConnection;
import com.gymtrack.main.Main;
import com.gymtrack.model.User;
import com.gymtrack.util.SessionManager;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;

public class TrainerDashboardController implements Initializable {

    @FXML private Label welcomeLabel;
    @FXML private Label subtitleLabel;

    // Profile labels
    @FXML private Label tProfileName;
    @FXML private Label tProfileSpeciality;
    @FXML private Label tProfileExperience;
    @FXML private Label tProfilePhone;
    @FXML private Label tProfileEmail;

    // Clients table
    @FXML private TableView<ClientRow> clientsTable;
    @FXML private TableColumn<ClientRow, Integer> colClientId;
    @FXML private TableColumn<ClientRow, String>  colClientName;
    @FXML private TableColumn<ClientRow, String>  colClientGender;
    @FXML private TableColumn<ClientRow, String>  colClientPlan;
    @FXML private TableColumn<ClientRow, String>  colClientPhone;
    @FXML private TextField clientSearchField;

    // Attendance table
    @FXML private TableView<ClientRow> attendanceTable;
    @FXML private TableColumn<ClientRow, Integer> colAttId;
    @FXML private TableColumn<ClientRow, String>  colAttName;
    @FXML private TableColumn<ClientRow, String>  colAttGender;
    @FXML private TableColumn<ClientRow, String>  colAttPlan;
    @FXML private TableColumn<ClientRow, Boolean> colAttPresent;
    @FXML private TextField attendanceSearchField;

    private int trainerId = -1;
    private ObservableList<ClientRow> allClients = FXCollections.observableArrayList();

    // Inner model class for table rows 
    public static class ClientRow {
        private final SimpleIntegerProperty  id;
        private final SimpleStringProperty   name;
        private final SimpleStringProperty   gender;
        private final SimpleStringProperty   plan;
        private final SimpleStringProperty   phone;
        private final SimpleBooleanProperty  present;

        public ClientRow(int id, String name, String gender, String plan, String phone) {
            this.id      = new SimpleIntegerProperty(id);
            this.name    = new SimpleStringProperty(name);
            this.gender  = new SimpleStringProperty(gender);
            this.plan    = new SimpleStringProperty(plan);
            this.phone   = new SimpleStringProperty(phone);
            this.present = new SimpleBooleanProperty(false);
        }

        public int    getId()          { return id.get();       }
        public String getName()        { return name.get();     }
        public String getGender()      { return gender.get();   }
        public String getPlan()        { return plan.get();     }
        public String getPhone()       { return phone.get();    }
        public boolean isPresent()     { return present.get();  }
        public SimpleBooleanProperty presentProperty() { return present; }
        public SimpleIntegerProperty  idProperty()     { return id;      }
        public SimpleStringProperty   nameProperty()   { return name;    }
        public SimpleStringProperty   genderProperty() { return gender;  }
        public SimpleStringProperty   planProperty()   { return plan;    }
        public SimpleStringProperty   phoneProperty()  { return phone;   }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        User user = SessionManager.getCurrentUser();
        if (user == null) return;

        welcomeLabel.setText("Trainer · " + user.getUsername());
        loadProfile(user.getUserId());
        setupTables();
        loadClients();
    }

    private void loadProfile(int userId) {
        try {
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(
                "SELECT * FROM Trainers WHERE user_id = ?"
            );
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                trainerId = rs.getInt("trainer_id");
                String name = rs.getString("full_name");
                subtitleLabel.setText("Welcome, " + name + "!");
                tProfileName.setText(name);
                tProfileSpeciality.setText(rs.getString("speciality"));
                tProfileExperience.setText(rs.getInt("experience") + " years");
                tProfilePhone.setText(rs.getString("phone"));
                tProfileEmail.setText(rs.getString("email"));
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void setupTables() {
        // Clients table columns
        colClientId.setCellValueFactory(d -> d.getValue().idProperty().asObject());
        colClientName.setCellValueFactory(d -> d.getValue().nameProperty());
        colClientGender.setCellValueFactory(d -> d.getValue().genderProperty());
        colClientPlan.setCellValueFactory(d -> d.getValue().planProperty());
        colClientPhone.setCellValueFactory(d -> d.getValue().phoneProperty());

        // Attendance table columns (same data + checkbox)
        colAttId.setCellValueFactory(d -> d.getValue().idProperty().asObject());
        colAttName.setCellValueFactory(d -> d.getValue().nameProperty());
        colAttGender.setCellValueFactory(d -> d.getValue().genderProperty());
        colAttPlan.setCellValueFactory(d -> d.getValue().planProperty());
        colAttPresent.setCellValueFactory(d -> d.getValue().presentProperty());
        colAttPresent.setCellFactory(CheckBoxTableCell.forTableColumn(colAttPresent));
        attendanceTable.setEditable(true);
    }

    private void loadClients() {
        if (trainerId == -1) return;
        allClients.clear();
        try {
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(
                "SELECT m.member_id, m.full_name, m.gender, m.phone, mp.plan_type " +
                "FROM TrainerAssignments ta " +
                "JOIN Members m ON ta.member_id = m.member_id " +
                "LEFT JOIN MembershipPlans mp ON m.member_id = mp.member_id " +
                "WHERE ta.trainer_id = ?"
            );
            ps.setInt(1, trainerId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                allClients.add(new ClientRow(
                    rs.getInt("member_id"),
                    rs.getString("full_name"),
                    rs.getString("gender") != null ? rs.getString("gender") : "—",
                    rs.getString("plan_type") != null ? rs.getString("plan_type") : "—",
                    rs.getString("phone")
                ));
            }
        } catch (Exception e) { e.printStackTrace(); }

        clientsTable.setItems(allClients);
        attendanceTable.setItems(allClients);
    }

    @FXML
    private void filterClients() {
        String q = clientSearchField.getText().toLowerCase();
        clientsTable.setItems(allClients.filtered(r -> r.getName().toLowerCase().contains(q)));
    }

    @FXML
    private void filterAttendance() {
        String q = attendanceSearchField.getText().toLowerCase();
        attendanceTable.setItems(allClients.filtered(r -> r.getName().toLowerCase().contains(q)));
    }

    @FXML
    private void markAttendance() {
        if (trainerId == -1) return;
        String today = LocalDate.now().toString();
        List<String> markedNames = new ArrayList<>();

        try {
            Connection conn = DatabaseConnection.getConnection();
            for (ClientRow row : allClients) {
                String status = row.isPresent() ? "Present" : "Absent";
                PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO Attendance (member_id, trainer_id, date, status) VALUES (?, ?, ?, ?)"
                );
                ps.setInt(1, row.getId());
                ps.setInt(2, trainerId);
                ps.setString(3, today);
                ps.setString(4, status);
                ps.executeUpdate();
                if (row.isPresent()) markedNames.add(row.getName());
            }

            String msg = markedNames.isEmpty()
                ? "Attendance recorded. No members marked present."
                : "Present: " + String.join(", ", markedNames);
            showAlert(Alert.AlertType.INFORMATION, "Attendance saved for " + today, msg);

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to save attendance: " + e.getMessage());
        }
    }

    @FXML
    private void handleLogout() {
        SessionManager.clearSession();
        Stage stage = (Stage) welcomeLabel.getScene().getWindow();
        Main.switchScene(stage, "/com/gymtrack/view/Login.fxml");
    }

    private void showAlert(Alert.AlertType type, String title, String msg) {
        Alert a = new Alert(type);
        a.setTitle(title); a.setHeaderText(null); a.setContentText(msg); a.showAndWait();
    }
}
