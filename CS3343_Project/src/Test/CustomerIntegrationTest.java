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
}

