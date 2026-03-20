/**
 * 
 */
/**
 * 
 */
module StudentResultSystem {
    requires java.sql;
    requires javafx.controls;
    requires javafx.fxml;

    opens com to javafx.graphics, javafx.fxml;
}