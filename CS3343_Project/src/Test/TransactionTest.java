package Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import Objects.Item;
import Objects.PurchaseRecord;
import Objects.Transaction;


public class TransactionTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private PurchaseRecord record;
    private Item item;
    private Transaction transaction;
    private long transactionId;
    private List<PurchaseRecord> itemsPurchased;
    private double totalAmount;
    private Date dateOfPurchase;
    private String status;
    
    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outContent));
        transactionId = System.currentTimeMillis();
        item = new Item(1, "Laptop", 3000, "electronics", 100);
        dateOfPurchase = new Date();
        record = new PurchaseRecord(1, item, dateOfPurchase, "Purchased");
        itemsPurchased = new ArrayList<>();
        itemsPurchased.add(record);
        totalAmount = 2;
        status = "Purchased";
        transaction = new Transaction(transactionId, itemsPurchased,totalAmount, dateOfPurchase, status);
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    public void ConstructorCreatesTransaction() {
        assertNotNull(transaction);
    }
    
    @Test
    public void generateTransactionId() {
    	String result = transaction.getTransactionDetails();
        assertTrue(result.contains("TXN"));
    }
    
    @Test
    public void getTransactionDetailsTransactionID() {
    	String result = transaction.getTransactionDetails();
        assertTrue(result.contains("Transaction ID:"));
    }
    @Test
    public void getTransactionDetailsTotalAmount() {
    	String result = transaction.getTransactionDetails();
        assertTrue(result.contains("Total Amount:"));
    }
    @Test
    public void getTransactionDetailsDate() {
    	String result = transaction.getTransactionDetails();
        assertTrue(result.contains("Date:"));
    }
    @Test
    public void getTransactionDetailsStatus() {
    	String result = transaction.getTransactionDetails();
        assertTrue(result.contains("Status:"));
    }
    
    @Test
    public void getItemsPurchased() {
        assertTrue(transaction.getItemsPurchased() instanceof List<PurchaseRecord>);
    }
    
    @Test
    public void getStatus() {
        assertTrue(transaction.getStatus() instanceof String);
    }
    
}
