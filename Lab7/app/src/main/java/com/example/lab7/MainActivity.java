package com.example.lab7;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    String fname = "notes.json";
    EditText out;
    CalendarView cal;
    String selectedDate;
    Button add;
    Button delete;
    Button change;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        existBase(fname);

        out = findViewById(R.id.out);

        add = findViewById(R.id.add);
        change = findViewById(R.id.change);
        delete = findViewById(R.id.delete);
        add.setVisibility(View.INVISIBLE);
        change.setVisibility(View.INVISIBLE);
        delete.setVisibility(View.INVISIBLE);
        out.setVisibility(View.INVISIBLE);

        CalendarView cal = findViewById(R.id.calendar);
        cal.setOnDateChangeListener(new CalendarView.OnDateChangeListener(){
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                int mYear = year;
                int mMonth = month;
                int mDay = dayOfMonth;
                out.setVisibility(View.VISIBLE);
                selectedDate = new StringBuilder().append(mDay).append(".").append(mMonth + 1).append(".").append(mYear).toString();
                check();
            }
        });

    }
    private void check(){
        List<Note> notes = openFromInt();
        String outtext = "";
        for (Note itVar : notes){
            if(selectedDate.equals(itVar.getDate())){
                outtext+=itVar.getValue();
                change.setVisibility(View.VISIBLE);
                delete.setVisibility(View.VISIBLE);
                add.setVisibility(View.INVISIBLE);
                out.setText(outtext);
                return;
            }
        }

        out.setText("");
        change.setVisibility(View.INVISIBLE);
        delete.setVisibility(View.INVISIBLE);
        add.setVisibility(View.VISIBLE);
        return;
    }

    private boolean existBase(String fname) {
        boolean rc = false;
        File f = new File(super.getFilesDir(),fname);

        Toast ex = Toast.makeText(getApplicationContext(),"Такой файл уже существует!", Toast.LENGTH_SHORT);
        Toast cr = Toast.makeText(getApplicationContext(),"Файл создан!", Toast.LENGTH_SHORT);
        Toast dcr = Toast.makeText(getApplicationContext(),"Файл не был создан!", Toast.LENGTH_SHORT);

        if (rc = f.exists())
        {
            ex.show();
        }
        else
        {
            File f1 = new File(super.getFilesDir(), fname);
            try
            {
                f1.createNewFile();
                cr.show();
            }
            catch (IOException e)
            {
                dcr.show();
            }
        }
        return rc;
    }

    public void saveToInternal(List<Note> list){

        boolean result = JSONHelper.exportToJSONInternal(this, list);
        if(result){
            Toast.makeText(this, "Данные сохранены", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, "Не удалось сохранить данные", Toast.LENGTH_LONG).show();
        }
    }

    public List<Note> openFromInt(){
        List<Note> NoteList = new ArrayList<Note>();
        try{
            NoteList = JSONHelper.importFromJSONInternal(this);
        }catch(Exception e){
            Toast.makeText(this, "Файл отсутствует", Toast.LENGTH_LONG).show();
        }
        return NoteList != null ? NoteList : new ArrayList<Note>();
    }

    public void addButton(View view) {
        try {
            List<Note> notes = openFromInt();
            String outstr = out.getText().toString();
            notes.add(new Note(outstr, selectedDate));
            saveToInternal(notes);
            check();
        }
        catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void changeButton(View view) {
        try {
            List<Note> notes = openFromInt();
            Note temp = new Note();
            for (Note itVar : notes){
                if(selectedDate.equals(itVar.getDate())){
                    temp = itVar;
                }
            }
            temp.setValue(out.getText().toString());
            saveToInternal(notes);
            check();
        }
        catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void deleteButton(View view) {
        try {
            List<Note> notes = openFromInt();
            Note temp = new Note();
            for (Note itVar : notes){
                if(selectedDate.equals(itVar.getDate())){
                    temp = itVar;
                }
            }
            notes.remove(temp);
            saveToInternal(notes);
            check();
        }
        catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}