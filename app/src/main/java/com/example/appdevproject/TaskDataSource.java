package com.example.appdevproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.sql.SQLException;
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
    public Task getSpecificContact(int taskId) {
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

}
