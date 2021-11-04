package com.example.lab10;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "StudentsDB.db"; // название бд
    private static final int SCHEMA = 1; // версия базы данных
    static final String TABLE_GROUPS = "Groups";// название таблицы в бд
    static final String TABLE_STUDENTS = "Students";// название таблицы в бд
    // названия столбцов
    public static final String COLUMN_ID_GROUP = "IDGROUP";
    public static final String COLUMN_ID_STUDENT = "IDSTUDENT";
    public static final String COLUMN_FACULTY = "FACULTY";
    public static final String COLUMN_COURSE = "COURSE";
    public static final String COLUMN_NAME = "NAME";
    public static final String COLUMN_HEAD = "HEAD";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE Groups (" + COLUMN_ID_GROUP
                + " INTEGER PRIMARY KEY NOT NULL UNIQUE," + COLUMN_FACULTY
                + " TEXT UNIQUE NOT NULL, " + COLUMN_COURSE
                + " INTEGER NOT NULL," + COLUMN_NAME
                + " TEXT UNIQUE NOT NULL, " + COLUMN_HEAD
                + " TEXT UNIQUE NOT NULL);");

        db.execSQL("CREATE TABLE Students (" + COLUMN_ID_GROUP
                + " INTEGER PRIMARY KEY NOT NULL," + COLUMN_ID_STUDENT
                + " INTEGER PRIMARY KEY NOT NULL UNIQUE, " + COLUMN_NAME
                + " TEXT UNIQUE NOT NULL," + "FOREIGN KEY(IDGROUP) REFERENCES Groups(IDGROUP));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GROUPS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
        onCreate(db);
    }
}
