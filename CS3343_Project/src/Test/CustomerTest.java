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

public class CustomerTest {

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
	public void searchByNameUnsuccessfully() {
		String input = "1\nMeki\n3\n";
		ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		Menu.setInputStream(testInput);

		input = "3\n1\nNonExistentProduct\n2\n5\n8\n";
		testInput = new ByteArrayInputStream(input.getBytes());
		Customer.setInputStream(testInput);
		
		Menu.loginPage();
		
		String output = outContent.toString();
		assertTrue(output.contains("No items found") || output.contains("0 item(s) found"));
	}
	
	@Test
	public void searchByCodeSuccessfully() {
		String input = "1\nMeki\n3\n";
		ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		Menu.setInputStream(testInput);

		input = "3\n2\n5\n2\n5\n8\n";
		testInput = new ByteArrayInputStream(input.getBytes());
		Customer.setInputStream(testInput);

		Menu.loginPage();
		
		String output = outContent.toString();
		assertTrue(output.contains("Pen"));
	}
	
	@Test
	public void searchByCodeUnsuccessfully() {
		String input = "1\nMeki\n3\n";
		ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		Menu.setInputStream(testInput);

		input = "3\n2\n999\n3\n2\n5\n8\n";
		testInput = new ByteArrayInputStream(input.getBytes());
		Customer.setInputStream(testInput);
		
		Menu.loginPage();
		String output = outContent.toString();
		assertTrue(output.contains("Shampoo"));
	}
	
	@Test
	public void searchByCatSuccessfully() {
		String input = "1\nMeki\n3\n";
		ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		Menu.setInputStream(testInput);
		
		input = "3\n3\n1\n2\n5\n8\n";
		testInput = new ByteArrayInputStream(input.getBytes());
		Customer.setInputStream(testInput);
		
		Menu.loginPage();
		
		String output = outContent.toString();
		assertTrue(output.contains("item(s) found"));
		assertFalse(output.contains("0 item(s) found"));
	}
	
	@Test
	public void searchByCatUnsuccessfully() {
		String input = "1\nMeki\n3\n";
		ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		Menu.setInputStream(testInput);

		input = "3\n3\n999\n1\n2\n5\n8\n";
		testInput = new ByteArrayInputStream(input.getBytes());
		Customer.setInputStream(testInput);
		
		Menu.loginPage();
		
		String output = outContent.toString();
		assertTrue(output.contains("Invalid option, please try again!") || 
			output.contains("Wrong input, please enter an integer."));
	}

	@Test
	public void searchByNameCaseSensitive() {
		String input = "1\nUser1\n3\n";
		ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		Menu.setInputStream(testInput);

		input = "3\n1\ntoys\n2\n5\n8\n";
		testInput = new ByteArrayInputStream(input.getBytes());
		Customer.setInputStream(testInput);

		Menu.loginPage();
		
		String output = outContent.toString();
		assertTrue(output.contains("item(s) found") || output.contains("No items found"));
	}

	@Test
	public void multipleSearchOperations() {
		String input = "1\nUser1\n3\n";
		ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		Menu.setInputStream(testInput);

		input = "3\n1\nToys\n2\n2\n5\n2\n5\n8\n";
		testInput = new ByteArrayInputStream(input.getBytes());
		Customer.setInputStream(testInput);

		Menu.loginPage();
		
		String output = outContent.toString();
		assertTrue(output.contains("Toys"));
		assertTrue(output.contains("Pen"));
	}

	@Test
	public void CreateReviewWithValidInput() {
		String input = "1\nReviewer1\n3\n";
		ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		Menu.setInputStream(testInput);

		input = "5\n1\n1\nBA111\n3\n3\n1\nToys\n1\n1\n2\ny\n5\n4\n1\n1\n4\n3\n4\n1\n3\n6\n5\n5\nGreatProduct\n5\n8\n";
		testInput = new ByteArrayInputStream(input.getBytes());
		Customer.setInputStream(testInput);

		Menu.loginPage();
		
		String output = outContent.toString();
		assertTrue(output.contains("Payment successful"));
		assertTrue(output.contains("Wrong input, please enter an integer."));
	}


