package Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import Menu.Menu;
import Objects.Item;
import User.Admin;
import Instances.ItemInventory;
import Main.Main;

public class AdminTest {
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;
	private Admin admin;

	@BeforeEach
	public void setUp() {
		System.setOut(new PrintStream(outContent));
		admin = new Admin("Admin111");
		
		ItemInventory.getItems().clear();
		Main.initializeInventory();
	}

	@AfterEach
	public void tearDown() {
		System.setOut(originalOut);
		Menu.setInputStream(null);
		Admin.setInputStream(null);
	}
	
	@Test
	public void addItemTrue() {
		boolean result = admin.addItem(new Item(111, "Poker", 500, "others", 100));
        assertTrue(result);
	}
	
	@Test
	public void addItemFalse() {
		boolean result = admin.addItem(new Item(1, "Poker", 500, "others", 100));
        assertFalse(result);
	}
	
	@Test
	public void removeItemTrue() {
		boolean result = admin.removeItem(1);
        assertTrue(result);
	}
	
	@Test
	public void removeItemFalse() {
		boolean result = admin.removeItem(1000);
        assertFalse(result);
	}
	
	@Test
    public void editItemTrue() {
		boolean result = admin.edititem(1, new Item(1, "Poker", 500, "others", 100));
        assertTrue(result);
    }
	
	@Test
    public void editItemFalse() {
		boolean result = admin.edititem(1000, new Item(1, "Poker", 500, "others", 100));
        assertFalse(result);
    }
	
	@Test
	void displayinventory() {
		admin.displayinventory();
		String output = outContent.toString();
		assertTrue(output.contains("Current Inventory:"));
	}
	
	@Test
	public void login() {
        String input = "5\n";
        ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
        Admin.setInputStream(testInput);
        
        admin.login();
        
		String output = outContent.toString();
		assertTrue(output.contains("Admin111 logged in."));
	}
	
	@Test
	public void logout() {
        String input = "5\n";
        ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
        Admin.setInputStream(testInput);
        
        admin.login();
        
		String output = outContent.toString();
		assertTrue(output.contains("Admin111 logged out."));
	}
	
	@Test
	public void handleAddItemSuccessfully() {
        String input = "1\nPoker\n500\nothers\n100\nY\n2\n5";
        ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
        Admin.setInputStream(testInput);
        
        admin.login();
        
		String output = outContent.toString();
		assertTrue(output.contains("Item added."));
        
	}
	
	@Test
	public void handleAddItemNegativePrice() {
        String input = "1\nPoker\n-500\n500\nothers\n100\ny\n2\n5";
        ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
        Admin.setInputStream(testInput);
        
        admin.login();
        
		String output = outContent.toString();
		assertTrue(output.contains("Price cannot be negative. Please enter a valid number."));
        
	}
	
	@Test
	public void handleAddItemInvalidPrice() {
        String input = "1\nPoker\nxxx\n500\nothers\n100\ny\n2\n5";
        ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
        Admin.setInputStream(testInput);
        
        admin.login();
        
		String output = outContent.toString();
		assertTrue(output.contains("Invalid input! Please enter a NUMBER for price."));
        
	}

	@Test
	public void handleAddItemNegativeQuantity() {
        String input = "1\nPoker\n500\nothers\n-100\n100\ny\n2\n5";
        ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
        Admin.setInputStream(testInput);
        
        admin.login();
        
		String output = outContent.toString();
		assertTrue(output.contains("Quantity cannot be negative. Please enter a positive integer."));
        
	}
	
	@Test
	public void handleAddItemInvalidQuantity() {
        String input = "1\nPoker\n500\nothers\nxxx\n100\ny\n2\n5";
        ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
        Admin.setInputStream(testInput);
        
        admin.login();
        
		String output = outContent.toString();
		assertTrue(output.contains("Invalid input! Please enter an INTEGER for quantity."));
        
	}
	
	@Test
	public void handleAddItemCancel() {
        String input = "1\nPoker\n500\nothers\n100\nn\n2\n5";
        ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
        Admin.setInputStream(testInput);
        
        admin.login();
        
		String output = outContent.toString();
		assertTrue(output.contains("Addition is cancelled."));
	}
	
	@Test
	public void handleAddAnotherItem() {
        String input = "1\nPoker\n500\nothers\n100\ny\n1\nChess\n600\nothers\n10\ny\n2\n5";
        ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
        Admin.setInputStream(testInput);
        
        admin.login();
        
		String output = outContent.toString();
		
		int occurrences = output.split("Item added.", -1).length - 1;
		assertEquals(2, occurrences);
	}
	
	@Test
	public void handleRMItemSuccessfully() {
        String input = "2\n1\nY\n2\n5";
        ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
        Admin.setInputStream(testInput);
        
        admin.login();
        
		String output = outContent.toString();
		assertTrue(output.contains("Item removed."));
	}
	
	@Test
	public void handleRMItemCancel() {
        String input = "2\n1\nn\n2\n5";
        ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
        Admin.setInputStream(testInput);
        
        admin.login();
        
		String output = outContent.toString();
		assertTrue(output.contains("Removal cancelled."));
	}
	
