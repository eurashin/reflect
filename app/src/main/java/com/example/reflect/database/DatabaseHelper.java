package com.example.reflect.database;/*
Creates the entire SQL database framework upon starting the app.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class DatabaseHelper extends SQLiteOpenHelper {
    /************************************* FOOD DATABASE *************************************/
    // Information
    static final String DB_NAME = "REFLECT_FOODS.DB";
    static final int DB_VERSION = 4;

    // Entities
    private static final String CREATE_ITEM_TABLE =
            "create table item(" +
                    "_id TEXT PRIMARY KEY, " +
                    "name TEXT NOT NULL);";

    private static final String CREATE_RECIPE_TABLE =
            "create table recipe(" +
                    "name TEXT PRIMARY KEY);";

    private static final String CREATE_LIST_TABLE =
            "create table list(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "date_created TEXT NOT NULL);";

    // Relationships
    private static final String CREATE_CONTAINS_RELATION =
            "CREATE TABLE recipe_contains_item(" +
                    "recipe_name TEXT NOT NULL, " +
                    "item_id TEXT NOT NULL, " +
                    "amount FLOAT," +
                    "unit TEXT," +
                    "FOREIGN KEY(recipe_name) REFERENCES recipe(name), " +
                    "FOREIGN KEY(item_id) REFERENCES item(id), " +
                    "PRIMARY KEY(recipe_name, item_id));";

    private static final String CREATE_IN_LIST_RELATION =
            "create table item_in_list(" +
                    "list_id INTEGER NOT NULL, " +
                    "item_id TEXT NOT NULL, " +
                    "FOREIGN KEY(list_id) REFERENCES list(id), " +
                    "FOREIGN KEY(item_id) REFERENCES item(id), " +
                    "PRIMARY KEY(list_id, item_id));";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create REFLECT_FOODS.DB
        db.execSQL(CREATE_ITEM_TABLE);
        db.execSQL(CREATE_LIST_TABLE);
        db.execSQL(CREATE_RECIPE_TABLE);
        db.execSQL(CREATE_CONTAINS_RELATION);
        db.execSQL(CREATE_IN_LIST_RELATION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS recipe_contains_item"); // DROP THE FOOD TABLES
        db.execSQL("DROP TABLE IF EXISTS item_in_list;");
        db.execSQL("DROP TABLE IF EXISTS recipe;");
        db.execSQL("DROP TABLE IF EXISTS item;");
        db.execSQL("DROP TABLE IF EXISTS list;");

        onCreate(db);
    }
}
