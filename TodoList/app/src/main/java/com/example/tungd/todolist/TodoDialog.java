package com.example.tungd.todolist;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Created by tungd on 5/6/2017.
 */

public class TodoDialog extends DialogFragment {
    private EditText todoName, todoDueDate, todoNotes, todoEmergency;
    private CheckBox todoIsCompleted;

    public interface TodoDialogListener {
        public void onDialogPositiveClick(DialogFragment dialogFragment);
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
        builder.setView(inflater.inflate(R.layout.create_todo_dialog, null));

        Bundle bundle = getArguments();
        todoName.setText(bundle.getString("todo_name"));
        todoDueDate.setText(bundle.getString("todo_due_date"));
        todoEmergency.setText(bundle.getString("todo_emergency"));
        todoNotes.setText(bundle.getString("todo_notes"));

        builder.setTitle("Todo")
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        todoDialogListener.onDialogPositiveClick(TodoDialog.this);
                    }
                })
                .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        return builder.create();
    }
}
