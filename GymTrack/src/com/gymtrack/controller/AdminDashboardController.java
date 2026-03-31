package com.gymtrack.controller;

import com.gymtrack.database.DatabaseConnection;
import com.gymtrack.main.Main;
import com.gymtrack.util.SessionManager;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdminDashboardController implements Initializable {

    @FXML private TabPane mainTabPane;

    // Overview
    @FXML private Label totalMembersLabel;
    @FXML private Label totalTrainersLabel;

    // Members Table
    @FXML private TableView<MemberRow>            membersTable;
    @FXML private TableColumn<MemberRow, Integer> colMId;
    @FXML private TableColumn<MemberRow, String>  colMName;
    @FXML private TableColumn<MemberRow, String>  colMGender;
    @FXML private TableColumn<MemberRow, String>  colMPlan;
    @FXML private TableColumn<MemberRow, String>  colMPhone;
    @FXML private TableColumn<MemberRow, String>  colMTrainer;
    @FXML private TextField memberSearchField;

    // Trainers Table
    @FXML private TableView<TrainerRow>            trainersTable;
    @FXML private TableColumn<TrainerRow, Integer> colTId;
    @FXML private TableColumn<TrainerRow, String>  colTName;
    @FXML private TableColumn<TrainerRow, String>  colTGender;
    @FXML private TableColumn<TrainerRow, String>  colTPhone;
    @FXML private TableColumn<TrainerRow, String>  colTSpeciality;
    @FXML private TableColumn<TrainerRow, Integer> colTExperience;
    @FXML private TextField trainerSearchField;

    // Assign
    @FXML private ComboBox<String> assignMemberCombo;
    @FXML private ComboBox<String> assignTrainerCombo;
    @FXML private Label            assignStatusLabel;

    // Delete
    @FXML private TableView<UserRow>            deleteTable;
    @FXML private TableColumn<UserRow, Boolean> colDelSelect;
    @FXML private TableColumn<UserRow, Integer> colDelId;
    @FXML private TableColumn<UserRow, String>  colDelName;
    @FXML private TableColumn<UserRow, String>  colDelRole;
    @FXML private TableColumn<UserRow, String>  colDelPhone;
    @FXML private TableColumn<UserRow, String>  colDelEmail;
    @FXML private TextField deleteSearchField;

    // Leave Requests
    @FXML private TableView<LeaveRow>            leavesTable;
    @FXML private TableColumn<LeaveRow, Integer> leaveIdCol;
    @FXML private TableColumn<LeaveRow, String>  leaveMemberCol;
    @FXML private TableColumn<LeaveRow, String>  leaveReasonCol;
    @FXML private TableColumn<LeaveRow, String>  leaveStartCol;
    @FXML private TableColumn<LeaveRow, String>  leaveEndCol;
    @FXML private TableColumn<LeaveRow, String>  leaveStatusCol;
    @FXML private TableColumn<LeaveRow, Void>    leaveActionCol;

    // Payments
    @FXML private TableView<PaymentRow>            paymentsTable;
    @FXML private TableColumn<PaymentRow, Integer> colPayId;
    @FXML private TableColumn<PaymentRow, String>  colPayMember;
    @FXML private TableColumn<PaymentRow, Double>  colPayAmount;
    @FXML private TableColumn<PaymentRow, String>  colPayDate;
    @FXML private TableColumn<PaymentRow, String>  colPayStatus;
    @FXML private ComboBox<String> paymentMemberCombo;
    @FXML private TextField        paymentAmountField;
    @FXML private Label            paymentStatusLabel;

    // Data Lists
    private ObservableList<MemberRow>  allMembers  = FXCollections.observableArrayList();
    private ObservableList<TrainerRow> allTrainers = FXCollections.observableArrayList();
    private ObservableList<UserRow>    allUsers    = FXCollections.observableArrayList();
    private ObservableList<LeaveRow>   allLeaves   = FXCollections.observableArrayList();
    private ObservableList<PaymentRow> allPayments = FXCollections.observableArrayList();

    // Inner Classes 

    public static class MemberRow {
        private final SimpleIntegerProperty id;
        private final SimpleStringProperty  name, gender, plan, phone, trainer;

        public MemberRow(int id, String name, String gender,
                         String plan, String phone, String trainer) {
            this.id      = new SimpleIntegerProperty(id);
            this.name    = new SimpleStringProperty(name);
            this.gender  = new SimpleStringProperty(gender != null ? gender : "—");
            this.plan    = new SimpleStringProperty(plan != null ? plan : "—");
            this.phone   = new SimpleStringProperty(phone);
            this.trainer = new SimpleStringProperty(trainer != null ? trainer : "Unassigned");
        }
        public SimpleIntegerProperty idProperty()      { return id;      }
        public SimpleStringProperty  nameProperty()    { return name;    }
        public SimpleStringProperty  genderProperty()  { return gender;  }
        public SimpleStringProperty  planProperty()    { return plan;    }
        public SimpleStringProperty  phoneProperty()   { return phone;   }
        public SimpleStringProperty  trainerProperty() { return trainer; }
        public int    getId()   { return id.get();   }
        public String getName() { return name.get(); }
    }

    public static class TrainerRow {
        private final SimpleIntegerProperty id, experience;
        private final SimpleStringProperty  name, gender, phone, speciality;

        public TrainerRow(int id, String name, String gender,
                          String phone, String speciality, int experience) {
            this.id         = new SimpleIntegerProperty(id);
            this.name       = new SimpleStringProperty(name);
            this.gender     = new SimpleStringProperty(gender != null ? gender : "—");
            this.phone      = new SimpleStringProperty(phone);
            this.speciality = new SimpleStringProperty(speciality);
            this.experience = new SimpleIntegerProperty(experience);
        }
        public SimpleIntegerProperty idProperty()         { return id;         }
        public SimpleStringProperty  nameProperty()       { return name;       }
        public SimpleStringProperty  genderProperty()     { return gender;     }
        public SimpleStringProperty  phoneProperty()      { return phone;      }
        public SimpleStringProperty  specialityProperty() { return speciality; }
        public SimpleIntegerProperty experienceProperty() { return experience; }
        public int    getId()   { return id.get();   }
        public String getName() { return name.get(); }
    }

    public static class UserRow {
        private final SimpleIntegerProperty id;
        private final SimpleStringProperty  name, role, phone, email;
        private final SimpleBooleanProperty selected;

        public UserRow(int id, String name, String role, String phone, String email) {
            this.id       = new SimpleIntegerProperty(id);
            this.name     = new SimpleStringProperty(name);
            this.role     = new SimpleStringProperty(role);
            this.phone    = new SimpleStringProperty(phone != null ? phone : "—");
            this.email    = new SimpleStringProperty(email != null ? email : "—");
            this.selected = new SimpleBooleanProperty(false);
        }
        public SimpleBooleanProperty selectedProperty() { return selected; }
        public SimpleIntegerProperty idProperty()       { return id;       }
        public SimpleStringProperty  nameProperty()     { return name;     }
        public SimpleStringProperty  roleProperty()     { return role;     }
        public SimpleStringProperty  phoneProperty()    { return phone;    }
        public SimpleStringProperty  emailProperty()    { return email;    }
        public boolean isSelected() { return selected.get(); }
        public int     getId()      { return id.get();       }
        public String  getName()    { return name.get();     }
    }

    public static class LeaveRow {
        private final SimpleIntegerProperty leaveId;
        private final SimpleStringProperty  memberName, reason, startDate, endDate, status;

        public LeaveRow(int leaveId, String memberName, String reason,
                        String startDate, String endDate, String status) {
            this.leaveId    = new SimpleIntegerProperty(leaveId);
            this.memberName = new SimpleStringProperty(memberName);
            this.reason     = new SimpleStringProperty(reason);
            this.startDate  = new SimpleStringProperty(startDate);
            this.endDate    = new SimpleStringProperty(endDate);
            this.status     = new SimpleStringProperty(status);
        }
        public int    getLeaveId() { return leaveId.get(); }
        public String getStatus()  { return status.get();  }
        public SimpleIntegerProperty leaveIdProperty()    { return leaveId;    }
        public SimpleStringProperty  memberNameProperty() { return memberName; }
        public SimpleStringProperty  reasonProperty()     { return reason;     }
        public SimpleStringProperty  startDateProperty()  { return startDate;  }
        public SimpleStringProperty  endDateProperty()    { return endDate;    }
        public SimpleStringProperty  statusProperty()     { return status;     }
    }

    public static class PaymentRow {
        private final SimpleIntegerProperty paymentId;
        private final SimpleStringProperty  memberName, paymentDate, status;
        private final SimpleDoubleProperty  amount;

        public PaymentRow(int paymentId, String memberName,
                          double amount, String paymentDate, String status) {
            this.paymentId   = new SimpleIntegerProperty(paymentId);
            this.memberName  = new SimpleStringProperty(memberName);
            this.amount      = new SimpleDoubleProperty(amount);
            this.paymentDate = new SimpleStringProperty(paymentDate);
            this.status      = new SimpleStringProperty(status);
        }
        public SimpleIntegerProperty paymentIdProperty()   { return paymentId;   }
        public SimpleStringProperty  memberNameProperty()  { return memberName;  }
        public SimpleDoubleProperty  amountProperty()      { return amount;      }
        public SimpleStringProperty  paymentDateProperty() { return paymentDate; }
        public SimpleStringProperty  statusProperty()      { return status;      }
    }

    // Initialize 

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupMembersTable();
        setupTrainersTable();
        setupDeleteTable();
        setupLeavesTable();
        setupPaymentsTable();
        loadAll();
    }

    private void loadAll() {
        loadStats();
        loadMembers();
        loadTrainers();
        loadUsersForDelete();
        populateAssignCombos();
        loadLeaves();
        loadPayments();
        populatePaymentMemberCombo();
    }

    // Overview

    private void loadStats() {
        try {
            Connection conn = DatabaseConnection.getConnection();
            ResultSet rm = conn.createStatement()
                .executeQuery("SELECT COUNT(*) FROM Members");
            if (rm.next()) totalMembersLabel.setText(String.valueOf(rm.getInt(1)));
            ResultSet rt = conn.createStatement()
                .executeQuery("SELECT COUNT(*) FROM Trainers");
            if (rt.next()) totalTrainersLabel.setText(String.valueOf(rt.getInt(1)));
        } catch (Exception e) { e.printStackTrace(); }
    }

    // Sidebar + Tab Navigation 
    // Tab order: Overview, Members=1, Trainers=2, Assign, Delete, Leave, Payments

    @FXML private void sideGoOverview() { mainTabPane.getSelectionModel().select(0); }
    @FXML private void goToMembers()    { mainTabPane.getSelectionModel().select(1); }
    @FXML private void goToTrainers()   { mainTabPane.getSelectionModel().select(2); }
    @FXML private void goToAssign()     { mainTabPane.getSelectionModel().select(3); }
    @FXML private void goToDelete()     { mainTabPane.getSelectionModel().select(4); }
    @FXML private void goToLeave()      { mainTabPane.getSelectionModel().select(5); }
    @FXML private void goToPayments()   { mainTabPane.getSelectionModel().select(6); }

    // Members Table

    private void setupMembersTable() {
        colMId.setCellValueFactory(d -> d.getValue().idProperty().asObject());
        colMName.setCellValueFactory(d -> d.getValue().nameProperty());
        colMGender.setCellValueFactory(d -> d.getValue().genderProperty());
        colMPlan.setCellValueFactory(d -> d.getValue().planProperty());
        colMPhone.setCellValueFactory(d -> d.getValue().phoneProperty());
        colMTrainer.setCellValueFactory(d -> d.getValue().trainerProperty());
    }

    private void loadMembers() {
        allMembers.clear();
        try {
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(
                "SELECT m.member_id, m.full_name, m.gender, m.phone, " +
                "COALESCE(MAX(mp.plan_type), 'N/A') AS plan_type, " +
                "COALESCE(MAX(t.full_name), 'Not Assigned') AS trainer_name " +
                "FROM Members m " +
                "LEFT JOIN MembershipPlans mp ON m.member_id = mp.member_id " +
                "LEFT JOIN TrainerAssignments ta ON m.member_id = ta.member_id " +
                "LEFT JOIN Trainers t ON ta.trainer_id = t.trainer_id " +
                "GROUP BY m.member_id, m.full_name, m.gender, m.phone " +
                "ORDER BY m.member_id"
            );
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                allMembers.add(new MemberRow(
                    rs.getInt("member_id"),
                    rs.getString("full_name"),
                    rs.getString("gender"),
                    rs.getString("plan_type"),
                    rs.getString("phone"),
                    rs.getString("trainer_name")
                ));
            }
        } catch (Exception e) { e.printStackTrace(); }
        membersTable.setItems(allMembers);
    }

    @FXML
    private void filterMembers() {
        String q = memberSearchField.getText().toLowerCase();
        membersTable.setItems(allMembers.filtered(
            r -> r.getName().toLowerCase().contains(q)));
    }

    // Trainers Table 

    private void setupTrainersTable() {
        colTId.setCellValueFactory(d -> d.getValue().idProperty().asObject());
        colTName.setCellValueFactory(d -> d.getValue().nameProperty());
        colTGender.setCellValueFactory(d -> d.getValue().genderProperty());
        colTPhone.setCellValueFactory(d -> d.getValue().phoneProperty());
        colTSpeciality.setCellValueFactory(d -> d.getValue().specialityProperty());
        colTExperience.setCellValueFactory(d -> d.getValue().experienceProperty().asObject());
    }

    private void loadTrainers() {
        allTrainers.clear();
        try {
            ResultSet rs = DatabaseConnection.getConnection()
                .createStatement()
                .executeQuery("SELECT * FROM Trainers ORDER BY trainer_id");
            while (rs.next()) {
                allTrainers.add(new TrainerRow(
                    rs.getInt("trainer_id"),
                    rs.getString("full_name"),
                    rs.getString("gender"),
                    rs.getString("phone"),
                    rs.getString("speciality"),
                    rs.getInt("experience")
                ));
            }
        } catch (Exception e) {
            System.out.println("Error loading trainers: " + e.getMessage());
            e.printStackTrace();
        }
        trainersTable.setItems(allTrainers);
    }

    @FXML
    private void filterTrainers() {
        String q = trainerSearchField.getText().toLowerCase();
        trainersTable.setItems(allTrainers.filtered(
            r -> r.getName().toLowerCase().contains(q)));
    }

    // Assign Trainer 

    private void populateAssignCombos() {
        List<String> memberNames = new ArrayList<>();
        allMembers.forEach(m -> memberNames.add(m.getId() + " - " + m.getName()));
        assignMemberCombo.setItems(FXCollections.observableArrayList(memberNames));

        List<String> trainerNames = new ArrayList<>();
        allTrainers.forEach(t -> trainerNames.add(t.getId() + " - " + t.getName()));
        assignTrainerCombo.setItems(FXCollections.observableArrayList(trainerNames));
    }

    @FXML
    private void handleAssign() {
        String memberSel  = assignMemberCombo.getValue();
        String trainerSel = assignTrainerCombo.getValue();

        if (memberSel == null || trainerSel == null) {
            assignStatusLabel.setStyle("-fx-text-fill: red;");
            assignStatusLabel.setText("Please select both a member and a trainer.");
            return;
        }

        int memberId  = Integer.parseInt(memberSel.split(" - ")[0]);
        int trainerId = Integer.parseInt(trainerSel.split(" - ")[0]);

        Connection conn = DatabaseConnection.getConnection();
        try {
            conn.setAutoCommit(false);
            PreparedStatement del = conn.prepareStatement(
                "DELETE FROM TrainerAssignments WHERE member_id = ?"
            );
            del.setInt(1, memberId);
            del.executeUpdate();

            PreparedStatement ins = conn.prepareStatement(
                "INSERT INTO TrainerAssignments (member_id, trainer_id) VALUES (?, ?)"
            );
            ins.setInt(1, memberId);
            ins.setInt(2, trainerId);
            ins.executeUpdate();

            conn.commit();
            assignStatusLabel.setStyle("-fx-text-fill: green;");
            assignStatusLabel.setText("✅ Trainer assigned successfully!");
            loadMembers();
            populateAssignCombos();

        } catch (Exception e) {
            try { conn.rollback(); } catch (Exception ignore) {}
            assignStatusLabel.setStyle("-fx-text-fill: red;");
            assignStatusLabel.setText("❌ Assignment failed!");
            e.printStackTrace();
        } finally {
            try { conn.setAutoCommit(true); } catch (Exception ignore) {}
        }
    }

    // Delete Users 
    private void setupDeleteTable() {
        colDelSelect.setCellValueFactory(d -> d.getValue().selectedProperty());
        colDelSelect.setCellFactory(CheckBoxTableCell.forTableColumn(colDelSelect));
        colDelId.setCellValueFactory(d -> d.getValue().idProperty().asObject());
        colDelName.setCellValueFactory(d -> d.getValue().nameProperty());
        colDelRole.setCellValueFactory(d -> d.getValue().roleProperty());
        colDelPhone.setCellValueFactory(d -> d.getValue().phoneProperty());
        colDelEmail.setCellValueFactory(d -> d.getValue().emailProperty());
        deleteTable.setEditable(true);
    }

    private void loadUsersForDelete() {
        allUsers.clear();
        try {
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(
                "SELECT u.user_id, u.role, " +
                "COALESCE(m.full_name, t.full_name) AS full_name, " +
                "COALESCE(m.phone, t.phone) AS phone, " +
                "COALESCE(m.email, t.email) AS email " +
                "FROM Users u " +
                "LEFT JOIN Members m ON u.user_id = m.user_id " +
                "LEFT JOIN Trainers t ON u.user_id = t.user_id " +
                "WHERE u.role != 'Admin' ORDER BY u.user_id"
            );
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                allUsers.add(new UserRow(
                    rs.getInt("user_id"),
                    rs.getString("full_name"),
                    rs.getString("role"),
                    rs.getString("phone"),
                    rs.getString("email")
                ));
            }
        } catch (Exception e) { e.printStackTrace(); }
        deleteTable.setItems(allUsers);
    }

    @FXML
    private void filterDeleteTable() {
        String q = deleteSearchField.getText().toLowerCase();
        deleteTable.setItems(allUsers.filtered(
            r -> r.getName().toLowerCase().contains(q)));
    }

    @FXML
    private void deleteSelected() {
        List<UserRow> toDelete = new ArrayList<>();
        for (UserRow row : allUsers) {
            if (row.isSelected()) toDelete.add(row);
        }
        if (toDelete.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "No users selected",
                "Please select at least one user to delete.");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirm Delete");
        confirm.setHeaderText("Delete " + toDelete.size() + " user(s)?");
        confirm.setContentText("This action cannot be undone.");
        Optional<ButtonType> result = confirm.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Connection conn = DatabaseConnection.getConnection();
            try {
                conn.setAutoCommit(false);
                PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM Users WHERE user_id = ?"
                );
                for (UserRow row : toDelete) {
                    ps.setInt(1, row.getId());
                    ps.addBatch();
                }
                ps.executeBatch();
                conn.commit();
                showAlert(Alert.AlertType.INFORMATION, "Deleted",
                    toDelete.size() + " user(s) deleted successfully.");
                loadAll();
            } catch (Exception e) {
                try { conn.rollback(); } catch (Exception ignore) {}
                showAlert(Alert.AlertType.ERROR, "Error",
                    "Failed to delete: " + e.getMessage());
                e.printStackTrace();
            } finally {
                try { conn.setAutoCommit(true); } catch (Exception ignore) {}
            }
        }
    }

    // Leave Requests

    private void setupLeavesTable() {
        leaveIdCol.setCellValueFactory(d -> d.getValue().leaveIdProperty().asObject());
        leaveMemberCol.setCellValueFactory(d -> d.getValue().memberNameProperty());
        leaveReasonCol.setCellValueFactory(d -> d.getValue().reasonProperty());
        leaveStartCol.setCellValueFactory(d -> d.getValue().startDateProperty());
        leaveEndCol.setCellValueFactory(d -> d.getValue().endDateProperty());
        leaveStatusCol.setCellValueFactory(d -> d.getValue().statusProperty());

        leaveActionCol.setCellFactory(col -> new TableCell<>() {
            private final Button approveBtn = new Button("✅ Approve");
            private final Button rejectBtn  = new Button("❌ Reject");
            private final HBox   box        = new HBox(5, approveBtn, rejectBtn);
            {
                approveBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
                rejectBtn.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
                approveBtn.setOnAction(e -> {
                    LeaveRow row = getTableView().getItems().get(getIndex());
                    updateLeaveStatus(row.getLeaveId(), "Approved");
                });
                rejectBtn.setOnAction(e -> {
                    LeaveRow row = getTableView().getItems().get(getIndex());
                    updateLeaveStatus(row.getLeaveId(), "Rejected");
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    LeaveRow row = getTableView().getItems().get(getIndex());
                    setGraphic(row.getStatus().equals("Pending") ? box : null);
                }
            }
        });
    }

    private void loadLeaves() {
        allLeaves.clear();
        try {
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(
                "SELECT l.leave_id, m.full_name, l.reason, " +
                "l.start_date, l.end_date, l.status " +
                "FROM Leaves l JOIN Members m ON l.member_id = m.member_id " +
                "ORDER BY l.leave_id DESC"
            );
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                allLeaves.add(new LeaveRow(
                    rs.getInt("leave_id"),
                    rs.getString("full_name"),
                    rs.getString("reason"),
                    rs.getString("start_date"),
                    rs.getString("end_date"),
                    rs.getString("status")
                ));
            }
        } catch (Exception e) { e.printStackTrace(); }
        leavesTable.setItems(allLeaves);
    }

    private void updateLeaveStatus(int leaveId, String status) {
        Connection conn = DatabaseConnection.getConnection();
        try {
            conn.setAutoCommit(false);
            PreparedStatement ps = conn.prepareStatement(
                "UPDATE Leaves SET status = ? WHERE leave_id = ?"
            );
            ps.setString(1, status);
            ps.setInt(2, leaveId);
            int rows = ps.executeUpdate();
            if (rows == 0) { conn.rollback(); return; }
            conn.commit();
            System.out.println("✅ Leave " + status);
            loadLeaves();
        } catch (Exception e) {
            try { conn.rollback(); } catch (Exception ignore) {}
            e.printStackTrace();
        } finally {
            try { conn.setAutoCommit(true); } catch (Exception ignore) {}
        }
    }

    // Payments
    private void setupPaymentsTable() {
        colPayId.setCellValueFactory(d -> d.getValue().paymentIdProperty().asObject());
        colPayMember.setCellValueFactory(d -> d.getValue().memberNameProperty());
        colPayAmount.setCellValueFactory(d -> d.getValue().amountProperty().asObject());
        colPayDate.setCellValueFactory(d -> d.getValue().paymentDateProperty());
        colPayStatus.setCellValueFactory(d -> d.getValue().statusProperty());
    }

    private void loadPayments() {
        allPayments.clear();
        try {
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(
                "SELECT p.payment_id, m.full_name, p.amount, " +
                "p.payment_date, p.status " +
                "FROM Payments p " +
                "JOIN Members m ON p.member_id = m.member_id " +
                "ORDER BY p.payment_id DESC"
            );
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                allPayments.add(new PaymentRow(
                    rs.getInt("payment_id"),
                    rs.getString("full_name"),
                    rs.getDouble("amount"),
                    rs.getString("payment_date"),
                    rs.getString("status")
                ));
            }
        } catch (Exception e) { e.printStackTrace(); }
        paymentsTable.setItems(allPayments);
    }

    private void populatePaymentMemberCombo() {
        List<String> names = new ArrayList<>();
        allMembers.forEach(m -> names.add(m.getId() + " - " + m.getName()));
        paymentMemberCombo.setItems(FXCollections.observableArrayList(names));
    }

    @FXML
    private void handleProcessPayment() {
        String memberSel = paymentMemberCombo.getValue();
        String amountStr = paymentAmountField.getText().trim();

        if (memberSel == null || amountStr.isEmpty()) {
            paymentStatusLabel.setStyle("-fx-text-fill: red;");
            paymentStatusLabel.setText("❌ Select member and enter amount!");
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(amountStr);
            if (amount <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            paymentStatusLabel.setStyle("-fx-text-fill: red;");
            paymentStatusLabel.setText("❌ Invalid amount!");
            return;
        }

        int memberId = Integer.parseInt(memberSel.split(" - ")[0]);

        Connection conn = DatabaseConnection.getConnection();
        try {
            conn.setAutoCommit(false);
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO Payments (member_id, amount, status) VALUES (?, ?, 'Paid')"
            );
            ps.setInt(1, memberId);
            ps.setDouble(2, amount);
            ps.executeUpdate();
            conn.commit();
            paymentStatusLabel.setStyle("-fx-text-fill: green;");
            paymentStatusLabel.setText("✅ Payment of Rs." + amount + " recorded!");
            paymentAmountField.clear();
            loadPayments();
        } catch (Exception e) {
            try { conn.rollback(); } catch (Exception ignore) {}
            paymentStatusLabel.setStyle("-fx-text-fill: red;");
            paymentStatusLabel.setText("❌ Payment failed!");
            e.printStackTrace();
        } finally {
            try { conn.setAutoCommit(true); } catch (Exception ignore) {}
        }
    }

    // Logout

    @FXML
    private void handleLogout() {
        SessionManager.clearSession();
        Stage stage = (Stage) totalMembersLabel.getScene().getWindow();
        Main.switchScene(stage, "/com/gymtrack/view/Login.fxml");
    }

    private void showAlert(Alert.AlertType type, String title, String msg) {
        Alert a = new Alert(type);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}
