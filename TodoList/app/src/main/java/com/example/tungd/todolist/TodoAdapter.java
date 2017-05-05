package com.example.tungd.todolist;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Filter;

import java.util.ArrayList;

/**
 * Created by tungd on 5/5/2017.
 */

public class TodoAdapter extends ArrayAdapter<Todo> {
    private ArrayList<Todo> todoList;
    private Activity activity;
    private Filter filter;

    public TodoAdapter(Activity activity, int resource, ArrayList<Todo> todoList) {
        super(activity, resource, todoList);
        this.activity = activity;
        this.todoList = todoList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.customized_listview, null, true);
        CheckBox checkBox = (CheckBox) rowView.findViewById(R.id.checkbox);

        Todo todo = todoList.get(position);

        checkBox.setText(todo.getName());
        checkBox.setChecked(todo.isCompleted());

        return rowView;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        if (filter == null) {

        }
    }
}
