package com.riact.ricart.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
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
    public static final String KEY_ISSUBMITTED="issubmitted";

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
                + KEY_DATE + "  TEXT," + KEY_ORDER + " TEXT,"+ KEY_TOTAL+" TEXT,"+ KEY_ISSUBMITTED+" TEXT"+")";
        db.execSQL(createTableQuerry);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDER);
        // Create tables again

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
                    user.add(cursor.getString(3));
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

    public void deleteUser()
    {
        String createTableQuerry="CREATE TABLE " + TABLE_USER + "("
                + KEY_NAME + "  TEXT," + KEY_PHONE + " TEXT,"
                + KEY_ADDRESS + " TEXT," + KEY_EMAIL+" TEXT"+")";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDER);
        db.execSQL(createTableQuerry);
        createTableQuerry="CREATE TABLE " + TABLE_ORDER + "("
                + KEY_DATE + "  TEXT," + KEY_ORDER + " TEXT,"+ KEY_TOTAL+" TEXT,"+ KEY_ISSUBMITTED+" TEXT"+")";
        db.execSQL(createTableQuerry);
        db.close();


    }

    public void addOrder(String date,String orderData,String total,String isSubmitted)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_DATE,date);
        values.put(KEY_ORDER,orderData);
        values.put(KEY_TOTAL,total);
        values.put(KEY_ISSUBMITTED,isSubmitted);
        db.insert(TABLE_ORDER, null, values);
        db.close();
    }
    public void updateSubmittedStatus(String date,String submitted)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ISSUBMITTED,submitted);
        db.update(TABLE_ORDER, values, KEY_DATE+"='"+date+"'", null);

    }

    public String getOrder(String date)
    {
        String selectQuery = "SELECT  * FROM " + TABLE_ORDER+" WHERE date='"+date+"'";
        SQLiteDatabase db = this.getWritableDatabase();
        String order=new String ();
        try {
            Cursor cursor = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    // Adding contact to list
                    order=cursor.getString(1);
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

    public List<List> getAllOrder()
    { String selectQuery = "SELECT  * FROM " + TABLE_ORDER;
        SQLiteDatabase db = this.getWritableDatabase();
        List<List> order=new ArrayList<>();
        try {
            Cursor cursor = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    // Adding contact to list

                    List list=new ArrayList();
                    list.add(cursor.getString(0));
                    list.add(cursor.getString(1));
                    list.add(cursor.getString(2));
                    list.add(cursor.getString(3));
                    order.add(list);
                } while (cursor.moveToNext());
            }
        }
        catch (Exception e)
        {
e.printStackTrace();
        }
        finally {
            db.close();
        }

        return order;
    }

    public void deleteOrder(String date)
    {
        String deleteQuer="DELETE  FROM "+TABLE_ORDER+" WHERE "+KEY_DATE+" ='"+date+"'";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(deleteQuer);
        db.close();
    }

    public void updateOrder(String date,String data,String amount)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ORDER,data);
        values.put(KEY_TOTAL,amount);
        db.update(TABLE_ORDER, values, KEY_DATE+"='"+date+"'", null);

    }

    public long getSubmittedCount()
    {
        String selectQuery = "SELECT COUNT(*) FROM " + TABLE_ORDER+" WHERE "+KEY_ISSUBMITTED+" = 'true'";
        SQLiteDatabase db = this.getWritableDatabase();

        long numRows = DatabaseUtils.longForQuery(db, selectQuery, null);
        db.close();

        return numRows;
    }
    public long getSavedCount()
    {
        String selectQuery = "SELECT COUNT(*) FROM " + TABLE_ORDER+" WHERE "+KEY_ISSUBMITTED+" = 'false'";
        SQLiteDatabase db = this.getWritableDatabase();
        long numRows = DatabaseUtils.longForQuery(db, selectQuery, null);
        db.close();
        return numRows;
    }


}
