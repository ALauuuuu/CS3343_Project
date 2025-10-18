package main;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Searching {
    private static final Scanner input = new Scanner(System.in);
    public static void searchMenu(){
        while (true) {
            System.out.println("Online Shopping > Search");
            System.out.println("Option: 1 Search By Name ");
            System.out.println("Option: 2 Search By Code ");
            System.out.println("Option: 3 Search By Category ");
            System.out.println("Please enter an option number(an integer):");
            try {
                int choice = input.nextInt();
                switch (choice) {
                    case 1:
                        searchByName();
                        break; // Exit the method
                    case 2:
                        
                        break; // Exit the method
                    case 3:

                        break;
                    default:
                        System.out.println("Invalid option, please try again.\n");
                        break; // Continue the loop
                }
            } catch (InputMismatchException e) {
                System.out.println("Wrong input, please enter an integer.\n");
                searchMenu(); // Recursive call to restart the search process
            }
        }
    }
    private static void searchByName(){
        System.out.println("");
    }

}
