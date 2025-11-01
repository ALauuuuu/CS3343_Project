package Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import Main.Main;
import Menu.Menu;
import User.Customer;

public class CustomerTest {

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;

	@BeforeEach
	public void setUp() {
		System.setOut(new PrintStream(outContent));
		Main.initializeInventory();
	}
	
	@Test
	public void searchByNameSuccessfully() {
		        String input = "1\nAmy\n3\n";
		        ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		        Menu.setInputStream(testInput);

				input = "3\n1\nToys\n4\n8\n";
				testInput = new ByteArrayInputStream(input.getBytes());
		        Customer.setInputStream(testInput);

		        Menu.loginPage();
		        
				String output = outContent.toString();
				assertEquals(true, output.contains("item(s) found"));
				assertEquals(true, output.contains("Toys"));
				
			    Menu.setInputStream(null);
		        Customer.setInputStream(null);
				

	}
	
	@Test
	public void searchByNameUnsuccessfully() {
				String input = "1\nMeki\n3\n";
				ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
				Menu.setInputStream(testInput);
		
				input = "3\n1\nNonExistentProduct\n2\n4\n8\n";
				testInput = new ByteArrayInputStream(input.getBytes());
				Customer.setInputStream(testInput);
				
				Menu.loginPage();
				
				String output = outContent.toString();
				assertEquals(true, output.contains("0 item(s) found"));
				
				Menu.setInputStream(null);
				Customer.setInputStream(null);
	}
	
	@Test
	public void searchByCodeSuccessfully() {
				String input = "1\nMeki\n3\n";
				ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
				Menu.setInputStream(testInput);

				input = "3\n2\n5\n2\n4\n8\n";
				testInput = new ByteArrayInputStream(input.getBytes());
				Customer.setInputStream(testInput);

				Menu.loginPage();
				
				String output = outContent.toString();
				assertEquals(true, output.contains("Pen"));
				
				Menu.setInputStream(null);
				Customer.setInputStream(null);

	}
	
	@Test
	public void searchByCodeUnsuccessfully() {
				String input = "1\nMeki\n3\n";
				ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
				Menu.setInputStream(testInput);

				// first search with invalid code 999, then retry with code 3.
				input = "3\n2\n999\n3\n2\n4\n8\n";
				testInput = new ByteArrayInputStream(input.getBytes());
				Customer.setInputStream(testInput);
				
				Menu.loginPage();
				String output = outContent.toString();
				assertEquals(true, output.contains("Shampoo"));
				
				Menu.setInputStream(null);
				Customer.setInputStream(null);

	}
	
	@Test
	public void searchByCatSuccessfully() {
				String input = "1\nMeki\n3\n";
				ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
				Menu.setInputStream(testInput);
				
				input = "3\n3\n1\n2\n4\n8\n";
				testInput = new ByteArrayInputStream(input.getBytes());
				Customer.setInputStream(testInput);
				
				Menu.loginPage();
				
				String output = outContent.toString();
				assertEquals(true, output.contains("item(s) found"));
				assertEquals(false, output.contains("0 item(s) found"));
				
				Menu.setInputStream(null);
				Customer.setInputStream(null);
	}
	
	@Test
	public void searchByCatUnsuccessfully() {
				String input = "1\nMeki\n3\n";
				ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
				Menu.setInputStream(testInput);
		
				input = "3\n3\n999\n1\n2\n4\n8\n";
				testInput = new ByteArrayInputStream(input.getBytes());
				Customer.setInputStream(testInput);
				
				Menu.loginPage();
				
				String output = outContent.toString();
				assertEquals(true, output.contains("Invalid option, please try again!") || output.contains("Wrong input, please enter an integer."));
				
				Menu.setInputStream(null);
				Customer.setInputStream(null);
	}

	@Test
	public void addItemToCartSuccessfully() {
				String input = "1\nMeki\n3\n";
				ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
				Menu.setInputStream(testInput);
		
				input = "3\n1\nToys\n1\n1\n2\ny\n4\n8\n";
				testInput = new ByteArrayInputStream(input.getBytes());
				Customer.setInputStream(testInput);
		
				Menu.loginPage();
				
				String output = outContent.toString();
				
				assertTrue(output.contains("Item added to cart successfully"));
				
				Menu.setInputStream(null);
				Customer.setInputStream(null);
	}

}
