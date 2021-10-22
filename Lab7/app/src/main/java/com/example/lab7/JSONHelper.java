package com.example.lab7;

import android.content.Context;
import android.os.Environment;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

class JSONHelper {

    private static final String FILE_NAME = "notes.json";

    static boolean exportToJSONInternal(Context context, List<Note> dataList) {

        Gson gson = new Gson();
        DataItems dataItems = new DataItems();
        dataItems.setNotes(dataList);
        String jsonString = gson.toJson(dataItems);

        FileOutputStream fileOutputStream = null;

        try {
            fileOutputStream = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            fileOutputStream.write(jsonString.getBytes());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return false;
    }
    static List<Note> importFromJSONInternal(Context context) {

        InputStreamReader streamReader = null;
        FileInputStream fileInputStream = null;
        try{
            fileInputStream = context.openFileInput(FILE_NAME);
            streamReader = new InputStreamReader(fileInputStream);
            Gson gson = new Gson();
            DataItems dataItems = gson.fromJson(streamReader, DataItems.class);
            return  dataItems != null ? dataItems.getNotes() : new ArrayList<Note>();
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
        finally {
            if (streamReader != null) {
                try {
                    streamReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }
    static boolean exportToJSONExternal(Context context, List<Note> dataList) {

        Gson gson = new Gson();
        DataItems dataItems = new DataItems();
        dataItems.setNotes(dataList);
        String jsonString = gson.toJson(dataItems);

        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), FILE_NAME);
        try
        {
            //Если нет директорий в пути, то они будут созданы:
            if (!file.getParentFile().exists())
                file.getParentFile().mkdirs();
            //Если файл существует, то он будет перезаписан:
            file.createNewFile();
            FileOutputStream fOut = new FileOutputStream(file, false);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            myOutWriter.write(jsonString);
            myOutWriter.close();
            fOut.close();
            return true;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return false;
    }
    static List<Note> importFromJSONExternal(Context context) {

        InputStreamReader streamReader = null;
        FileOutputStream fos = null;
        FileReader fr = null;
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), FILE_NAME);
        StringBuilder stringBuilder = new StringBuilder();

        try
        {
            fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            stringBuilder.append(line);

            Gson gson = new Gson();
            DataItems dataItems = gson.fromJson(stringBuilder.toString(), DataItems.class);
            return  dataItems != null ? dataItems.getNotes() : new ArrayList<Note>();
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            try{
                if(fos!=null)
                    fos.close();
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }

        return null;
    }


    private static class DataItems {
        private List<Note> notes;
        List<Note> getNotes() {
            return notes;
        }
        void setNotes(List<Note> notes) {
            this.notes = notes;
        }
    }
}