package com.example.appdevproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TaskDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "mytasks.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String CREATE_TABLE_TASK =
            "create table task (_id integer primary key autoincrement, "
                    + "taskname text not null, notes text, "
                    + "priority text, date text, priority_weight integer);";

    public TaskDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_TASK);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*Log.w(ContactDBHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS contact");
        onCreate(db); */
    }

}
