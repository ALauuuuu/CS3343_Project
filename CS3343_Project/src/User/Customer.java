package User;

import java.io.InputStream;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import Instances.ItemInventory;
import Objects.Item;
import Objects.Notification;
import Objects.PurchaseRecord;
import Objects.Review;
import Objects.ShoppingCart;
import Objects.Transaction;
import Payment.BankAccount;
import Payment.CreditCard;
import Payment.PayMe;
import Payment.PaymentMethod;


public class Customer {
    private String userName;
    private int userId;
    private ShoppingCart shoppingCart;
    private List<PurchaseRecord> purchaseHistory;
    private List<Transaction> transactions;
    private List<Notification> notifications;
    private static Scanner userInput = null;
    private List<BankAccount> bankAccount;
    private List<CreditCard> creditCard;
    private List<PayMe> payMe;


    public static void setInputStream(InputStream in) {
        if (in == null) {
            userInput = null;
            return;
        }
        userInput = new Scanner(in);
    }
    
    public Customer(String userName){
        this.userName = userName;
        this.userId = 12345; //test
        this.shoppingCart = new ShoppingCart();
        this.shoppingCart = new ShoppingCart();
        this.bankAccount = new ArrayList<>();
        this.creditCard = new ArrayList<>();
        this.payMe = new ArrayList<>();
    }

    public void login(){
        System.out.println(userName + " logged in.\n");
        this.homeMenu();
    }

    private void logout(){
        System.out.println(userName + " logged out.");
    }

