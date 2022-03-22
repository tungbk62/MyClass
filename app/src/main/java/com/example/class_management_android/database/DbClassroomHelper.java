package com.example.class_management_android.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.class_management_android.model.Classroom;

import java.util.ArrayList;
import java.util.List;

public class DbClassroomHelper extends SQLiteOpenHelper implements DbHelper
{
    public static final String DATABASE_NAME = "classroom_database";
    public static final int DATABASE_VERSION = 2;
    public static final String TABLE_NAME = "classrooms";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_SUBJECT_NAME = "class_name";
    public static final String COLUMN_START_TIME = "start_time";
    public static final String COLUMN_END_TIME = "end_time";
    public static final String COLUMN_CLASSROOM_NAME = "classroom_name";
    public static final String COLUMN_WEEK_DAY = "week_day";


    public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME
            + " (" + COLUMN_ID + " text primary key not null, " + COLUMN_SUBJECT_NAME + " text not null, "
            + COLUMN_START_TIME + " text not null, " + COLUMN_END_TIME + " text not null, "
            + COLUMN_CLASSROOM_NAME + " text not null, " + COLUMN_WEEK_DAY + " text not null, " + " integer);";
    private static final String TAG = "DbzClassManagerHelper";

    // constructor
    public DbClassroomHelper(Context context, SQLiteDatabase.CursorFactory factory)
    {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    // create a table in database
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(TABLE_CREATE);
    }

    // upgrade the table in database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        Log.w(TAG, "Upgrade the database from " + oldVersion + " to "
                + newVersion + ", all the data has been destroyed!");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
        Log.w(TAG, "Downgrade the database from " + newVersion + " to "
                + oldVersion + ", all the data has been destroyed!");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
                        // =======================================================
                        // =====IMPLEMENT FUNCTIONS IN THE DBHELPER INTERFACE=====
                        // =======================================================

    @Override
    public List<Classroom> getList()
    {
        List<Classroom> classroom = new ArrayList<Classroom>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TABLE_NAME, new String[]{COLUMN_ID, COLUMN_SUBJECT_NAME, COLUMN_START_TIME,
                        COLUMN_END_TIME, COLUMN_CLASSROOM_NAME, COLUMN_WEEK_DAY},
                null, null, null, null, COLUMN_SUBJECT_NAME);
        if (c.moveToFirst()) {
            do {
                String classID = c.getString(c.getColumnIndex(COLUMN_ID));
                String className = c.getString(c.getColumnIndex(COLUMN_SUBJECT_NAME));
                String startTime = c.getString(c.getColumnIndex(COLUMN_START_TIME));
                String endTime = c.getString(c.getColumnIndex(COLUMN_END_TIME));
                String classRoom = c.getString(c.getColumnIndex(COLUMN_CLASSROOM_NAME));
                String weekDay = c.getString(c.getColumnIndex(COLUMN_WEEK_DAY));
                Classroom classManager = new Classroom(classID,className,startTime,endTime,classRoom,weekDay);
                classroom.add(classManager);
            } while (c.moveToNext());
            c.close();
        }
        db.close();
        return classroom;
    }

    @Override
    public Classroom get(String id)
    {
        Classroom classroom;
        SQLiteDatabase db = this.getReadableDatabase();
        String sql_request = "SELECT * FROM " + TABLE_NAME + " WHERE "
                + COLUMN_ID+ " = " + "'" + id + "'";
        Cursor c = db.rawQuery(sql_request, null);
        if (c.moveToFirst())
        {
            String className = c.getString(c.getColumnIndex(COLUMN_SUBJECT_NAME));
            String startTime = c.getString(c.getColumnIndex(COLUMN_START_TIME));
            String endTime = c.getString(c.getColumnIndex(COLUMN_END_TIME));
            String classRoom = c.getString(c.getColumnIndex(COLUMN_CLASSROOM_NAME));
            String weekDay = c.getString(c.getColumnIndex(COLUMN_WEEK_DAY));
            classroom = new Classroom(id,className,startTime,endTime,classRoom,weekDay);
        }
        else
            classroom = null;
        c.close();
        db.close();
        return classroom;
    }

    @Override
    public long add(Object object)
    {
        Classroom classroom = (Classroom) object;
        long ret = -1;
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, classroom.getId());
        values.put(COLUMN_SUBJECT_NAME, classroom.getSubjectName());
        values.put(COLUMN_START_TIME, classroom.getStartTime());
        values.put(COLUMN_END_TIME, classroom.getEndTime());
        values.put(COLUMN_CLASSROOM_NAME,classroom.getClassroomName());
        values.put(COLUMN_WEEK_DAY,classroom.getWeekDay());
        SQLiteDatabase db = this.getWritableDatabase();
        ret = db.insert(TABLE_NAME, null, values);
        db.close();
        return ret;
    }

    @Override
    public int update(Object object)
    {
        Classroom classroom = (Classroom) object;
        int ret = -1;
        ContentValues values = new ContentValues();
        values.put(COLUMN_SUBJECT_NAME, classroom.getSubjectName());
        values.put(COLUMN_START_TIME, classroom.getStartTime());
        values.put(COLUMN_END_TIME, classroom.getEndTime());
        values.put(COLUMN_CLASSROOM_NAME,classroom.getClassroomName());
        values.put(COLUMN_WEEK_DAY,classroom.getWeekDay());
        SQLiteDatabase db = this.getWritableDatabase();
        ret = db.update(TABLE_NAME, values, COLUMN_ID + "=?", new String[]{classroom.getId()});
        db.close();
        return ret;
    }

    @Override
    public int delete(String id)
    {
        int ret = -1;
        SQLiteDatabase db = this.getWritableDatabase();
        ret = db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{id});
        db.close();
        return ret;
    }

    @Override
    public List<Classroom> search(String searchText)
    {
        List<Classroom> classrooms = new ArrayList<Classroom>();
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE "
                + COLUMN_CLASSROOM_NAME + " LIKE " + "'%" + searchText + "%'";
        Cursor c = db.rawQuery(sql, null);
        if (c.moveToFirst()) {
            do {
                String classID = c.getString(c.getColumnIndex(COLUMN_ID));
                String className = c.getString(c.getColumnIndex(COLUMN_CLASSROOM_NAME));
                String startTime = c.getString(c.getColumnIndex(COLUMN_START_TIME));
                String endTime = c.getString(c.getColumnIndex(COLUMN_END_TIME));
                String classRoom = c.getString(c.getColumnIndex(COLUMN_CLASSROOM_NAME));
                String weekDay = c.getString(c.getColumnIndex(COLUMN_WEEK_DAY));
                Classroom classroom = new Classroom(classID,className,startTime,endTime,classRoom,weekDay);
                classrooms.add(classroom);
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return classrooms;
    }
}
