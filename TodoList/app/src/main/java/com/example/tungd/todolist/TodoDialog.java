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
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Created by tungd on 5/6/2017.
 */

public class TodoDialog extends DialogFragment {
    private EditText todoName, todoDueDate, todoNotes, todoEmergency;
    private CheckBox todoIsCompleted;
    View dialogView;

    public interface TodoDialogListener {
        public void onDialogPositiveClick(DialogFragment dialogFragment, boolean isCreated);

        public void onDialogNegativeClick(DialogFragment dialogFragment, boolean isCreated);
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

        Bundle bundle = getArguments();
        String a = bundle.getString("todo_name");
        todoName.setText(bundle.getString("todo_name"));
        todoDueDate.setText(bundle.getString("todo_due_date"));
        todoEmergency.setText(bundle.getString("todo_emergency"));
        todoNotes.setText(bundle.getString("todo_notes"));

        builder.setTitle("Todo");
        if (bundle.getBoolean("isCreated")) {
            builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    todoDialogListener.onDialogPositiveClick(TodoDialog.this, true);

                }
            });

            builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    todoDialogListener.onDialogNegativeClick(TodoDialog.this, true);

                }
            });
        } else {
            builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    todoDialogListener.onDialogPositiveClick(TodoDialog.this, false);

                }
            });

            builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    todoDialogListener.onDialogNegativeClick(TodoDialog.this, false);

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
    }
}
