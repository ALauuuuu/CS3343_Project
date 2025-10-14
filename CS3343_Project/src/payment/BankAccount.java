package payment;

public class BankAccount implements PaymentMethod {
    private String accountId;

    @Override
    public boolean pay(double amount) {
        return false;
    }

    @Override
    public boolean refund(double amount) {
        return false;
    }
}
