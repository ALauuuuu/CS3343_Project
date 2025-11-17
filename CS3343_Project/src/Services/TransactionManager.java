package Services;

import java.util.List;
import java.util.ArrayList;

import Objects.Transaction;

public class TransactionManager {
    private List<Transaction> transactions = new ArrayList<>();

    public void addTransaction(Transaction transaction) {
    	this.transactions.add(transaction);
    }
    
	public List<Transaction> getTransactions() {
		return transactions;
	}
	
    public boolean notifyUser() {
        // Logic to notify user
        return true;
    }
}
