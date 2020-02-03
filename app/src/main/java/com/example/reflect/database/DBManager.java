package com.example.reflect.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class DBManager {
    private DatabaseHelper dbHelper;
    private Context context;
    private SQLiteDatabase database;

    public DBManager(Context c) {
        context = c;
    }

    public DBManager open() throws SQLException {
        Log.d("TEST", "OPEning/.....");
        dbHelper = new DatabaseHelper(context);
        Log.d("TEST", "done");
        database = dbHelper.getWritableDatabase();

    /*
        // Fill with initial food data
        try {
            InputStream is = context.getAssets().open("foods.csv");
            BufferedReader csvReader = new BufferedReader(new InputStreamReader(is));
            String row;
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(",");
                for(int i=0; i<data.length; i++) {
                    Log.d("TEST", data[i]);
                    // ID = 0, name = 1, group = 2
                }
                this.insertItem(data[0], data[1]);
            }
            csvReader.close();
        }
        catch (java.io.IOException e) {
            Log.e("ERR", e.getMessage());
        }

        */
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    /************************************* FOOD ACTIONS *********************************/
    // Insert
    public void insertItem(String id, String name) {
        ContentValues contentValue = new ContentValues();
        contentValue.put("_id", id);
        contentValue.put("name", name);
        database.insert("item", null, contentValue);
    }

    public void insertRecipe(String id, String name) {
        ContentValues contentValue = new ContentValues();
        contentValue.put("name", name);
        database.insert("recipe", null, contentValue);
    }

    public void insertList(String id, String date) {
        ContentValues contentValue = new ContentValues();
        contentValue.put("id", id);
        contentValue.put("date_created", date);
        database.insert("list", null, contentValue);
    }

    public void insertItemInRecipe(String recipeName, String itemID) {
        ContentValues contentValue = new ContentValues();
        contentValue.put("item_id", itemID);
        contentValue.put("recipe_name", recipeName);
        database.insert("recipe_contains_food", null, contentValue);
    }

    public void insertItemInList(String itemID, int listID) {
        ContentValues contentValue = new ContentValues();
        contentValue.put("item_id", itemID);
        contentValue.put("list_id", listID);
        database.insert("food_in_list", null, contentValue);
    }

    // Update

    // Fetch
    public Cursor fetchRecipes() {
        String[] columns = new String[] {"name"};
        Cursor cursor = database.query("recipe", columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fetchRecipeDetails(String recipeName) {
        String[] columns = new String[] {"item_id", "amount", "unit"};
        Cursor cursor = database.query("recipe_contains_item", columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fetchItems() {
        String[] columns = new String[] {"_id", "name"};
        Cursor cursor = database.query("item", columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }


    //Delete
    public void delete_list(int id) {
        // Delete all food items from the list

        // Delete list entity
        database.delete("list", "id=" + id, null);
    }

    /*

    public int update(long _id, String name, String desc) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(com.example.reflect.database.DatabaseHelper.SUBJECT, name);
        contentValues.put(com.example.reflect.database.DatabaseHelper.DESC, desc);
        int i = database.update(com.example.reflect.database.DatabaseHelper.TABLE_NAME, contentValues, com.example.reflect.database.DatabaseHelper._ID + " = " + _id, null);
        return i;
    }


     */

}
