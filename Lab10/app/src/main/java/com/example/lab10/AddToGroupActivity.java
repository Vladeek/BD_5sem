package com.example.lab10;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddToGroupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_group);


    }

    DBhelper helper = new DBhelper(AddToGroupActivity.this, "DB_10",null, 1);

    public void insert(View view)
    {
        EditText ID_G = findViewById(R.id.idg);
        EditText FAC = findViewById(R.id.fac);
        EditText COURSE = findViewById(R.id.course);
        EditText NAME = findViewById(R.id.name);
        EditText HEAD = findViewById(R.id.head);
        //ind = IDcheck();
        if(ID_G.getText().length() > 0
                && FAC.getText().length() > 0
                && COURSE.getText().length() > 0
                && NAME.getText().length() > 0
                && HEAD.getText().length() > 0)
        {
            helper.addElementToG(ID_G.getText().toString(), FAC.getText().toString(), COURSE.getText().toString(),NAME.getText().toString(), HEAD.getText().toString());
            Toast.makeText(AddToGroupActivity.this, "Inserted",Toast.LENGTH_SHORT).show();
        }
        ID_G.setText("");
        FAC.setText("");
        COURSE.setText("");
        NAME.setText("");
        HEAD.setText("");
    }
}