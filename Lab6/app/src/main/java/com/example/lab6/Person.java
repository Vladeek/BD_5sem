package com.example.lab6;
import java.io.Serializable;

public class Person implements Serializable{

    private String firstName;
    private String secondName;
    private String phoneNumber;
    private String dateOfBirth;

    public Person(String firstName, String secondName, String phoneNumber, String dateOfBirth) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
    }

    public Person() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }


    @Override
    public String toString() {
        return
                "Имя: " + firstName + "\n" +
                        "Фамилия: " + secondName + "\n" +
                        "Номер: " + phoneNumber + "\n" +
                        "Дата рождения: " + dateOfBirth + "\n\n";
    }
}
