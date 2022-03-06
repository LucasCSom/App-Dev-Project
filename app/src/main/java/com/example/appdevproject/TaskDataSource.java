package com.example.appdevproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

public class TaskDataSource {
    private SQLiteDatabase database;
    private TaskDBHelper dbHelper;

    public TaskDataSource(Context context) {
        dbHelper = new TaskDBHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public boolean insertTask(Task c) {
        boolean didSucceed = false;
        try {
            ContentValues initialValues = new ContentValues();

            initialValues.put("taskname", c.getTaskName());
            initialValues.put("notes", c.getNotes());
            initialValues.put("priority", c.getPriority());

            didSucceed = database.insert("task", null, initialValues) > 0;
        }
        catch (Exception e) {
            //Do nothing -will return false if there is an exception
        }
        return didSucceed;
    }

    public boolean updateTask(Task c) {
        boolean didSucceed = false;
        try {
            Long rowId = (long) c.getTaskID();
            ContentValues updateValues = new ContentValues();

            updateValues.put("taskname", c.getTaskName());
            updateValues.put("notes", c.getNotes());
            updateValues.put("priority", c.getPriority());

            didSucceed = database.update("task", updateValues, "_id=" + rowId, null) > 0;
        }
        catch (Exception e) {
            //Do nothing -will return false if there is an exception
        }
        return didSucceed;
    }
    public boolean deleteTask(int contactId) {
        boolean didDelete = false;
        try {
            didDelete = database.delete("task","_id="+contactId,null)>0;
        }
        catch (Exception e) {
            //Do nothing -return value already set to false
        }
        return didDelete;
    }
    public Task getSpecificTask(int taskId) {
        Task task = new Task();
        String query = "SELECT * FROM task WHERE _id = " + taskId;
        Cursor cursor = database.rawQuery(query,null);

        if (cursor.moveToFirst()) {
            task.setTaskID(cursor.getInt(0));
            task.setTaskName(cursor.getString(1));
            task.setNotes(cursor.getString(2));
            task.setPriority(cursor.getString(3));

            cursor.close();
        }
        return task;
    }

    public int getLastTaskID() {
        int lastId;
        try {
            String query = "Select MAX(_id) from task";
            Cursor cursor = database.rawQuery(query,null);

            cursor.moveToFirst();
            lastId = cursor.getInt(0);
            cursor.close();
        }
        catch (Exception e) {
            lastId = -1;
        }
        return lastId;
    }

    public ArrayList<Task> getTasks(String sortField, String sortOrder) {
        ArrayList<Task> tasks = new ArrayList<Task>();
        try {
            String query = "SELECT * FROM task ORDER BY "+sortField+ " "+sortOrder;
            Cursor cursor = database.rawQuery(query, null);

            Task newTask;
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                newTask = new Task();
                newTask.setTaskID(cursor.getInt(0));
                newTask.setTaskName(cursor.getString(1));
                newTask.setNotes(cursor.getString(2));
                newTask.setPriority(cursor.getString(3));
                tasks.add(newTask);
                cursor.moveToNext();
            }
            cursor.close();
        }
        catch (Exception e) {
            tasks = new ArrayList<Task>();
        }
        return tasks;
    }
}
