package com.example.lab9;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    String fname = "SimpleTable.db";
    EditText ID, F, T;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        existBase(fname);
        EditText ID = findViewById(R.id.id_field);
        EditText F = findViewById(R.id.f_field);
        EditText T = findViewById(R.id.t_field);
    }

    private boolean existBase(String fname) {
        boolean rc = false;
        File f = new File(super.getFilesDir(), fname);

        Toast ex = Toast.makeText(getApplicationContext(), "БД уже существует!", Toast.LENGTH_SHORT);
        Toast cr = Toast.makeText(getApplicationContext(), "БД создана!", Toast.LENGTH_SHORT);
        Toast dcr = Toast.makeText(getApplicationContext(), "БД не была создана!", Toast.LENGTH_SHORT);

        if (rc = f.exists()) {
            ex.show();
        } else {
            File f1 = new File(super.getFilesDir(), fname);
            try {
                f1.createNewFile();
                cr.show();
            } catch (IOException e) {
                dcr.show();
            }
        }
        return rc;
    }

    public void insert(View view) {

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