package database;

import java.time.LocalDateTime;

public class RDBUser {
    private Long id;
    private String username;
    private String password;
    private String email;
    private int age;
    private boolean isMutted;
    private LocalDateTime muteTime;
    private boolean isBanned;
    private long banTime = 0;
    private String tag;
    private String tagColor;
    private String nameColor = "#1E1E1E";
    private String permission;


    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public boolean isMutted() {
        return isMutted;
    }

    public void setMutted(boolean mutted) {
        isMutted = mutted;
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
    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public LocalDateTime getMuteTime() {
        return muteTime;
    }

    public void setMuteTime(LocalDateTime muteTime) {
        this.muteTime = muteTime;
    }
}

