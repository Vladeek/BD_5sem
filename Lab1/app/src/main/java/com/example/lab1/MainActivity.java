package com.example.lab1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void Text(View view)
    {
        // выводим сообщение
        Toast.makeText(this, "Не нужно было этого делать...", Toast.LENGTH_SHORT).show();
        
    }
}