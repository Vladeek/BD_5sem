package com.example.lab9;

import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "SimpleTable.db"; // название бд
    private static final int SCHEMA = 1; // версия базы данных
    static final String TABLE = "SimpleTable"; // название таблицы в бд
    // названия столбцов
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_F = "F";
    public static final String COLUMN_T = "T";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE SimpleTable (" + COLUMN_ID
                + " INTEGER PRIMARY KEY," + COLUMN_F
                + " REAL, " + COLUMN_T + " TEXT);");
        // добавление начальных данных
        db.execSQL("INSERT INTO " + TABLE + " (" + COLUMN_F
                + ", " + COLUMN_T + ") VALUES (1.235, 'text');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }
}
