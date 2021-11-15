package com.example.lab10;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddToStudActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_stud);



    }

    DBhelper helper = new DBhelper(AddToStudActivity.this, "DB_10",null, 1);

    public void insert(View view)
    {
        EditText ID_G = findViewById(R.id.idg);
        EditText ID_S = findViewById(R.id.fac);
        EditText NAME = findViewById(R.id.course);
        //ind = IDcheck();
        if(ID_G.getText().length() > 0 && ID_S.getText().length() > 0 && NAME.getText().length() > 0)
        {
            helper.addElementToS(ID_G.getText().toString(), ID_S.getText().toString(), NAME.getText().toString());
            Toast.makeText(AddToStudActivity.this, "Inserted",Toast.LENGTH_SHORT).show();
        }
        ID_G.setText("");
        ID_S.setText("");
        NAME.setText("");
    }
}