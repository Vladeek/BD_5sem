package com.example.lab8;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    String fname = "notes.xml";
    String fnamecat = "categories.xml";

    String selectedDate;
    EditText noteText;
    EditText catText;
    Spinner noteSpinner;
    ListView listViewNotes;
    ListView listViewCategories;
    int ChosenPosition = 0;
    Button changeButton;
    Button deleteButton;
    Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        existBase(fname);

        noteText = findViewById(R.id.note_text);
        catText = findViewById(R.id.cat_text);
        noteSpinner = findViewById(R.id.note_spinner);

        listViewNotes = findViewById(R.id.listView);
        listViewCategories = findViewById(R.id.listViewCategory);

        addButton = findViewById(R.id.add);
        changeButton = findViewById(R.id.change);
        deleteButton = findViewById(R.id.delete);

        changeButton.setVisibility(View.INVISIBLE);
        deleteButton.setVisibility(View.INVISIBLE);

        CalendarView cal = findViewById(R.id.calendar);
        cal.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                int mYear = year;
                int mMonth = month;
                int mDay = dayOfMonth;
                selectedDate = new StringBuilder().append(mDay).append(".").append(mMonth + 1).append(".").append(mYear).toString();
                check();
            }
        });
        listViewNotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
                ChosenPosition = position;

                changeButton.setVisibility(View.VISIBLE);
                deleteButton.setVisibility(View.VISIBLE);
                addButton.setVisibility(View.INVISIBLE);
            }
        });
    }

    private boolean existBase(String fname) {
        boolean rc = false;
        File f = new File(super.getFilesDir(), fname);
        File f2 = new File(super.getFilesDir(), fnamecat);

        Toast cr = Toast.makeText(getApplicationContext(), "Файл создан!", Toast.LENGTH_SHORT);
        Toast dcr = Toast.makeText(getApplicationContext(), "Файл не был создан!", Toast.LENGTH_SHORT);

        if (rc = f.exists()) {

        } else {
            File f1 = new File(super.getFilesDir(), fname);
            try {
                f1.createNewFile();
                cr.show();
            } catch (IOException e) {
                dcr.show();
            }
        }
        if (rc = f2.exists()) {

        } else {
            File f1 = new File(super.getFilesDir(), fnamecat);
            try {
                f1.createNewFile();
                cr.show();
            } catch (IOException e) {
                dcr.show();
            }
        }
        return rc;
    }

    public void saveNotes(List<Note> list) {
        XMLHelper xml = new XMLHelper(fname);
        xml.writeNoteToInternal(this, list);
    }

    public void saveCategories(List<String> list) {
        XMLHelper xml = new XMLHelper(fnamecat);
        xml.writeCategoryToInternal(this, list);
    }

    public List<Note> openNotes() {
        List<Note> NoteList = new ArrayList<Note>();
        XMLHelper xml = new XMLHelper(fname);
        try {
            NoteList = xml.readNoteFromInternal(this);
        } catch (Exception e) {
            Toast.makeText(this, "Файл отсутствует", Toast.LENGTH_LONG).show();
        }
        return NoteList != null ? NoteList : new ArrayList<Note>();
    }

    public List<String> openCategories() {
        List<String> list = new ArrayList<String>();
        XMLHelper xml = new XMLHelper(fnamecat);
        try {
            list = xml.readCategoryFromInternal(this);
        } catch (Exception e) {
            Toast.makeText(this, "Файл отсутствует", Toast.LENGTH_LONG).show();
        }
        return list != null ? list : new ArrayList<String>();
    }

    public void addButton(View view) {
        try {
            List<Note> notes = openNotes();
            if (notes.size() > 19)
                return;
            int count = 0;
            for (Note var : notes) {
                if (var.getDate().equals(selectedDate))
                    count++;
            }
            if (count > 4)
                return;
            String outstr = noteText.getText().toString();
            String outcat = noteSpinner.getSelectedItem().toString();
            notes.add(new Note(outstr, selectedDate, outcat));

            CustomComparator ds = new CustomComparator();
            Collections.sort(notes, ds);
            saveNotes(notes);
            check();
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void check() {
        changeButton.setVisibility(View.INVISIBLE);
        deleteButton.setVisibility(View.INVISIBLE);
        addButton.setVisibility(View.VISIBLE);
        List<Note> notes = openNotes();
        List<String> noteForView = new ArrayList<>();
        List<String> categories = openCategories();
        for (Note itVar : notes) {
            if (selectedDate.equals(itVar.getDate())) {
                noteForView.add(itVar.getValue() + " - " + itVar.getCategory());
            }
        }
        ArrayAdapter<String> adapter1 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, categories);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        noteSpinner.setAdapter(adapter1);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, noteForView);
        listViewNotes.setAdapter(adapter);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, categories);
        listViewCategories.setAdapter(adapter2);
        /*NoteAdapter adapter = new NoteAdapter(this, noteForView);
        recyclerViewNotes.setAdapter(adapter);*/

        /*CategoryAdapter adapter2 = new CategoryAdapter(this, categories);
        recyclerViewCategories.setAdapter(adapter2);*/
        return;
    }


    public void deleteButton(View view) {
        try {
            List<Note> notes = openNotes();
            Note tmp = notes.get(ChosenPosition);
            notes.remove(tmp);
            saveNotes(notes);
            check();
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void addCatButton(View view) {
        List<String> categories = openCategories();
        if (categories.size() > 4)
            return;
        String newCat = catText.getText().toString();
        categories.add(newCat);
        saveCategories(categories);
        check();
    }

    public void changeButton(View view) {
        try {
            List<Note> notes = openNotes();
            if (notes.size() > 19)
                return;
            int count = 0;
            for (Note var : notes) {
                if (var.getDate().equals(selectedDate))
                    count++;
            }
            if (count > 4)
                return;

            String outstr = noteText.getText().toString();
            String outcat = noteSpinner.getSelectedItem().toString();

            Note tmp = notes.get(ChosenPosition);
            tmp.setCategory(outcat);
            tmp.setValue(outstr);

            CustomComparator ds = new CustomComparator();
            Collections.sort(notes, ds);
            saveNotes(notes);
            check();
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}