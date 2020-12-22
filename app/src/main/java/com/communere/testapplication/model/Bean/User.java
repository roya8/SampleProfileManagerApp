package com.communere.testapplication.model.Bean;

import java.util.Objects;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_table")
public class User {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private String fullName;

    private String username;

    private String email;

    private String password;

    private String image;

    //**************************************************************************************************
    //**************************************************************************************************
    //constructor

    @Ignore
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String fullName, String username, String email, String password, String image) {
        this(username, password);
        this.fullName = fullName;
        this.email = email;
        this.image = image;
    }


    //**************************************************************************************************
    //**************************************************************************************************
    //setters

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    //**************************************************************************************************
    //**************************************************************************************************
    //getters

    public long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getImage() {
        return image;
    }


    //**************************************************************************************************
    //**************************************************************************************************
    //equals & hashmap

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User userInfo = (User) o;
        return id == userInfo.id &&
                Objects.equals(username, userInfo.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username);
    }

    //**************************************************************************************************
    //**************************************************************************************************
    //    toString

    @Override
    public String toString() {
        return "UserInfo{" +
                "id=" + id +
                ", fullName=" + fullName +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", image='" + image + '\'' +
                '}';
    }




}
