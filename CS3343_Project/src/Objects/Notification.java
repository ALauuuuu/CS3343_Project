package Objects;

import java.util.Date;

public class Notification {
    private String message;
    private Date date;

    public Notification(Date date, String message) {
        this.date = date;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public Date getDate() {
        return date;
    }

    public String toString() {
        return "Date: " + date + ", Message: " + message;
    }
}