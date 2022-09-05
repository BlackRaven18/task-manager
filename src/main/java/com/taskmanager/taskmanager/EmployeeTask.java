package com.taskmanager.taskmanager;

public class EmployeeTask {

    private int id;
    private String name, surname, description;

    public EmployeeTask(int id, String name, String surname, String description) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
