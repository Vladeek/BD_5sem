package com.example.lab4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    String fname = "Base_Lab.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ExistBase(fname);
        TextView textView = (TextView) findViewById(R.id.out);
        openText();
    }

    private boolean ExistBase(String fname) {
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
                dex.show();
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

    public void openText(){
        FileOutputStream fos = null;
        try
        {
            FileInputStream fin = null;
            TextView textView = (TextView) findViewById(R.id.out);
            fin = openFileInput(fname);
            byte[] bytes = new byte[fin.available()];
            fin.read(bytes);
            String text = new String (bytes);
            textView.setText(text);
        }
        catch(IOException ex)
        {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        finally
        {
            try{
                if(fos!=null)
                    fos.close();
            }
            catch(IOException ex){

                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void saveText(View view){

        FileOutputStream fos = null;
        try {
            TextView name = (TextView) findViewById(R.id.firstName);
            TextView secondName = (TextView) findViewById(R.id.secondName);
            String nametext = name.getText().toString();
            String secondNameText = secondName.getText().toString();
            String Result = nametext+"; "+secondNameText+"; "+"\r\n";

            fos = openFileOutput(fname, MODE_PRIVATE);
            fos.write(Result.getBytes());
            Toast.makeText(this, "Файл сохранен", Toast.LENGTH_SHORT).show();
            openText();
        }
        catch(IOException ex) {

            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        finally{
            try{
                if(fos!=null)
                    fos.close();
            }
            catch(IOException ex){

                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }




}