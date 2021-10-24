package com.example.lab8;

import java.util.Comparator;

public class CustomComparator implements Comparator<Note> {
    @Override
    public int compare(Note o1, Note o2) {
        return o1.getCategory().compareTo(o2.getCategory());
    }
}
