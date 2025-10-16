package main;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {
    // It's better to have one scanner for System.in that is not closed.
    private static final Scanner input = new Scanner(System.in);

    public static int showLoginPage() {
        while (true) {
            System.out.println("Online Shopping > Login");
            System.out.println("Option: 1 Customer ");
            System.out.println("Option: 2 Admin ");
            System.out.println("Please enter an option number(an integer):");

            try {
                int choice = input.nextInt();
                switch (choice) {
                    case 1:
                        // User.login();
                        System.out.println("Login as Customer");
                        return 1; // Exit the method

                    case 2:
                        // User.login();
                        System.out.println("Login as Admin");
                        return 2; // Exit the method

                    default:
                        System.out.println("Invalid option, please try again.\n");
                        break; // Continue the loop
                }
            } catch (InputMismatchException e) {
                System.out.println("Wrong input, please enter an integer.\n");
                showLoginPage(); // Recursive call to restart the login process
            }
        }
    }

    public static void showHomePage() {
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
    }

    public static void showAdminPage() {
        System.out.println("Welcome Admin!\n");
        // Add admin menu options here
    }
}
