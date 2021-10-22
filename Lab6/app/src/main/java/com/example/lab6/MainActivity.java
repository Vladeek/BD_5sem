package com.example.lab6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    String fname = "person.json";
    ListView listView;
    Pattern DATE_PATTERN2 = Pattern.compile("^\\d{2}.\\d{1}.\\d{4}$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        verifyStoragePermissions(this);
        existBase(fname);
        existBaseExt(fname);
        openFromInt();
        openFromExt();

    }

    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    private boolean existBase(String fname) {
        boolean rc = false;
        File f = new File(super.getFilesDir(),fname);

        Toast ex = Toast.makeText(getApplicationContext(),"Такой файл уже существует!", Toast.LENGTH_SHORT);
        Toast dex = Toast.makeText(getApplicationContext(),"Такого файла не существует!", Toast.LENGTH_SHORT);
        Toast cr = Toast.makeText(getApplicationContext(),"Файл создан!", Toast.LENGTH_SHORT);
        Toast dcr = Toast.makeText(getApplicationContext(),"Файл не был создан!", Toast.LENGTH_SHORT);

        if (rc = f.exists())
        {
            ex.show();
        }
        else
        {
            //dex.show();
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

    private boolean existBaseExt(String fname) {
        boolean rc = false;
        File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fname);

        Toast ex = Toast.makeText(getApplicationContext(),"Такой файл уже существует!", Toast.LENGTH_SHORT);
        Toast dex = Toast.makeText(getApplicationContext(),"Такого файла не существует!", Toast.LENGTH_SHORT);
        Toast cr = Toast.makeText(getApplicationContext(),"Файл создан!", Toast.LENGTH_SHORT);
        Toast dcr = Toast.makeText(getApplicationContext(),"Файл не был создан!", Toast.LENGTH_SHORT);

        if (rc = f.exists())
        {
            ex.show();
        }
        else
        {
            //dex.show();
            File f1 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fname);
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

    public void saveToInternal(View view){

        TextView name = (TextView) findViewById(R.id.firstName);
        TextView secondName = (TextView) findViewById(R.id.secondName);
        TextView number = (TextView) findViewById(R.id.phone);
        TextView dateOfBirth = (TextView) findViewById(R.id.dateOfBirth);
        String namestr = name.getText().toString();
        String secondNamestr = secondName.getText().toString();
        String numberstr = number.getText().toString();
        String dateOfBirthstr = dateOfBirth.getText().toString();
        if(!(DATE_PATTERN2.matcher(dateOfBirthstr).matches())){
            Toast.makeText(this, "Введите дату в формате dd.m.yyyy", Toast.LENGTH_SHORT).show();
            return;
        }

        Person person = new Person(namestr, secondNamestr, numberstr, dateOfBirthstr);
        List<Person> personList = new ArrayList<Person>();

        personList = JSONHelper.importFromJSONInternal(this);
        personList.add(person);
        boolean result = JSONHelper.exportToJSONInternal(this, personList);
        if(result){
            Toast.makeText(this, "Данные сохранены", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, "Не удалось сохранить данные", Toast.LENGTH_LONG).show();
        }
        openFromInt();
    }

    public List<Person> openFromInt(){
        List<Person> personList = new ArrayList<Person>();
        try{
            personList = JSONHelper.importFromJSONInternal(this);
            TextView textView = (TextView) findViewById(R.id.outInternal);
            String text = new String (personList!=null ? personList.toString() : "");
            textView.setText(text);
        }catch(Exception e){
            Toast.makeText(this, "Файл отсутствует", Toast.LENGTH_LONG).show();
        }
        return personList;
    }

    public List<Person> openFromExt(){
        List<Person> personList = new ArrayList<Person>();
        try{
            personList = JSONHelper.importFromJSONExternal(this);
            TextView out = (TextView) findViewById(R.id.outExternal);
            String text = new String (personList!=null ? personList.toString() : "");
            out.setText(text);
        }catch(Exception e){
            Toast.makeText(this, "Файл отсутствует", Toast.LENGTH_LONG).show();
        }
        return personList;

    }

    public  void deleteExternalFile(View view){
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fname);
        if(file.delete()){
            //Toast.makeText(this, "Файл удален", Toast.LENGTH_SHORT).show();
        }else
            System.out.println("Файл person.json не был найден в корневой папке проекта");

        TextView out = (TextView) findViewById(R.id.outExternal);
        out.setText("");
        existBaseExt(fname);
    }

    public  void deleteInternalFile(View view){
        File file = new File("data/data/com.example.lab6/files/person.json");
        if(file.delete()){
            //Toast.makeText(this, "Файл удален", Toast.LENGTH_SHORT).show();
        }else
            System.out.println("Файл person.json не был найден в корневой папке проекта");

        TextView out = (TextView) findViewById(R.id.outInternal);
        out.setText("");
        existBase(fname);
    }

    public void saveToExternal(View view){
        TextView name = (TextView) findViewById(R.id.firstName);
        TextView secondName = (TextView) findViewById(R.id.secondName);
        TextView number = (TextView) findViewById(R.id.phone);
        TextView dateOfBirth = (TextView) findViewById(R.id.dateOfBirth);
        String namestr = name.getText().toString();
        String secondNamestr = secondName.getText().toString();
        String numberstr = number.getText().toString();
        String dateOfBirthstr = dateOfBirth.getText().toString();
        if(!(DATE_PATTERN2.matcher(dateOfBirthstr).matches())){
            Toast.makeText(this, "Введите дату в формате dd.m.yyyy", Toast.LENGTH_SHORT).show();
            return;
        }

        Person person = new Person(namestr, secondNamestr, numberstr, dateOfBirthstr);
        List<Person> personList = new ArrayList<Person>();

        personList = JSONHelper.importFromJSONExternal(this);
        personList.add(person);
        boolean result = JSONHelper.exportToJSONExternal(this, personList);
        if(result){
            Toast.makeText(this, "Данные сохранены", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, "Не удалось сохранить данные", Toast.LENGTH_LONG).show();
        }
        openFromExt();
    }

    public void search(View view) {
        try {
            EditText et = findViewById(R.id.search);
            TextView out = findViewById(R.id.list);
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

    public void nextAct(View view) {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

}