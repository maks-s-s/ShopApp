package Chat;

import com.google.gson.annotations.SerializedName;

public class Abuse {
    private Long id;
    //@SerializedName("message_id")
    private long messageId;
    //@SerializedName("sender_email")
    private String senderEmail;
    private String reason;
    private String time;
    private String description;

    public Abuse (long messageId, String senderEmail, String reason, String time, String description) {
        this.messageId = messageId;
        this.senderEmail = senderEmail;
        this.reason = reason;
        this.time = time;
        this.description = description;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long message) {
        this.messageId = message;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String sender) {
        this.senderEmail = sender;
    }
}
