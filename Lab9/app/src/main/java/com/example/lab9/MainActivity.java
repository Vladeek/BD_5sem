package com.example.lab9;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    EditText ID, F, T;
    SQLiteDatabase db;
    DatabaseHelper sqlHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sqlHelper = new DatabaseHelper(this);
        db = sqlHelper.getWritableDatabase();
        ID = findViewById(R.id.id_field);
        F = findViewById(R.id.f_field);
        T = findViewById(R.id.t_field);
    }

    public void insert(View view) {
        try {
            int idStr = Integer.parseInt(ID.getText().toString());
            float fStr = Float.parseFloat(F.getText().toString());
            String tStr = T.getText().toString();
            ContentValues values = new ContentValues();
            values.put("ID", idStr);
            values.put("F", fStr);
            values.put("T", tStr);
            // вставляем, возвращает primary key
            long newRowId;
            newRowId = db.insert(
                    DatabaseHelper.TABLE,
                    null,
                    values);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
        }
    }

    public void select(View view) {
        try {
            String[] projection = {DatabaseHelper.COLUMN_ID, DatabaseHelper.COLUMN_F, DatabaseHelper.COLUMN_T};
            String selection = DatabaseHelper.COLUMN_ID + " = ?";
            String[] selectionArgs = {ID.getText().toString()};
            Cursor cursor = db.query(
                    DatabaseHelper.TABLE, // имя таблицы
                    projection, // столбцы
                    selection, // столбцы для WHERE
                    selectionArgs, // значения для WHERE
                    null, // не группировать строки
                    null, // не фильтровать
                    null // порядок сортировки
            );
            int count = cursor.getCount();
            cursor.moveToFirst();
            String idValue = cursor.getString(0);
            float fValue = cursor.getFloat(1);
            String tValue = cursor.getString(2);
            ID.setText(idValue);
            F.setText(String.valueOf(fValue));
            T.setText(tValue);
            cursor.close();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
        }

    }

    public void selectRaw(View view) {
        try {
            String[] selectionArgs = {ID.getText().toString()};
            Cursor cursor = db.rawQuery("select ID, F, T from SimpleTable where ID = ?", selectionArgs);
            cursor.moveToFirst();
            String idValue = cursor.getString(0);
            float fValue = cursor.getFloat(1);
            String tValue = cursor.getString(2);
            ID.setText(idValue);
            F.setText(String.valueOf(fValue));
            T.setText(tValue);
            cursor.close();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
        }
    }

    public void update(View view) {
        try {
            String[] selectionArgs = {ID.getText().toString()};
            String selection = DatabaseHelper.COLUMN_ID + " LIKE ?";
            int idStr = Integer.parseInt(ID.getText().toString());
            float fStr = Float.parseFloat(F.getText().toString());
            String tStr = T.getText().toString();
            ContentValues values = new ContentValues();
            values.put("F", fStr);
            values.put("T", tStr);
            long newRowId;
            newRowId = db.update(
                    DatabaseHelper.TABLE,
                    values,
                    selection,
                    selectionArgs);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
        }
    }

    public void delete(View view) {
        try {
            String selection = DatabaseHelper.COLUMN_ID + " LIKE ?";
            String[] selectionArgs = {ID.getText().toString()};
            int deletedRows = db.delete(DatabaseHelper.TABLE, selection,
                    selectionArgs);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
        }
    }
}