package com.example.shop.demo;


import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.LocalDateTime.*;

@Entity
@Table(name = "\"user\"")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String email;
    private int age;
    private String muteTime; // end of the Mute
    private String banTime; // end of the Mute
    private String tag;
    private String tagColor = "#1E1E1E";
    private String nameColor = "#1E1E1E";
    private String permission = "user";
    // add sorted array "roles"


    public User() {
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTagColor() {
        return tagColor;
    }

    public void setTagColor(String tagColor) {
        this.tagColor = tagColor;
    }

    public String getNameColor() {
        return nameColor;
    }

    public void setNameColor(String nameColor) {
        this.nameColor = nameColor;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getMuteTime() {
        return muteTime;
    }

    public void setMuteTime(String muteTime) {
        this.muteTime = muteTime;
    }

    public String getBanTime() {
        return banTime;
    }

    public void setBanTime(String banTime) {
        this.banTime = banTime;
    }
}
