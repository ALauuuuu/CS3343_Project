package models;

import java.util.Date;

public class Notification {
    private String message;
    private Date date;
    private int transactionId;

    public Notification(String message, Date date, int transactionId) {
        this.message = message;
        this.date = date;
        this.transactionId = transactionId;
    }

    public String getMessage() {
        return message;
    }

    public Date getDate() {
        return date;
    }

    public int getTransactionId() {
        return transactionId;
    }
}
