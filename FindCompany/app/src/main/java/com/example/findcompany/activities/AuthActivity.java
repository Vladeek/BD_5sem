package com.example.findcompany.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.findcompany.App;
import com.example.findcompany.R;
import com.example.findcompany.data.AppDatabase;
import com.example.findcompany.data.UserDao;
import com.example.findcompany.model.User;

public class AuthActivity extends AppCompatActivity {

    EditText userNameE, passwordE;
    Button signInBtn, toSignUpBtn;
    String userName;
    String password;
    AppDatabase database;
    UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_auth);
        database = App.getInstance().getDatabase();
        userDao = database.userDao();
        initViews();
        setButtonListeners();
    }


    public void initViews() {
        userNameE = findViewById(R.id.userName);
        passwordE = findViewById(R.id.password);
        toSignUpBtn = findViewById(R.id.toSignUpBtn);
        signInBtn = findViewById(R.id.sigInBtn);
    }

    public void bindingValues() {
        userName = userNameE.getText().toString();
        password = passwordE.getText().toString();
    }


    public void setButtonListeners() {
        signInBtn.setOnClickListener(view -> {
            try {
                bindingValues();
                if (userDao.signIn(userName, password) == true){
                    startActivity(new Intent(this, MainActivity.class));
                }
                else Toast.makeText(this, "No such user", Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        toSignUpBtn.setOnClickListener(view -> {
            startActivity(new Intent(this, RegActivity.class));
        });
    }

}