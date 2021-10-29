package com.example.lab9;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


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
    }

    public void select(View view) {

    }

    public void selectRaw(View view) {
    }

    public void update(View view) {
    }

    public void delete(View view) {
    }
}