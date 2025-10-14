package payment;

import java.util.Date;

public class CreditCard implements PaymentMethod {
    private String cardNumber;
    private Date expiryDate;

    @Override
    public boolean pay(double amount) {
        return false;
    }

    @Override
    public boolean refund(double amount) {
        return false;
    }
}
