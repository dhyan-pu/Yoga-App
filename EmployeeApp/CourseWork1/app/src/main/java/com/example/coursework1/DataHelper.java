package com.example.coursework1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "schedules.db";
    static final String TABLE_NAME = "yoga_course";
    private static final String COURSE_NAME = "Course_Name";
    private static final String COURSE_START_AT = "Course_Start_At";
    private static final String COURSE_END_AT = "Course_End_At";
    private static final String WEEK_OF_THE_DAY = "Week_Of_The_Day";
    private static final String CAPACITY = "Capacity";
    private static final String DURATION = "Duration";
    private static final String PRICE_PER_CLASS = "Price_Per_Class";
    private static final String DESCRIPTION = "Description";

    public DataHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" + COURSE_NAME + " TEXT, " + COURSE_START_AT + " TEXT, " +
                COURSE_END_AT + " TEXT, " + WEEK_OF_THE_DAY + " TEXT, " + CAPACITY + " TEXT, " + DURATION + " TEXT, " + PRICE_PER_CLASS + " TEXT, " + DESCRIPTION + " TEXT)";

        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTableQuery = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(dropTableQuery);
        onCreate(db);
    }

    public boolean insert(String courseName, String courseStartsAt, String courseEndAt, String weekOfTheDay, String capacity, String duration, String pricePerClass, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COURSE_NAME, courseName);
        contentValues.put(COURSE_START_AT, courseStartsAt);
        contentValues.put(COURSE_END_AT, courseEndAt);
        contentValues.put(WEEK_OF_THE_DAY, weekOfTheDay);
        contentValues.put(CAPACITY, capacity);
        contentValues.put(DURATION, duration);
        contentValues.put(PRICE_PER_CLASS, pricePerClass);
        contentValues.put(DESCRIPTION, description);
        long result = db.insert(TABLE_NAME, null, contentValues);
        db.close();

        return result != -1;
    }

    public Cursor fetch() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        return db.rawQuery(query, null);
    }
}

