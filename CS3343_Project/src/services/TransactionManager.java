package Services;

import java.util.List;

import Objects.Transaction;

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
