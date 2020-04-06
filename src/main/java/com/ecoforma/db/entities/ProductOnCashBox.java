package com.ecoforma.db.entities;

public class ProductOnCashBox {
    private int productOnStoreID;
    private int productID;
    private String productName;
    private String categoryName;
    private int cost;
    private String storeName;
    private int count;

    public int getProductOnStoreID() {
        return productOnStoreID;
    }

    public void setProductOnStoreID(int productOnStoreID) {
        this.productOnStoreID = productOnStoreID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
