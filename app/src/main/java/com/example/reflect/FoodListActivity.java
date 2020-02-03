package com.example.reflect;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.reflect.database.DBManager;

import java.util.List;

public class FoodListActivity extends AppCompatActivity {
    private DBManager dbManager;
    private ListView mListView;
    private SimpleCursorAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        dbManager = new DBManager(this);
        dbManager.open();
        Cursor cursor = dbManager.fetchItems();


        final String[] from = new String[] {"name"};
        final int[] to = new int[] {R.id.food_cursor_name};
        adapter = new SimpleCursorAdapter(this, R.layout.food_view_cursor, cursor, from, to, 0);
        adapter.notifyDataSetChanged();

        mListView = (ListView) findViewById(R.id.food_list_view);
        mListView.setAdapter(adapter);
    }


}
