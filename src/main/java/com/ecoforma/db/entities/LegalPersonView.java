package com.ecoforma.db.entities;

public class LegalPersonView {
    private int ID;
    private String name;
    private String adress;
    private String phoneNumber;
    private String contractName;
    private String endOfContract;

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

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getEndOfContract() {
        return endOfContract;
    }

    public void setEndOfContract(String endOfContract) {
        this.endOfContract = endOfContract;
    }
}
