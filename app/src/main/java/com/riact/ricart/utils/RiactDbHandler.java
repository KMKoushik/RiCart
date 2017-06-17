package com.riact.ricart.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by koushik on 4/6/17.
 */

public class RiactDbHandler extends SQLiteOpenHelper {

    public static final int DB_VERSION=1;

    public static final String DATABASE_NAME = "RiactDB";
    public static final String TABLE_USER="users";
    public static final String KEY_NAME = "name";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_EMAIL="email";

    public static final String TABLE_ORDER="myorder";
    public static final String KEY_DATE = "date";
    public static final String KEY_ORDER = "orders";
    public static final String KEY_TOTAL = "total";

    public RiactDbHandler(Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuerry="CREATE TABLE " + TABLE_USER + "("
                + KEY_NAME + "  TEXT," + KEY_PHONE + " TEXT,"
                + KEY_ADDRESS + " TEXT," + KEY_EMAIL+" TEXT"+")";
        db.execSQL(createTableQuerry);

        createTableQuerry="CREATE TABLE " + TABLE_ORDER + "("
                + KEY_DATE + "  TEXT," + KEY_ORDER + " TEXT,"+ KEY_TOTAL+" TEXT"+")";
        db.execSQL(createTableQuerry);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDER);
        // Create tables again
        onCreate(db);

    }

    public void addUsers(String name,String phone,String address,String email)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME,name);
        values.put(KEY_PHONE,phone);
        values.put(KEY_ADDRESS,address);
        values.put(KEY_EMAIL,email);
        db.insert(TABLE_USER, null, values);
        db.close();
    }

    public List<String> getUser() {
        List<String> user = new ArrayList<String>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_USER;
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            Cursor cursor = db.rawQuery(selectQuery, null);
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {

                    user.add(cursor.getString(0));
                    user.add(cursor.getString(1));
                    user.add(cursor.getString(2));
                } while (cursor.moveToNext());
            }
        }
        catch (Exception e)
        {

        }
        finally {
            db.close();
        }

        return user;
    }

    public void addOrder(String date,String orderData,String total)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_DATE,date);
        values.put(KEY_ORDER,orderData);
        values.put(KEY_TOTAL,total);
        db.insert(TABLE_ORDER, null, values);
        //String query="INSERT INTO myorder(date,orders) VALUES ('today','poda naye')";
        //db.execSQL(query);
        db.close();
    }

    public List<String> getOrder(String date)
    {
        String selectQuery = "SELECT  * FROM " + TABLE_ORDER+" WHERE date='"+date+"'";
        SQLiteDatabase db = this.getWritableDatabase();
        List<String> order=new ArrayList<>();
        try {
            Cursor cursor = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    // Adding contact to list
                    order.add(cursor.getString(1));
                    order.add(cursor.getString(2));
                } while (cursor.moveToNext());
            }
        }
        catch (Exception e)
        {

        }
        finally {
            db.close();
        }

        return order;
    }

    public List<String> getAllOrder()
    {
        //get all orders here
        return null;
    }


}