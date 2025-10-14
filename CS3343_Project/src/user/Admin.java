package user;

import shopping.Item;
import java.util.List;

public class Admin extends User {
    private List<Item> managedItems;

    public Admin(String username, String password) {
        super(username);
    }

    @Override
    public void viewNotifications() {
        // Implementation for viewing notifications
        System.out.println("Viewing notifications for Admin: " + getUsername());
    }

    public boolean addItem(Item newItem) {
        return false;
    }

    public boolean removeItem(int itemId) {
        return false;
    }

    public boolean updateItemDetails(int itemId, Item updatedInfo) {
        return false;
    }
}
