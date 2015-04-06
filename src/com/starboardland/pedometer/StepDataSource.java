package com.starboardland.pedometer;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.sql.SQLException;

/**
 * Created by ChandlerWu on 4/6/15.
 */
public class StepDataSource {

    private SQLiteDatabase database;
    private StepDatabaseHelper dbHelper;
    private Context mContext;

    public StepDataSource (Context context) {
        mContext = context;
        dbHelper = new StepDatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void saveStep(int segment, int steps) {
        ContentValues values = new ContentValues();
        values.put(StepDatabaseHelper.COLUMN_SEGMENTS, segment);
        values.put(StepDatabaseHelper.COLUMN_STEP_STEPS, steps);
        database.insert(StepDatabaseHelper.STEP_TABLE, null, values);
//        long id = database.insert(StepDatabaseHelper.STEP_TABLE, null, values);
//        Toast t = Toast.makeText(mContext, "ID: " + id, Toast.LENGTH_LONG);
//        t.show();
    }
}
