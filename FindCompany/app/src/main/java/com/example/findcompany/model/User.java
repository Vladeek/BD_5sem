package com.example.findcompany.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "Users")
public class User implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "userName")
    private String userName;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "number")
    private int number;

    @ColumnInfo(name = "password")
    private String password;

    protected User(Parcel in) {
        id = in.readInt();
        userName = in.readString();
        name = in.readString();
        number = in.readInt();
        password = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public User(String userName, String name, int number, String password) {
        this.userName = userName;
        this.name = name;
        this.number = number;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && number == user.number && Objects.equals(userName, user.userName) && Objects.equals(name, user.name) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, name, number, password);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", name='" + name + '\'' +
                ", number=" + number +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(userName);
        parcel.writeString(name);
        parcel.writeInt(number);
        parcel.writeString(password);
    }
}
