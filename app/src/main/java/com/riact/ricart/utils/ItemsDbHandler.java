package com.riact.ricart.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by koushik on 10/6/17.
 */

public class ItemsDbHandler extends SQLiteOpenHelper {

    public static final int DB_VERSION=1;

    public static final String DATABASE_NAME = "RiactDB";
    public static final String TABLE_ITEM="items";

    public static final String KEY_ITEM = "item";


    public ItemsDbHandler(Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuerry="CREATE TABLE " + TABLE_ITEM + "("
                + KEY_ITEM + "  TEXT," +")";
        db.execSQL(createTableQuerry);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEM);

        // Create tables again
        onCreate(db);

    }

    public void addItem(String item)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ITEM,item);

        db.insert(TABLE_ITEM, null, values);
        db.close();
    }

    public List<String> getItems() {
        List<String> item = new ArrayList<String>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_ITEM;
        SQLiteDatabase db = this.getWritableDatabase();

        try {


            Cursor cursor = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {


                    // Adding contact to list
                    item.add(cursor.getString(0));
                } while (cursor.moveToNext());
            }
        }
        catch (Exception e)
        {

        }
        finally {
            db.close();
        }

        return item;
    }

}
