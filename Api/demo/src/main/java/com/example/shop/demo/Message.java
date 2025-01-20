package com.example.shop.demo;

import jakarta.persistence.*;

@Entity
@Table(name = "\"message\"")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String time;
    private String tag;
    private String tagColor;
    private String name;
    private String nameColor;
    private String text;
    private String textColor;
    private int avatar;
    private boolean isEdited;
    private boolean isAbused;
    private boolean isReplied;
    private boolean isDeleted;
    private boolean isMuted;
    private boolean isBanned;
    private String sendersEmail;
    private String messageBackgroundColor;
    private String sendersAccess;


    public Message() {
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameColor() {
        return nameColor;
    }

    public void setNameColor(String nameColor) {
        this.nameColor = nameColor;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }

    public boolean isEdited() {
        return isEdited;
    }

    public void setEdited(boolean edited) {
        isEdited = edited;
    }

    public boolean isAbused() {
        return isAbused;
    }

    public void setAbused(boolean abused) {
        isAbused = abused;
    }

    public boolean isReplied() {
        return isReplied;
    }

    public void setReplied(boolean replied) {
        isReplied = replied;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public boolean isMuted() {
        return isMuted;
    }

    public void setMuted(boolean muted) {
        isMuted = muted;
    }

    public boolean isBanned() {
        return isBanned;
    }

    public void setBanned(boolean banned) {
        isBanned = banned;
    }

    public String getSendersEmail() {
        return sendersEmail;
    }

    public void setSendersEmail(String sendersEmail) {
        this.sendersEmail = sendersEmail;
    }

    public String getMessageBackgroundColor() {
        return messageBackgroundColor;
    }

    public void setMessageBackgroundColor(String messageBackgroundColor) {
        this.messageBackgroundColor = messageBackgroundColor;
    }

    public String getSendersAccess() {
        return sendersAccess;
    }

    public void setSendersAccess(String sendersAccess) {
        this.sendersAccess = sendersAccess;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
