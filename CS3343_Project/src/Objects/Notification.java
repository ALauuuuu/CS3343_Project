package Objects;

import java.util.Date;

public class Notification {
    private String message;
    private Date date;

    public Notification(String message, Date date) {
        this.message = message;
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public Date getDate() {
        return date;
    }
}