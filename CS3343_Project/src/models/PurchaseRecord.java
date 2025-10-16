package models;

import java.util.Date;

public class PurchaseRecord {
    private int recordId;
    private Item item;
    private Date purchaseDate;
    private String status;

    public PurchaseRecord(int recordId, Item item, Date purchaseDate, String status) {
        this.recordId = recordId;
        this.item = item;
        this.purchaseDate = purchaseDate;
        this.status = status;
    }

    public String getDetails() {
        return "Record ID: " + recordId + ", Item: " + item.getName() + ", Date: " + purchaseDate + ", Status: " + status;
    }
}
