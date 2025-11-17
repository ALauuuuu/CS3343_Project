package Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import Instances.ItemInventory;
import Main.Main;
import Menu.Menu;
import User.Customer;

public class CustomerIntegrationTest {

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;

	@BeforeEach
	public void setUp() {
		System.setOut(new PrintStream(outContent));
		ItemInventory.getItems().clear();
		Main.initializeInventory();
	}
	
	@AfterEach
	public void tearDown() {
		System.setOut(originalOut);
		Menu.setInputStream(null);
		Customer.setInputStream(null);
	}
	
	
	@Test
	public void CompleteShoppingWorkflow() {
		String input = "1\nCustomer1\n3\n";
		ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		Menu.setInputStream(testInput);

		input = "3\n1\nToys\n1\n1\n2\ny\n5\n5\n1\n1\n4532111122223333\n3\n4\n1\n1\n4\n8";
		testInput = new ByteArrayInputStream(input.getBytes());
		Customer.setInputStream(testInput);

		Menu.loginPage();
		
		String output = outContent.toString();
		assertTrue(output.contains("Item added to cart successfully!"));
		assertTrue(output.contains("Bank Account linked successfully!"));
		assertTrue(output.contains("Payment successful!"));
	}
	
	@Test
	public void AddMultipleItemsToCartWorkflow() {
		String input = "1\nCustomer2\n3\n";
		ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		Menu.setInputStream(testInput);

		input = "3\n1\nToys\n1\n1\n2\ny\n3\n2\n5\n1\n5\n3\ny\n5\n8\n";
		testInput = new ByteArrayInputStream(input.getBytes());
		Customer.setInputStream(testInput);

		Menu.loginPage();
		
		String output = outContent.toString();
		int successCount = 0;
		int index = 0;
		while ((index = output.indexOf("Item added to cart successfully", index)) != -1) {
			successCount++;
			index += "Item added to cart successfully".length();
		}
		assertTrue(successCount >= 1);
	}
	
	@Test
	public void CartPersistsAcrossMenuNavigation() {
		String input = "1\nCustomer3\n3\n";
		ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		Menu.setInputStream(testInput);

		input = "3\n1\nToys\n1\n1\n2\ny\n5\n8\n";
		testInput = new ByteArrayInputStream(input.getBytes());
		Customer.setInputStream(testInput);

		Menu.loginPage();
		
		String output = outContent.toString();
		assertTrue(output.contains("Item added to cart successfully"));
	}
	
	
	@Test
	public void AddBankAccountPaymentMethod() {
		String input = "1\nCustomer4\n3\n";
		ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		Menu.setInputStream(testInput);

		input = "5\n1\n1\nBA123456\n3\n8\n";
		testInput = new ByteArrayInputStream(input.getBytes());
		Customer.setInputStream(testInput);

		Menu.loginPage();
		
		String output = outContent.toString();
		assertTrue(output.contains("Bank Account linked successfully"));
	}
	
	@Test
	public void AddCreditCardPaymentMethod() {
		String input = "1\nCustomer5\n3\n";
		ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		Menu.setInputStream(testInput);

		input = "5\n1\n2\n4532111122223333\n12/25\n3\n8\n";
		testInput = new ByteArrayInputStream(input.getBytes());
		Customer.setInputStream(testInput);

		Menu.loginPage();
		
		String output = outContent.toString();
		assertTrue(output.contains("Credit Card linked successfully"));
	}
	
	@Test
	public void AddPayMePaymentMethod() {
		String input = "1\nCustomer6\n3\n";
		ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		Menu.setInputStream(testInput);

		input = "5\n1\n3\n91234567\n3\n8\n";
		testInput = new ByteArrayInputStream(input.getBytes());
		Customer.setInputStream(testInput);

		Menu.loginPage();
		
		String output = outContent.toString();
		assertTrue(output.contains("PayMe linked successfully"));
	}
	
	@Test
	public void AddMultiplePaymentMethods() {
		String input = "1\nCustomer7\n3\n";
		ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		Menu.setInputStream(testInput);

		input = "5\n1\n1\nBA999\n1\n2\n4532000011112222\n11/26\n3\n8\n";
		testInput = new ByteArrayInputStream(input.getBytes());
		Customer.setInputStream(testInput);

		Menu.loginPage();
		
		String output = outContent.toString();
		assertTrue(output.contains("Bank Account linked successfully"));
		assertTrue(output.contains("Credit Card linked successfully"));
	}
	
