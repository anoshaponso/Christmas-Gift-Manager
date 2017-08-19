package com.anosh.christmasgiftmanager.Classes;

/**
 * Created by Anosh on 11/17/2015.
 */
public class Person_Class extends Parrent_Class {
    int group;
    float budget;
    String contact;


    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public float getBudget() {
        return budget;
    }

    public void setBudget(float budget) {
        this.budget = budget;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }
}
