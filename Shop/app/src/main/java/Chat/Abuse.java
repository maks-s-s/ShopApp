package Chat;

public class Abuse {
    private Long id;
    private Long message;
    private String sender;
    private String reason;
    private String time;
    private String description;

    public Abuse (Long message, String sender, String reason, String time, String description) {
        this.message = message;
        this.sender = sender;
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

    public Long getMessage() {
        return message;
    }

    public void setMessage(Long message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
