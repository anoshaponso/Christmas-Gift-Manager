package com.anosh.christmasgiftmanager.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.anosh.christmasgiftmanager.Classes.Gift_Class;
import com.anosh.christmasgiftmanager.Classes.Group_Class;
import com.anosh.christmasgiftmanager.Classes.Person_Class;
import com.anosh.christmasgiftmanager.Classes.Store_Class;

import java.util.ArrayList;

/**
 * Created by Anosh on 11/17/2015.
 */
public class DataSource {
    int age, dayCount;
    private SQLiteDatabase database;
    private DBClass dbHelper;
    private String[] allColumnsPersons = {DBClass.COLUMN_ID_PERSON, DBClass.COLUMN_PERSON_NAME, DBClass.COLUMN_PERSON_GROUP,
            DBClass.COLUMN_PERSON_BUDGET, DBClass.COLUMN_PERSON_IMAGE, DBClass.COLUMN_PERSON_CONTACT};
    private String[] allColumnsGifts = {DBClass.COLUMN_ID_GIFT, DBClass.COLUMN_GIFT_NAME, DBClass.COLUMN_PERSON_ID,
            DBClass.COLUMN_GIFT_PRICE, DBClass.COLUMN_GIFT_NOTE, DBClass.COLUMN_GIFT_STATUS, DBClass.COLUMN_GIFT_STORE};
    private String[] allColumnsStores = {DBClass.COLUMN_ID_STORE, DBClass.COLUMN_STORE_NAME};
    private String[] allColumnsGroups = {DBClass.COLUMN_ID_GROUP, DBClass.COLUMN_GROUP_NAME, DBClass.COLUMN_GROUP_BUDGET, DBClass.COLUMN_GROUP_IMAGE};

