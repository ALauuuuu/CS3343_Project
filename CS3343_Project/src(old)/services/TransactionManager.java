package services;

import models.Transaction;
import java.util.List;

public class TransactionManager {
    private List<Transaction> transactions;

    public boolean addTransaction(Transaction transaction) {
        return transactions.add(transaction);
    }

    public boolean notifyUser() {
        // Logic to notify user
        return true;
    }
}
