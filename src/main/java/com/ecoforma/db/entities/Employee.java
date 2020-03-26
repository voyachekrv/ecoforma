package com.ecoforma.db.entities;

public class Employee {
    private int ID;
    private String name;
    private String dateOfBirth;
    private String passport;
    private String education;
    private String adress;
    private String phoneNumber;
    private String email;
    private String dateOfEmployment;
    private int post_ID;
    private int department_ID;
    private int personalSalary;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDateOfEmployment() {
        return dateOfEmployment;
    }

    public void setDateOfEmployment(String dateOfEmployment) {
        this.dateOfEmployment = dateOfEmployment;
    }

    public int getPost_ID() {
        return post_ID;
    }

    public void setPost_ID(int post_ID) {
        this.post_ID = post_ID;
    }

    public int getDepartment_ID() {
        return department_ID;
    }

    public void setDepartment_ID(int department_ID) {
        this.department_ID = department_ID;
    }

    public int getPersonalSalary() {
        return personalSalary;
    }

    public void setPersonalSalary(int personalSalary) {
        this.personalSalary = personalSalary;
    }
}
