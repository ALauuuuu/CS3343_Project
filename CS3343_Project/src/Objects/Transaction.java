package Objects;

import java.util.Date;
import java.util.List;

public class Transaction {
    private int transactionId;
    private List<PurchaseRecord> itemsPurchased;
    private double totalAmount;
    private Date dateOfPurchase;
    private String status;

    public Transaction(int transactionId, List<PurchaseRecord> itemsPurchased, double totalAmount, Date dateOfPurchase, String status) {
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
    	String result = "Transaction ID: " + transactionId;
    	for (PurchaseRecord record : itemsPurchased) {
    		            result += "\n" + record.getDetails();
    	}
    	result += "\n" + "Total Amount: $" + totalAmount + ", Date: " + dateOfPurchase + ", Status: " + status + "\n";
        return result;
    }

    public List<PurchaseRecord> getItemsPurchased() {
        return itemsPurchased;
    }

    public String getStatus() {
        return status;
    }
}