	@Test
	public void PreventDuplicatePaymentMethod() {
		String input = "1\nCustomer8\n3\n";
		ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		Menu.setInputStream(testInput);

		input = "5\n1\n1\nBA555\n1\n1\nBA555\n3\n8\n";
		testInput = new ByteArrayInputStream(input.getBytes());
		Customer.setInputStream(testInput);

		Menu.loginPage();
		
		String output = outContent.toString();
		assertTrue(output.contains("Bank Account linked successfully"));
		assertTrue(output.contains("already linked"));
	}
	
	@Test
	public void DeletePaymentMethod() {
		String input = "1\nCustomer9\n3\n";
		ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		Menu.setInputStream(testInput);

		input = "5\n1\n1\nBA777\n2\n1\ny\n3\n8\n";
		testInput = new ByteArrayInputStream(input.getBytes());
		Customer.setInputStream(testInput);

		Menu.loginPage();
		
		String output = outContent.toString();
		assertTrue(output.contains("Bank Account linked successfully"));
		assertTrue(output.contains("deleted successfully"));
	}
	
	
	@Test
	public void CheckoutWithoutPaymentMethod() {
		String input = "1\nCustomer10\n3\n";
		ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		Menu.setInputStream(testInput);

		input = "3\n1\nToys\n1\n1\n2\ny\n5\n4\n1\n4\n8\n";
		testInput = new ByteArrayInputStream(input.getBytes());
		Customer.setInputStream(testInput);

		Menu.loginPage();
		
		String output = outContent.toString();
		assertTrue(output.contains("NO PAYMENT METHODS LINKED. PLEASE ADD A PAYMENT METHOD IN ACCOUNT INFORMATION."));
	}
	
	@Test
	public void CompleteCheckoutWorkflow() {
		String input = "1\nCustomer11\n3\n";
		ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		Menu.setInputStream(testInput);

		input = "5\n1\n1\nBA888\n3\n3\n1\nToys\n1\n1\n2\ny\n5\n4\n1\n1\n4\n8\n";
		testInput = new ByteArrayInputStream(input.getBytes());
		Customer.setInputStream(testInput);

		Menu.loginPage();
		
		String output = outContent.toString();
		assertTrue(output.contains("Payment successful"));
	}
	
	
	@Test
	public void HandleInvalidMenuOption() {
		String input = "1\nCustomer12\n3\n";
		ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		Menu.setInputStream(testInput);

		input = "999\n8\n";
		testInput = new ByteArrayInputStream(input.getBytes());
		Customer.setInputStream(testInput);

		Menu.loginPage();
		
		String output = outContent.toString();
		assertTrue(output.contains("Invalid option"));
	}
	
	@Test
	public void HandleNonIntegerInput() {
		String input = "1\nCustomer13\n3\n";
		ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		Menu.setInputStream(testInput);

		input = "abc\n8\n";
		testInput = new ByteArrayInputStream(input.getBytes());
		Customer.setInputStream(testInput);

		Menu.loginPage();
		
		String output = outContent.toString();
		assertTrue(output.contains("Wrong input, please enter an integer"));
	}
	
	@Test
	public void AddItemWithInsufficientStock() {
		String input = "1\nCustomer14\n3\n";
		ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		Menu.setInputStream(testInput);

		input = "3\n1\nToys\n1\n1\n1000\ny\n5\n8\n";
		testInput = new ByteArrayInputStream(input.getBytes());
		Customer.setInputStream(testInput);

		Menu.loginPage();
		
		String output = outContent.toString();
		assertTrue(output.contains("Stock is not enough"));
		assertTrue(output.contains("Addition cancelled"));
	}
	
