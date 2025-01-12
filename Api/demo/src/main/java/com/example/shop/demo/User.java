package com.example.shop.demo;


import jakarta.persistence.*;

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
    private boolean isMutted = false;
    private long muteTime = 0;
    private boolean isBanned = false;
    private long banTime = 0;
    private String tag;
    private String tagColor = "#1E1E1E";
    private String nameColor = "#1E1E1E";
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

    public boolean isMutted() {
        return isMutted;
    }

    public void setMutted(boolean mutted) {
        isMutted = mutted;
    }

    public long getMuteTime() {
        return muteTime;
    }

    public void setMuteTime(long muteTime) {
        this.muteTime = muteTime;
    }

    public boolean isBanned() {
        return isBanned;
    }

    public void setBanned(boolean banned) {
        isBanned = banned;
    }

    public long getBanTime() {
        return banTime;
    }

    public void setBanTime(long banTime) {
        this.banTime = banTime;
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
}
