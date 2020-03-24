package com.ecoforma.entities;

public class Product {
    private int ID;
    private String name;
    private String characteristics;
    private int productCategory_ID;
    private int cost;

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

    public String getCharacteristics() {
        return characteristics;
    }

    public void setCharacteristics(String characteristics) {
        this.characteristics = characteristics;
    }

    public int getProductCategory_ID() {
        return productCategory_ID;
    }

    public void setProductCategory_ID(int productCategory_ID) {
        this.productCategory_ID = productCategory_ID;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
