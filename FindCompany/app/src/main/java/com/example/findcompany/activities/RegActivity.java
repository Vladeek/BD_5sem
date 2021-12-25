package com.example.findcompany.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
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

public class RegActivity extends AppCompatActivity {

    EditText userNameE, nameE, numberE, passwordE;
    Button signUpBtn, cancelBtn;
    String userName;
    String name;
    int number;
    String password;
    AppDatabase database;
    UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_reg);
        database = App.getInstance().getDatabase();
        userDao = database.userDao();
        initViews();
        setButtonListeners();
    }

    public void initViews() {
        userNameE = findViewById(R.id.userName);
        nameE = findViewById(R.id.name);
        numberE = findViewById(R.id.number);
        passwordE = findViewById(R.id.password);
        signUpBtn = findViewById(R.id.signUpBtn);
        cancelBtn = findViewById(R.id.cancelBtn);
    }

    public void bindingValues() {
        userName = userNameE.getText().toString();
        name = nameE.getText().toString();
        number = Integer.parseInt(numberE.getText().toString());;
        password = passwordE.getText().toString();
    }



    public void setButtonListeners() {
        signUpBtn.setOnClickListener(view -> {
                try {
                bindingValues();

                if (userName.length() >= 4 && userName.length() <= 15) {
                    if (password.length() >= 4 && password.length() <= 15) {
                        User user = new User(userName, name, number, password);
                        userDao.insertAll(user);
                        startActivity(new Intent(this, AuthActivity.class));
                        clearFields();
                        Toast.makeText(this, "User was successfully sign up ", Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        cancelBtn.setOnClickListener(view -> {
            startActivity(new Intent(this, AuthActivity.class));
        });
    }

    public void clearFields() {
        userNameE.setText("");
        nameE.setText("");
        numberE.setText("");
        passwordE.setText("");
    }
}