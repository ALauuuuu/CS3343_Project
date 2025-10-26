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
	
	@Test
	public void searchByNameSuccessfully() {
		        String input = "1\nAmy\n3\n";
		        ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
		        Menu.setInputStream(testInput);

				input = "3\n1\nToys\n4\n8\n";
				testInput = new ByteArrayInputStream(input.getBytes());
		        Customer.setInputStream(testInput);

		        Main.initializeInventory();
		        Menu.loginPage();
		        
		        Menu.setInputStream(null);
		        Customer.setInputStream(null);
	//	        assertEquals(true,);

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
				assertEquals(true, output.contains("Item code:5") || output.contains("Pen"));
				
				Menu.setInputStream(null);
				Customer.setInputStream(null);

	}
	
	@Test
	public void searchByCodeUnsuccessfully() {

	}
	
	@Test
	public void searchByCatSuccessfully() {

	}
	
	@Test
	public void searchByCatUnsuccessfully() {

	}

}
