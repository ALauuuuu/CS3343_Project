package payment;

public class InternalWallet implements PaymentMethod {
    private double balance;

    @Override
    public boolean pay(double amount) {
        return false;
    }

    @Override
    public boolean refund(double amount) {
        return false;
    }
}
