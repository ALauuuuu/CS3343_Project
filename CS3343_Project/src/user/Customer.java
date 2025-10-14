package user;

import shopping.Item;
import shopping.ShoppingCart;
import shopping.PurchaseRecord;
import notification.Notification;
import payment.PaymentMethod;
import transaction.Transaction;
import java.util.List;

public class Customer extends User {

    public Customer(String username) {
        super(username); // Explicit call to the User class constructor
    }

    @Override
    public void viewNotifications() {
        // Implementation for viewing notifications
        for (Notification notification : notifications) {
            System.out.println(notification);
        }
    }
    private ShoppingCart shoppingCart;
    private List<PurchaseRecord> purchaseHistory;
    private List<Notification> notifications;

    public List<Item> searchItems(String criteria) {
        return null;
    }

    public List<Item> filterItems(String criteria) {
        return null;
    }

    public boolean addToCart(Item item) {
        return false;
    }

    public boolean removeFromCart(Item item) {
        return false;
    }

    public Transaction checkout(PaymentMethod paymentMethod) {
        return null;
    }

    public boolean rateItem(int itemId, int rating, String comment) {
        return false;
    }

    public boolean requestRefund(PurchaseRecord purchaseRecord) {
        return false;
    }
}
