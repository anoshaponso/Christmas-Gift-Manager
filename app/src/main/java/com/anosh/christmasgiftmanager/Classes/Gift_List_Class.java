package com.anosh.christmasgiftmanager.Classes;

/**
 * Created by adminslt on 11/30/2014.
 */
public class Gift_List_Class {
    int id;
    String name;
    double amount;
    String status;
    int store;
    String image;

    public Gift_List_Class(int id, String name, double amount, String status, String image, int store) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.amount = amount;
        this.status = status;
        this.store = store;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getStore() {
        return store;
    }

    public void setStore(int store) {
        this.store = store;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
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

}
