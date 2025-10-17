package models;

import java.util.Date;
import java.util.List;

import components.ItemInventory;
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
        Item target = ItemInventory.getInstance().findItemByCode(itemId);
        int reviewId = 0; // should be making a function to generate unique id or not using it
        int userId = 0; // should be making a function to generate unique id or passing the User obj
        Date date = new Date();
        Review review = new Review(reviewId,userId,rating,comment,date);
        target.addReview(review);
        return true;
    }

    //this function need a new singleton class AllRefundRequests for saving all requests (maybe also a new class RefundRequest)
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

