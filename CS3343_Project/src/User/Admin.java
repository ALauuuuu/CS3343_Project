package User;

import java.util.InputMismatchException;
import java.util.Scanner;

import Instances.ItemInventory;
import Services.NotificationManager;
import Services.TransactionManager;
import Objects.Notification;
import Objects.Item;

public class Admin {
    private static Scanner userInput = new Scanner(System.in);
    private String userName;
    private NotificationManager notificationManager;
    private TransactionManager transactionManager;

    public Admin(String userName){
        this.userName = userName;
        this.notificationManager = new NotificationManager();
        this.transactionManager = new TransactionManager();
    }
    
    public void login(){
        System.out.println(userName + " logged in.\n");
        this.homeMenu();
    }
    private void logout(){
        System.out.println(userName + " logged out.");
    }
    private void homeMenu(){
        boolean loggedIn = true;
        while (loggedIn) {
            System.out.println("\nOnline Shopping > Admin Page");
            System.out.println("--------------------------");
            System.out.println("Option: 1  Add Items");
            System.out.println("Option: 2  Remove Items");
            System.out.println("Option: 3  Edit Items");
            System.out.println("Option: 4  View Notifications");
            System.out.println("Option: 5  Log out");
            System.out.println("--------------------------\n");
            try{
                System.out.println("Please enter an option number(an integer): ");
                int choice = userInput.nextInt();

                    switch (choice) {
                        case 1:
                            System.out.println("==========================");
                            System.out.println("Add Items page");
                            handleAddItem();
                            break;
                            
                        case 2:
                            System.out.println("==========================");
                            System.out.println("Remove Items page");
                            handleRMItem();
                            break;
                        
                        case 3:
                            System.out.println("==========================");
                            System.out.println("Edit Items page");
                            handleEditItem();
                            break;

                        case 4:
                            System.out.println("==========================");
                            System.out.println("View Notifications page");
                            handleViewNotifications();
                            break;

                        case 5:
                            System.out.println("==========================");
                            System.out.println("Logging out......");
                            this.logout();
                            loggedIn = false;
                            break;
                            
                        default:
                            System.out.println("Invalid option. Please try again.");
                            break;
                    }
            } catch (InputMismatchException e){
                System.out.println("Wrong input, please enter an integer.\n");
                userInput.nextLine(); //clear buffer
            }
        }
        return;
    }

    public boolean addItem(Item newItem) {
        // add item to inventory
        ItemInventory.addItem(newItem);
        return true; // pending to add actual success check
    }

    public boolean removeItem(int itemId) {
        // remove item from inventory
        ItemInventory.removeItem(itemId);
        return true; // pending to add actual success check
    }

    public boolean edititem(int itemId, Item updatedInfo) {
        // edit item in inventory
        ItemInventory.removeItem(itemId);
        ItemInventory.addItem(updatedInfo);
        return true; // pending to add actual success check
    }

    public void displayinventory() {
        System.out.println("Current Inventory:");
        ItemInventory.showItems(0, ItemInventory.getItems().size());
        System.out.println();
    }

