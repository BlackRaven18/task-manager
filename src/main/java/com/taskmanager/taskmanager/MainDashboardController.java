package com.taskmanager.taskmanager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainDashboardController implements Initializable {

    //***********************************************
    // Main Dashboard variables
    //***********************************************

    @FXML private Label dashboardIdLabel;
    @FXML private Label dashboardNameLabel;
    @FXML private Label dashboardSurnameLabel;

    @FXML private TableView<EmployeeTask> employeeTaskTable;
    @FXML private TableColumn<EmployeeTask, Integer> employeeTaskEmployeeIdColumn;
    @FXML private TableColumn<EmployeeTask, String> employeeTaskNameColumn;
    @FXML private TableColumn<EmployeeTask, String> employeeTaskSurnameColumn;
    @FXML private TableColumn<EmployeeTask, String> employeeTaskDescriptionColumn;

    @FXML private MenuButton chooseTaskMenuButton;

    //***********************************************
    // Employee tab variables
    //***********************************************

    @FXML private TextField employeeNameTextField;
    @FXML private TextField employeeSurnameTextField;

    @FXML private TableView<Employee>  employeeTable;
    @FXML private TableColumn<Employee, Integer> employeeIdColumn;
    @FXML private TableColumn<Employee, String> employeeNameColumn;
    @FXML private TableColumn<Employee, String> employeeSurnameColumn;


    //***********************************************
    // Task tab variables
    //***********************************************

    @FXML private TextField taskDescriptionTextField;

    @FXML private TableView<Task>  taskTable;
    @FXML private TableColumn<Task, Integer> taskIdColumn;
    @FXML private TableColumn<Task, String> taskDescriptionColumn;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        dashboardIdLabel.setText("");
        dashboardNameLabel.setText("");
        dashboardSurnameLabel.setText("");

        showEmployeeTaskData();
        showEmployees();
        showTasks();


        addItemsToChooseTaskMenuButton();



    }

    private void refreshTables(){
        showEmployeeTaskData();
        employeeTaskTable.refresh();

        showEmployees();
        employeeTable.refresh();

        showTasks();
        taskTable.refresh();
    }

    @FXML
    public void logOut(){
        TaskManagerApplication taskManagerApplication = new TaskManagerApplication();
        taskManagerApplication.changeScene("log-view.fxml", 800, 600);
    }

    //***********************************************
    // Main Dashboard methods
    //***********************************************

    private void addItemsToChooseTaskMenuButton(){

        chooseTaskMenuButton.getItems().clear();

        ArrayList<Task> taskList = DatabaseManager.getTasks();

        for(Task task : taskList){
            MenuItem item = new MenuItem(task.getDescription());
            item.setOnAction(event -> {
                if(dashboardIdLabel.getText().isEmpty()){
                    return;
                }

                DatabaseManager.updateEmployeeTask(Integer.parseInt(dashboardIdLabel.getText()), task.getId());
                setEmployeeTaskData();
                employeeTaskTable.refresh();
            });
            chooseTaskMenuButton.getItems().add(item);
        }


    }

    @FXML
    public void showEmployeeTaskData(){
        setEmployeeTaskData();
    }

    @FXML
    public void setEmployeeTaskData(){
        employeeTaskTable.setItems(getEmployeeTaskList());

        employeeTaskEmployeeIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        employeeTaskNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        employeeTaskSurnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        employeeTaskDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
    }


    private ObservableList<EmployeeTask> getEmployeeTaskList(){
        ObservableList<EmployeeTask> observableList = FXCollections.observableArrayList();

        ArrayList<EmployeeTask> employeeTaskList = DatabaseManager.getEmployeesWithTasks();

        observableList.addAll(employeeTaskList);

        return observableList;
    }

    @FXML public void selectEmployeeTasksTableRow(){
        int index =employeeTaskTable.getSelectionModel().getSelectedIndex();

        if(index < 0){
            return;
        }

        dashboardIdLabel.setText(String.valueOf(employeeTaskEmployeeIdColumn.getCellData(index)));
        dashboardNameLabel.setText(employeeTaskNameColumn.getCellData(index));
        dashboardSurnameLabel.setText(employeeTaskSurnameColumn.getCellData(index));
    }

    //***********************************************
    // Employee tab methods
    //***********************************************
    @FXML
    public int getSelectedEmployeeId(){
        int index = employeeTable.getSelectionModel().getSelectedIndex();

        if(index < 0){
            return -1;
        }

        return employeeIdColumn.getCellData(index);
    }

    private ObservableList<Employee> getEmployees(){
        ObservableList<Employee> observableList = FXCollections.observableArrayList();

        ArrayList<Employee> employees = DatabaseManager.getEmployees();

        observableList.addAll(employees);

        return observableList;
    }


    private void showEmployees(){
        employeeTable.setItems(getEmployees());

        employeeIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        employeeNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        employeeSurnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
    }

    @FXML
    public void addNewEmployee(){
        DatabaseManager.addEmployee(employeeNameTextField.getText(), employeeSurnameTextField.getText());

        refreshTables();

    }

    @FXML
    public void deleteEmployee(){
        DatabaseManager.deleteEmployee(getSelectedEmployeeId());
        refreshTables();

    }

    //***********************************************
    // Task tab methods
    //***********************************************

    @FXML
    public int getSelectedTaskId(){
        int index = taskTable.getSelectionModel().getSelectedIndex();

        if(index < 0){
            return -1;
        }

        return taskIdColumn.getCellData(index);
    }

    private ObservableList<Task> getTasks(){
        ObservableList<Task> observableList = FXCollections.observableArrayList();

        ArrayList<Task> employees = DatabaseManager.getTasks();

        observableList.addAll(employees);

        return observableList;
    }

    private void showTasks(){
        taskTable.setItems(getTasks());

        taskIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        taskDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

    }

    @FXML
    public void addNewTask(){
        DatabaseManager.addTask(taskDescriptionTextField.getText());

        addItemsToChooseTaskMenuButton();

        refreshTables();
    }

    @FXML
    public void deleteTask(){
        DatabaseManager.deleteTask(getSelectedTaskId());

        addItemsToChooseTaskMenuButton();

        refreshTables();

    }





}
