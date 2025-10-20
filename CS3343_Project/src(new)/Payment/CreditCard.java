package Payment;

import java.util.Date;

public class CreditCard implements PaymentMethod {
    private String cardNumber;
    private Date expiryDate;

    public CreditCard(String cardNumber, Date expiryDate) {
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    @Override
    public boolean pay(double amount) {
        System.out.println("Paid " + amount + " using Credit Card " + cardNumber);
        return true;
    }

    @Override
    public boolean refund(double amount) {
        System.out.println("Refunded " + amount + " to Credit Card " + cardNumber);
        return true;
    }
}
