package com.example.tungd.todolist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner statusSpinner = (Spinner) findViewById(R.id.status);
        Spinner prioritySpinner = (Spinner) findViewById(R.id.priority);
        statusSpinner.setOnItemSelectedListener(this);
        prioritySpinner.setOnItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.customized_toolbar, menu);
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
