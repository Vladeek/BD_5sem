package com.example.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class MainActivity extends AppCompatActivity {

    String fname = "Base_Lab.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ExistBase(fname);
        openFromFile();
    }

    private boolean ExistBase(String file) {
        boolean rc = false;
        File f = new File(super.getFilesDir(), fname);

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

    public void openFromFile(){
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

    public void findInFile(View view){

        EditText key1 = (EditText) findViewById(R.id.key1);
        EditText value1 = (EditText) findViewById(R.id.value1);
        value1.setText("");
        int key1v = key1.getText().toString().hashCode();
        String parse = Integer.toString(key1v);
        try(FileReader reader = new FileReader("data/data/com.example.lab5/files/Base_Lab.txt"))
        {
            // читаем посимвольно
            String[] arr;
            int c;
            String str = "";
            while((c=reader.read())!=-1){
                if ((char)c == ';'){
                   arr = str.split(",");
                   if(arr[0].equals(parse)){
                       value1.setText(arr[1]);
                   }
                   str = "";
                }
                else{
                    str+= (char)c;
                    if ((char)c == '\n')
                        str = "";

                }
            }
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
    }

    public void saveToFile(View view){

        FileOutputStream fos = null;
        try {
            TextView key = (TextView) findViewById(R.id.key);
            TextView value = (TextView) findViewById(R.id.value);
            int keyv = key.getText().toString().hashCode();

            try
            {
                FileInputStream fin = null;
                fin = openFileInput(fname);
                byte[] bytes = new byte[fin.available()];
                fin.read(bytes);
                //fin.close();
                String text = new String (bytes);
                String[] arr = text.split("\n");
                String[][] finalarr = new String[arr.length][];
                for (int i=0; i<arr.length; i++)
                {
                    finalarr[i] = arr[i].split(",");
                }
                for (int i=0; i<arr.length; i++)
                {
                    if(finalarr[i][0].equals(Integer.toString(keyv))){
                        finalarr[i][1] = " "+value.getText().toString()+"; \r";
                        String buf="";
                        for (int j=0; j<arr.length; j++)
                        {
                            //if(!finalarr[j][0].equals("\r\n")){
                                buf+=finalarr[j][0];
                                buf+=","+finalarr[j][1]+"\n";
                            //}
                        }
                        fos = openFileOutput(fname, MODE_PRIVATE);
                        fos.write(buf.getBytes());
                        Toast.makeText(this, "Файл сохранен", Toast.LENGTH_SHORT).show();
                        openFromFile();
                        return;
                    }
                }
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

            String valuev = value.getText().toString();
            String Result = keyv+", "+ valuev + "; "+"\r\n";

            fos = openFileOutput(fname, MODE_APPEND);
            fos.write(Result.getBytes());
            Toast.makeText(this, "Файл сохранен", Toast.LENGTH_SHORT).show();
            openFromFile();


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

    public  void deleteFile(View view){
        File file = new File("data/data/com.example.lab5/files/Base_Lab.txt");
        if(file.delete()){
            Toast.makeText(this, "Файл удален", Toast.LENGTH_SHORT).show();
        }else System.out.println("Файл file.txt не был найден в корневой папке проекта");
        TextView out = (TextView) findViewById(R.id.out);
        out.setText("");
    }

}
