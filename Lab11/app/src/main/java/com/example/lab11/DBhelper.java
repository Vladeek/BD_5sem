package com.example.lab11;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DBhelper extends SQLiteOpenHelper {

    public static final String STUDENTS_TABLE_NAME = "Students";
    public static final String GROUP_TABLE_NAME = "Groups";
    public static final String FACULTY_TABLE_NAME = "Faculties";
    public static final String SUB_TABLE_NAME = "Subjects";
    public static final String PROGRESS_TABLE_NAME = "Progress";
    public static final String ID_GROUP = "ID_GROUP";
    public static final String FACULTY = "FACULTY";
    public static final String COURSE = "COURSE";
    public static final String NAME = "NAME";
    public static final String ID_FACULTY = "ID_FACULTY";
    public static final String ID_SUBJECT = "ID_SUBJECT";
    public static final String SUBJECT = "SUBJECT";
    public static final String EXAM_DATE = "EXAM_DATE";
    public static final String MARK = "MARK";
    public static final String TEACHER = "TEACHER";
    public static final String OFFICE_TIMETABLE = "OFFICE_TIMETABLE";
    public static final String DEAN = "DEAN";
    public static final String BIRTHDAY = "BIRTHDAY";
    public static final String ADDRESS = "ADDRESS";
    public static final String HEAD = "HEAD";
    public static final String ID_STUDENT = "ID_STUDENT";
    private static final String DATABASE_NAME = "DB_11.db";
    private static final int SCHEMA = 1;

    public Context context;

    public DBhelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE IF NOT EXISTS Groups (ID_GROUP INTEGER PRIMARY KEY , FACULTY TEXT, COURSE TEXT, NAME TEXT, HEAD TEXT);");

        db.execSQL("CREATE TABLE IF NOT EXISTS Faculties (ID_FACULTY INTEGER PRIMARY KEY , FACULTY TEXT, DEAN TEXT, OFFICE_TIMETABLE);");

        db.execSQL("CREATE TABLE IF NOT EXISTS Subject (ID_SUBJECT INTEGER PRIMARY KEY , SUBJECT TEXT);");

        db.execSQL("CREATE TABLE IF NOT EXISTS Progress (ID_PROGRESS INTEGER PRIMARY KEY , ID_SUBJECT INTEGER, EXAMDATE INTEGER, MARK INTEGER, TEACHER TEXT, FOREIGN KEY(ID_SUBJECT) REFERENCES Subjects(ID_SUBJECT) on delete cascade on update cascade);");

        db.execSQL("CREATE TABLE IF NOT EXISTS Students (ID_STUDENT INTEGER PRIMARY KEY, ID_GROUP INTEGER, NAME TEXT, BIRTHDATE INTEGER, ADDRESS TEXT, FOREIGN KEY(ID_GROUP) REFERENCES Groups(ID_GROUP) on delete cascade on update cascade);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public DBhelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }

    /*public boolean addElementToS(String ID_G, String ID_S, String textType) {
        SQLiteDatabase db = this.getWritableDatabase();//
        ContentValues cv = new ContentValues();

        cv.put(ID_GROUP, ID_G);
        cv.put(ID_STUDENT, ID_S);
        cv.put(NAME, textType);
        long result = db.insert(S_TABLE_NAME, null, cv);

        if (result == -1) {
            //Toast.makeText(context, "Ошибка добавления данных", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            //Toast.makeText(context, "Данные успешно были добавлены", Toast.LENGTH_SHORT).show();
            return true;
        }
    }*/



    /*public String[] selectRawElement(String TABLE_NAME, int GR_NUM) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + S_TABLE_NAME + " WHERE " + ID_GROUP + " = " + GR_NUM + ";", null);
        try {
            cursor.moveToFirst();
            return new String[]{cursor.getString(0), cursor.getString(1), cursor.getString(2)};
        } catch (SQLiteException e) {
            e.printStackTrace();
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            //Toast.makeText(context, "Нет элемента с таким ID", Toast.LENGTH_SHORT).show();
        }
        return new String[]{};
    }*/
}
