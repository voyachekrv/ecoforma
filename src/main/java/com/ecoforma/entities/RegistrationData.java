package com.ecoforma.entities;

public class RegistrationData {
    long ID;
    long employee_ID;
    String login;
    String password;
    int role_ID;

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public long getEmployee_ID() {
        return employee_ID;
    }

    public void setEmployee_ID(long employee_ID) {
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
