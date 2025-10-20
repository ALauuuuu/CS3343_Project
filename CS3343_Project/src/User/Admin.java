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
            System.out.println("Admin functions will soon available");
            System.out.println("Option: 8 Log out");
            System.out.println("--------------------------\n");
            try{
                System.out.println("Please enter an option number(an integer): ");
                int choice = userInput.nextInt();
                    switch (choice) {
                        case 8:
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
    public boolean updateItemDetails(int itemId, Item updatedInfo) {
        // Logic to update item details
        //Item.setMethod should be add to prevent deleting directly
        //ItemInventory.getInstance().removeItem(itemId);
        //ItemInventory.getInstance().addItem(updatedInfo);
        return true;
    }
}
