package com.example.omarsanchez.simpletodo.model;

import com.example.omarsanchez.simpletodo.util.Priority;

import java.io.Serializable;

/**
 * Created by omarsanchez on 2/19/17.
 */

public class Task implements Serializable {
    private String taskName;
    private Priority priority;
    private String date;
    private boolean status;
    private String note;

    public Task(String taskName, String date, String note, Priority priority, boolean status) {
        this.taskName = taskName;
        this.priority = priority;
        this.date = date;
        this.status = status;
        this.note = note;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isDone() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getStatus() {
        if (isDone()) {
            return "Done";
        } else {
            return "To do";
        }
    }
}
