package models;

import java.util.Date;
import java.util.List;

public class Transaction {
    private int transactionId;
    private List<Item> itemsPurchased;
    private double totalAmount;
    private Date dateOfPurchase;
    private String status;

    public Transaction(int transactionId, List<Item> itemsPurchased, double totalAmount, Date dateOfPurchase, String status) {
        this.transactionId = transactionId;
        this.itemsPurchased = itemsPurchased;
        this.totalAmount = totalAmount;
        this.dateOfPurchase = dateOfPurchase;
        this.status = status;
    }

    public String generateTransactionId() {
        return "TXN" + System.currentTimeMillis();
    }

    public String generateReceipt() {
        return "Receipt for transaction " + transactionId;
    }

    public String getTransactionDetails() {
        return "Transaction ID: " + transactionId + ", Amount: " + totalAmount + ", Date: " + dateOfPurchase;
    }

    public List<Item> getItemsPurchased() {
        return itemsPurchased;
    }

    public String getStatus() {
        return status;
    }
}