    private void handleAddItem() {
        displayinventory();

        System.out.println("Please enter item name: ");
        String name = userInput.next();

        double price = 0;
        while (true) {
            System.out.println("Item price (number): ");
        try {
            price = userInput.nextDouble();
            if (price < 0) {
                System.out.println("Price cannot be negative. Please enter a valid number.");
                continue;
            }
            break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a NUMBER for price.");
                userInput.nextLine(); // Clear buffer
        }
        }
        
        System.out.println("item category: ");
        String category = userInput.next();
        
        int quantity = 0;
        while (true) {
            System.out.println("Item quantity (integer): ");
            try {
                quantity = userInput.nextInt();
                if (quantity < 0) {
                    System.out.println("Quantity cannot be negative. Please enter a positive integer.");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter an INTEGER for quantity.");
                userInput.nextLine();
            }
        }

        Item newItem = new Item(ItemInventory.getItemCount() + 1, name, price, category, quantity);
        
        System.out.println("Confirm addition?(y/n): ");
        char confirm = userInput.next().charAt(0);
        if (confirm == 'y' || confirm == 'Y') {
            if (this.addItem(newItem)) {
                System.out.println("Item added.\n");
                this.notificationManager.addNotifications(
                    new Notification("New item " + name + " added to inventory.", new java.util.Date()));
            } else {
                System.out.println("Addition is failed.");
            }
        } else {
            System.out.println("Addition is cancelled.\n");
        }

        System.out.println("==========================");
        System.out.println("Option 1: Add another item");
        System.out.println("Option 2: Return to Admin Menu");
        System.out.println("Please enter your next action: ");
        int choice = userInput.nextInt();
        if (choice == 1) {
            handleAddItem();
        } else {
            return;
        }

    }
    
    private void handleRMItem() {
        displayinventory();

        System.out.println("Please enter item code to remove: ");
        int itemId = userInput.nextInt();
        System.out.println("Confirm removal?(y/n): ");
        char confirm = userInput.next().charAt(0);
        if (confirm == 'y' || confirm == 'Y') {
            if (this.removeItem(itemId)) {
                System.out.println("Item removed.\n");
                this.notificationManager.addNotifications(
                    new Notification("Item with code " + itemId + " removed from inventory.", new java.util.Date()));
            } else {
                System.out.println("Removal cancelled.\n");
            }
        } else {
            System.out.println("Removal cancelled.\n");
        }

        System.out.println("==========================");
        System.out.println("Option 1: Remove another item");
        System.out.println("Option 2: Return to Admin Menu");
        System.out.println("Please enter your next action: ");
        int choice = userInput.nextInt();
        if (choice == 1) {
            handleRMItem();
        } else {
            return;
        }
    }

    private void handleEditItem() {
        displayinventory();

        System.out.println("Please enter item code to edit: ");
        int itemId = userInput.nextInt();

        System.out.println("Please enter new item name: ");
        String name = userInput.next();

        double price = 0;
        while (true) {
            System.out.println("Item price (number): ");
        try {
            price = userInput.nextDouble();
            if (price < 0) {
                System.out.println("Price cannot be negative. Please enter a valid number.");
                continue;
            }
            break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a NUMBER for price.");
                userInput.nextLine(); // Clear buffer
        }
        }

        System.out.println("item category: ");
        String category = userInput.next();
        
        int quantity = 0;
        while (true) {
            System.out.println("Item quantity (integer): ");
            try {
                quantity = userInput.nextInt();
                if (quantity < 0) {
                    System.out.println("Quantity cannot be negative. Please enter a positive integer.");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter an INTEGER for quantity.");
                userInput.nextLine();
            }
        }

        Item updatedInfo = new Item(itemId, name, price, category, quantity);

        System.out.println("Confirm edit?(y/n): ");
        char confirm = userInput.next().charAt(0);
        if (confirm == 'y' || confirm == 'Y') {
            if (this.edititem(itemId, updatedInfo)) {
                System.out.println("Item edited.");
                this.notificationManager.addNotifications(
                    new Notification("Item with code " + itemId + " edited in inventory.", new java.util.Date()));
            } else {
                System.out.println("Edit cancelled.\n");
            }
        } else {
            System.out.println("Edit cancelled.\n");
        }

        System.out.println("==========================");
        System.out.println("Option 1: Edit another item");
        System.out.println("Option 2: Return to Admin Menu");
        System.out.println("Please enter your next action: ");
        
        int choice = userInput.nextInt();
        if (choice == 1) {
            handleEditItem();
        } else {
            return;
        }
    }

    private void handleViewNotifications() {
        System.out.println("Displaying notifications...");
        for (Notification notification : this.notificationManager.getNotifications()) {
            System.out.println(notification.getDate() + " - " + notification.getMessage());
        }
        System.out.println("==========================");
        System.out.println("Option 1: Refresh notifications");
        System.out.println("Option 2: Return to Admin Menu");
        System.out.println("Please enter your next action: ");
        int choice = userInput.nextInt();
        if (choice == 1) {
            handleViewNotifications();
        } else {
            return;
        }
    }
}