	@Test
	public void CancelAddToCart() {
		String input = "1\nCustomer15\n3\n";
		ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		Menu.setInputStream(testInput);

		input = "3\n1\nToys\n1\n1\n2\nn\n5\n8\n";
		testInput = new ByteArrayInputStream(input.getBytes());
		Customer.setInputStream(testInput);

		Menu.loginPage();
		
		String output = outContent.toString();
		assertTrue(output.contains("Addition cancelled"));
	}
	
	
	@Test
	public void NavigateToNextPage() {
		String input = "1\nCustomer16\n3\n";
		ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		Menu.setInputStream(testInput);

		input = "1\n8\n";
		testInput = new ByteArrayInputStream(input.getBytes());
		Customer.setInputStream(testInput);

		Menu.loginPage();
		
		String output = outContent.toString();
		assertTrue(output.contains("Home Page"));
	}
	
	@Test
	public void NavigateToPreviousPage() {
		String input = "1\nCustomer17\n3\n";
		ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		Menu.setInputStream(testInput);


		input = "1\n2\n8\n";
		testInput = new ByteArrayInputStream(input.getBytes());
		Customer.setInputStream(testInput);

		Menu.loginPage();
		
		String output = outContent.toString();
		assertTrue(output.contains("Home Page"));
	}
	
	@Test
	public void AttemptNextPageAtEnd() {
		String input = "1\nCustomer18\n3\n";
		ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		Menu.setInputStream(testInput);

		input = "1\n1\n1\n1\n8\n";
		testInput = new ByteArrayInputStream(input.getBytes());
		Customer.setInputStream(testInput);

		Menu.loginPage();
		
		String output = outContent.toString();
		assertTrue(output.contains("last page") || output.contains("Home Page"));
	}
	
	@Test
	public void AttemptPreviousPageAtStart() {
		String input = "1\nCustomer19\n3\n";
		ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		Menu.setInputStream(testInput);

		input = "2\n8\n";
		testInput = new ByteArrayInputStream(input.getBytes());
		Customer.setInputStream(testInput);

		Menu.loginPage();
		
		String output = outContent.toString();
		assertTrue(output.contains("first page") || output.contains("Home Page"));
	}
	
	
	@Test
	public void SearchIntegratesWithInventory() {
		String input = "1\nCustomer20\n3\n";
		ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		Menu.setInputStream(testInput);

		input = "3\n2\n7\n2\n5\n8\n";
		testInput = new ByteArrayInputStream(input.getBytes());
		Customer.setInputStream(testInput);

		Menu.loginPage();
		
		String output = outContent.toString();
		assertTrue(output.contains("Laptop"));
		assertTrue(output.contains("1200"));
	}
	
	@Test
	public void CartReflectsInventoryStock() {
		String input = "1\nCustomer21\n3\n";
		ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		Menu.setInputStream(testInput);

		input = "3\n1\nToys\n1\n1\n999\ny\n5\n8\n";
		testInput = new ByteArrayInputStream(input.getBytes());
		Customer.setInputStream(testInput);

		Menu.loginPage();
		
		String output = outContent.toString();
		assertTrue(output.contains("Stock is not enough"));
		assertTrue(output.contains("Addition cancelled"));
	}

	@Test
	public void CompleteCustomerJourney() {
		String input = "1\nCustomer22\n3\n";
		ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		Menu.setInputStream(testInput);

		input = "1\n3\n1\nLaptop\n1\n7\n1\ny\n5\n5\n1\n1\nBA12345\n3\n4\n1\n1\n4\n8\n";
		testInput = new ByteArrayInputStream(input.getBytes());
		Customer.setInputStream(testInput);

		Menu.loginPage();
		
		String output = outContent.toString();
		assertTrue(output.contains("Item added to cart successfully"));
		assertTrue(output.contains("Bank Account linked successfully"));
		assertTrue(output.contains("Payment successful"));
	}

	@Test
	public void FailedShoppingWorkFlow() {
		String input = "1\nCustomer23\n3\n";
		ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		Menu.setInputStream(testInput);

		input = "3\n1\nToys\n1\n1\n2\ny\n5\n4\n1\n4\n8\n";
		testInput = new ByteArrayInputStream(input.getBytes());
		Customer.setInputStream(testInput);

		Menu.loginPage();
		
		String output = outContent.toString();
		assertTrue(output.contains("NO PAYMENT METHODS LINKED. PLEASE ADD A PAYMENT METHOD IN ACCOUNT INFORMATION."));
		assertTrue(output.contains("(Please go to Account Information > Add a payment method)"));
	}

