package com.anosh.christmasgiftmanager.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Anosh on 11/17/2015.
 */
public class DBClass extends SQLiteOpenHelper {
    public static final String TABLE_PERSON = "person";
    public static final String COLUMN_ID_PERSON = "person_id";
    public static final String COLUMN_PERSON_NAME = "name";
    public static final String COLUMN_PERSON_BUDGET = "budget";
    public static final String COLUMN_PERSON_IMAGE = "image";
    public static final String COLUMN_PERSON_CONTACT = "contact";
    public static final String COLUMN_PERSON_GROUP = "group_id";
    public static final String TABLE_GIFT = "gift";
    public static final String COLUMN_ID_GIFT = "gift_id";
    public static final String COLUMN_GIFT_NAME = "gift_name";
    public static final String COLUMN_PERSON_ID = "person_id";
    public static final String COLUMN_GIFT_PRICE = "price";
    public static final String COLUMN_GIFT_STATUS = "status";
    public static final String COLUMN_GIFT_NOTE = "note";
    public static final String COLUMN_GIFT_STORE = "store";
    public static final String TABLE_STORE = "store";
    public static final String COLUMN_ID_STORE = "store_id";
    public static final String COLUMN_STORE_NAME = "store_name";
    public static final String TABLE_GROUP = "group_details";
    public static final String COLUMN_ID_GROUP = "group_id";
    public static final String COLUMN_GROUP_NAME = "group_name";
    public static final String COLUMN_GROUP_IMAGE = "image";
    public static final String COLUMN_GROUP_BUDGET = "budget";
    private static final String DATABASE_NAME = "ChristmasGifts";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_PERSON_CREATE = "create table "
            + TABLE_PERSON + "(" + COLUMN_ID_PERSON
            + " integer primary key autoincrement, " + COLUMN_PERSON_NAME
            + " text not null," + COLUMN_PERSON_GROUP
            + " integer not null," + COLUMN_PERSON_BUDGET + " real,"
            + COLUMN_PERSON_IMAGE + " text," + COLUMN_PERSON_CONTACT + " text);";
    private static final String TABLE_GIFT_CREATE = "create table "
            + TABLE_GIFT + "(" + COLUMN_ID_GIFT
            + " integer primary key autoincrement, " + COLUMN_GIFT_NAME
            + " text not null," + COLUMN_PERSON_ID + " integer not null,"
            + COLUMN_GIFT_PRICE + " real not null,"
            + COLUMN_GIFT_STATUS + " text,"
            + COLUMN_GIFT_NOTE + " text,"
            + COLUMN_GIFT_STORE + " int);";
    private static final String TABLE_STORE_CREATE = "create table "
            + TABLE_STORE + "(" + COLUMN_ID_STORE
            + " integer primary key autoincrement, " + COLUMN_STORE_NAME
            + " text not null);";
    private static final String TABLE_GROUP_CREATE = "create table "
            + TABLE_GROUP + "(" + COLUMN_ID_GROUP
            + " integer primary key autoincrement, " + COLUMN_GROUP_NAME
            + " text not null," + COLUMN_GROUP_IMAGE + " text," + COLUMN_GROUP_BUDGET + " real);";

    public DBClass(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static void add(SQLiteDatabase db) {
        db.execSQL("insert into group_details (group_name,image,budget) values('Friends','',10)");
        db.execSQL("insert into person (name,image,budget,group_id,contact) values ('Friend','',10,1,'')");
        db.execSQL("insert into store (store_name) values ('Amazon'),('eBay'),('Asos'),('Walmart'),('Etsy'),('MR PORTER'),('Alibaba.com'),('NASTY GAL'),('Zappos'),('ModCloth');");
        db.execSQL("insert into gift (gift_name,person_id,price,status,note,store) values('Gift1',1,10,'Not purchased','',1)");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_PERSON_CREATE);
        db.execSQL(TABLE_GIFT_CREATE);
        db.execSQL(TABLE_STORE_CREATE);
        db.execSQL(TABLE_GROUP_CREATE);
        add(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PERSON_CREATE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GIFT_CREATE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STORE_CREATE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GROUP_CREATE);
        add(db);
        onCreate(db);
    }
}
