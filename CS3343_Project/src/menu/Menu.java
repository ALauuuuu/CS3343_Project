package menu;

import java.util.Scanner;
import shopping.ItemInventory;
import shopping.Item;

public class Menu {
    private static ItemInventory itemInventory = new ItemInventory();
    private static int currentPage = 0;
    private static final int itemsPerPage = 9;

    public static void showLoginPage() {
        Scanner input = new Scanner(System.in);
        System.out.println("Online Shopping > Login");
        System.out.println("Option: 1 Customer ");
        System.out.println("Option: 2 Admin ");
        System.out.println("Please enter an option number(an integer):");
        int choice = input.nextInt();
        
        switch (choice) {
            case 1:
                //Customer.login();
                System.out.println("Login as Customer");
                showHomePage();
                break;
            
            case 2:
                //User.login();
                System.out.println("Login as Admin");
                showAdminPage();
                break;

            default:
                System.out.println("Wrong input, try again");
                input.close();
                showLoginPage(); // Recursive call to handle wrong input
                break;
        }
        input.close();
        return;
    }

    public static void showHomePage() {
        itemInventory.showItems(currentPage * itemsPerPage, (currentPage + 1) * itemsPerPage);
        
        System.out.println("Option: 1 Next page");
        System.out.println("Option: 2 Searching/Sorting");
        System.out.println("Option: 3 Item details");
        System.out.println("Option: 4 Shopping cart");
        System.out.println("Option: 5 Wallet");
        System.out.println("Option: 6 Purchase history");
        System.out.println("Option: 7 Notification");
        System.out.println("Option: 8 Refresh");
        System.out.println("Option: 9 Log out");
        System.out.print("Please enter an option number(an integer): ");
        
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                currentPage++;
                showHomePage();
                break;
            case 2:
                showSearchEngine();
                break;
            case 3:
                System.out.print("Please enter the Item code(an integer): ");
                int itemCode = scanner.nextInt();
                Item item = itemInventory.findItemByCode(itemCode);
                if (item != null) {
                    System.out.println(item.getDetails());
                } else {
                    System.out.println("Item not found.");
                }
                showHomePage();
                break;
            default:
                System.out.println("Invalid option.");
                showHomePage();
                break;
        }
    }

    public static void showShoppingCart() {}
    public static void showSearchEngine() {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Online shopping > Searching/Sorting");
            System.out.println("Option: 1 Searching");
            System.out.println("Option: 2 Sorting");
            System.out.print("Please enter an option number(an integer): ");
            int choice = scanner.nextInt();
            if (choice == 1) {
                System.out.println("Option: 1 Search by Name");
                System.out.println("Option: 2 Search by Item Code");
                System.out.println("Option: 3 Search by Categories");
                System.out.print("Please enter an option number(an integer): ");
                int searchChoice = scanner.nextInt();
                // Implement search logic
            } else if (choice == 2) {
                System.out.println("Option: 1 Sort by price");
                System.out.println("Option: 2 Sort by rating");
                System.out.print("Please enter an option number(an integer): ");
                int sortChoice = scanner.nextInt();
                System.out.println("Option: 1 Ascending");
                System.out.println("Option: 2 Descending");
                System.out.print("Please enter an option number(an integer): ");
                int orderChoice = scanner.nextInt();
                // Implement sort logic
            }
        } finally {
            scanner.close();
        }
        showHomePage();
    }
    public static void showWallet() {}
    public static void showAdminPage() {}
}