    public DataSource(Context context) {
        dbHelper = new DBClass(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }


    //Birthday Table
    public Person_Class addPerson(String personName, String imagepath, float budget, String contact, int group) {
        ContentValues values = new ContentValues();
        values.put(DBClass.COLUMN_PERSON_NAME, personName);
        values.put(DBClass.COLUMN_PERSON_GROUP, group);
        values.put(DBClass.COLUMN_PERSON_IMAGE, imagepath);
        values.put(DBClass.COLUMN_PERSON_BUDGET, budget);
        values.put(DBClass.COLUMN_PERSON_CONTACT, contact);
        long insertId = database.insert(DBClass.TABLE_PERSON, null,
                values);
        Cursor cursor = database.query(DBClass.TABLE_PERSON,
                allColumnsPersons, DBClass.COLUMN_ID_PERSON + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Person_Class newComment = cursorToBirthday(cursor);
        cursor.close();
        return newComment;
    }

    public void deletePerson(int id) {

        System.out.println("Comment deleted with id: " + id);
        database.delete(DBClass.TABLE_PERSON, DBClass.COLUMN_ID_PERSON
                + " = " + id, null);
    }

    public void updatePerson(int id, String personName, float budget, String imagepath) {
        ContentValues values = new ContentValues();
        values.put(DBClass.COLUMN_PERSON_BUDGET, budget);
        values.put(DBClass.COLUMN_PERSON_NAME, personName);
        values.put(DBClass.COLUMN_PERSON_IMAGE, imagepath);

        System.out.println("Comment deleted with id: " + id);
        database.update(DBClass.TABLE_PERSON, values, DBClass.COLUMN_ID_PERSON
                + " = " + id, null);
    }

    public ArrayList<Person_Class> getAllPersons() {
        ArrayList<Person_Class> persons = new ArrayList<Person_Class>();

        Cursor cursor = database.query(DBClass.TABLE_PERSON,
                allColumnsPersons, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Person_Class birthday = cursorToBirthday(cursor);
            persons.add(birthday);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return persons;
    }

    private Person_Class cursorToBirthday(Cursor cursor) {
        Person_Class person = new Person_Class();
        person.setId(cursor.getInt(0));
        person.setName(cursor.getString(1));
        person.setGroup(cursor.getInt(2));
        person.setBudget(cursor.getFloat(3));
        person.setImage(cursor.getString(4));
        person.setContact(cursor.getString(5));

        return person;
    }

    //Gift Table

    //to add values into Gift Table
    public Gift_Class createGift(String giftName, int personId, double price, String note, String status, int store) {
        ContentValues values = new ContentValues();
        values.put(DBClass.COLUMN_GIFT_NAME, giftName);
        values.put(DBClass.COLUMN_PERSON_ID, personId);
        values.put(DBClass.COLUMN_GIFT_PRICE, price);
        values.put(DBClass.COLUMN_GIFT_STATUS, status);
        values.put(DBClass.COLUMN_GIFT_NOTE, note);
        values.put(DBClass.COLUMN_GIFT_STORE, store);
        long insertId = database.insert(DBClass.TABLE_GIFT, null, values);
        Cursor cursor = database.query(DBClass.TABLE_GIFT,
                allColumnsGifts, DBClass.COLUMN_ID_GIFT + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Gift_Class newGift = cursorToGift(cursor);
        cursor.close();
        return newGift;
    }

    //to delete a value in gift table
    public void deleteGift(int id) {

        System.out.println("Comment deleted with id: " + id);
        database.delete(DBClass.TABLE_GIFT, DBClass.COLUMN_ID_GIFT
                + " = " + id, null);
    }

    public void updateGift(int id, String giftName, double price, String note, String isBought, int store) {
        ContentValues values = new ContentValues();
        values.put(DBClass.COLUMN_GIFT_NAME, giftName);

        values.put(DBClass.COLUMN_GIFT_PRICE, price);
        values.put(DBClass.COLUMN_GIFT_STATUS, isBought);
        values.put(DBClass.COLUMN_GIFT_NOTE, note);
        values.put(DBClass.COLUMN_GIFT_STORE, store);

        System.out.println("Comment deleted with id: " + id);
        database.update(DBClass.TABLE_GIFT, values, DBClass.COLUMN_ID_GIFT
                + " = " + id, null);
    }

    //to get all Gifts data to a list
    public ArrayList<Gift_Class> getAllGifts() {
        ArrayList<Gift_Class> gifts = new ArrayList<Gift_Class>();

        Cursor cursor = database.query(DBClass.TABLE_GIFT,
                allColumnsGifts, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Gift_Class gift = cursorToGift(cursor);
            gifts.add(gift);
            cursor.moveToNext();
        }

        cursor.close();
        return gifts;
    }


    private Gift_Class cursorToGift(Cursor cursor) {
        Gift_Class gift = new Gift_Class();
        gift.setId(cursor.getInt(0));
        gift.setName(cursor.getString(1));
        gift.setPersonId(cursor.getInt(2));
        gift.setPrice(cursor.getDouble(3));
        gift.setNote(cursor.getString(4));
        gift.setStatus(cursor.getString(5));
        gift.setStore(cursor.getInt(6));

        return gift;
    }


    //Store Table

    //to add values into Store Table
    public Store_Class createStore(String storeName) {
        ContentValues values = new ContentValues();
        values.put(DBClass.COLUMN_STORE_NAME, storeName);

        long insertId = database.insert(DBClass.TABLE_STORE, null, values);
        Cursor cursor = database.query(DBClass.TABLE_STORE,
                allColumnsStores, DBClass.COLUMN_ID_STORE + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Store_Class newStore = cursorToStore(cursor);
        cursor.close();
        return newStore;
    }

    private Store_Class cursorToStore(Cursor cursor) {
        Store_Class store = new Store_Class();
        store.setId(cursor.getInt(0));
        store.setName(cursor.getString(1));
        return store;
    }

    public ArrayList<Store_Class> getAllStores() {
        ArrayList<Store_Class> stores = new ArrayList<Store_Class>();

        Cursor cursor = database.query(DBClass.TABLE_STORE,
                allColumnsStores, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Store_Class store = cursorToStore(cursor);
            stores.add(store);
            cursor.moveToNext();
        }

        cursor.close();
        return stores;
    }

    //Group_View Table

    //to add values into Group_View Table
    public Group_Class createGroup(String groupName, float budget, String image) {
        ContentValues values = new ContentValues();
        values.put(DBClass.COLUMN_GROUP_NAME, groupName);
        values.put(DBClass.COLUMN_GROUP_BUDGET, budget);
        values.put(DBClass.COLUMN_GROUP_IMAGE, image);

        long insertId = database.insert(DBClass.TABLE_GROUP, null, values);
        Cursor cursor = database.query(DBClass.TABLE_GROUP,
                allColumnsGroups, DBClass.COLUMN_ID_GROUP + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Group_Class newGroup = cursorToGroup(cursor);
        cursor.close();
        return newGroup;
    }

    private Group_Class cursorToGroup(Cursor cursor) {
        Group_Class group = new Group_Class();
        group.setId(cursor.getInt(0));
        group.setName(cursor.getString(1));
        group.setImage(cursor.getString(3));
        group.setBudget(cursor.getFloat(2));
        return group;
    }

    public void deleteGroup(int id) {

        System.out.println("Comment deleted with id: " + id);
        database.delete(DBClass.TABLE_GROUP, DBClass.COLUMN_ID_GROUP
                + " = " + id, null);
    }

    public void updateGroup(int id, float budget) {
        ContentValues values = new ContentValues();
        values.put(DBClass.COLUMN_GROUP_BUDGET, budget);


        System.out.println("Comment updated with id: " + id);
        database.update(DBClass.TABLE_GROUP, values, DBClass.COLUMN_ID_GROUP
                + " = " + id, null);
    }

    public ArrayList<Group_Class> getAllGroups() {
        ArrayList<Group_Class> groups = new ArrayList<Group_Class>();

        Cursor cursor = database.query(DBClass.TABLE_GROUP,
                allColumnsGroups, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Group_Class group = cursorToGroup(cursor);
            groups.add(group);
            cursor.moveToNext();
        }

        cursor.close();
        return groups;
    }
}
