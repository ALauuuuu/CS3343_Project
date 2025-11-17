package Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.Date;

import java.util.ArrayList;
import java.util.List;

import Objects.Item;
import Objects.PurchaseRecord;
import Services.TransactionManager;
import Objects.Transaction;

public class TransactionManagerTest {
	Date date = new Date();
	
	@Test
	void AddTransaction() {
		List<PurchaseRecord> items = new ArrayList<>();
		Item item1 = new Item(1, "Item1", 10.0, "Category1", 100);
		items.add(new PurchaseRecord(1, item1, date, "Completed"));
		TransactionManager manager = new TransactionManager();
		Transaction transaction = new Transaction(1, items, 100.0, date, "Payment");
		manager.addTransaction(transaction);
		assertEquals(1, manager.getTransactions().size());
	}
	
	@Test
	void NotifyUser() {
		TransactionManager manager = new TransactionManager();
		assertTrue(manager.notifyUser());
	}
	
}