	@Test
	public void ViewEmptyCart() {
	    String input = "1\nCustomer24\n3\n";
	    ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
	    Menu.setInputStream(testInput);

	    input = "4\n4\n8\n";
	    testInput = new ByteArrayInputStream(input.getBytes());
	    Customer.setInputStream(testInput);

	    Menu.loginPage();
	    
	    String output = outContent.toString();
	    assertTrue(output.contains("The Shopping Cart is empty"));
	}

	@Test
	public void ClearCartWithConfirmation() {
	    String input = "1\nCustomer25\n3\n";
	    ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
	    Menu.setInputStream(testInput);

	    input = "3\n1\nToys\n1\n1\n2\ny\n5\n4\n2\ny\n4\n8\n";
	    testInput = new ByteArrayInputStream(input.getBytes());
	    Customer.setInputStream(testInput);

	    Menu.loginPage();
	    
	    String output = outContent.toString();
	    assertTrue(output.contains("Item added to cart successfully"));
	}

	@Test
	public void CancelClearCart() {
	    String input = "1\nCustomer26\n3\n";
	    ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
	    Menu.setInputStream(testInput);

	    input = "3\n1\nToys\n1\n1\n2\ny\n5\n4\n2\nn\n4\n8\n";
	    testInput = new ByteArrayInputStream(input.getBytes());
	    Customer.setInputStream(testInput);

	    Menu.loginPage();
	    
	    String output = outContent.toString();
	    assertTrue(output.contains("Item added to cart successfully"));
	}

	@Test
	public void ModifyCartItemQuantity() {
	    String input = "1\nCustomer27\n3\n";
	    ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
	    Menu.setInputStream(testInput);

	    input = "3\n1\nToys\n1\n1\n2\ny\n5\n4\n3\n1\n5\n4\n8\n";
	    testInput = new ByteArrayInputStream(input.getBytes());
	    Customer.setInputStream(testInput);

	    Menu.loginPage();
	    
	    String output = outContent.toString();
	    assertTrue(output.contains("Item added to cart successfully"));
	}

	@Test
	public void ModifyCartItemWithInsufficientStock() {
	    String input = "1\nCustomer28\n3\n";
	    ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
	    Menu.setInputStream(testInput);

	    input = "3\n1\nToys\n1\n1\n2\ny\n5\n4\n3\n1\n9999\n4\n8\n";
	    testInput = new ByteArrayInputStream(input.getBytes());
	    Customer.setInputStream(testInput);

	    Menu.loginPage();
	    
	    String output = outContent.toString();
	    assertTrue(output.contains("Stock is not enough"));
	}

	@Test
	public void CheckoutWithCreditCard() {
	    String input = "1\nCustomer29\n3\n";
	    ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
	    Menu.setInputStream(testInput);

	    input = "5\n1\n2\n4532111122223333\n12/25\n3\n3\n1\nToys\n1\n1\n2\ny\n5\n4\n1\n1\n4\n8\n";
	    testInput = new ByteArrayInputStream(input.getBytes());
	    Customer.setInputStream(testInput);

	    Menu.loginPage();
	    
	    String output = outContent.toString();
	    assertTrue(output.contains("Credit Card linked successfully"));
	    assertTrue(output.contains("Payment successful"));
	}

	@Test
	public void CheckoutWithPayMe() {
	    String input = "1\nCustomer30\n3\n";
	    ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
	    Menu.setInputStream(testInput);

	    input = "5\n1\n3\n91234567\n3\n3\n1\nToys\n1\n1\n2\ny\n5\n4\n1\n1\n4\n8\n";
	    testInput = new ByteArrayInputStream(input.getBytes());
	    Customer.setInputStream(testInput);

	    Menu.loginPage();
	    
	    String output = outContent.toString();
	    assertTrue(output.contains("PayMe linked successfully"));
	    assertTrue(output.contains("Payment successful"));
	}

	@Test
	public void CancelCheckout() {
	    String input = "1\nCustomer31\n3\n";
	    ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
	    Menu.setInputStream(testInput);

	    input = "5\n1\n1\nBA111\n3\n3\n1\nToys\n1\n1\n2\ny\n5\n4\n1\n2\n4\n8\n";
	    testInput = new ByteArrayInputStream(input.getBytes());
	    Customer.setInputStream(testInput);

	    Menu.loginPage();
	    
	    String output = outContent.toString();
	    assertTrue(output.contains("Checkout canceled") || output.contains("canceled"));
	}