	@Test
	public void handleRMNonExistItem() {
        String input = "2\n1000\ny\n2\n5";
        ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
        Admin.setInputStream(testInput);
        
        admin.login();
        
		String output = outContent.toString();
		assertTrue(output.contains("Removal cancelled."));
	}
	
	@Test
	public void handleRMAnotherItem() {
        String input = "2\n1\ny\n1\n2\ny\n2\n5";
        ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
        Admin.setInputStream(testInput);
        
        admin.login();
        
		String output = outContent.toString();
		int occurrences = output.split("Item removed.", -1).length - 1;
		assertEquals(2, occurrences);
	}
	
	@Test
	public void handleEditItemSuccessfully() {
        String input = "3\n1\nPoker\n700\nothers\n50\nY\n2\n5";
        ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
        Admin.setInputStream(testInput);
        
        admin.login();
        
		String output = outContent.toString();
		assertTrue(output.contains("Item edited."));
	}
	
	@Test
	public void handleEditItemNegativePrice() {
        String input = "3\n1\nPoker\n-700\n700\nothers\n50\ny\n2\n5";
        ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
        Admin.setInputStream(testInput);
        
        admin.login();
        
		String output = outContent.toString();
		assertTrue(output.contains("Price cannot be negative. Please enter a valid number."));
	}
	
	@Test
	public void handleEditItemInvalidPrice() {
        String input = "3\n1\nPoker\nxxx\n700\nothers\n50\ny\n2\n5";
        ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
        Admin.setInputStream(testInput);
        
        admin.login();
        
		String output = outContent.toString();
		assertTrue(output.contains("Invalid input! Please enter a NUMBER for price."));
	}
	
	@Test
	public void handleEditItemNegativeQuantity() {
        String input = "3\n1\nPoker\n700\nothers\n-50\n50\ny\n2\n5";
        ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
        Admin.setInputStream(testInput);
        
        admin.login();
        
		String output = outContent.toString();
		assertTrue(output.contains("Quantity cannot be negative. Please enter a positive integer."));
	}
	
	@Test
	public void handleEditItemInvalidPriceQuantity() {
        String input = "3\n1\nPoker\n700\nothers\nxxx\n50\ny\n2\n5";
        ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
        Admin.setInputStream(testInput);
        
        admin.login();
        
		String output = outContent.toString();
		assertTrue(output.contains("Invalid input! Please enter an INTEGER for quantity."));
	}
	
	@Test
	public void handleEditNonExistItem() {
        String input = "3\n1000\nPoker\n700\nothers\n50\ny\n2\n5";
        ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
        Admin.setInputStream(testInput);
        
        admin.login();
        
		String output = outContent.toString();
		assertTrue(output.contains("Edit cancelled."));
	}
	
	@Test
	public void handleEditItemCancel() {
        String input = "3\n1\nPoker\n700\nothers\n50\nn\n2\n5";
        ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
        Admin.setInputStream(testInput);
        
        admin.login();
        
		String output = outContent.toString();
		assertTrue(output.contains("Edit cancelled."));
	}
	
	@Test
	public void handleEditAnotherItem() {
        String input = "3\n1\nPoker\n700\nothers\n50\ny\n1\n2\ntoys\n50\nothers\n10\ny\n2\n5";
        ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
        Admin.setInputStream(testInput);
        
        admin.login();
        
		String output = outContent.toString();
		int occurrences = output.split("Item edited.", -1).length - 1;
		assertEquals(2, occurrences);
	}
	
	@Test
	public void handleViewEmptyNotification() {
        String input = "4\n2\n5";
        ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
        Admin.setInputStream(testInput);
        
        admin.login();
        
		String output = outContent.toString();
		assertTrue(output.contains("Displaying notifications..."));
	}
	
	@Test
	public void handleViewNotifications() {
        String input = "1\nPoker\n500\nothers\n100\nY\n2\n4\n2\n5";
        ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
        Admin.setInputStream(testInput);
        
        admin.login();
        
		String output = outContent.toString();
		assertTrue(output.contains("Displaying notifications..."));
	}
	
	
	@Test
	public void handleViewNotificationsRefresh() {
        String input = "4\n1\n2\n5";
        ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
        Admin.setInputStream(testInput);
        
        admin.login();
        
		String output = outContent.toString();
		int occurrences = output.split("Displaying notifications...", -1).length - 1;
		assertEquals(2, occurrences);
	}
	
	@Test
	public void homeMenuNotExistOption() {
        String input = "1000\n5";
        ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
        Admin.setInputStream(testInput);
        
        admin.login();
        
		String output = outContent.toString();
		assertTrue(output.contains("Invalid option. Please try again."));
	}
	
	@Test
	public void homeMenuInvalidOption() {
        String input = "xxx\n5";
        ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
        Admin.setInputStream(testInput);
        
        admin.login();
        
		String output = outContent.toString();
		assertTrue(output.contains("Wrong input, please enter an integer."));
	}
	
}
