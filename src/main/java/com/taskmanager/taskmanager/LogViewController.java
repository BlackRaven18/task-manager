package com.taskmanager.taskmanager;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.net.URL;
import java.time.temporal.Temporal;
import java.util.ResourceBundle;

public class LogViewController implements Initializable {

    @FXML private Label warningLabel;
    @FXML private TextField loginField;
    @FXML private TextField passwordField;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        warningLabel.setTextFill(Color.RED);
        warningLabel.setText("");

    }


    @FXML
    public void logIntoDashboard(){

        if(!loginField.getText().equals(TaskManagerApplication.LOGIN)){
            warningLabel.setText("Niepoprawny login");
            return;
        }

        if(!passwordField.getText().equals(TaskManagerApplication.PASSWORD)){
            warningLabel.setText("Niepoprawne haslo");
            return;
        }

        TaskManagerApplication taskManagerApplication = new TaskManagerApplication();
        taskManagerApplication.changeScene("main-dashboard-view.fxml", 800, 600);


    }
}
