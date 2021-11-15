package com.example.lab10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    DBhelper databaseHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    SimpleCursorAdapter userAdapter;
    DBhelper sqlHelper;
    ListView userList;
    int SavedGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        File file = getDatabasePath("DB_10");
        try
        {
            SQLiteDatabase DB = SQLiteDatabase.openDatabase(file.getPath(),null,SQLiteDatabase.OPEN_READONLY);
        }
        catch (Exception exception)
        {
            try
            {
                SQLiteDatabase DB = MainActivity.this.openOrCreateDatabase("DB_10", MODE_PRIVATE,null);
                Toast.makeText(MainActivity.this, "DB exists",Toast.LENGTH_SHORT).show();
            }
            catch (Exception e)
            {
                Toast.makeText(MainActivity.this, "DB creation/opening failed",Toast.LENGTH_SHORT).show();
            }
        }
        databaseHelper = new DBhelper(getApplicationContext());

    }


    DBhelper helper = new DBhelper(MainActivity.this, "DB_10",null, 1);

    public void select_s(View view)
    {
        EditText GN = findViewById(R.id.groupnum);
        userList = findViewById(R.id.list);

        if(GN.getText().length() > 0)
        {
            try {
                db = databaseHelper.getReadableDatabase();

                int group = Integer.parseInt(String.valueOf(GN.getText()));
                String sql = "select * from Students where _id = " + group;
                userCursor = db.rawQuery(sql,null);
                // определяем, какие столбцы из курсора будут выводиться в ListView
                String[] headers = new String[]{DBhelper.ID_GROUP, DBhelper.NAME};
                // создаем адаптер, передаем в него курсор
                userAdapter = new SimpleCursorAdapter(this, android.R.layout.two_line_list_item,
                        userCursor, headers, new int[]{android.R.id.text1,android.R.id.text2}, 0);
                userList.setAdapter(userAdapter);//userList

                SavedGroup = group;
            }
            catch (Exception e)
            {
                Toast.makeText(this, "Нет такой группы!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void select_g(View view)
    {
        EditText GN = findViewById(R.id.groupnum);
        userList = findViewById(R.id.list);

        if(true)
        {
            try {
                db = databaseHelper.getReadableDatabase();

                String sql = "select * from Groups";
                userCursor = db.rawQuery(sql,null);
                // определяем, какие столбцы из курсора будут выводиться в ListView
                String[] headers = new String[]{DBhelper.ID_GROUP, DBhelper.NAME};
                // создаем адаптер, передаем в него курсор
                userAdapter = new SimpleCursorAdapter(this, android.R.layout.two_line_list_item,
                        userCursor, headers, new int[]{android.R.id.text1,android.R.id.text2}, 0);
                userList.setAdapter(userAdapter);//userList

            }
            catch (Exception e)
            {
                Toast.makeText(this, "Нет такой группы!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void add_student(View view)
    {
        try {
            Intent intent = new Intent(this, AddToStudActivity.class);
            startActivity(intent);
        }
        catch (Exception e)
        {Toast.makeText(this, "error",Toast.LENGTH_SHORT);}
    }

    public void add_group(View view)
    {
        try {
            Intent intent = new Intent(this, AddToGroupActivity.class);
            startActivity(intent);
        }
        catch (Exception e)
        {Toast.makeText(this, "error",Toast.LENGTH_SHORT);}
    }

    public void deleteGroup(View view){
        EditText GN = findViewById(R.id.groupnum);
        int group = Integer.parseInt(String.valueOf(GN.getText()));
        try
        {
            db.delete(DBhelper.G_TABLE_NAME,"_id = ?", new String[] {String.valueOf(group)});
            Toast.makeText(this, "удалено", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e)
        {Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();}


    }

    public void deleteStudent(View view){
        EditText GN = findViewById(R.id.groupnum);
        int students = Integer.parseInt(String.valueOf(GN.getText()));
        try
        {
            db.delete(DBhelper.S_TABLE_NAME,"ID_STUDENT = ?", new String[] {String.valueOf(students)});
            Toast.makeText(this, "удалено", Toast.LENGTH_SHORT).show();

        }
        catch (Exception e) {}

        Toast.makeText(this, "удалено", Toast.LENGTH_SHORT).show();
    }

}