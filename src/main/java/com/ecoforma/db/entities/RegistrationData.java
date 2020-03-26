package com.ecoforma.db.entities;

public class RegistrationData {
    private int ID;
    private int employee_ID;
    private String login;
    private String password;
    private int role_ID;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getEmployee_ID() {
        return employee_ID;
    }

    public void setEmployee_ID(int employee_ID) {
        this.employee_ID = employee_ID;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole_ID() {
        return role_ID;
    }

    public void setRole_ID(int role_ID) {
        this.role_ID = role_ID;
    }
}
