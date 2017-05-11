package com.example.tungd.todolist;

import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, TodoDialog.TodoDialogListener {
    TodoAdapter adapter;
    Spinner statusSpinner, prioritySpinner;
    ListView todoListView;
    public ArrayList<Todo> todoArrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        readJsonFile();
        adapter = new TodoAdapter(this, R.layout.todo_listview, todoArrayList);
//        initAdapter();
        initLayoutObject();

        updateSpinner();

        todoListView.setAdapter(adapter);
    }

    private void updateSpinner() {
        statusSpinner.setOnItemSelectedListener(this);
        prioritySpinner.setOnItemSelectedListener(this);
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
                DialogFragment dialogFragment = new TodoDialog();

                Bundle bundle = createBundle(" ", 0, 10, " ", false);
                bundle.putBoolean("is_created", true);
                dialogFragment.setArguments(bundle);
                dialogFragment.show(getSupportFragmentManager(), "Todo");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private Bundle createBundle(String todoName, long todoDueDate, int todoEmergency, String todoNotes, boolean todoIsCompleted) {
        Bundle bundle = new Bundle();
        bundle.putString("todo_name", todoName);
        bundle.putLong("todo_due_date", todoDueDate);
        bundle.putInt("todo_emergency", todoEmergency);
        bundle.putString("todo_notes", todoNotes);
        bundle.putBoolean("todo_is_completed", todoIsCompleted);
        return bundle;
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

    @Override
    public void onDialogPositiveClick(DialogFragment dialogFragment, Todo todo, int pos) {
        if (pos == -1) {
            todoArrayList.add(todo);
            writeJsonFile();
            adapter.notifyDataSetChanged();
        } else {
            todoArrayList.remove(pos);
            todoArrayList.add(pos, todo);
            writeJsonFile();
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialogFragment, Todo todo, int pos) {

    }

    public void readJsonFile() {
        String jsonStr = "";
        FileInputStream fis = null;
        try {
            fis = openFileInput("data.json");
            byte[] buffer = new byte[1024];
            while (fis.read(buffer) != -1) {
                jsonStr += new String(buffer, "UTF-8");
            }
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONArray jsonArray;
        try {
            jsonArray = new JSONArray(jsonStr);
            if (todoArrayList.size() != 0) {
                todoArrayList.clear();
            }
            for (int i = 0; i < jsonArray.length(); ++i) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Todo todo = new Todo(
                        jsonObject.getString("todo_name"),
                        jsonObject.getString("todo_note"),
                        new Date(jsonObject.getInt("todo_due_date")),
                        jsonObject.getInt("todo_emergency"),
                        jsonObject.getBoolean("todo_is_completed"));
                todoArrayList.add(todo);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void writeJsonFile() {
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < todoArrayList.size(); ++i) {
            Todo todo = todoArrayList.get(i);
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("todo_name", todo.getName());
                jsonObject.put("todo_note", todo.getNotes());
                jsonObject.put("todo_due_date", todo.getDueDate().getTime());
                jsonObject.put("todo_emergency", todo.getEmergency());
                jsonObject.put("todo_is_completed", todo.isCompleted());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            jsonArray.put(jsonObject);
        }
        String str = jsonArray.toString();

        try {
            FileOutputStream fos = openFileOutput("data.json", Context.MODE_PRIVATE);
            byte[] b = str.getBytes();
            fos.write(b);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