    private void homeMenu(){
        if (userInput == null) {
            userInput = new Scanner(System.in);
        }
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
                    System.out.println();
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
                            System.out.println("==========================");
                            System.out.println("Search functionality Page");
                            this.searchMenu();
                            break;
                        case 4: // Shopping cart
                            System.out.println("==========================");
                            System.out.println("Shopping Cart Page");
                            this.shoppingCartMenu();
                            break;
                        case 5: // Account information
                            System.out.println("==========================");
                            System.out.println("Account Information Page");
                            this.accountinformationMenu();
                            break;
                        case 6: // Purchasing history
                            System.out.println("==========================");
                            System.out.println("Purchasing History Page");
                            this.purchasingHistoryMenu();
                            break;
                        case 7: // Notification
                            System.out.println("==========================");
                            System.out.println("Notification Page");
                            this.notificationMenu();
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
            System.out.println("--------------------------");
            System.out.println("Option: 1 Search By Name ");
            System.out.println("Option: 2 Search By Code ");
            System.out.println("Option: 3 Search By Category ");
            System.out.println("Option: 4 Check item details ");
            System.out.println("Option: 5 Back to Home Page ");
            System.out.println("--------------------------");
            System.out.println("Please enter an option number(an integer):");
            try {
                int choice = userInput.nextInt();
                switch (choice) {
                    case 1:
                        searchByName();
                        askAddToCart();
                        break; // Exit the method
                    case 2:
                        searchByCode();
                        askAddToCart();
                        break; // Exit the method
                    case 3:
                        searchByCategory();
                        askAddToCart();
                        break;
                    case 4:
                        checkItemDetails();
                        break;
                    case 5:
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

    private void askAddToCart(){
        while(true){
            System.out.println("--------------------------");
            System.out.println("Option 1: Add an item to the shopping cart");
            System.out.println("Option 2: Back to Search Menu");
            System.out.println("--------------------------");
            System.out.print("Please enter an option number(an integer): ");

            try{
                int c = userInput.nextInt();
                userInput.nextLine(); // Clear buffer

                if (c == 1) {
                    System.out.print("Please enter item code(an integer): ");
                    try{
                        int itemCode = userInput.nextInt();
                        Item target = ItemInventory.searchByCode(itemCode);

                        if (target == null){
                            System.out.println("Item not found. Please try again.");
                            continue; // Ask again instead of returning
                        }

                        System.out.print("Please enter quantity(an integer): ");
                        int qty = userInput.nextInt();
                        userInput.nextLine(); // Clear buffer

                        if(qty <= 0){
                            System.out.println("Quantity must be positive. Please try again.");
                            continue;
                        }

                        System.out.print("Confirm addition?(y/n): ");
                        char confirm = userInput.next().charAt(0);
                        userInput.nextLine(); // Clear buffer

                        if (confirm == 'y' || confirm == 'Y') {
                            boolean added = this.shoppingCart.addItem(target, qty);
                            if(added){
                                System.out.println("Item added to cart successfully!");
                            }else{
                                System.out.println("Addition cancelled.");
                            }
                            System.out.println("==========================");
                            return; // Exit after successful addition
                        } else {
                            System.out.println("Addition cancelled.");
                            return; // Exit on cancellation
                        }

                    } catch (InputMismatchException e){
                        System.out.println("Wrong input, please enter an integer.\n");
                        userInput.nextLine(); // Clear buffer
                        // Continue loop to ask again
                    }

                } else if(c == 2){
                    return; // Exit to search menu

                } else{
                    System.out.println("Invalid option, please enter 1 or 2.\n");
                    // Continue loop
                }

            } catch (InputMismatchException e){
                System.out.println("Wrong input, please enter an integer.\n");
                userInput.nextLine(); // Clear buffer
                // Continue loop
            }
        }
    }
    
    private boolean searchByName(){
        System.out.println("Please enter item name:");
        List<Item> target = ItemInventory.searchByName(userInput.next());
        if (target.size() == 0){
            System.out.println("No items found.");
            return false;
        }
        System.out.println("There are " + target.size() + " item(s) found.");
        for(int i=0; i < target.size(); i++){
            System.out.println(target.get(i).getDetails());
        }
        return true;
    }

    private void searchByCode(){
        while (true) {
            System.out.println("Please enter item code:");
            try{
                int code = userInput.nextInt();
                Item target = ItemInventory.searchByCode(code);
                
                if (target == null) {
                    System.out.println("Item not found. Please try again.");
                } else {
                    System.out.println(target.getDetails());
                    return; 
                }
            } catch (InputMismatchException e){
                System.out.println("Wrong input, please enter an integer.\n");
                userInput.nextLine(); //clear buffer
            }
        }
    }

    private boolean searchByCategory(){
        System.out.println("Please select item Category:");
        List<String> allCat = ItemInventory.getAllCategory();
        System.out.println("--------------------------");
        for(int i = 0; i < allCat.size(); i++)
            System.out.println("Option: "+(i+1)+" "+allCat.get(i));
        
        System.out.println("--------------------------");

        try{
            int choice = userInput.nextInt();
            if (choice < 1 || choice > allCat.size()){
                System.out.println("Invalid option, please try again! \n");
                searchByCategory();
                return false;
            }

            List<Item> target = ItemInventory.searchByCategory(allCat.get(choice-1));
            System.out.println("There are "+target.size()+" item(s) found.");
            for(int i=0; i < target.size(); i++)
                System.out.println(target.get(i).getDetails());

        } catch (InputMismatchException e){
            System.out.println("Wrong input, please enter an integer.\n");
            userInput.nextLine(); //clear buffer
            searchByCategory();
        }   
        return true;
    }

    private void checkItemDetails(){
        while (true) {
            System.out.println("Please enter item code:");
            try{
                int code = userInput.nextInt();
                userInput.nextLine();
                Item target = ItemInventory.searchByCode(code);
                if (target == null) {
                    System.out.println("Item not found. Please try again.");
                    checkItemDetails();
                    return;
                }
                System.out.println(target.getDetails());
                System.out.println("--------------------------");
                target.showReviews();
                System.out.println("--------------------------");
                System.out.println("Option: 1 Add one to shopping cart");
                System.out.println("Option: 2 Back to Search Menu");
                if(target.getCanReview())
                    System.out.println("Option: 3 Leave a review");
                System.out.println("--------------------------");
                System.out.println("Please enter an option number(an integer):");
                int c = userInput.nextInt();
                userInput.nextLine(); // Clear buffer
                if (c == 1) {
                    this.shoppingCart.addItem(target, 1);
                    return; // Exit to search menu
                } else if(c == 2){
                    return; // Exit to search menu
                } else if(c == 3 && target.getCanReview() == true){
                    createReview(target);
                    return; // Exit to search menu
                }else{
                    System.out.println("Invalid option, please enter 1 or 2.\n");
                    // Continue loop
                }

            } catch (InputMismatchException e){
                System.out.println("Wrong input, please enter an integer.\n");
                userInput.nextLine(); //clear buffer
            }
        }
         
    }

    private void createReview(Item target){
        while (true) {
            try {
                System.out.println("Please enter rating (1 to 5)");
                int rating = userInput.nextInt();
                userInput.nextLine(); //clear buffer
                if(rating<1||rating>5){
                    throw new InputMismatchException();
                }
                System.out.println("Please enter comment");
                String comment = userInput.next();
                userInput.nextLine();
                target.addReview(new Review(111, this.userName, rating,comment,new Date()));
                return;
            } catch (InputMismatchException e) {
                System.out.println("Wrong input, please enter an integer.\n");
                userInput.nextLine(); //clear buffer
            }
        }
    }

    private void shoppingCartMenu(){
        //testing
        this.shoppingCart.addItem(ItemInventory.searchByCode(1), 1);
        this.shoppingCart.addItem(ItemInventory.searchByCode(2), 3);
        this.shoppingCart.addItem(ItemInventory.searchByCode(3), 4);
        this.shoppingCart.addItem(ItemInventory.searchByCode(1), 1);
        //
        while (true) {
            System.out.println("\nOnline Shopping > Shopping Cart");
            System.out.println("--------------------------");
            if(this.shoppingCart.isEmpty()){
                System.out.println("The Shopping Cart is empty.");
            }
            this.shoppingCart.DisplayCart();
            System.out.println("Total: $"+this.shoppingCart.calculateTotal());
            System.out.println("--------------------------");
            System.out.println("Option: 1 Checkout");
            System.out.println("Option: 2 Clear Cart");
            System.out.println("Option: 3 Change Quantity for specfic item");
            System.out.println("Option: 4 Back to Home Page ");
            System.out.println("--------------------------");
            System.out.println("Please enter an option number(an integer):");
            try {
                int choice = userInput.nextInt();
                switch (choice) {
                    case 1:
                        this.checkout();
                        break;
                    case 2:
                        while(true){
                            System.out.println("Are you sure to clear the cart? (y/n):");
                            String c = userInput.next().toLowerCase();
                            if (c.equals("y")) {
                                this.shoppingCart.clearCart();
                                break;
                            }else if(c.equals("n")){
                                break;
                            }else{
                                System.out.println("Wrong input, please enter a charater.\n");
                            }
                        }
                        break;
                    case 3:
                        System.out.println("Please enter item code(an integer):");
                        int itemCode = userInput.nextInt();
                        System.out.println("Please enter new quantity(an integer):");
                        int qty = userInput.nextInt();
                        this.shoppingCart.modifyItem(ItemInventory.searchByCode(itemCode), qty);
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

    private void purchasingHistoryMenu(){
        while(true){
            if(purchaseHistory == null || purchaseHistory.isEmpty()){
                System.out.println("No purchase history.");
                return;
            } else {
                int i = 1;
                for(Transaction record : transactions){
                    System.out.println(i + ": " + record.getTransactionDetails());
                    i++;
                }
                System.out.println("--------------------------");
                System.out.println("Option: 1 Refund ");
                System.out.println("Option: 2 Back to Home Page ");
                System.out.println("--------------------------");
                try {
                    int choice = userInput.nextInt();
                    switch (choice) {
                        case 1:
                            refund();
                            break; // Exit the method
                        case 2:
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

    private void refund(){
        System.out.println("--------------------------");
        userInput.nextLine();
        System.out.println("Please enter target Refund ID: ");
        try {
            int choice = userInput.nextInt();
            if(choice<1 || choice>purchaseHistory.size()){
                throw new InputMismatchException();
            }
            PurchaseRecord target = purchaseHistory.get(choice-1);
			if (target.getStatus() == "Refunded") {
				System.out.println("This item has already been refunded.\n");
				return;
			}
            target.setStatus("Refunded");
            this.notifications.add(new Notification(new Date(), " Refund successful!"));
            notifications.get(notifications.size()-1);
        } catch (InputMismatchException e) {
            System.out.println("Wrong input, please enter an correct integer.\n");
            userInput.nextLine(); //clear buffer
        }
        
    }

    private void accountinformationMenu(){
        while(true){
            System.out.println("\n=== Account Information ===");
            System.out.println("Username: " + this.userName);
            System.out.println("\nLinked Payment Methods:");
            System.out.println("--------------------------");
        
            // Display all linked payment methods
            if(!this.bankAccount.isEmpty()){
                for(int i = 0; i < this.bankAccount.size(); i++){
                    System.out.println((i+1) + ". Bank Account | Account ID: " + 
                        this.bankAccount.get(i).getAccountId());
                }
            }
            if(!this.creditCard.isEmpty()){
                SimpleDateFormat sdf = new SimpleDateFormat("MM/yy");
                for(int i = 0; i < this.creditCard.size(); i++){
                System.out.println((i+1) + ". Credit Card | Card Number: " +
                    this.creditCard.get(i).getCardNumber() +
                    " | Expiry Date: " + sdf.format(this.creditCard.get(i).getExpiryDate()));
                }
            }
            if(!this.payMe.isEmpty()){
                for(int i = 0; i < this.payMe.size(); i++){
                    System.out.println((i+1) + ". PayMe | Account ID: " + 
                        this.payMe.get(i).getAccountId());
                }
            }
        
            if(this.bankAccount.isEmpty() && this.creditCard.isEmpty() && this.payMe.isEmpty()){
                System.out.println("No payment methods linked.");
            }
        
            System.out.println("--------------------------");
            System.out.println("Option: 1 Add a payment method");
            System.out.println("Option: 2 Delete a payment method");
            System.out.println("Option: 3 Go Back to Home Page");
            System.out.println("Please enter an option number(an integer):");
        
            try{
                int choice = userInput.nextInt();
                userInput.nextLine(); // Clear buffer
            
                switch(choice){
                    case 1:
                        addPaymentMethod();
                        break;
                    case 2:
                        deletePaymentMethod();
                        break;
                    case 3:
                        return; // Exit to home menu
                    default:
                        System.out.println("Invalid option. Please try again.");
                        break;
                }
            } catch(InputMismatchException e){
                System.out.println("Wrong input, please enter an integer.\n");
                userInput.nextLine(); // Clear buffer
            }
        }
    }

    private void addPaymentMethod(){
        System.out.println("\n=== Add Payment Method ===");
        System.out.println("Select payment method type:");
        System.out.println("--------------------------");
        System.out.println("Option: 1 Bank Account");
        System.out.println("Option: 2 Credit Card");
        System.out.println("Option: 3 PayMe");
        System.out.println("Option: 4 Cancel");
        System.out.println("--------------------------");
        System.out.print("Please enter an option: ");
        
        try{
            int choice = userInput.nextInt();
            userInput.nextLine(); // Clear buffer
            
            switch(choice){
                case 1:
                    System.out.print("Enter Bank Account ID: ");
                    String accountId = userInput.nextLine();
                    for (BankAccount ba : this.bankAccount) {
                        if (ba.getAccountId().equals(accountId)) {
                            System.out.println("This Bank Account is already linked.");
                            return;
                        }
                    }
                    bankAccount.add(new BankAccount(accountId));
                    System.out.println("Bank Account linked successfully!");
                    break;
                    
                case 2:
                    System.out.print("Enter Card Number: ");
                    String cardNumber = userInput.nextLine();
                    for (CreditCard cc : this.creditCard) {
                        if (cc.getCardNumber().equals(cardNumber)) {
                            System.out.println("This Credit Card is already linked.");
                            return;
                        }
                    }

                    System.out.print("Enter Expiry Date (MM/YY): ");
                    String expiryInput = userInput.nextLine();
                    Date expiryDate = null;
                    try {
                        // "MM/yy" is the correct pattern for a date like "10/27"
                        SimpleDateFormat sdf = new SimpleDateFormat("MM/yy");
                        // Parsing returns a Date with day set to 1 of that month
                        expiryDate = sdf.parse(expiryInput);
                    } catch (ParseException e) {
                        System.out.println("Invalid date format. Please enter as MM/YY (e.g. 11/29).");
                        // Optionally, ask for input again or just abort
                    }

                    creditCard.add(new CreditCard(cardNumber, expiryDate));
                    System.out.println("Credit Card linked successfully!");
                    break;
                    
                case 3:
                    System.out.print("Enter PayMe Account ID: ");
                    String payMeId = userInput.nextLine();
                    for (PayMe pm : this.payMe) {
                        if (pm.getAccountId().equals(payMeId)) {
                            System.out.println("This PayMe account is already linked.");
                            return;
                        }
                    }
                    payMe.add(new PayMe(payMeId));
                    System.out.println("PayMe linked successfully!");
                    break;
                    
                case 4:
                    return;
                    
                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        } catch(InputMismatchException e){
            System.out.println("Wrong input, please enter an integer.\n");
            userInput.nextLine(); // Clear buffer
        }
    }

    private void deletePaymentMethod(){
        System.out.println("\n=== Delete Payment Method ===");
    
        if(this.bankAccount.isEmpty() && this.creditCard.isEmpty() && this.payMe.isEmpty()){
            System.out.println("No payment methods to delete.");
            return;
        }

        System.out.println("Select payment method to delete:");
        int optionNum = 1;

        // Display all bank accounts
        for(int i = 0; i < this.bankAccount.size(); i++){
            System.out.println(optionNum + ". Bank Account | Account ID: " + 
                this.bankAccount.get(i).getAccountId());
            optionNum++;
        }

        // Display all credit cards
        for(int i = 0; i < this.creditCard.size(); i++){
            System.out.println(optionNum + ". Credit Card | Card Number: " + 
                this.creditCard.get(i).getCardNumber());
            optionNum++;
        }

        // Display all PayMe accounts
        for(int i = 0; i < this.payMe.size(); i++){
            System.out.println(optionNum + ". PayMe | Account ID: " + 
                this.payMe.get(i).getAccountId());
            optionNum++;
        }

        System.out.println(optionNum + ". Cancel");
        System.out.println("--------------------------");
        System.out.print("Please enter an option: ");

        try{
            int choice = userInput.nextInt();
            userInput.nextLine(); // Clear buffer

            int currentOption = 1;

            // Check bank accounts
            for(int i = 0; i < this.bankAccount.size(); i++){
                if(choice == currentOption){
                    System.out.print("Confirm deletion? (y/n): ");
                    String confirm = userInput.nextLine().toLowerCase();
                    if(confirm.equals("y")){
                        this.bankAccount.remove(i);
                        System.out.println("Bank Account deleted successfully!");
                    }
                    return;
                }
                currentOption++;
            }

            // Check credit cards
            for(int i = 0; i < this.creditCard.size(); i++){
                if(choice == currentOption){
                    System.out.print("Confirm deletion? (y/n): ");
                    String confirm = userInput.nextLine().toLowerCase();
                    if(confirm.equals("y")){
                        this.creditCard.remove(i);
                        System.out.println("Credit Card deleted successfully!");
                    }
                    return;
                }
                currentOption++;
            }

            // Check PayMe accounts
            for(int i = 0; i < this.payMe.size(); i++){
                if(choice == currentOption){
                    System.out.print("Confirm deletion? (y/n): ");
                    String confirm = userInput.nextLine().toLowerCase();
                    if(confirm.equals("y")){
                        this.payMe.remove(i);
                        System.out.println("PayMe deleted successfully!");
                    }
                    return;
                }
                currentOption++;
            }

            if(choice == currentOption){
                return; // Cancel
            }

            System.out.println("Invalid choice.");

        } catch(InputMismatchException e){
            System.out.println("Wrong input, please enter an integer.\n");
            userInput.nextLine(); // Clear buffer
        }

    }

    private void notificationMenu(){
        if(notifications == null || notifications.isEmpty()){
            System.out.println("No notifications.");
        } else {
            for(Notification noti : notifications){
                System.out.println(noti.toString());
            }
        }
    }

    private void checkout(){
        if(this.shoppingCart.isEmpty()){
            System.out.println("Cart is empty, cannot checkout.");
            return;
        }

        System.out.println("\n=== Checkout ===");
        this.shoppingCart.DisplayCart();
        System.out.println("Total: $" + this.shoppingCart.calculateTotal());
        System.out.println("================");

        if(this.bankAccount.isEmpty() && this.creditCard.isEmpty() && this.payMe.isEmpty()){
            System.out.println("NO PAYMENT METHODS LINKED. PLEASE ADD A PAYMENT METHOD IN ACCOUNT INFORMATION.");
            System.out.println("(Please go to Account Information > Add a payment method)");
            return;
        }

        System.out.println("Choose payment method:");
        int optionNum = 1;
        
        // Display all available payment methods
        for(int i = 0; i < this.bankAccount.size(); i++){
            System.out.println("Option: " + optionNum + " | Bank Account | ID: " + 
                this.bankAccount.get(i).getAccountId());
            optionNum++;
        }

        for(int i = 0; i < this.creditCard.size(); i++){
            System.out.println("Option: " + optionNum + " | Credit Card | Number: " + 
                this.creditCard.get(i).getCardNumber());
            optionNum++;
        }

        for(int i = 0; i < this.payMe.size(); i++){
            System.out.println("Option: " + optionNum + " | PayMe | ID: " + 
                this.payMe.get(i).getAccountId());
            optionNum++;
        }
        System.out.println("Option: " + optionNum + " | Cancel");
        System.out.println("--------------------------");

        try{
            int choice = userInput.nextInt();
            userInput.nextLine(); // Clear buffer
            PaymentMethod pm = null;
            int currentOption = 0;

            // Check bank accounts
            for(int i = 0; i < this.bankAccount.size(); i++){
                currentOption++;
                if(choice == currentOption){
                    pm = this.bankAccount.get(i);
                    break;
                }
            }

            // Check credit cards
            if(pm == null){
                for(int i = 0; i < this.creditCard.size(); i++){
                    currentOption++;
                    if(choice == currentOption){
                        pm = this.creditCard.get(i);
                        break;
                    }
                }
            }

            // Check PayMe
            if(pm == null){
                for(int i = 0; i < this.payMe.size(); i++){
                    currentOption++;
                    if(choice == currentOption){
                        pm = this.payMe.get(i);
                        break;
                    }
                }
            }

            if(choice == currentOption + 1){
                System.out.println("Checkout canceled.");
                return; // Cancel
            }

            if(pm == null){
                System.out.println("Invalid choice.");
                return;
            }

            double total = this.shoppingCart.calculateTotal();

            if(pm.pay(total)){
                System.out.println("Payment successful!");
                List<PurchaseRecord> allRecordsThisTime = new ArrayList<>();
                for(Item item : this.shoppingCart.getCartItems()) {
                    PurchaseRecord record = new PurchaseRecord(
                        purchaseHistory == null ? 1 : purchaseHistory.size() + 1, 
                        item, new Date(), "Purchased");
                    this.addPurchaseRecord(record);
                    allRecordsThisTime.add(record);
                    item.updateQuantity(item.getQuantity()-this.shoppingCart.getCartItemQuantity(item));
                }
				Transaction transaction = new Transaction(System.currentTimeMillis(),
						allRecordsThisTime, total, new Date(), "Completed");
				this.addItemsPurchased(transaction);
                if(this.notifications == null){
                    this.notifications = new ArrayList<>();
                }
                this.notifications.add(
                    new Notification(new Date(), "Purchase successful! Total: $" + total));
                for(Item item : this.shoppingCart.getCartItems())
                    item.setCanReview(true);
                this.shoppingCart.clearCart();
            } else {
                System.out.println("Payment failed.");
            }

        } catch(InputMismatchException e){
            System.out.println("Wrong input, please enter an integer.\n");
            userInput.nextLine(); // Clear buffer
        }
    }

    public void addPurchaseRecord(PurchaseRecord record){
        if(this.purchaseHistory == null){
            this.purchaseHistory = new ArrayList<>();
        }
        this.purchaseHistory.add(record);
    }
    
    public void addItemsPurchased(Transaction itemsPurchased) {
        if(this.transactions == null){
            this.transactions = new ArrayList<>();
        }
        this.transactions.add(itemsPurchased);
    }

}
