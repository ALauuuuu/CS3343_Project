package models;

import components.ItemInventory;

public class Admin extends User {
    public Admin(String username) {
        super(username);
    }

    public boolean addItem(Item newItem) {
        // Logic to add item
        ItemInventory.getInstance().addItem(newItem);
        return true;
    }

    public boolean removeItem(int itemId) {
        // Logic to remove item
        ItemInventory.getInstance().removeItem(itemId);
        return true;
    }

    public boolean updateItemDetails(int itemId, Item updatedInfo) {
        // Logic to update item details
        //Item.setMethod should be add to prevent deleting directly
        //ItemInventory.getInstance().removeItem(itemId);
        //ItemInventory.getInstance().addItem(updatedInfo);
        return true;
    }
}
