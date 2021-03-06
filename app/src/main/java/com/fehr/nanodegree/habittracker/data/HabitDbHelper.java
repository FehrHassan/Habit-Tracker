package com.fehr.nanodegree.habittracker.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.fehr.nanodegree.habittracker.data.HabitContract.HabitEntry;


public class HabitDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "habittracker.db";
    private static final int DATABASE_VERSION = 1;

    String SQL_CREATE_HABITS_TABLE = "CREATE TABLE " + HabitEntry.TABLE_NAME + " ("
            + HabitEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + HabitEntry.COLUMN_NAME + " TEXT NOT NULL, "
            + HabitEntry.COLUMN_FREQUENCY + " INTEGER NOT NULL DEFAULT 0);";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + HabitEntry.TABLE_NAME;

    public HabitDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_HABITS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
