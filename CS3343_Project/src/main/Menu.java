package main;

import java.util.Scanner;

public class Menu {
    public static void showLoginPage(){
        Scanner input = new Scanner(System.in);
        System.out.println("Online Shopping > Login");
        System.out.println("Option: 1 Customer ");
        System.out.println("Option: 2 Admin ");
        System.out.println("Please enter an option number(an integer):");
        int choice = input.nextInt();
        input.close();
        switch (choice) {
            case 1:
                //User.login();
                System.out.println("Login as Customer");
                break;
            
            case 2:
                //User.login();
                System.out.println("Login as Admin");
                break;

            default:
                System.out.println("Wrong input, try again");
                //i forgot how to call this function itself correctly bruh
                break;
        }
        return;
    }
}
