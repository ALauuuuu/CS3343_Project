package User;

import java.util.InputMismatchException;
import java.util.Scanner;

import Objects.Item;

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
            System.out.println("Option: 4 View Notifications");
            System.out.println("Option: 5 Log out");
            System.out.println("--------------------------\n");
            try{
                System.out.println("Please enter an option number(an integer): ");
                int choice = userInput.nextInt();
                    switch (choice) {
                        case 1:
                            System.out.println("Please enter item name, price, category, quantity: ");
                            String name = userInput.next();
                            double price = userInput.nextDouble();
                            String category = userInput.next();
                            int quantity = userInput.nextInt();
                            Item newItem = new Item(0, name, price, category, quantity);
                            System.out.println("Confirm addition?(y/n): ");
                            char confirm = userInput.next().charAt(0);
                            if (confirm == 'y' || confirm == 'Y') {
                                System.out.println("Item added.");
                                this.addItem(newItem);
                            } else {
                                System.out.println("Addition cancelled.");
                            }
                            break;
                            
                        case 2:
                            System.out.println("Remove Items selected.");
                            // Logic to remove items
                            break;
                        
                        case 3:
                            System.out.println("Edit Items selected.");
                            // Logic to edit items
                            break;

                        case 4:
                            System.out.println("View Notifications selected.");
                            // Logic to view notifications
                            break;

                        case 5:
                            System.out.println("Logging out...");
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
        // Logic to add item
        return true;
    }

    public boolean removeItem(int itemId) {
        // Logic to remove item
        return true;
    }

    public boolean edititem(int itemId, Item updatedInfo) {
        // Logic to update item details
        //Item.setMethod should be add to prevent deleting directly
        //ItemInventory.getInstance().removeItem(itemId);
        //ItemInventory.getInstance().addItem(updatedInfo);
        return true;
    }
}