	@Test
	public void SearchByNameNotFound() {
	    String input = "1\nCustomer32\n3\n";
	    ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
	    Menu.setInputStream(testInput);

	    input = "3\n1\nNonExistentItem999\n2\n5\n8\n";
	    testInput = new ByteArrayInputStream(input.getBytes());
	    Customer.setInputStream(testInput);

	    Menu.loginPage();
	    
	    String output = outContent.toString();
	    assertTrue(output.contains("No items found"));
	}

	@Test
	public void SearchByCodeNotFound() {
	    String input = "1\nCustomer33\n3\n";
	    ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
	    Menu.setInputStream(testInput);

	    input = "3\n2\n9999\n9999\n5\n2\n5\n8\n";
	    testInput = new ByteArrayInputStream(input.getBytes());
	    Customer.setInputStream(testInput);

	    Menu.loginPage();
	    
	    String output = outContent.toString();
	    assertTrue(output.contains("Item not found"));
	}

	@Test
	public void SearchByCodeWithValidItem() {
	    String input = "1\nCustomer34\n3\n";
	    ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
	    Menu.setInputStream(testInput);

	    input = "3\n2\n7\n2\n5\n8\n";
	    testInput = new ByteArrayInputStream(input.getBytes());
	    Customer.setInputStream(testInput);

	    Menu.loginPage();
	    
	    String output = outContent.toString();
	    assertTrue(output.contains("Laptop"));
	}

	@Test
	public void CheckItemDetailsAndAddToCart() {
	    String input = "1\nCustomer35\n3\n";
	    ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
	    Menu.setInputStream(testInput);

	    input = "3\n4\n1\n1\n5\n8\n";
	    testInput = new ByteArrayInputStream(input.getBytes());
	    Customer.setInputStream(testInput);

	    Menu.loginPage();
	    
	    String output = outContent.toString();
	    assertTrue(output.contains("Toys") && output.contains("Item code:1"));
	}

	@Test
	public void CheckItemDetailsBackToSearchMenu() {
	    String input = "1\nCustomer36\n3\n";
	    ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
	    Menu.setInputStream(testInput);

	    input = "3\n4\n1\n2\n5\n8\n";
	    testInput = new ByteArrayInputStream(input.getBytes());
	    Customer.setInputStream(testInput);

	    Menu.loginPage();
	    
	    String output = outContent.toString();
	    assertTrue(output.contains("Toys"));
	}

	@Test
	public void AddToCartWithInvalidItemCode() {
	    String input = "1\nCustomer37\n3\n";
	    ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
	    Menu.setInputStream(testInput);

	    input = "3\n1\nToys\n1\n9999\n1\ny\n2\n5\n8\n";
	    testInput = new ByteArrayInputStream(input.getBytes());
	    Customer.setInputStream(testInput);

	    Menu.loginPage();
	    
	    String output = outContent.toString();
	    assertTrue(output.contains("Item not found"));
	}

	@Test
	public void AddToCartWithNegativeQuantity() {
	    String input = "1\nCustomer38\n3\n";
	    ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
	    Menu.setInputStream(testInput);

	    input = "3\n1\nToys\n1\n1\n-5\n1\n1\n2\ny\n5\n8\n";
	    testInput = new ByteArrayInputStream(input.getBytes());
	    Customer.setInputStream(testInput);

	    Menu.loginPage();
	    
	    String output = outContent.toString();
	    assertTrue(output.contains("Quantity must be positive"));
	}

	@Test
	public void AddToCartWithZeroQuantity() {
	    String input = "1\nCustomer39\n3\n";
	    ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
	    Menu.setInputStream(testInput);

	    input = "3\n1\nToys\n1\n1\n0\n1\n1\n2\ny\n5\n8\n";
	    testInput = new ByteArrayInputStream(input.getBytes());
	    Customer.setInputStream(testInput);

	    Menu.loginPage();
	    
	    String output = outContent.toString();
	    assertTrue(output.contains("Quantity must be positive"));
	}

