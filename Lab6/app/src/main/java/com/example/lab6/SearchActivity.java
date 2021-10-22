package com.example.lab6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }

    public void search(View view) {
        try {
            EditText et = findViewById(R.id.search);
            TextView out =  findViewById(R.id.list);
            String text = et.getText().toString();
            out.setText("");
            String outtext = "";
            List<Person> lp = openFromExt();
            for (Person itVar : lp){
                if(text.equals(itVar.getFirstName()) || text.equals(itVar.getSecondName()))
                    outtext+=itVar.getPhoneNumber()+"\n";
            }
            out.setText(outtext);
        }
        catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    public List<Person> openFromExt(){
        List<Person> personList = new ArrayList<Person>();
        try{
            personList = JSONHelper.importFromJSONExternal(this);
            TextView out = (TextView) findViewById(R.id.outExternal);
            String text = new String (personList!=null ? personList.toString() : "");
            out.setText(text);
        }catch(Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return personList;

    }

    public void prevAct(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}