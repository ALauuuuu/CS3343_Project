package main;

import java.util.InputMismatchException;
import java.util.Scanner;

import components.ItemInventory;
import models.Admin;
import models.Customer;
import models.Item;
import models.User;

public class Main {
    private static ItemInventory itemInventory = ItemInventory.getInstance();
    private static User currentUser;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initializeInventory();
        while (true) {

            int choice = Menu.showLoginPage();
            if (choice == 3){
                return; //Exit the program
            }
            System.out.print("Enter username: ");
            String username = scanner.nextLine();

            if (choice == 1) {
                currentUser = new Customer(username);
                currentUser.login();
                handleCustomerMenu();
            
            } else if (choice == 2) {
                currentUser = new Admin(username);
                currentUser.login();
                handleAdminMenu();
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void initializeInventory() {
        // Add 12 default items
        itemInventory.addItem(new Item(1, "Toys", 100, "others", 10));
        itemInventory.addItem(new Item(2, "Snack", 20, "food", 100));
        itemInventory.addItem(new Item(3, "Shampoo", 100, "personal care", 50));
        itemInventory.addItem(new Item(4, "Books", 150, "education", 30));
        itemInventory.addItem(new Item(5, "Pen", 20, "stationery", 200));
        itemInventory.addItem(new Item(6, "Shoes", 500, "apparel", 20));
        itemInventory.addItem(new Item(7, "Laptop", 1200, "electronics", 15));
        itemInventory.addItem(new Item(8, "Mouse", 25, "electronics", 100));
        itemInventory.addItem(new Item(9, "Keyboard", 75, "electronics", 75));
        itemInventory.addItem(new Item(10, "T-shirt", 250, "apparel", 80));
        itemInventory.addItem(new Item(11, "Jeans", 600, "apparel", 40));
        itemInventory.addItem(new Item(12, "Hat", 120, "apparel", 60));
    }

    private static void handleCustomerMenu() {
        boolean loggedIn = true;
        //boolean IsHomepage = true;
        int page = 0;
        while (loggedIn) {
            try{
                Menu.showHomePage();
                //if (IsHomepage) {
                    itemInventory.showItems(page * 9, (page + 1) * 9);
                    //IsHomepage = false;
                //}
                System.out.println("Please enter an option number(an integer): ");
                int choice = scanner.nextInt();
                    switch (choice) {
                        case 1: // Next page
                            page++;
                            if (page * 9 >= itemInventory.getItems().size()) {
                                System.out.println("You are on the last page.");
                                page--;
                            }
                            //IsHomepage = true;
                            break;
                        case 2: // Previous page
                            if (page > 0) {
                                page--;
                            } else {
                                System.out.println("You are on the first page.");
                            }
                            //IsHomepage = true;
                            break;
                        case 3: // Search
                            System.out.println("Search functionality to be implemented.");
                            Searching.searchMenu();
                            break;
                        case 4: // Shopping cart
                            System.out.println("Shopping cart functionality to be implemented.");
                            break;
                        case 5: // Account information
                            System.out.println("Account information functionality to be implemented.");
                            break;
                        case 6: // Purchasing history
                            System.out.println("Purchasing history functionality to be implemented.");
                            break;
                        case 7: // Notification
                            System.out.println("Notification functionality to be implemented.");
                            break;
                        case 8: // Log out
                            currentUser.logout();
                            loggedIn = false;
                            break;
                        default:
                            System.out.println("Invalid option. Please try again.");
                            break;
                    }
            } catch (InputMismatchException e){
                System.out.println("Wrong input, please enter an integer.\n");
                scanner.nextLine(); //clear buffer
            }
            
        }
    }

    private static void handleAdminMenu() {
        boolean loggedIn = true;
        while (loggedIn) {
            Menu.showAdminPage();
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 5: // Log out
                    currentUser.logout();
                    loggedIn = false;
                    break;
                // Other cases will be implemented later
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }
}
