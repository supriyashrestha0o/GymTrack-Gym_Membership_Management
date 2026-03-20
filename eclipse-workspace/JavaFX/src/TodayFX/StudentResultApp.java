package TodayFX;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class StudentResultApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Student Result Management System");

        // 1. Create UI Controls
        Label lblSid = new Label("Student ID:");
        TextField txtSid = new TextField();

        Label lblName = new Label("Student Name:");
        TextField txtName = new TextField();

        Label lblPop = new Label("POP Marks:");
        TextField txtPop = new TextField();

        Label lblCn = new Label("CN Marks:");
        TextField txtCn = new TextField();

        Label lblDb = new Label("DB Marks:");
        TextField txtDb = new TextField();

        Button btnCalculate = new Button("Calculate & Save");
        btnCalculate.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");

        TextArea resultArea = new TextArea();
        resultArea.setEditable(false);
        resultArea.setPromptText("Results will appear here...");

        // 2. Layout (GridPane for Form)
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        grid.add(lblSid, 0, 0);
        grid.add(txtSid, 1, 0);
        grid.add(lblName, 0, 1);
        grid.add(txtName, 1, 1);
        grid.add(lblPop, 0, 2);
        grid.add(txtPop, 1, 2);
        grid.add(lblCn, 0, 3);
        grid.add(txtCn, 1, 3);
        grid.add(lblDb, 0, 4);
        grid.add(txtDb, 1, 4);
        grid.add(btnCalculate, 1, 5);

        // 3. Main Container
        VBox mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(10));
        mainLayout.getChildren().addAll(grid, resultArea);

        // 4. Button Logic (Connecting everything)
        btnCalculate.setOnAction(e -> {
            try {
                // Create Student object and fill data from UI
                Student s = new Student();
                s.sid = Integer.parseInt(txtSid.getText());
                s.name = txtName.getText();
                s.pop = Double.parseDouble(txtPop.getText());
                s.cn = Double.parseDouble(txtCn.getText());
                s.db = Double.parseDouble(txtDb.getText());

                // Perform Calculation
                ResultService service = new ResultServiceImpl();
                service.calculate(s);

                // Save to Database
                StudentRepository repo = new StudentRepository();
                repo.save(s);

                // Display in UI
                String output = "--- Student Result ---\n" +
                        "SID: " + s.sid + "\n" +
                        "Name: " + s.name + "\n" +
                        "Total: " + s.total + "\n" +
                        "Average: " + String.format("%.2f", s.average) + "\n" +
                        "Result: " + s.result;
                resultArea.setText(output);

            } catch (NumberFormatException ex) {
                resultArea.setText("Error: Please enter valid numbers!");
            } catch (Exception ex) {
                resultArea.setText("Error: " + ex.getMessage());
            }
        });

        // 5. Show Scene
        Scene scene = new Scene(mainLayout, 400, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
