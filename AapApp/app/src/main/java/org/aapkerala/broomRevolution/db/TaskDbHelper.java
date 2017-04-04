package org.aapkerala.broomRevolution.db;

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
        String createTable = "CREATE TABLE " + TaskContract.TaskEntry.TABLE + " ( " + TaskContract.TaskEntry.NUMBER + " INTEGER PRIMARY KEY);";
        String createTable1="CREATE TABLE "+TaskContract.TaskEntry.TABLE1+" ( "+TaskContract.TaskEntry.ID + " TEXT PRIMARY KEY, "+TaskContract.TaskEntry.STATUS + " TEXT, "+
                TaskContract.TaskEntry.NAME + " TEXT, "+TaskContract.TaskEntry.DISTRICT + " TEXT, "+TaskContract.TaskEntry.PC + " TEXT, "+
                TaskContract.TaskEntry.LAC + " TEXT, "+TaskContract.TaskEntry.WARD + " TEXT, "+TaskContract.TaskEntry.BOOTH + " TEXT, "+TaskContract.TaskEntry.SECTOR + " TEXT, "+TaskContract.TaskEntry.PINCODE + " TEXT, "+TaskContract.TaskEntry.GENDER + " TEXT);";
        db.execSQL(createTable);
        db.execSQL(createTable1);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TaskContract.TaskEntry.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TaskContract.TaskEntry.TABLE1);
        onCreate(db);
    }
}
