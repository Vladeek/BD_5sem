package com.example.lab8;

import java.io.Serializable;

public class Note implements Serializable {

    private String value;
    private String category;
    private String date;

    public Note(String value, String date, String category) {
        this.value = value;
        this.date = date;
        this.category = category;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}