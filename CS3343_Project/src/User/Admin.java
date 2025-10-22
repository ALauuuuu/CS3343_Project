package User;

import java.util.InputMismatchException;
import java.util.Scanner;

import Objects.Item;
import Instances.ItemInventory;
import Services.NotificationManager;

public class Admin {
    private static Scanner userInput = new Scanner(System.in);
    private String userName;
    public Admin(String userName){
        this.userName = userName;
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
                            System.out.println("Add Items page");
                            handleAddItem();
                            break;
                            
                        case 2:
                            System.out.println("Remove Items page");
                            handleRMItem();
                            break;
                        
                        case 3:
                            System.out.println("Edit Items page");
                            handleEditItem();
                            break;

                        case 4:
                            System.out.println("View Notifications page");
                            handleViewNotifications();
                            break;

                        case 5:
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
        // add notification 
        return true;
    }

    public boolean removeItem(int itemId) {
        // remove item from inventory
        ItemInventory.removeItem(itemId);
        // add notification 
        return true;
    }

    public boolean edititem(int itemId, Item updatedInfo) {
        // edit item in inventory
        ItemInventory.removeItem(itemId);
        ItemInventory.addItem(updatedInfo);

        // add notification
        return true;
    }

    private void handleAddItem() {

        System.out.println("Please enter item name: ");
        String name = userInput.next();
        System.out.println("item price: ");
        double price = userInput.nextDouble();
        System.out.println("item category: ");
        String category = userInput.next();
        System.out.println("item quantity: ");
        int quantity = userInput.nextInt();

        Item newItem = new Item(0, name, price, category, quantity);
        
        System.out.println("Confirm addition?(y/n): ");
        char confirm = userInput.next().charAt(0);
        if (confirm == 'y' || confirm == 'Y') {
            if (this.addItem(newItem)) {
                System.out.println("Item added.");
            } else {
                System.out.println("Addition is failed.");
            }
        } else {
            System.out.println("Addition is cancelled.");
        }

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
        System.out.println("Please enter item code to remove: ");
        int itemId = userInput.nextInt();
        System.out.println("Confirm removal?(y/n): ");
        char confirm = userInput.next().charAt(0);
        if (confirm == 'y' || confirm == 'Y') {
            if (this.removeItem(itemId)) {
                System.out.println("Item removed.");
            } else {
                System.out.println("Removal cancelled.");
            }
        } else {
            System.out.println("Removal cancelled.");
        }
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
        System.out.println("Please enter item code to edit: ");
        int itemId = userInput.nextInt();
        System.out.println("Please enter new item name, price, category, quantity: ");

        String name = userInput.next();
        double price = userInput.nextDouble();
        String category = userInput.next();
        int quantity = userInput.nextInt();
        Item updatedInfo = new Item(itemId, name, price, category, quantity);

        System.out.println("Confirm edit?(y/n): ");
        char confirm = userInput.next().charAt(0);
        if (confirm == 'y' || confirm == 'Y') {
            if (this.edititem(itemId, updatedInfo)) {
                System.out.println("Item edited.");
            } else {
                System.out.println("Edit cancelled.");
            }
        } else {
            System.out.println("Edit cancelled.");
        }

        System.out.println("\nOption 1: Edit another item");
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
        // Logic to display notifications
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
