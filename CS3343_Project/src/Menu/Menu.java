package Menu;

import java.util.InputMismatchException;
import java.util.Scanner;

import User.Customer;
import User.Admin;

public class Menu {
    private static Scanner userInput = new Scanner(System.in);
    public static void loginPage(){
        String userName;
        Admin currentAdmin = new Admin(null);
        Customer currentCustomer = new Customer(null);
        while (true) {
            System.out.println("Online Shopping > Login");
            System.out.println("--------------------------");
            System.out.println("Option: 1 Customer ");
            System.out.println("Option: 2 Admin ");
            System.out.println("Option: 3 Exit ");
            System.out.println("Please enter an option number(an integer):");
            try {
                int choice = userInput.nextInt();
                switch (choice) {
                    case 1:
                        System.out.println("Login as Customer");
                        System.out.print("Enter username: ");
                        userName = userInput.next();
                        currentCustomer = new Customer(userName);
                        currentCustomer.login();
                        break;
                    case 2:
                        System.out.println("Login as Admin");
                        System.out.print("Enter username: ");
                        userName = userInput.next();
                        currentAdmin = new Admin(userName);
                        currentAdmin.login();
                        break;
                    case 3:
                        System.out.println("Exit");
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
}
