package Payment;

public class BankAccount implements PaymentMethod {
    private String accountId;

    public BankAccount(String accountId) {
        this.accountId = accountId;
    }

    @Override
    public boolean pay(double amount) {
        System.out.println("Paid " + amount + " using Bank Account " + accountId);
        return true;
    }

    @Override
    public boolean refund(double amount) {
        System.out.println("Refunded " + amount + " to Bank Account " + accountId);
        return true;
    }
}
