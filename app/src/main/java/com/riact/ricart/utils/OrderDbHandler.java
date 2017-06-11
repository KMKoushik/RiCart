package com.riact.ricart.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by koushik on 11/6/17.
 */

public class OrderDbHandler extends SQLiteOpenHelper {

    public static final int DB_VERSION=1;

    public static final String DATABASE_NAME = "RiactDB";
    public static final String TABLE_ORDER="orders";

    public static final String KEY_DATE = "date";
    public static final String KEY_ORDER = "order";


    public OrderDbHandler(Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTableQuerry="CREATE TABLE " + TABLE_ORDER + "("
                + KEY_DATE + "  TEXT," + KEY_ORDER + " TEXT"+")";
        db.execSQL(createTableQuerry);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDER);
        // Create tables again
        onCreate(db);
    }

    public void addOrder(String date,String orderData)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_DATE,date);
        values.put(KEY_ORDER,orderData);
        db.insert(TABLE_ORDER, null, values);
        db.close();
    }

    public List<String> getOrder(String date)
    {
        String selectQuery = "SELECT  * FROM " + TABLE_ORDER+" WHERE date="+date;
        SQLiteDatabase db = this.getWritableDatabase();
        List<String> order=new ArrayList<>();
        try {
            Cursor cursor = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    // Adding contact to list
                    order.add(cursor.getString(1));
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


}
