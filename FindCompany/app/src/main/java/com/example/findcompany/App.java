package com.example.findcompany;

import android.app.Application;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.findcompany.data.AppDatabase;
import com.example.findcompany.data.UserDao;

public class App extends Application {

    private AppDatabase database;
    private UserDao userDao;
    private static App instance;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(this, AppDatabase.class, "database").allowMainThreadQueries().build();
        userDao = database.userDao();
    }

    public AppDatabase getDatabase() {
        return database;
    }

    public void setDatabase(AppDatabase database) {
        this.database = database;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
