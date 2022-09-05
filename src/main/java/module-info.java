module com.taskmanager.taskmanager {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.sql;

    opens com.taskmanager.taskmanager to javafx.fxml;
    exports com.taskmanager.taskmanager;
}