package com.starboardland.pedometer;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ChandlerWu on 4/3/15.
 */
public class StepDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "step.sqlite";
    private static final int VERSION = 1;

    public static final String STEP_TABLE = "step";
    private static final String COLUMN_STEP_ID = "_id";
    public static final String COLUMN_SEGMENTS = "_segment";
    public static final String COLUMN_STEP_STEPS = "_steps";

    public StepDatabaseHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // create the "step" table
        db.execSQL("create table step (" + COLUMN_STEP_ID + " integer primary key autoincrement, _segment integer, _steps integer)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {}

}
