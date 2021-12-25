package com.example.findcompany.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.findcompany.model.User;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM users")
    List<User> getAll();

    @Query("SELECT * FROM users WHERE userName LIKE :userName AND password LIKE :password")
    boolean signIn(String userName, String password);

    @Query("SELECT * FROM users")
    LiveData<List<User>> getAllLiveData();

    @Query("SELECT * FROM users WHERE id IN (:userId)")
    List<User> loadAllByIds(int[] userId);

    @Query("SELECT * FROM users WHERE id LIKE :id")
    User findById(int id);

    @Insert()
    void insertAll(User users);

    @Update()
    void update(User users);

    @Delete
    void delete(User user);
}
