package com.taskmanager.taskmanager;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseManager {

    private static final String URL = "jdbc:sqlite:SQLite/mydatabase.db";

    private static final String getEmployeesWithTasksQuerry = """
            SELECT pr.id_pracownika, pr.imie, pr.nazwisko, zad.opis_zadania
            FROM pracownicy pr
            LEFT JOIN pracownik_zadanie pz on pr.id_pracownika = pz.id_pracownika
            LEFT JOIN zadania zad on pz.id_zadania = zad.id_zadania;""";

    private static final String getTasksQuerry ="SELECT * from zadania ORDER BY id_zadania";
    private static final String getEmployeesQuerry = "SELECT * FROM pracownicy ORDER BY id_pracownika;";

    private static final String getNumberOfEmployees = "SELECT COUNT(*) FROM pracownicy;";
    private static final String getNumberOfTasks = "SELECT COUNT(*) FROM zadania;";

    public static void updateEmployeeTask(int employee_id, int task_id){
        try (Connection connection = DriverManager.getConnection(URL);
             Statement statement = connection.createStatement()) {
            String create = "UPDATE pracownik_zadanie SET id_zadania = " + task_id + " WHERE id_pracownika = " + employee_id + ";";
            statement.execute(create);
        }catch (SQLException e){
            System.err.println("updateEmployeeTaskERROR");
        }
    }

    static public ArrayList<Employee> getEmployees(){
        ArrayList<Employee> employees = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(URL);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(getEmployeesQuerry)){
            while (resultSet.next()){
                Employee employee = new Employee(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3)
                );
                employees.add(employee);

            }
        }catch (SQLException e){
            System.err.println("getEmployeesError");
        }

        return employees;
    }


    private static int getNumberOfEmployees(){
        int numberOfEmployees = 0;

        try (Connection connection = DriverManager.getConnection(URL);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(getNumberOfEmployees)){

            while (resultSet.next()){
                numberOfEmployees = resultSet.getInt(1);
            }
        }catch (SQLException e){
            System.err.println("getNumberOfEmployeesError");
        }

        return numberOfEmployees;

    }

    private static int getNumberOfTasks(){
        int numberOfTasks = 0;

        try (Connection connection = DriverManager.getConnection(URL);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(getNumberOfTasks)){

            while (resultSet.next()){
                numberOfTasks = resultSet.getInt(1);
            }
        }catch (SQLException e){
            System.err.println("getNumberOfTasksError");
        }

        return numberOfTasks;

    }


    public static void addEmployee(String name, String surname){
        try (Connection connection = DriverManager.getConnection(URL);
             Statement statement = connection.createStatement()) {
            int newId = DatabaseManager.getNumberOfEmployees() + 1;
            String create = "INSERT INTO pracownicy VALUES(" + newId + "," + "'" + name+ "'," + "'" + surname+ "');";
            statement.execute(create);
            String addToEmployeeTask = "INSERT INTO pracownik_zadanie VALUES(" + newId + ",1);";
            statement.execute(addToEmployeeTask);

        }catch (SQLException e){
            System.err.println("addEmployeeERROR");
        }
    }

    public static void addTask(String description){
        try (Connection connection = DriverManager.getConnection(URL);
             Statement statement = connection.createStatement()) {
            int newId = DatabaseManager.getNumberOfTasks() + 1;
            String create = "INSERT INTO zadania VALUES(" + newId + "," + "'" + description +  "');";
            statement.execute(create);

        }catch (SQLException e){
            System.err.println("addTaskERROR");
        }
    }

    public static void deleteEmployee(int id){
        try (Connection connection = DriverManager.getConnection(URL);
             Statement statement = connection.createStatement()) {
            String create = "DELETE FROM pracownicy WHERE id_pracownika = " + id;
            statement.execute(create);

            String deleteFromEmployeeTask = "DELETE FROM pracownik_zadanie where id_pracownika =" + id;
            statement.execute(deleteFromEmployeeTask);

        }catch (SQLException e){
            System.err.println("deleteEmployeeERROR");
        }
    }

    public static void deleteTask(int id){
        try (Connection connection = DriverManager.getConnection(URL);
             Statement statement = connection.createStatement()) {
            String create = "DELETE FROM zadania WHERE id_zadania = " + id;
            statement.execute(create);

        }catch (SQLException e){
            System.err.println("deleteTaskERROR");
        }
    }




    public static ArrayList<Task> getTasks(){
        ArrayList<Task> tasks = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(URL);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(getTasksQuerry)){
            while (resultSet.next()){
                Task task = new Task(
                        resultSet.getInt(1),
                        resultSet.getString(2)
                );
                tasks.add(task);

            }
        }catch (SQLException e){
            System.err.println("getTaskError");
        }

        return tasks;
    }

    public static ArrayList<EmployeeTask> getEmployeesWithTasks() {
        ArrayList<EmployeeTask> employeeTasks = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(URL);
                Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(getEmployeesWithTasksQuerry)){
            while (resultSet.next()){
                EmployeeTask task = new EmployeeTask(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4)
                        );
                employeeTasks.add(task);

            }
        }catch (SQLException e){
            System.err.println("getEmployeesWithTasksERROR");
        }

        return employeeTasks;
    }





}
