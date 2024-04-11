package com.example.coursework1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ClassDataHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "class.db";
    static final String TABLE_NAME = "yoga_class";
    private static final String TEACHER_NAME = "Teacher_Name";
    private static final String DATE = "Date";
    private static final String ADDITIONAL_COMMENTS = "Additional_Comments";
    private static final String IMAGE = "Image";

    public ClassDataHelper(Context context) {
        super(context, DATABASE_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" + TEACHER_NAME + " TEXT, " + DATE + " TEXT, " +
                ADDITIONAL_COMMENTS + " TEXT, " + IMAGE + " BLOB)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTableQuery = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(dropTableQuery);
        onCreate(db);
    }

    public boolean insert(String teacherName, String date, String comments, byte[] image){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TEACHER_NAME, teacherName);
        contentValues.put(DATE, date);
        contentValues.put(ADDITIONAL_COMMENTS, comments);
        contentValues.put(IMAGE, image);

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
