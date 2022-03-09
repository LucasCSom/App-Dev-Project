package com.example.appdevproject;

import java.util.Calendar;

public class Task {

    private int taskID;
    private String taskName;
    private String notes;
    private String priority;
    private Calendar date;
    private int priority_weight;

    public Task() {
        taskID = -1;
        date = Calendar.getInstance();
    }
    public String getTaskName() {
        return taskName;
    }

    public int getPriority_weight() {
        return priority_weight;
    }

    public void setPriority_weight(int priority_weight) {
        this.priority_weight = priority_weight;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public int getTaskID() {
        return taskID;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }
}
