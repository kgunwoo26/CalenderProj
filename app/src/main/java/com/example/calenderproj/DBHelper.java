package com.example.calenderproj;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    private static final String TAG = "TEST";

    public DBHelper(@Nullable Context context) {
        super(context, EventContract.DB_NAME, null, EventContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG,getClass().getName()+".onCreate()");
        db.execSQL(EventContract.Events.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        Log.i(TAG,getClass().getName() +".onUpgrade()");
        db.execSQL(EventContract.Events.DELETE_TABLE);
        onCreate(db);
    }

    public void insertEventBySQL(String title,
                                 String date,
                                 String start_Time,
                                 String end_Time,
                                 String place,
                                 String memo){
        try {
            String sql = String.format (
                    "INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s) " +
                            "VALUES (NULL,'%s','%s','%s','%s','%s','%s')",
                    EventContract.Events.TABLE_NAME,
                    EventContract.Events._ID,
                    EventContract.Events.KEY_TITLE,
                    EventContract.Events.KEY_DATE,
                    EventContract.Events.KEY_START_TIME,
                    EventContract.Events.KEY_END_TIME,
                    EventContract.Events.KEY_PLACE,
                    EventContract.Events.KEY_MEMO,
                    title,date,start_Time,end_Time,place,memo);
            getWritableDatabase().execSQL(sql);
        } catch (SQLException e) {
            Log.e(TAG,"Error in inserting recodes");
        }
    }
    public void deleteEventBySQL(String title) {
        try {
            String sql = String.format (
                    "DELETE FROM %s WHERE %s = %s",
                    EventContract.Events.TABLE_NAME,
                    EventContract.Events.KEY_TITLE,
                    title);
            getWritableDatabase().execSQL(sql);
        } catch (SQLException e) {
            Log.e(TAG,"Error in deleting recodes");
        }
    }

    public void updateEventBySQL(String _id,
                                 String title,
                                 String date,
                                 String start_Time,
                                 String end_Time,
                                 String place,
                                 String memo){
        try {
            String sql = String.format (
                    "UPDATE %s SET %s = '%s',%s = '%s',%s = '%s',%s = '%s',%s = '%s',%s = '%s' WHERE %s = %s",
                    EventContract.Events.TABLE_NAME,
                    EventContract.Events.KEY_TITLE,title,
                    EventContract.Events.KEY_DATE,date,
                    EventContract.Events.KEY_START_TIME,start_Time,
                    EventContract.Events.KEY_END_TIME,end_Time,
                    EventContract.Events.KEY_PLACE,place,
                    EventContract.Events.KEY_MEMO,memo,
                    EventContract.Events._ID,_id);
            getWritableDatabase().execSQL(sql);
        } catch (SQLException e) {
            Log.e(TAG,"Error in inserting recodes");
        }
    }

    public Cursor getAllEventBySQL() {
        String sql = "Select * FROM " + EventContract.Events.TABLE_NAME;
        return getReadableDatabase().rawQuery(sql,null);
    }
    public Cursor getAllSchedule(String date){
        String sql = "Select * FROM " + EventContract.Events.TABLE_NAME + " WHERE Date =" + date;
        return getReadableDatabase().rawQuery(sql,null);
    }
}
