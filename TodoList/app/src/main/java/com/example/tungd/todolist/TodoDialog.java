package com.example.tungd.todolist;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by tungd on 5/6/2017.
 */

public class TodoDialog extends DialogFragment {
    private EditText todoName, todoDueDate, todoNotes, todoEmergency;
    private CheckBox todoIsCompleted;
    View dialogView;
    Todo todo;

    public interface TodoDialogListener {
        public void onDialogPositiveClick(DialogFragment dialogFragment, Todo todo, int pos);

        public void onDialogNegativeClick(DialogFragment dialogFragment, Todo todo, int pos);
    }

    TodoDialogListener todoDialogListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            todoDialogListener = (TodoDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement TodoDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        dialogView = inflater.inflate(R.layout.create_todo_dialog, null);
        builder.setView(dialogView);

        initLayoutObject();

        final Bundle bundle = getArguments();
        String a = bundle.getString("todo_name");
        todoName.setText(bundle.getString("todo_name"));
        final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy", Locale.US);
        todoDueDate.setText(sdf.format(new Date(bundle.getLong("todo_due_date"))));
        todoEmergency.setText("" + bundle.getInt("todo_emergency"));
        todoNotes.setText(bundle.getString("todo_notes"));
        todoIsCompleted.setChecked(bundle.getBoolean("todo_is_completed"));

        final Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

            private void updateLabel() {
                todoDueDate.setText(sdf.format(myCalendar.getTime()));
            }
        };

        todoDueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(), date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        builder.setTitle("Todo");
        if (bundle.getBoolean("is_created")) {
            builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    todo = new Todo(todoName.getText().toString(),
                            todoNotes.getText().toString(),
                            new Date(todoDueDate.getText().toString()),
                            Integer.parseInt(todoEmergency.getText().toString()),
                            todoIsCompleted.isChecked());
                    todoDialogListener.onDialogPositiveClick(TodoDialog.this, todo, -1);

                }
            });

            builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    todoDialogListener.onDialogNegativeClick(TodoDialog.this, null, -1);

                }
            });
        } else {
            builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    todo = new Todo(todoName.getText().toString(),
                            todoNotes.getText().toString(),
                            new Date(todoDueDate.getText().toString()),
                            Integer.parseInt(todoEmergency.getText().toString()),
                            todoIsCompleted.isChecked());
                    todoDialogListener.onDialogPositiveClick(TodoDialog.this, todo, bundle.getInt("pos"));

                }
            });

            builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    todoDialogListener.onDialogNegativeClick(TodoDialog.this, null, bundle.getInt("pos"));

                }
            });
        }
        return builder.create();
    }

    

    private void initLayoutObject() {

        todoName = (EditText) dialogView.findViewById(R.id.todo_name);
        todoDueDate = (EditText) dialogView.findViewById(R.id.todo_due_date);
        todoEmergency = (EditText) dialogView.findViewById(R.id.todo_emergency);
        todoNotes = (EditText) dialogView.findViewById(R.id.todo_notes);
        todoIsCompleted = (CheckBox) dialogView.findViewById(R.id.is_completed);
    }

    private Bundle createBundle(String todoName, String todoDueDate, int todoEmergency, String todoNotes, boolean todoIsCompleted) {
        Bundle bundle = new Bundle();
        bundle.putString("todo_name", todoName);
        bundle.putString("todo_due_date", todoDueDate);
        bundle.putInt("todo_emergency", todoEmergency);
        bundle.putString("todo_notes", todoNotes);
        bundle.putBoolean("todo_is_completed", todoIsCompleted);
        return bundle;
    }

}
