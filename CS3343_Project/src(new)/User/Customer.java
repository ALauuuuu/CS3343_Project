package User;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import Instances.ItemInventory;
import Objects.Item;
import Objects.Notification;
import Objects.PurchaseRecord;
import Objects.ShoppingCart;


public class Customer {
    private String userName;
    private ShoppingCart shoppingCart;
    private List<PurchaseRecord> purchaseHistory;
    private List<Notification> notifications;
    private static Scanner userInput = new Scanner(System.in);
    public Customer(String userName){
        this.userName = userName;
        this.shoppingCart = new ShoppingCart();
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
        //boolean IsHomepage = true;
        int page = 0;
        while (loggedIn) {
            System.out.println("\nOnline Shopping > Home Page");
            System.out.println("--------------------------");
            System.out.println("Option: 1 Next page");
            System.out.println("Option: 2 Previous page");
            System.out.println("Option: 3 Search");
            System.out.println("Option: 4 Shopping cart");
            System.out.println("Option: 5 Account information");
            System.out.println("Option: 6 Purchasing history");
            System.out.println("Option: 7 Notification");
            System.out.println("Option: 8 Log out");
            System.out.println("--------------------------\n");
            try{
                //if (IsHomepage) {
                    ItemInventory.showItems(page * 9, (page + 1) * 9);
                    //IsHomepage = false;
                //}
                System.out.println("Please enter an option number(an integer): ");
                int choice = userInput.nextInt();
                    switch (choice) {
                        case 1: // Next page
                            page++;
                            if (page * 9 >= ItemInventory.getItems().size()) {
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
                            this.searchMenu();
                            break;
                        case 4: // Shopping cart
                            System.out.println("Shopping cart functionality to be implemented.");
                            this.shoppingCartMenu();
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

    private void searchMenu(){
        while (true) {
            System.out.println("Online Shopping > Search");
            System.out.println("Option: 1 Search By Name ");
            System.out.println("Option: 2 Search By Code ");
            System.out.println("Option: 3 Search By Category ");
            System.out.println("Option: 4 Back to Home Page ");
            System.out.println("Please enter an option number(an integer):");
            try {
                int choice = userInput.nextInt();
                switch (choice) {
                    case 1:
                        searchByName();
                        break; // Exit the method
                    case 2:
                        searchByCode();
                        break; // Exit the method
                    case 3:
                        searchByCategory();
                        break;
                    case 4:
                        return;
                    default:
                        System.out.println("Invalid option, please try again.\n");
                        break; // Continue the loop
                }
            } catch (InputMismatchException e) {
                System.out.println("Wrong input, please enter an integer.\n");
                userInput.nextLine(); //clear buffer
            }
        }
    }
    private void searchByName(){
        System.out.println("Please enter item name:");
        List<Item> target = ItemInventory.searchByName(userInput.next());
        System.out.println("There are "+target.size()+" item(s) found.");
        for(int i=0; i < target.size(); i++){
            System.out.println(target.get(i).getDetails());
        }
    }
    private void searchByCode(){
        System.out.println("Please enter item code:");
        try{
            Item target = ItemInventory.searchByCode(userInput.nextInt());
            System.out.println(target.getDetails());
        } catch (InputMismatchException e){
            System.out.println("Wrong input, please enter an integer.\n");
            userInput.nextLine(); //clear buffer
            searchByCode();
        }
            
    }
    private void searchByCategory(){
        System.out.println("Please select item Category:");
        List<String> allCat = ItemInventory.getAllCategory();
        for(int i = 0; i < allCat.size(); i++)
            System.out.println("Option: "+(i+1)+" "+allCat.get(i));
        try{
            List<Item> target = ItemInventory.searchByCategory(allCat.get(userInput.nextInt()-1));
            System.out.println("There are "+target.size()+" item(s) found.");
            for(int i=0; i < target.size(); i++)
                System.out.println(target.get(i).getDetails());
        } catch (InputMismatchException e){
            System.out.println("Wrong input, please enter an integer.\n");
            userInput.nextLine(); //clear buffer
            searchByCategory();
        }   
    }

    private void shoppingCartMenu(){
        if(this.shoppingCart.isEmpty()){
            System.out.println("The Shopping Cart is empty.");
            return;
        }
        System.out.println("\nOnline Shopping > Shopping Cart");

    }
}
