package main;

import java.util.InputMismatchException;
import java.util.Scanner;

import components.ItemInventory;

import java.util.List;

import models.Customer;
import models.Item;

public class Searching {
    private static final Scanner input = new Scanner(System.in);
    public static void searchMenu(){
        while (true) {
            System.out.println("Online Shopping > Search");
            System.out.println("Option: 1 Search By Name ");
            System.out.println("Option: 2 Search By Code ");
            System.out.println("Option: 3 Search By Category ");
            System.out.println("Option: 4 Back to Home Page ");
            System.out.println("Please enter an option number(an integer):");
            try {
                int choice = input.nextInt();
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
                input.nextLine(); //clear buffer
            }
        }
    }
    private static void searchByName(){
        System.out.println("Please enter item name:");
        List<Item> target = Customer.searchByName(input.next());
        System.out.println("\nThere are " + target.size() + " item(s) found.\n");
        for(int i=0; i < target.size(); i++){
            System.out.println(target.get(i).getDetails());
        }
    }
    private static void searchByCode(){
        System.out.println("Please enter item code:");
        try{
            Item target = Customer.searchByCode(input.nextInt());
            System.out.println(target.getDetails());
        } catch (InputMismatchException e){
            System.out.println("Wrong input, please enter an integer.\n");
            input.nextLine(); //clear buffer
            searchByCode();
        }
            
    }
    private static void searchByCategory(){
        System.out.println("Please select item Category:");
        List<String> allCat = ItemInventory.getInstance().getAllCategory();
        for(int i = 0; i < allCat.size(); i++)
            System.out.println("Option: "+(i+1)+" "+allCat.get(i));
        try{
            List<Item> target = Customer.searchByCategory(allCat.get(input.nextInt()-1));
            System.out.println("There are "+target.size()+" item(s) found.");
            for(int i=0; i < target.size(); i++)
                System.out.println(target.get(i).getDetails());
        } catch (InputMismatchException e){
            System.out.println("Wrong input, please enter an integer.\n");
            input.nextLine(); //clear buffer
            searchByCategory();
        }
        
        }
}