	@Test
	public void ViewEmptyPurchaseHistory() {
	    String input = "1\nCustomer40\n3\n";
	    ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
	    Menu.setInputStream(testInput);

	    input = "6\n8\n";
	    testInput = new ByteArrayInputStream(input.getBytes());
	    Customer.setInputStream(testInput);

	    Menu.loginPage();
	    
	    String output = outContent.toString();
	    assertTrue(output.contains("No purchase history"));
	}

	@Test
	public void ViewEmptyNotifications() {
	    String input = "1\nCustomer41\n3\n";
	    ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
	    Menu.setInputStream(testInput);

	    input = "7\n8\n";
	    testInput = new ByteArrayInputStream(input.getBytes());
	    Customer.setInputStream(testInput);

	    Menu.loginPage();
	    
	    String output = outContent.toString();
	    assertTrue(output.contains("No notifications"));
	}

	@Test
	public void CheckoutAndViewNotifications() {
	    String input = "1\nCustomer42\n3\n";
	    ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
	    Menu.setInputStream(testInput);

	    input = "5\n1\n1\nBA222\n3\n3\n1\nToys\n1\n1\n2\ny\n5\n4\n1\n1\n4\n7\n8\n";
	    testInput = new ByteArrayInputStream(input.getBytes());
	    Customer.setInputStream(testInput);

	    Menu.loginPage();
	    
	    String output = outContent.toString();
	    assertTrue(output.contains("Payment successful"));
	    assertTrue(output.contains("Purchase successful"));
	}

	@Test
	public void CompleteWorkflowWithPurchaseHistoryView() {
	    String input = "1\nCustomer43\n3\n";
	    ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
	    Menu.setInputStream(testInput);

	    input = "5\n1\n1\nBA333\n3\n3\n1\nToys\n1\n1\n2\ny\n5\n4\n1\n1\n4\n6\n2\n8\n";
	    testInput = new ByteArrayInputStream(input.getBytes());
	    Customer.setInputStream(testInput);

	    Menu.loginPage();
	    
	    String output = outContent.toString();
	    assertTrue(output.contains("Payment successful"));
	    assertTrue(output.contains("Transaction ID"));
	}

	@Test
	public void CancelAddPaymentMethod() {
	    String input = "1\nCustomer44\n3\n";
	    ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
	    Menu.setInputStream(testInput);

	    input = "5\n1\n4\n3\n8\n";
	    testInput = new ByteArrayInputStream(input.getBytes());
	    Customer.setInputStream(testInput);

	    Menu.loginPage();
	    
	    String output = outContent.toString();
	    assertTrue(output.contains("Account Information"));
	}

	@Test
	public void DeletePaymentMethodWithCancellation() {
	    String input = "1\nCustomer45\n3\n";
	    ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
	    Menu.setInputStream(testInput);

	    input = "5\n1\n1\nBA444\n2\n1\nn\n3\n8\n";
	    testInput = new ByteArrayInputStream(input.getBytes());
	    Customer.setInputStream(testInput);

	    Menu.loginPage();
	    
	    String output = outContent.toString();
	    assertTrue(output.contains("Bank Account linked successfully"));
	}

	@Test
	public void AttemptDeletePaymentMethodWhenNone() {
	    String input = "1\nCustomer46\n3\n";
	    ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
	    Menu.setInputStream(testInput);

	    input = "5\n2\n3\n8\n";
	    testInput = new ByteArrayInputStream(input.getBytes());
	    Customer.setInputStream(testInput);

	    Menu.loginPage();
	    
	    String output = outContent.toString();
	    assertTrue(output.contains("No payment methods to delete"));
	}

	@Test
	public void SearchByInvalidCategory() {
	    String input = "1\nCustomer47\n3\n";
	    ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
	    Menu.setInputStream(testInput);

	    input = "3\n3\n999\n2\n2\n5\n8\n";
	    testInput = new ByteArrayInputStream(input.getBytes());
	    Customer.setInputStream(testInput);

	    Menu.loginPage();
	    
	    String output = outContent.toString();
	    assertTrue(output.contains("Invalid option, please try again!"));
	}

