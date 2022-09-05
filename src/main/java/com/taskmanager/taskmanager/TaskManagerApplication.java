package com.taskmanager.taskmanager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;


public class TaskManagerApplication extends Application {

    public static final String LOGIN = "";
    public static final String PASSWORD = "";

    private static Stage fakeStage;


    @Override
    public void start(Stage stage) throws IOException{

        fakeStage = stage;
        stage.setResizable(false);

        FXMLLoader fxmlLoader = new FXMLLoader(TaskManagerApplication.class.getResource("log-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("Task Manager");
        stage.setScene(scene);
        stage.show();

    }


    public static void main(String[] args){launch();}

    public void changeScene(String fxml, double width, double height){
        try {
            Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxml)));
            fakeStage.getScene().setRoot(parent);
            fakeStage.setWidth(width + 14.4);
            fakeStage.setHeight(height + 37.6);
            fakeStage.centerOnScreen();
        }catch (IOException e){
            System.err.println("Change scene ERROR");
        }
    }
}