	@Test
	public void testCreateReviewWithNonIntegerRating() {
		String input = "1\nReviewer3\n3\n";
		ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		Menu.setInputStream(testInput);

		input = "5\n1\n1\nBA333\n3\n3\n1\nToys\n1\n1\n2\ny\n5\n4\n1\n1\n4\n3\n4\n1\nabc\n3\n4\nNice\n5\n8\n";
		testInput = new ByteArrayInputStream(input.getBytes());
		Customer.setInputStream(testInput);

		Menu.loginPage();
		
		String output = outContent.toString();
		assertTrue(output.contains("Payment successful"));
		assertTrue(output.contains("Wrong input, please enter an integer"));
	}

	@Test
	public void RefundSuccessfully() {
		String input = "1\nRefundUser\n3\n";
		ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		Menu.setInputStream(testInput);

		input = "5\n1\n1\nBA444\n3\n3\n1\nToys\n1\n1\n2\ny\n5\n4\n1\n1\n4\n6\n1\n1\n2\n8\n";
		testInput = new ByteArrayInputStream(input.getBytes());
		Customer.setInputStream(testInput);

		Menu.loginPage();
		
		String output = outContent.toString();
		assertTrue(output.contains("Payment successful"));
		assertTrue(output.contains("Refunded"));
	}

	@Test
	public void RefundAlreadyRefundedItem() {
		String input = "1\nRefundUser2\n3\n";
		ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		Menu.setInputStream(testInput);

		input = "5\n1\n1\nBA555\n3\n3\n1\nToys\n1\n1\n2\ny\n5\n4\n1\n1\n4\n6\n1\n1\n1\n1\n2\n8\n";
		testInput = new ByteArrayInputStream(input.getBytes());
		Customer.setInputStream(testInput);

		Menu.loginPage();
		
		String output = outContent.toString();
		assertTrue(output.contains("Payment successful"));
		assertTrue(output.contains("This item has already been refunded"));
	}

	@Test
	public void RefundWithInvalidId() {
		String input = "1\nRefundUser3\n3\n";
		ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		Menu.setInputStream(testInput);

		input = "5\n1\n1\nBA666\n3\n3\n1\nToys\n1\n1\n2\ny\n5\n4\n1\n1\n4\n6\n1\n999\n2\n8\n";
		testInput = new ByteArrayInputStream(input.getBytes());
		Customer.setInputStream(testInput);

		Menu.loginPage();
		
		String output = outContent.toString();
		assertTrue(output.contains("Payment successful"));
		assertTrue(output.contains("Wrong input, please enter an correct integer"));
	}

	@Test
	public void RefundWithNonIntegerInput() {
		String input = "1\nRefundUser4\n3\n";
		ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		Menu.setInputStream(testInput);

		input = "5\n1\n1\nBA777\n3\n3\n1\nToys\n1\n1\n2\ny\n5\n4\n1\n1\n4\n6\n1\nabc\n2\n8\n";
		testInput = new ByteArrayInputStream(input.getBytes());
		Customer.setInputStream(testInput);

		Menu.loginPage();
		
		String output = outContent.toString();
		assertTrue(output.contains("Payment successful"));
		assertTrue(output.contains("Wrong input, please enter an correct integer"));
	}

	@Test
	public void ClearCartWithWrongInput() {
		String input = "1\nCartUser\n3\n";
		ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		Menu.setInputStream(testInput);

		input = "3\n1\nToys\n1\n1\n2\ny\n5\n4\n2\nmaybe\ny\n4\n8\n";
		testInput = new ByteArrayInputStream(input.getBytes());
		Customer.setInputStream(testInput);

		Menu.loginPage();
		
		String output = outContent.toString();
		assertTrue(output.contains("Item added to cart successfully"));
		assertTrue(output.contains("Wrong input, please enter a charater"));
	}

	@Test
	public void InvalidAddToCartOption() {
		String input = "1\nUser5\n3\n";
		ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		Menu.setInputStream(testInput);

		input = "3\n1\nToys\n5\n2\n5\n8\n";
		testInput = new ByteArrayInputStream(input.getBytes());
		Customer.setInputStream(testInput);

		Menu.loginPage();
		
		String output = outContent.toString();
		assertTrue(output.contains("Invalid option, please enter 1 or 2"));
	}

