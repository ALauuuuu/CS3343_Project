package models;

public class Admin extends User {
    public Admin(String username) {
        super(username);
    }

    public boolean addItem(Item newItem) {
        // Logic to add item
        return true;
    }

    public boolean removeItem(int itemId) {
        // Logic to remove item
        return true;
    }

    public boolean updateItemDetails(int itemId, Item updatedInfo) {
        // Logic to update item details
        return true;
    }
}
