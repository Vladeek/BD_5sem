package com.example.lab7;
import java.io.Serializable;
import java.util.Date;

public class Note implements Serializable{

    private String value;
    private String date;

    public Note(String value, String date) {
        this.value = value;
        this.date = date;
    }

    public Note() {
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}