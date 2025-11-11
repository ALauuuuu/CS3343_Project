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
	public void testCompleteShoppingWorkflow() {
		String input = "1\nCustomer1\n3\n";
		ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		Menu.setInputStream(testInput);

		input = "3\n1\nToys\n1\n1\n2\ny\n4\n4\n8\n";
		testInput = new ByteArrayInputStream(input.getBytes());
		Customer.setInputStream(testInput);

		Menu.loginPage();
		
		String output = outContent.toString();
		assertTrue(output.contains("Item added to cart successfully"));
		assertTrue(output.contains("Shopping Cart"));
	}
	
	@Test
	public void testAddMultipleItemsToCartWorkflow() {
		String input = "1\nCustomer2\n3\n";
		ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		Menu.setInputStream(testInput);

		input = "3\n1\nToys\n1\n1\n2\ny\n3\n2\n5\n1\n5\n3\ny\n4\n8\n";
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
		assertTrue(successCount >= 2);
	}
	
	@Test
	public void testCartPersistsAcrossMenuNavigation() {
		String input = "1\nCustomer3\n3\n";
		ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		Menu.setInputStream(testInput);

		input = "3\n1\nToys\n1\n1\n2\ny\n4\n4\n8\n";
		testInput = new ByteArrayInputStream(input.getBytes());
		Customer.setInputStream(testInput);

		Menu.loginPage();
		
		String output = outContent.toString();
		assertTrue(output.contains("Item added to cart successfully"));
		assertTrue(output.contains("Shopping Cart"));
	}
	
	@Test
	public void testAddItemWithInsufficientStock() {
		String input = "1\nCustomer14\n3\n";
		ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		Menu.setInputStream(testInput);

		input = "3\n1\nToys\n1\n1\n1000\ny\n2\n4\n8\n";
		testInput = new ByteArrayInputStream(input.getBytes());
		Customer.setInputStream(testInput);

		Menu.loginPage();
		
		String output = outContent.toString();
		assertTrue(output.contains("Stock is not enough"));
	}
	
	
	@Test
	public void testAddBankAccountPaymentMethod() {
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
	public void testAddCreditCardPaymentMethod() {
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
	public void testAddPayMePaymentMethod() {
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
	public void testAddMultiplePaymentMethods() {
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
	public void testPreventDuplicatePaymentMethod() {
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
	public void testDeletePaymentMethod() {
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
	public void testCheckoutWithoutPaymentMethod() {
		String input = "1\nCustomer10\n3\n";
		ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		Menu.setInputStream(testInput);

		input = "3\n1\nToys\n1\n1\n2\ny\n4\n4\n1\n4\n8\n";
		testInput = new ByteArrayInputStream(input.getBytes());
		Customer.setInputStream(testInput);

		Menu.loginPage();
		
		String output = outContent.toString();
		assertTrue(output.contains("NO PAYMENT METHODS LINKED") || 
			output.contains("PLEASE ADD A PAYMENT METHOD"));
	}
	
	@Test
	public void testCompleteCheckoutWorkflow() {
		String input = "1\nCustomer11\n3\n";
		ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		Menu.setInputStream(testInput);

		input = "5\n1\n1\nBA888\n3\n3\n1\nToys\n1\n1\n2\ny\n4\n4\n1\n1\n6\n8\n";
		testInput = new ByteArrayInputStream(input.getBytes());
		Customer.setInputStream(testInput);

		Menu.loginPage();
		
		String output = outContent.toString();
		assertTrue(output.contains("Payment successful"));
	}
	
	
	@Test
	public void testHandleInvalidMenuOption() {
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
	public void testHandleNonIntegerInput() {
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
	public void testCancelAddToCart() {
		String input = "1\nCustomer15\n3\n";
		ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		Menu.setInputStream(testInput);

		input = "3\n1\nToys\n1\n1\n2\nn\n2\n4\n8\n";
		testInput = new ByteArrayInputStream(input.getBytes());
		Customer.setInputStream(testInput);

		Menu.loginPage();
		
		String output = outContent.toString();
		assertTrue(output.contains("Addition cancelled"));
	}
	
	
	@Test
	public void testNavigateToNextPage() {
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
	public void testNavigateToPreviousPage() {
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
	public void testAttemptNextPageAtEnd() {
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
	public void testAttemptPreviousPageAtStart() {
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
	public void testSearchIntegratesWithInventory() {
		String input = "1\nCustomer20\n3\n";
		ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		Menu.setInputStream(testInput);

		input = "3\n2\n7\n2\n4\n8\n";
		testInput = new ByteArrayInputStream(input.getBytes());
		Customer.setInputStream(testInput);

		Menu.loginPage();
		
		String output = outContent.toString();
		assertTrue(output.contains("Laptop"));
		assertTrue(output.contains("1200"));
	}
	
	@Test
	public void testCartReflectsInventoryStock() {
		String input = "1\nCustomer21\n3\n";
		ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		Menu.setInputStream(testInput);

		input = "3\n1\nToys\n1\n1\n10\ny\n2\n4\n8\n";
		testInput = new ByteArrayInputStream(input.getBytes());
		Customer.setInputStream(testInput);

		Menu.loginPage();
		
		String output = outContent.toString();
		assertTrue(output.contains("Item added to cart successfully"));
	}

	@Test
	public void testCompleteCustomerJourney() {
		String input = "1\nCustomer22\n3\n";
		ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		Menu.setInputStream(testInput);

		input = "1\n3\n1\nLaptop\n1\n7\n1\ny\n5\n1\n1\nBA12345\n3\n4\n1\n1\n8\n";
		testInput = new ByteArrayInputStream(input.getBytes());
		Customer.setInputStream(testInput);

		Menu.loginPage();
		
		String output = outContent.toString();
		assertTrue(output.contains("Item added to cart successfully"));
		assertTrue(output.contains("Bank Account linked successfully"));
		assertTrue(output.contains("Payment successful"));
	}
}
