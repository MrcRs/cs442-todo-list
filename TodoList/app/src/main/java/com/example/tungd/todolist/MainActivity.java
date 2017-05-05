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
import android.widget.Toast;

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

        statusSpinner = (Spinner) findViewById(R.id.status);
        prioritySpinner = (Spinner) findViewById(R.id.priority);
        statusSpinner.setOnItemSelectedListener(this);
        prioritySpinner.setOnItemSelectedListener(this);

        todoListView = (ListView) findViewById(R.id.todo_list);
        initAdapter();
        todoListView.setAdapter(adapter);

    }

    private void initAdapter() {
        adapter = new TodoAdapter(this, R.layout.customized_listview, new ArrayList<Todo>());
        for (int i = 0;i < 5;++i) {
            adapter.add(new Todo("name" + i, "notes" + i, new Date(2017, 5, 7), i, i % 2 == 0));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.customized_toolbar, menu);
        MenuItem searchItem = menu.findItem(R.id.search_button);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);

                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_button:
                Toast.makeText(getApplicationContext(), "Click add button", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.search_button:
                Toast.makeText(getApplicationContext(), "Click search button", Toast.LENGTH_SHORT).show();
                return true;
        }
        return false;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner = (Spinner) parent;
        switch (spinner.getId()) {
            case R.id.status:
//                update list when select status
                Toast.makeText(getApplicationContext(), spinner.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
                break;

            case R.id.priority:
//                updaet list when select priority
                Toast.makeText(getApplicationContext(), spinner.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
