package Main;

import Instances.ItemInventory;
import Menu.Menu;
import Objects.Item;

public class Main {
    public static void main(String[] args) {
        initializeInventory();
        Menu.loginPage();
        return;
    }
    private static void initializeInventory() {
        // Add 12 default items
        ItemInventory.addItem(new Item(1, "Toys", 100, "others", 10));
        ItemInventory.addItem(new Item(2, "Snack", 20, "food", 100));
        ItemInventory.addItem(new Item(3, "Shampoo", 100, "personal care", 50));
        ItemInventory.addItem(new Item(4, "Books", 150, "education", 30));
        ItemInventory.addItem(new Item(5, "Pen", 20, "stationery", 200));
        ItemInventory.addItem(new Item(6, "Shoes", 500, "apparel", 20));
        ItemInventory.addItem(new Item(7, "Laptop", 1200, "electronics", 15));
        ItemInventory.addItem(new Item(8, "Mouse", 25, "electronics", 100));
        ItemInventory.addItem(new Item(9, "Keyboard", 75, "electronics", 75));
        ItemInventory.addItem(new Item(10, "T-shirt", 250, "apparel", 80));
        ItemInventory.addItem(new Item(11, "Jeans", 600, "apparel", 40));
        ItemInventory.addItem(new Item(12, "Hat", 120, "apparel", 60));
    }
}
