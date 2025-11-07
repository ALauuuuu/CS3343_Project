package Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Date;

import Objects.Item;
import Objects.PurchaseRecord;

public class PurchaseRecordTest {
    
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private PurchaseRecord record;
    private Item testItem;
    private Date testDate;

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outContent));
        testItem = new Item(1, "Laptop", 1200.0, "electronics", 10);
        testDate = new Date();
        record = new PurchaseRecord(1, testItem, testDate, "Purchased");
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    public void ConstructorCreatesRecord() {
        assertNotNull(record);
    }

    @Test
    public void GetDetailsContainsItemName() {
        String details = record.getDetails();
        assertTrue(details.contains("Laptop"));
    }

    @Test
    public void GetDetailsContainsStatus() {
        String details = record.getDetails();
        assertTrue(details.contains("Purchased"));
    }

    @Test
    public void GetDetailsContainsDate() {
        String details = record.getDetails();
        assertTrue(details.contains(testDate.toString()));
    }

    @Test
    public void GetDetailsFormat() {
        String details = record.getDetails();
        assertTrue(details.contains("Record ID:"));
        assertTrue(details.contains("Item:"));
        assertTrue(details.contains("Date:"));
        assertTrue(details.contains("Status:"));
    }

    @Test
    public void PurchaseRecordWithDifferentStatus() {
        PurchaseRecord shippedRecord = new PurchaseRecord(2, testItem, testDate, "Shipped");
        String details = shippedRecord.getDetails();
        assertTrue(details.contains("Shipped"));
    }

    @Test
    public void PurchaseRecordWithDeliveredStatus() {
        PurchaseRecord deliveredRecord = new PurchaseRecord(3, testItem, testDate, "Delivered");
        String details = deliveredRecord.getDetails();
        assertTrue(details.contains("Delivered"));
    }

    @Test
    public void PurchaseRecordWithCancelledStatus() {
        PurchaseRecord cancelledRecord = new PurchaseRecord(4, testItem, testDate, "Cancelled");
        String details = cancelledRecord.getDetails();
        assertTrue(details.contains("Cancelled"));
    }

    @Test
    public void MultiplePurchaseRecordsWithDifferentItems() {
        Item item1 = new Item(1, "Laptop", 1200.0, "electronics", 10);
        Item item2 = new Item(2, "Mouse", 25.0, "electronics", 50);
        
        PurchaseRecord record1 = new PurchaseRecord(1, item1, testDate, "Purchased");
        PurchaseRecord record2 = new PurchaseRecord(2, item2, testDate, "Purchased");
        
        assertTrue(record1.getDetails().contains("Laptop"));
        assertTrue(record2.getDetails().contains("Mouse"));
    }

    @Test
    public void PurchaseRecordWithDifferentDates() {
        Date date1 = new Date(System.currentTimeMillis() - 86400000); 
        Date date2 = new Date(); 
        
        PurchaseRecord record1 = new PurchaseRecord(1, testItem, date1, "Purchased");
        PurchaseRecord record2 = new PurchaseRecord(2, testItem, date2, "Purchased");
        
        assertNotEquals(record1.getDetails(), record2.getDetails());
    }


    @Test
    public void PurchaseRecordWithSpecialCharactersInItemName() {
        Item specialItem = new Item(10, "T-Shirt (XL) & Hat", 50.0, "apparel", 20);
        PurchaseRecord specialRecord = new PurchaseRecord(1, specialItem, testDate, "Purchased");
        
        String details = specialRecord.getDetails();
        assertTrue(details.contains("T-Shirt (XL) & Hat"));
    }

    @Test
    public void SequentialRecordIds() {
        PurchaseRecord record1 = new PurchaseRecord(1, testItem, testDate, "Purchased");
        PurchaseRecord record2 = new PurchaseRecord(2, testItem, testDate, "Purchased");
        PurchaseRecord record3 = new PurchaseRecord(3, testItem, testDate, "Purchased");
        
        assertNotNull(record1);
        assertNotNull(record2);
        assertNotNull(record3);
    }

    @Test
    public void PurchaseRecordWithPastDate() {
        Date pastDate = new Date(System.currentTimeMillis() - 7 * 86400000); 
        PurchaseRecord pastRecord = new PurchaseRecord(1, testItem, pastDate, "Delivered");
        
        String details = pastRecord.getDetails();
        assertTrue(details.contains(pastDate.toString()));
    }

    @Test
    public void PurchaseRecordDetailsNotEmpty() {
        String details = record.getDetails();
        assertFalse(details.isEmpty());
    }

    @Test
    public void MultiplePurchaseRecordsForSameItem() {
        PurchaseRecord record1 = new PurchaseRecord(1, testItem, testDate, "Purchased");
        
        Date laterDate = new Date(System.currentTimeMillis() + 3600000);
        PurchaseRecord record2 = new PurchaseRecord(2, testItem, laterDate, "Shipped");
        
        assertTrue(record1.getDetails().contains("Purchased"));
        assertTrue(record2.getDetails().contains("Shipped"));
    }

}
