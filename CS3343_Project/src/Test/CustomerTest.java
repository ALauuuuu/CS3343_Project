package Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import Main.Main;
import Menu.Menu;
import User.Customer;

public class CustomerTest {
	
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
//		        assertEquals(true,);

	}
	
	@Test
	public void searchByNameUnsuccessfully() {

	}
	@Test
	public void searchByCodeSuccessfully() {

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
