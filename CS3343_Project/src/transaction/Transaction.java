package transaction;

import shopping.Item;
import java.util.Date;
import java.util.List;

public class Transaction {
    private int transactionId;
    private List<Item> itemsPurchased;
    private double totalAmount;
    private Date dateOfPurchase;
    private String status;

    public String generateTransactionId() {
        return null;
    }

    public String generateReceipt() {
        return null;
    }

    public String getTransactionDetails() {
        return null;
    }
}