	@Test
	public void InvalidCreditCardDateFormat() {
		String input = "1\nUser6\n3\n";
		ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		Menu.setInputStream(testInput);

		input = "5\n1\n2\n4532111122223333\n13-25\n4\n3\n8\n";
		testInput = new ByteArrayInputStream(input.getBytes());
		Customer.setInputStream(testInput);

		Menu.loginPage();
		
		String output = outContent.toString();
		assertTrue(output.contains("Invalid date format"));
	}

	@Test
	public void PaymentFailureScenario() {
		String input = "1\nUser7\n3\n";
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
	public void CheckItemDetailsRecursion() {
		String input = "1\nUser8\n3\n";
		ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		Menu.setInputStream(testInput);

		// Check item details with invalid code first (triggers recursion)
		input = "3\n4\n999\n1\n2\n5\n8\n";
		testInput = new ByteArrayInputStream(input.getBytes());
		Customer.setInputStream(testInput);

		Menu.loginPage();
		
		String output = outContent.toString();
		assertTrue(output.contains("Item not found. Please try again."));
	}

	@Test
	public void SearchByCategoryRecursion() {
		String input = "1\nUser9\n3\n";
		ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		Menu.setInputStream(testInput);

		input = "3\n3\nabc\n1\n2\n5\n8\n";
		testInput = new ByteArrayInputStream(input.getBytes());
		Customer.setInputStream(testInput);

		Menu.loginPage();
		
		String output = outContent.toString();
		assertTrue(output.contains("Wrong input, please enter an integer"));
	}

	@Test
	public void EmptyCartCheckout() {
		String input = "1\nUser10\n3\n";
		ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		Menu.setInputStream(testInput);

		// Try to checkout with empty cart
		input = "4\n1\n4\n8\n";
		testInput = new ByteArrayInputStream(input.getBytes());
		Customer.setInputStream(testInput);

		Menu.loginPage();
		
		String output = outContent.toString();
		assertTrue(output.contains("Cart is empty, cannot checkout"));
	}

	@Test
	public void testAddToCartContinueLoop() {
		String input = "1\nUser11\n3\n";
		ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		Menu.setInputStream(testInput);

		input = "3\n1\nToys\n1\n999\n1\n1\n2\ny\n5\n8\n";
		testInput = new ByteArrayInputStream(input.getBytes());
		Customer.setInputStream(testInput);

		Menu.loginPage();
		
		String output = outContent.toString();
		assertTrue(output.contains("Item not found. Please try again."));
		assertTrue(output.contains("Item added to cart successfully"));
	}

	@Test
	public void testSearchByCodeNonInteger() {
		String input = "1\nUser12\n3\n";
		ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		Menu.setInputStream(testInput);

		input = "3\n2\nabc\n5\n2\n5\n8\n";
		testInput = new ByteArrayInputStream(input.getBytes());
		Customer.setInputStream(testInput);

		Menu.loginPage();
		
		String output = outContent.toString();
		assertTrue(output.contains("Wrong input, please enter an integer"));
	}

	@Test
	public void CheckItemDetailsNonInteger() {
		String input = "1\nUser13\n3\n";
		ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		Menu.setInputStream(testInput);

		input = "3\n4\nabc\n1\n2\n5\n8\n";
		testInput = new ByteArrayInputStream(input.getBytes());
		Customer.setInputStream(testInput);

		Menu.loginPage();
		
		String output = outContent.toString();
		assertTrue(output.contains("Wrong input, please enter an integer"));
	}

	@Test
	public void InvalidAddPaymentMethodInput() {
		String input = "1\nUser14\n3\n";
		ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		Menu.setInputStream(testInput);

		input = "5\n1\nabc\n4\n3\n8\n";
		testInput = new ByteArrayInputStream(input.getBytes());
		Customer.setInputStream(testInput);

		Menu.loginPage();
		
		String output = outContent.toString();
		assertTrue(output.contains("Wrong input, please enter an integer"));
	}

	@Test
	public void DeletePaymentMethodNonInteger() {
		String input = "1\nUser15\n3\n";
		ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		Menu.setInputStream(testInput);

		input = "5\n1\n1\nBA123\n2\nabc\n3\n8\n";
		testInput = new ByteArrayInputStream(input.getBytes());
		Customer.setInputStream(testInput);

		Menu.loginPage();
		
		String output = outContent.toString();
		assertTrue(output.contains("Bank Account linked successfully"));
		assertTrue(output.contains("Wrong input, please enter an integer"));
	}

	@Test
	public void CheckoutNonInteger() {
		String input = "1\nUser16\n3\n";
		ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		Menu.setInputStream(testInput);

		input = "5\n1\n1\nBA456\n3\n3\n1\nToys\n1\n1\n2\ny\n5\n4\n1\nabc\n2\ny\n4\n8\n";
		testInput = new ByteArrayInputStream(input.getBytes());
		Customer.setInputStream(testInput);

		Menu.loginPage();
		
		String output = outContent.toString();
		assertTrue(output.contains("Wrong input, please enter an integer"));
	}

	@Test
	public void ModifyCartNonInteger() {
		String input = "1\nUser17\n3\n";
		ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		Menu.setInputStream(testInput);

		input = "3\n1\nToys\n1\n1\n2\ny\n5\n4\n3\nabc\n4\n8\n";
		testInput = new ByteArrayInputStream(input.getBytes());
		Customer.setInputStream(testInput);

		Menu.loginPage();
		
		String output = outContent.toString();
		assertTrue(output.contains("Wrong input, please enter an integer"));
	}

	@Test
	public void RemoveItemFromCart() {
		String input = "1\nUser18\n3\n";
		ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		Menu.setInputStream(testInput);

		input = "3\n1\nToys\n1\n1\n2\ny\n5\n4\n3\n1\n0\n4\n8\n";
		testInput = new ByteArrayInputStream(input.getBytes());
		Customer.setInputStream(testInput);

		Menu.loginPage();
		
		String output = outContent.toString();
		assertTrue(output.contains("Item added to cart successfully"));
	}

	@Test
	public void AskAddToCartWithNonIntegerItemCode() {
		String input = "1\nUser19\n3\n";
		ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		Menu.setInputStream(testInput);

		input = "3\n1\nToys\n1\nabc\n1\n1\n2\ny\n5\n8\n";
		testInput = new ByteArrayInputStream(input.getBytes());
		Customer.setInputStream(testInput);

		Menu.loginPage();
		
		String output = outContent.toString();
		assertTrue(output.contains("Wrong input, please enter an integer"));
	}

	@Test
	public void AskAddToCartWithNonIntegerQuantity() {
		String input = "1\nUser20\n3\n";
		ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		Menu.setInputStream(testInput);

		input = "3\n1\nToys\n1\n1\nabc\n2\ny\n5\n8\n";
		testInput = new ByteArrayInputStream(input.getBytes());
		Customer.setInputStream(testInput);

		Menu.loginPage();
		
		String output = outContent.toString();
		assertTrue(output.contains("Wrong input, please enter an integer"));
	}

	@Test
	public void CheckItemDetailsOption3WithReview() {
		String input = "1\nUser21\n3\n";
		ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		Menu.setInputStream(testInput);

		input = "5\n1\n1\nBA5000\n3\n3\n1\nToys\n1\n1\n1\ny\n5\n4\n1\n1\n4\n3\n4\n1\n3\n5\nExcellent\n5\n8\n";
		testInput = new ByteArrayInputStream(input.getBytes());
		Customer.setInputStream(testInput);

		Menu.loginPage();
		
		String output = outContent.toString();
		assertTrue(output.contains("Payment successful"));
	}

	@Test
	public void CheckItemDetailsOption1AddOne() {
		String input = "1\nUser22\n3\n";
		ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		Menu.setInputStream(testInput);

		input = "3\n4\n1\n1\n5\n8\n";
		testInput = new ByteArrayInputStream(input.getBytes());
		Customer.setInputStream(testInput);

		Menu.loginPage();
		
		String output = outContent.toString();
		assertTrue(output.contains("Toys"));
	}

	@Test
	public void AskAddToCartInvalidOption() {
		String input = "1\nUser23\n3\n";
		ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		Menu.setInputStream(testInput);

		input = "3\n1\nToys\n999\n2\n5\n8\n";
		testInput = new ByteArrayInputStream(input.getBytes());
		Customer.setInputStream(testInput);

		Menu.loginPage();
		
		String output = outContent.toString();
		assertTrue(output.contains("Invalid option, please enter 1 or 2"));
	}

	@Test
	public void AskAddToCartNonInteger() {
		String input = "1\nUser24\n3\n";
		ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		Menu.setInputStream(testInput);

		input = "3\n1\nToys\nabc\n2\n5\n8\n";
		testInput = new ByteArrayInputStream(input.getBytes());
		Customer.setInputStream(testInput);

		Menu.loginPage();
		
		String output = outContent.toString();
		assertTrue(output.contains("Wrong input, please enter an integer"));
	}

	@Test
	public void SearchByCategoryReturnsFalse() {
		String input = "1\nUser25\n3\n";
		ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		Menu.setInputStream(testInput);

		input = "3\n3\n0\n1\n2\n5\n8\n";
		testInput = new ByteArrayInputStream(input.getBytes());
		Customer.setInputStream(testInput);

		Menu.loginPage();
		
		String output = outContent.toString();
		assertTrue(output.contains("Invalid option, please try again!"));
	}

	@Test
	public void SearchByNameReturnsFalse() {
		String input = "1\nUser26\n3\n";
		ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		Menu.setInputStream(testInput);

		input = "3\n1\nNonExistentItemXYZ123\n2\n5\n8\n";
		testInput = new ByteArrayInputStream(input.getBytes());
		Customer.setInputStream(testInput);

		Menu.loginPage();
		
		String output = outContent.toString();
		assertTrue(output.contains("No items found"));
	}

	@Test
	public void PurchasingHistoryMenuRefundOption() {
		String input = "1\nUser27\n3\n";
		ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		Menu.setInputStream(testInput);

		// Buy item, view history, and refund
		input = "5\n1\n1\nBA6000\n3\n3\n1\nToys\n1\n1\n2\ny\n5\n4\n1\n1\n4\n6\n1\n1\n2\n8\n";
		testInput = new ByteArrayInputStream(input.getBytes());
		Customer.setInputStream(testInput);

		Menu.loginPage();
		
		String output = outContent.toString();
		assertTrue(output.contains("Refun"));
	}

	@Test
	public void DeletePaymentMethodCancel() {
		String input = "1\nUser28\n3\n";
		ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		Menu.setInputStream(testInput);

		// Add payment method and cancel deletion
		input = "5\n1\n1\nBA7000\n2\n2\n3\n8\n";
		testInput = new ByteArrayInputStream(input.getBytes());
		Customer.setInputStream(testInput);

		Menu.loginPage();
		
		String output = outContent.toString();
		assertTrue(output.contains("Bank Account linked successfully"));
	}

	@Test
	public void CheckoutCancelOption() {
		String input = "1\nUser29\n3\n";
		ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		Menu.setInputStream(testInput);

		input = "5\n1\n1\nBA8000\n3\n3\n1\nToys\n1\n1\n2\ny\n5\n4\n1\n2\n4\n8\n";
		testInput = new ByteArrayInputStream(input.getBytes());
		Customer.setInputStream(testInput);

		Menu.loginPage();
		
		String output = outContent.toString();
		assertTrue(output.contains("Checkout canceled"));
	}

	@Test
	public void ModifyCartQuantityNonInteger() {
		String input = "1\nUser30\n3\n";
		ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		Menu.setInputStream(testInput);

		input = "3\n1\nToys\n1\n1\n2\ny\n5\n4\n3\n1\nabc\n4\n8\n";
		testInput = new ByteArrayInputStream(input.getBytes());
		Customer.setInputStream(testInput);

		Menu.loginPage();
		
		String output = outContent.toString();
		assertTrue(output.contains("Wrong input, please enter an integer"));
	}

	@Test
	public void testAddItemConfirmLowerCaseY() {
		String input = "1\nUser31\n3\n";
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
	public void AddItemConfirmUpperCaseY() {
		String input = "1\nUser32\n3\n";
		ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		Menu.setInputStream(testInput);

		input = "3\n1\nToys\n1\n1\n2\nY\n5\n8\n";
		testInput = new ByteArrayInputStream(input.getBytes());
		Customer.setInputStream(testInput);

		Menu.loginPage();
		
		String output = outContent.toString();
		assertTrue(output.contains("Item added to cart successfully"));
	}

	@Test
	public void PaymentFailed() {
		String input = "1\nUser33\n3\n";
		ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		Menu.setInputStream(testInput);

		input = "5\n1\n1\nBA9000\n3\n3\n1\nToys\n1\n1\n2\ny\n5\n4\n1\n1\n4\n8\n";
		testInput = new ByteArrayInputStream(input.getBytes());
		Customer.setInputStream(testInput);

		Menu.loginPage();
		
		String output = outContent.toString();
		assertTrue(output.contains("Payment successful") || output.contains("Payment failed"));
	}

	@Test
	public void DeleteCreditCardWithConfirmation() {
		String input = "1\nUser34\n3\n";
		ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		Menu.setInputStream(testInput);

		input = "5\n1\n2\n4532111122223333\n12/25\n2\n1\ny\n3\n8\n";
		testInput = new ByteArrayInputStream(input.getBytes());
		Customer.setInputStream(testInput);

		Menu.loginPage();
		
		String output = outContent.toString();
		assertTrue(output.contains("Credit Card linked successfully"));
		assertTrue(output.contains("Credit Card deleted successfully"));
	}

	@Test
	public void DeletePayMeWithConfirmation() {
		String input = "1\nUser35\n3\n";
		ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		Menu.setInputStream(testInput);

		input = "5\n1\n3\n91234567\n2\n1\ny\n3\n8\n";
		testInput = new ByteArrayInputStream(input.getBytes());
		Customer.setInputStream(testInput);

		Menu.loginPage();
		
		String output = outContent.toString();
		assertTrue(output.contains("PayMe linked successfully"));
		assertTrue(output.contains("PayMe deleted successfully"));
	}

	@Test
	public void DeletePaymentMethodCancelChoice() {
		String input = "1\nUser36\n3\n";
		ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		Menu.setInputStream(testInput);

		input = "5\n1\n1\nBA10000\n1\n2\n4532111122223333\n12/25\n2\n3\n3\n8\n";
		testInput = new ByteArrayInputStream(input.getBytes());
		Customer.setInputStream(testInput);

		Menu.loginPage();
		
		String output = outContent.toString();
		assertTrue(output.contains("Bank Account linked successfully"));
		assertTrue(output.contains("Credit Card linked successfully"));
	}

	@Test
	public void DeletePaymentMethodInvalidChoice() {
		String input = "1\nUser37\n3\n";
		ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		Menu.setInputStream(testInput);

		input = "5\n1\n1\nBA11000\n2\n999\n3\n8\n";
		testInput = new ByteArrayInputStream(input.getBytes());
		Customer.setInputStream(testInput);

		Menu.loginPage();
		
		String output = outContent.toString();
		assertTrue(output.contains("Bank Account linked successfully"));
		assertTrue(output.contains("Invalid choice"));
	}

	@Test
	public void AddPaymentMethodInvalidChoice() {
		String input = "1\nUser38\n3\n";
		ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		Menu.setInputStream(testInput);

		input = "5\n1\n999\n4\n3\n8\n";
		testInput = new ByteArrayInputStream(input.getBytes());
		Customer.setInputStream(testInput);

		Menu.loginPage();
		
		String output = outContent.toString();
		assertTrue(output.contains("Invalid choice"));
	}

	@Test
	public void AddDuplicateCreditCard() {
		String input = "1\nUser39\n3\n";
		ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		Menu.setInputStream(testInput);

		input = "5\n1\n2\n4532111122223333\n12/25\n1\n2\n4532111122223333\n12/25\n3\n8\n";
		testInput = new ByteArrayInputStream(input.getBytes());
		Customer.setInputStream(testInput);

		Menu.loginPage();
		
		String output = outContent.toString();
		assertTrue(output.contains("Credit Card linked successfully"));
		assertTrue(output.contains("This Credit Card is already linked"));
	}

	@Test
	public void AddDuplicatePayMe() {
		String input = "1\nUser40\n3\n";
		ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		Menu.setInputStream(testInput);

		input = "5\n1\n3\n91234567\n1\n3\n91234567\n3\n8\n";
		testInput = new ByteArrayInputStream(input.getBytes());
		Customer.setInputStream(testInput);

		Menu.loginPage();
		
		String output = outContent.toString();
		assertTrue(output.contains("PayMe linked successfully"));
		assertTrue(output.contains("This PayMe account is already linked"));
	}

	@Test
	public void testSearchMenuInvalidOption() {
		String input = "1\nUser41\n3\n";
		ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		Menu.setInputStream(testInput);

		// Enter invalid option in search menu
		input = "3\n999\n5\n8\n";
		testInput = new ByteArrayInputStream(input.getBytes());
		Customer.setInputStream(testInput);

		Menu.loginPage();
		
		String output = outContent.toString();
		assertTrue(output.contains("Invalid option, please try again"));
	}

	@Test
	public void SearchMenuNonInteger() {
		String input = "1\nUser42\n3\n";
		ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		Menu.setInputStream(testInput);

		// Enter non-integer in search menu
		input = "3\nabc\n5\n8\n";
		testInput = new ByteArrayInputStream(input.getBytes());
		Customer.setInputStream(testInput);

		Menu.loginPage();
		
		String output = outContent.toString();
		assertTrue(output.contains("Wrong input, please enter an integer"));
	}

	@Test
	public void ShoppingCartMenuInvalidOption() {
		String input = "1\nUser43\n3\n";
		ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		Menu.setInputStream(testInput);

		input = "4\n999\n4\n8\n";
		testInput = new ByteArrayInputStream(input.getBytes());
		Customer.setInputStream(testInput);

		Menu.loginPage();
		
		String output = outContent.toString();
		assertTrue(output.contains("Invalid option, please try again"));
	}

	@Test
	public void ShoppingCartMenuNonInteger() {
		String input = "1\nUser44\n3\n";
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
	public void AccountInformationMenuInvalidOption() {
		String input = "1\nUser45\n3\n";
		ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		Menu.setInputStream(testInput);

		input = "5\n999\n3\n1\n1\n9\n3\n1\nToys\n1\n1\n-1\n1\n1\n2\nn\n5\n8\n";
		testInput = new ByteArrayInputStream(input.getBytes());
		Customer.setInputStream(testInput);

		Menu.loginPage();
		
		String output = outContent.toString();
		assertTrue(output.contains("Invalid option"));
		assertTrue(output.contains("You are on the last page"));
		assertTrue(output.contains("Invalid option. Please try again."));
		assertTrue(output.contains("Quantity must be positive. Please try again."));
		assertTrue(output.contains("Addition cancelled."));
	}

	@Test
	public void AccountInformationMenuNonInteger() {
		String input = "1\nUser46\n3\n";
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
	public void PurchaseHistoryMenuInvalidOption() {
		String input = "1\nUser47\n3\n";
		ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		Menu.setInputStream(testInput);

		input = "5\n1\n1\nBA12000\n3\n3\n1\nToys\n1\n1\n2\ny\n5\n4\n1\n1\n4\n6\n999\n2\n8\n";
		testInput = new ByteArrayInputStream(input.getBytes());
		Customer.setInputStream(testInput);

		Menu.loginPage();
		
		String output = outContent.toString();
		assertTrue(output.contains("Invalid option, please try again"));
	}

	@Test
	public void PurchaseHistoryMenuNonInteger() {
		String input = "1\nUser48\n3\n";
		ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		Menu.setInputStream(testInput);

		input = "5\n1\n1\nBA13000\n3\n3\n1\nToys\n1\n1\n2\ny\n5\n4\n1\n1\n4\n6\nabc\n2\n8\n";
		testInput = new ByteArrayInputStream(input.getBytes());
		Customer.setInputStream(testInput);

		Menu.loginPage();
		
		String output = outContent.toString();
		assertTrue(output.contains("Wrong input, please enter an integer"));
	}

	@Test
	public void AddPaymentMethodCancel() {
		String input = "1\nUser49\n3\n";
		ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		Menu.setInputStream(testInput);

		input = "2\n@\n7\n5\n1\n4\n3\n8\n";
		testInput = new ByteArrayInputStream(input.getBytes());
		Customer.setInputStream(testInput);

		Menu.loginPage();
		
		String output = outContent.toString();
		assertTrue(output.contains("You are on the first page."));
		assertTrue(output.contains("Wrong input, please enter an integer."));
		assertTrue(output.contains("Notification Page"));
		assertTrue(output.contains("Account Information"));
	}
	
	@Test
	public void CheckPurchasingHistory() {
		String input = "1\nUser50\n3\n";
		ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		Menu.setInputStream(testInput);

		input = "6\n5\n1\n1\nBA111\n1\n1\nBA111\n3\n8";
		testInput = new ByteArrayInputStream(input.getBytes());
		Customer.setInputStream(testInput);

		Menu.loginPage();
		
		String output = outContent.toString();
		assertTrue(output.contains("No purchase history"));
		assertTrue(output.contains("This Bank Account is already linked."));
	}
	
	@Test
	public void PaymentDelete() {
		String input = "1\nUser51\n3\n";
		ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		Menu.setInputStream(testInput);

		input = "5\n2\n1\n1\nBA1111\n2\n1\ny\n3\n8";
		testInput = new ByteArrayInputStream(input.getBytes());
		Customer.setInputStream(testInput);

		Menu.loginPage();
		
		String output = outContent.toString();
		assertTrue(output.contains("No payment methods to delete."));
		assertTrue(output.contains("Bank Account deleted successfully!"));
	}
	
	@Test
	public void CheckOut() {
		String input = "1\nUser52\n3\n";
		ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		Menu.setInputStream(testInput);

		input = "3\n2\n1\n1\n1\n2\ny\n5\n4\n1\n4\n5\n1\n1\nBA1234\n1\n2\n1111222233334444\n12/12\n1\n3\n111222\n3\n4\n1\n4\n4\n8";
		testInput = new ByteArrayInputStream(input.getBytes());
		Customer.setInputStream(testInput);

		Menu.loginPage();
		
		String output = outContent.toString();
		assertTrue(output.contains("NO PAYMENT METHODS LINKED. PLEASE ADD A PAYMENT METHOD IN ACCOUNT INFORMATION."));
		assertTrue(output.contains("(Please go to Account Information > Add a payment method"));
		
	}
	
	@Test
	public void PurchasePaymeNotification() {
		String input = "1\nUser53\n3\n";
		ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		Menu.setInputStream(testInput);

		input = "3\n2\n1\n1\n1\n2\ny\n5\n5\n1\n3\n@111\n3\n4\n1\n1\n4\n7\n8";
		testInput = new ByteArrayInputStream(input.getBytes());
		Customer.setInputStream(testInput);

		Menu.loginPage();
		
		String output = outContent.toString();
		assertTrue(output.contains("Purchase successful"));
	}
	
	@Test
	public void PurchaseCreditNotification() {
		String input = "1\nUser54\n3\n";
		ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		Menu.setInputStream(testInput);

		input = "3\n2\n1\n1\n1\n2\ny\n5\n5\n1\n2\n4444555566667777\n12/12\n3\n4\n1\n3\n1\n1\n4\n7\n8";
		testInput = new ByteArrayInputStream(input.getBytes());
		Customer.setInputStream(testInput);

		Menu.loginPage();
		
		String output = outContent.toString();
		assertTrue(output.contains("Invalid choice."));
		assertTrue(output.contains("Purchase successful"));
	}
	
	@Test
	public void UnclearCart() {
		String input = "1\nUser55\n3\n";
		ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		Menu.setInputStream(testInput);

		input = "3\n1\nToys\n1\n1\n2\ny\n5\n4\n2\nn\n4\n8";
		testInput = new ByteArrayInputStream(input.getBytes());
		Customer.setInputStream(testInput);

		Menu.loginPage();
		
		String output = outContent.toString();
		assertTrue(output.contains("Item added to cart successfully!"));
	}
	
	@Test
	public void NotEnoughStock() {
		String input = "1\nUser56\n3\n";
		ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		Menu.setInputStream(testInput);

		input = "3\n1\nToys\n1\n1\n11\ny\n5\n8";
		testInput = new ByteArrayInputStream(input.getBytes());
		Customer.setInputStream(testInput);

		Menu.loginPage();
		
		String output = outContent.toString();
		assertTrue(output.contains("Stock is not enough."));
		assertTrue(output.contains("Addition cancelled."));
	}
}
