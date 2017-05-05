package com.example.tungd.todolist;

import java.util.Date;

/**
 * Created by tungd on 5/5/2017.
 */

public class Todo {
    private String name, notes;
    private Date dueDate;
    private int emergency;
    private boolean isCompleted;

    public Todo(String name, String notes, Date dueDate, int emergency, boolean isCompleted) {
        this.name = name;
        this.notes = notes;
        this.dueDate = dueDate;
        this.emergency = emergency;
        this.isCompleted = isCompleted;
    }

    public String getName() {
        return name;
    }

    public String getNotes() {
        return notes;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public int getEmergency() {
        return emergency;
    }

    public boolean isCompleted() {
        return isCompleted;
    }
}
