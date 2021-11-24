package com.example.lab11;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    DBhelper databaseHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    SimpleCursorAdapter userAdapter;
    ListView userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        File file = getDatabasePath("DB_11.db");
        try
        {
            SQLiteDatabase DB = SQLiteDatabase.openDatabase(file.getPath(),null,SQLiteDatabase.OPEN_READONLY);
        }
        catch (Exception exception)
        {
            try
            {
                SQLiteDatabase DB = MainActivity.this.openOrCreateDatabase("DB_11.db", MODE_PRIVATE,null);
                Toast.makeText(MainActivity.this, "DB exists",Toast.LENGTH_SHORT).show();
            }
            catch (Exception e)
            {
                Toast.makeText(MainActivity.this, "DB creation/opening failed",Toast.LENGTH_SHORT).show();
            }
        }
        databaseHelper = new DBhelper(getApplicationContext());
    }
}