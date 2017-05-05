package com.example.tungd.todolist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    TodoAdapter adapter;
    Spinner statusSpinner, prioritySpinner;
    ListView todoListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initAdapter();
        initLayoutObject();

        updateSpinner();

        todoListView.setAdapter(adapter);
    }

    private void updateSpinner() {
        statusSpinner.setOnItemSelectedListener(this);
        prioritySpinner.setOnItemSelectedListener(this);
    }

    private void initAdapter() {
        adapter = new TodoAdapter(this, R.layout.todo_listview, new ArrayList<Todo>());
        for (int i = 0; i < 5; ++i) {
            adapter.add(new Todo("Name " + i, "Notes" + i, new Date(2017, 5, 7), i, (i % 2) == 0));
        }
    }

    private void initLayoutObject() {
        statusSpinner = (Spinner) findViewById(R.id.status);
        prioritySpinner = (Spinner) findViewById(R.id.priority);
        todoListView = (ListView) findViewById(R.id.todo_list_view);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_search_action_bar, menu);
        MenuItem searchItem = menu.findItem(R.id.search_button);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner = (Spinner) parent;
        switch (spinner.getId()) {
            case R.id.status:
                break;
            case R.id.priority:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
