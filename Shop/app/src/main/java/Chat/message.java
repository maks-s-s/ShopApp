package Chat;

import android.view.View;

import com.google.gson.annotations.SerializedName;

public class message {
    @SerializedName("id")
    private long id;
    @SerializedName("time")
    private String time;
    @SerializedName("tag")
    private String tag;
    @SerializedName("tagColor")
    private String tagColor;
    @SerializedName("name")
    private String name;
    @SerializedName("nameColor")
    private String nameColor;
    @SerializedName("text")
    private String text;
    @SerializedName("textColor")
    private String textColor;
    @SerializedName("avatar")
    private int avatar;
    @SerializedName("pinned")
    private boolean isPinned;
    @SerializedName("edited")
    private boolean isEdited;
    @SerializedName("abused")
    private boolean isAbused;
    @SerializedName("replied")
    private boolean isReplied;
    private long idReplied;
    private int repliedBackGroundColor;
    @SerializedName("deleted")
    private boolean isDeleted;
    @SerializedName("muted")
    private boolean isMuted;
    @SerializedName("banned")
    private boolean isBanned;
    @SerializedName("wasChanged")
    private boolean wasChanged;
    @SerializedName("changerEmail")
    private String changerEmail;
    @SerializedName("sendersEmail")
    private String sendersEmail;
    @SerializedName("messageBackgroundColor")
    private String messageBackgroundColor;
    @SerializedName("sendersAccess")
    private String sendersAccess;

    public message (String time, String name, String text, String sendersEmail) {
        this.time = time;
        this.name = name;
        this.text = text;
        this.sendersEmail = sendersEmail;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isWasChanged() {
        return wasChanged;
    }

    public void setWasChanged(boolean wasChanged) {
        this.wasChanged = wasChanged;
    }

    public String getChangerEmail() {
        return changerEmail;
    }

    public void setChangerEmail(String changerEmail) {
        this.changerEmail = changerEmail;
    }

    public boolean isPinned() {
        return isPinned;
    }

    public void setPinned(boolean pinned) {
        this.isPinned = pinned;
    }

    public long getIdReplied() {
        return idReplied;
    }

    public void setIdReplied(long idReplied) {
        this.idReplied = idReplied;
    }

    public int getRepliedBackGroundColor() {
        return repliedBackGroundColor;
    }

    public void setRepliedBackGroundColor(int repliedBackGroundColor) {
        this.repliedBackGroundColor = repliedBackGroundColor;
    }
}
