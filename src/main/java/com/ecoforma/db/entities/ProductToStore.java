package com.ecoforma.db.entities;

public class ProductToStore {
    private int ID;
    private int store_ID;
    private int product_ID;
    private int count;
    private byte deleted;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getStore_ID() {
        return store_ID;
    }

    public void setStore_ID(int store_ID) {
        this.store_ID = store_ID;
    }

    public int getProduct_ID() {
        return product_ID;
    }

    public void setProduct_ID(int product_ID) {
        this.product_ID = product_ID;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public byte getDeleted() {
        return deleted;
    }

    public void setDeleted(byte deleted) {
        this.deleted = deleted;
    }
}
