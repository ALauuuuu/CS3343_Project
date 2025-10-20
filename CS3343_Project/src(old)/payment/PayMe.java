package payment;

import interfaces.PaymentMethod;

public class PayMe implements PaymentMethod {
    private String accountId;

    public PayMe(String accountId) {
        this.accountId = accountId;
    }

    @Override
    public boolean pay(double amount) {
        System.out.println("Paid " + amount + " using PayMe " + accountId);
        return true;
    }

    @Override
    public boolean refund(double amount) {
        System.out.println("Refunded " + amount + " to PayMe " + accountId);
        return true;
    }
}
