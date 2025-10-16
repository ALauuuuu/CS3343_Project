package models;

import java.util.List;

import components.ShoppingCart;
import interfaces.FilterCriteria;
import interfaces.SearchCriteria;
import interfaces.PaymentMethod;

public class Customer extends User {
    private ShoppingCart shoppingCart;
    private List<PurchaseRecord> purchaseHistory;
    private List<Notification> notifications;

    public Customer(String username) {
        super(username);
        this.shoppingCart = new ShoppingCart();
    }

    public List<Item> searchItems(SearchCriteria criteria) {
        return null;
    }

    public List<Item> filterItems(FilterCriteria criteria) {
        return null;
    }

    public boolean addToCart(Item item) {
        return shoppingCart.addItem(item, 1);
    }

    public boolean removeFromCart(Item item) {
        return shoppingCart.removeItem(item);
    }

    public Receipt checkout(PaymentMethod paymentMethod) {
        double total = shoppingCart.calculateTotal();
        boolean success = paymentMethod.pay(total);
        if (success) {
            shoppingCart.clearCart();
            System.out.println("Payment successful.");
            return new Receipt();
        } else {
            System.out.println("Payment failed.");
            return null;
        }
    }

    public boolean rateItem(int itemId, int rating, String comment) {
        return true;
    }

    public boolean requestRefund(PurchaseRecord purchaseRecord) {
        return true;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public List<PurchaseRecord> getPurchaseHistory() {
        return purchaseHistory;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setPurchaseHistory(List<PurchaseRecord> purchaseHistory) {
        this.purchaseHistory = purchaseHistory;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }
}

