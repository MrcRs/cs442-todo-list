package com.example.tungd.todolist;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

import java.util.ArrayList;

/**
 * Created by tungd on 5/5/2017.
 */

public class TodoAdapter extends ArrayAdapter<Todo> {
    private Activity activity;
    private ArrayList<Todo> todoList;

    public TodoAdapter(Activity activity, int resource, ArrayList<Todo> todoList) {
        super(activity, resource, todoList);
        this.activity = activity;
        this.todoList = todoList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.todo_listview, null, true);
        CheckBox checkBox = (CheckBox) rowView.findViewById(R.id.check_box);

        Todo todo = todoList.get(position);

        checkBox.setChecked(todo.isCompleted());
        checkBox.setText(todo.getName());

        return rowView;
    }
}
