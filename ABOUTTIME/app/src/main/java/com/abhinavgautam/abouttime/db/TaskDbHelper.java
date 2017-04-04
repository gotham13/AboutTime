package com.abhinavgautam.abouttime.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by New on 18-10-2016.
 */
public class TaskDbHelper extends SQLiteOpenHelper {

    public TaskDbHelper(Context context) {
        super(context, TaskContract.DB_NAME, null, TaskContract.DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TaskContract.TaskEntry.TABLE + " ( " + TaskContract.TaskEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TaskContract.TaskEntry.COL_DATE+ " INTEGER, " + TaskContract.TaskEntry.COL_NOTI+ " INTEGER DEFAULT 0, "+TaskContract.TaskEntry.COL_TASK_TITLE + " TEXT NOT NULL);";
        String createTablen = "CREATE TABLE " + TaskContract.TaskEntry.TABLEN + " ( " + TaskContract.TaskEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TaskContract.TaskEntry.COL_INITIME+ " INTEGER, " + TaskContract.TaskEntry.COL_NOTI+ " INTEGER DEFAULT 0, "+TaskContract.TaskEntry.COL_TASK_TITLE + " TEXT NOT NULL);";
        String createTable0 = "CREATE TABLE " + TaskContract.TaskEntry.TABLE0 + " ( " +TaskContract.TaskEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TaskContract.TaskEntry.COL_INITIME+ " INTEGER, " + TaskContract.TaskEntry.COL_NOTI+ " INTEGER DEFAULT 0, "+ TaskContract.TaskEntry.COL_ROUTINE+ " TEXT NOT NULL);";
        String createTable1 = "CREATE TABLE " + TaskContract.TaskEntry.TABLE1 + " ( " +TaskContract.TaskEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TaskContract.TaskEntry.COL_INITIME+ " INTEGER, " + TaskContract.TaskEntry.COL_NOTI+ " INTEGER DEFAULT 0, "+ TaskContract.TaskEntry.COL_ROUTINE+ " TEXT NOT NULL);";
        String createTable2 = "CREATE TABLE " + TaskContract.TaskEntry.TABLE2 + " ( " +TaskContract.TaskEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TaskContract.TaskEntry.COL_INITIME+ " INTEGER, " + TaskContract.TaskEntry.COL_NOTI+ " INTEGER DEFAULT 0, "+ TaskContract.TaskEntry.COL_ROUTINE+ " TEXT NOT NULL);";
        String createTable3 = "CREATE TABLE " + TaskContract.TaskEntry.TABLE3 + " ( " +TaskContract.TaskEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TaskContract.TaskEntry.COL_INITIME+ " INTEGER, " + TaskContract.TaskEntry.COL_NOTI+ " INTEGER DEFAULT 0, "+ TaskContract.TaskEntry.COL_ROUTINE+ " TEXT NOT NULL);";
        String createTable4 = "CREATE TABLE " + TaskContract.TaskEntry.TABLE4 + " ( " +TaskContract.TaskEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TaskContract.TaskEntry.COL_INITIME+ " INTEGER, " + TaskContract.TaskEntry.COL_NOTI+ " INTEGER DEFAULT 0, "+ TaskContract.TaskEntry.COL_ROUTINE+ " TEXT NOT NULL);";
        String createTable5 = "CREATE TABLE " + TaskContract.TaskEntry.TABLE5 + " ( " +TaskContract.TaskEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TaskContract.TaskEntry.COL_INITIME+ " INTEGER, " + TaskContract.TaskEntry.COL_NOTI+ " INTEGER DEFAULT 0, "+ TaskContract.TaskEntry.COL_ROUTINE+ " TEXT NOT NULL);";
        String createTable6 = "CREATE TABLE " + TaskContract.TaskEntry.TABLE6 + " ( " +TaskContract.TaskEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TaskContract.TaskEntry.COL_INITIME+ " INTEGER, " + TaskContract.TaskEntry.COL_NOTI+ " INTEGER DEFAULT 0, "+ TaskContract.TaskEntry.COL_ROUTINE+ " TEXT NOT NULL);";
        db.execSQL(createTable);
        db.execSQL(createTable0);
        db.execSQL(createTable1);
        db.execSQL(createTable2);
        db.execSQL(createTable3);
        db.execSQL(createTable4);
        db.execSQL(createTable5);
        db.execSQL(createTable6);
        db.execSQL(createTablen);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TaskContract.TaskEntry.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TaskContract.TaskEntry.TABLE0);
        db.execSQL("DROP TABLE IF EXISTS " + TaskContract.TaskEntry.TABLE1);
        db.execSQL("DROP TABLE IF EXISTS " + TaskContract.TaskEntry.TABLE2);
        db.execSQL("DROP TABLE IF EXISTS " + TaskContract.TaskEntry.TABLE3);
        db.execSQL("DROP TABLE IF EXISTS " + TaskContract.TaskEntry.TABLE4);
        db.execSQL("DROP TABLE IF EXISTS " + TaskContract.TaskEntry.TABLE5);
        db.execSQL("DROP TABLE IF EXISTS " + TaskContract.TaskEntry.TABLE6);
        db.execSQL("DROP TABLE IF EXISTS " + TaskContract.TaskEntry.TABLEN);
        onCreate(db);
    }
}
