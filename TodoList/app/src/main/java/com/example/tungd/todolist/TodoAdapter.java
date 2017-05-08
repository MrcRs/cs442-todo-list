package com.example.tungd.todolist;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.ImageButton;

import java.util.ArrayList;

/**
 * Created by tungd on 5/5/2017.
 */

public class TodoAdapter extends ArrayAdapter<Todo> {
    private MainActivity activity;
    private ArrayList<Todo> originalTodoList;
    private ArrayList<Todo> filteredTodoList;
    private Filter filter;

    public TodoAdapter(MainActivity activity, int resource, ArrayList<Todo> todoList) {
        super(activity, resource, todoList);
        this.activity = activity;
        this.filteredTodoList = todoList;
        this.originalTodoList = (ArrayList<Todo>) todoList.clone();
        filter = new TodoNameFilter();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.todo_listview, null, true);
        final Todo todo = filteredTodoList.get(position);

        CheckBox checkBox = (CheckBox) rowView.findViewById(R.id.check_box);
        checkBox.setChecked(todo.isCompleted());
        checkBox.setText(todo.getName());

        ImageButton editButton = (ImageButton) rowView.findViewById(R.id.edit_button);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialog = new TodoDialog();
                Bundle bundle = createBundle(todo.getName(), todo.getDueDate().getTime(), todo.getEmergency(), todo.getNotes(), todo.isCompleted());
                bundle.putBoolean("is_created", false);
                dialog.setArguments(bundle);
                dialog.show(activity.getSupportFragmentManager(), "Todo");
            }
        });

        ImageButton deleteButton = (ImageButton) rowView.findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });

        return rowView;
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

    @NonNull
    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new TodoNameFilter();
        }
        return filter;
    }

    private class TodoNameFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            constraint = constraint.toString().toLowerCase();
            FilterResults results = new FilterResults();
            if (constraint != null && constraint.toString().length() > 0) {
                ArrayList<Todo> originalItems = new ArrayList<>();
                ArrayList<Todo> filteredItems = new ArrayList<>();
                synchronized (this) {
                    originalItems.addAll(originalTodoList);
                }
                for (int i = 0; i < originalItems.size(); ++i) {
                    Todo todo = originalItems.get(i);
                    if (todo.getName().toLowerCase().contains(constraint)) {
                        filteredItems.add(todo);
                    }
                    results.values = filteredItems;
                    results.count = filteredItems.size();
                }
            } else {
                synchronized (this) {
                    results.values = originalTodoList;
                    results.count = originalTodoList.size();
                }
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredTodoList = (ArrayList<Todo>) results.values;
            notifyDataSetChanged();
            clear();
            for (int i = 0; i < filteredTodoList.size(); ++i) {
                add(filteredTodoList.get(i));
            }
            notifyDataSetInvalidated();

        }
    }
}