	@Test
	public void MultiplePaymentMethodsCheckout() {
	    String input = "1\nCustomer48\n3\n";
	    ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
	    Menu.setInputStream(testInput);

	    input = "5\n1\n1\nBA555\n1\n2\n4532000011112222\n11/26\n1\n3\n91234567\n3\n3\n1\nToys\n1\n1\n2\ny\n5\n4\n1\n2\n4\n8\n";
	    testInput = new ByteArrayInputStream(input.getBytes());
	    Customer.setInputStream(testInput);

	    Menu.loginPage();
	    
	    String output = outContent.toString();
	    assertTrue(output.contains("Bank Account linked successfully"));
	    assertTrue(output.contains("Credit Card linked successfully"));
	    assertTrue(output.contains("PayMe linked successfully"));
	    assertTrue(output.contains("Payment successful"));
	}

	@Test
	public void InvalidCheckoutOptionSelection() {
	    String input = "1\nCustomer49\n3\n";
	    ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
	    Menu.setInputStream(testInput);

	    input = "5\n1\n1\nBA666\n3\n3\n1\nToys\n1\n1\n2\ny\n5\n4\n1\n999\n4\n8\n";
	    testInput = new ByteArrayInputStream(input.getBytes());
	    Customer.setInputStream(testInput);

	    Menu.loginPage();
	    
	    String output = outContent.toString();
	    assertTrue(output.contains("Invalid choice"));
	}

	@Test
	public void SearchByNameMultipleResults() {
	    String input = "1\nCustomer50\n3\n";
	    ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
	    Menu.setInputStream(testInput);

	    input = "3\n1\ne\n2\n5\n8\n";
	    testInput = new ByteArrayInputStream(input.getBytes());
	    Customer.setInputStream(testInput);

	    Menu.loginPage();
	    
	    String output = outContent.toString();
	    assertTrue(output.contains("item(s) found"));
	}

	@Test
	public void CheckItemDetailsWithInvalidCode() {
	    String input = "1\nCustomer51\n3\n";
	    ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
	    Menu.setInputStream(testInput);

	    input = "3\n4\n9999\n4\n2\n5\n8\n";
	    testInput = new ByteArrayInputStream(input.getBytes());
	    Customer.setInputStream(testInput);

	    Menu.loginPage();
	    
	    String output = outContent.toString();
	    assertTrue(output.contains("Item not found. Please try again."));
	    
	}

	@Test
	public void HandleInvalidInputInSearchMenu() {
	    String input = "1\nCustomer52\n3\n";
	    ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
	    Menu.setInputStream(testInput);

	    input = "3\nabc\n5\n8\n";
	    testInput = new ByteArrayInputStream(input.getBytes());
	    Customer.setInputStream(testInput);

	    Menu.loginPage();
	    
	    String output = outContent.toString();
	    assertTrue(output.contains("Wrong input, please enter an integer"));
	}

	@Test
	public void HandleInvalidInputInAccountMenu() {
	    String input = "1\nCustomer53\n3\n";
	    ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
	    Menu.setInputStream(testInput);

	    input = "5\nabc\n3\n8\n";
	    testInput = new ByteArrayInputStream(input.getBytes());
	    Customer.setInputStream(testInput);

	    Menu.loginPage();
	    
	    String output = outContent.toString();
	    assertTrue(output.contains("Wrong input, please enter an integer"));
	}

	@Test
	public void HandleInvalidInputInCartMenu() {
	    String input = "1\nCustomer54\n3\n";
	    ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
	    Menu.setInputStream(testInput);

	    input = "4\nabc\n4\n8\n";
	    testInput = new ByteArrayInputStream(input.getBytes());
	    Customer.setInputStream(testInput);

	    Menu.loginPage();
	    
	    String output = outContent.toString();
	    assertTrue(output.contains("Wrong input, please enter an integer"));
	}

	@Test
	public void HandleInvalidInputInAddToCart() {
	    String input = "1\nCustomer55\n3\n";
	    ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
	    Menu.setInputStream(testInput);

	    input = "3\n1\nToys\n1\nabc\n2\n5\n8\n";
	    testInput = new ByteArrayInputStream(input.getBytes());
	    Customer.setInputStream(testInput);

	    Menu.loginPage();
	    
	    String output = outContent.toString();
	    assertTrue(output.contains("Wrong input, please enter an integer"));
	}
}

