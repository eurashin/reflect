package com.example.reflect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SearchView;
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

        // Open the SQLite Database
        dbManager = new DBManager(this);
        dbManager.open();

        adapter = new SimpleCursorAdapter(this,
                R.layout.food_view_cursor,
                dbManager.fetchItems(),
                new String[] {"name"},
                new int[] {R.id.food_cursor_name},
                0);
        adapter.notifyDataSetChanged();

        mListView = (ListView) findViewById(R.id.food_list_view);
        mListView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.food_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);

        return true;
    }

}
