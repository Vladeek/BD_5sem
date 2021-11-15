package com.example.lab10;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DBhelper extends SQLiteOpenHelper {

    public static final String S_TABLE_NAME = "Students";
    public static final String G_TABLE_NAME = "Groups";
    public static final String ID_GROUP = "_id";
    public static final String FACULTY = "FACULTY";
    public static final String COURSE = "COURSE";
    public static final String NAME = "NAME";
    public static final String HEAD = "HEAD";
    public static final String ID_STUDENT = "ID_STUDENT";
    private static final String DATABASE_NAME = "DB_10";
    private static final int SCHEMA = 1;

    public Context context;

    public DBhelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onConfigure(SQLiteDatabase db)
    {
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //first table
        db.execSQL("CREATE TABLE IF NOT EXISTS Groups (_id INTEGER PRIMARY KEY , FACULTY TEXT, COURSE TEXT, NAME TEXT, HEAD TEXT);");
        //second table
        db.execSQL("CREATE TABLE IF NOT EXISTS Students (_id INTEGER, ID_STUDENT INTEGER, NAME TEXT, FOREIGN KEY(_id) REFERENCES Groups(_id) on delete cascade on update cascade);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addElementToS(String ID_G, String ID_S, String textType) {
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
    }

    public boolean addElementToG(String ID_G, String FAC, String C, String N, String H) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(ID_GROUP, ID_G);
        cv.put(FACULTY, FAC);
        cv.put(COURSE, C);
        cv.put(NAME, N);
        cv.put(HEAD, H);
        long result = db.insert(G_TABLE_NAME, null, cv);

        if (result == -1) {
            Toast.makeText(context, "Ошибка добавления данных", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            Toast.makeText(context, "Данные успешно были добавлены", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    public String[] selectElement(String TABLE_NAME, String ID) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, ID + "= ?", new String[]{ID}, null, null, null);
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
    }

    public DBhelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }

    String[] selectRawElement(String TABLE_NAME, int GR_NUM) {
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
    }
}
