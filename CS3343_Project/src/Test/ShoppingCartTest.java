package Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import Objects.Item;
import Objects.ShoppingCart;

public class ShoppingCartTest {
    
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private ShoppingCart cart;
    private Item item1;
    private Item item2;
    private Item item3;

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outContent));
        cart = new ShoppingCart();
        item1 = new Item(1, "Laptop", 1200.0, "electronics", 10);
        item2 = new Item(2, "Mouse", 25.0, "electronics", 50);
        item3 = new Item(3, "Keyboard", 75.0, "electronics", 30);
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    public void AddItemToCartSuccessfully() {
        boolean result = cart.addItem(item1, 2);
        assertTrue(result);
        assertEquals(1, cart.getCartItems().size());
        assertEquals(2, cart.getCartItemQuantity(item1));
    }

    @Test
    public void AddItemToCartInsufficientStock() {
        boolean result = cart.addItem(item1, 15); 
        assertFalse(result);
        String output = outContent.toString();
        assertTrue(output.contains("Stock is not enough"));
        assertEquals(0, cart.getCartItems().size());
    }

    @Test
    public void AddItemToCartWithZeroQuantity() {
        boolean result = cart.addItem(item1, 0);
        assertTrue(result);
        assertEquals(0, cart.getCartItemQuantity(item1));
    }

    @Test
    public void AddItemToCartMultipleTimes() {
        cart.addItem(item1, 2);
        boolean result = cart.addItem(item1, 3);
        
        assertTrue(result);
        assertEquals(1, cart.getCartItems().size());
        assertEquals(5, cart.getCartItemQuantity(item1));
    }

    @Test
    public void AddItemToCartExceedingStockAfterPreviousAdd() {
        cart.addItem(item1, 5);
        boolean result = cart.addItem(item1, 10); 
        
        assertFalse(result);
        assertEquals(5, cart.getCartItemQuantity(item1));
        String output = outContent.toString();
        assertTrue(output.contains("Stock is not enough"));
    }

    @Test
    public void AddMultipleDifferentItemsToCart() {
        cart.addItem(item1, 1);
        cart.addItem(item2, 2);
        cart.addItem(item3, 3);
        
        assertEquals(3, cart.getCartItems().size());
        assertEquals(1, cart.getCartItemQuantity(item1));
        assertEquals(2, cart.getCartItemQuantity(item2));
        assertEquals(3, cart.getCartItemQuantity(item3));
    }

    @Test
    public void AddItemToCartAtMaximumStock() {
        boolean result = cart.addItem(item1, 10); 
        assertTrue(result);
        assertEquals(10, cart.getCartItemQuantity(item1));
    }

    @Test
    public void RemoveItemSuccessfully() {
        cart.addItem(item1, 2);
        boolean result = cart.removeItem(item1);
        
        assertTrue(result);
        assertEquals(0, cart.getCartItems().size());
    }

    @Test
    public void RemoveNonExistentItem() {
        cart.addItem(item1, 2);
        boolean result = cart.removeItem(item2);
        
        assertFalse(result);
        assertEquals(1, cart.getCartItems().size());
    }

    @Test
    public void ModifyItemQuantitySuccessfully() {
        cart.addItem(item1, 2);
        boolean result = cart.modifyItem(item1, 5);
        
        assertTrue(result);
        assertEquals(5, cart.getCartItemQuantity(item1));
    }

    @Test
    public void ModifyItemQuantityExceedingStock() {
        cart.addItem(item1, 2);
        boolean result = cart.modifyItem(item1, 15); 
        
        assertFalse(result);
        String output = outContent.toString();
        assertTrue(output.contains("Stock is not enough"));
    }

    @Test
    public void ModifyItemToZeroQuantity() {
        cart.addItem(item1, 2);
        boolean result = cart.modifyItem(item1, 0);
        
        assertTrue(result);
        assertEquals(0, cart.getCartItemQuantity(item1));
    }

    @Test
    public void testClearCart() {
        cart.addItem(item1, 2);
        cart.addItem(item2, 3);
        cart.addItem(item3, 1);
        
        cart.clearCart();
        
        assertEquals(0, cart.getCartItems().size());
        assertTrue(cart.isEmpty());
    }

    @Test
    public void CalculateTotalEmpty() {
        double total = cart.calculateTotal();
        assertEquals(0.0, total, 0.01);
    }

    @Test
    public void CalculateTotalSingleItem() {
        cart.addItem(item1, 2);
        double total = cart.calculateTotal();
        
        assertEquals(2400.0, total, 0.01);
    }

    @Test
    public void CalculateTotalMultipleItems() {
        cart.addItem(item1, 2); 
        cart.addItem(item2, 3);
        cart.addItem(item3, 1);
        
        double total = cart.calculateTotal();
        assertEquals(2550.0, total, 0.01);
    }

    @Test
    public void IsEmptyWhenEmpty() {
        assertTrue(cart.isEmpty());
    }

    @Test
    public void IsEmptyWhenNotEmpty() {
        cart.addItem(item1, 1);
        assertFalse(cart.isEmpty());
    }

    @Test
    public void IsEmptyAfterClear() {
        cart.addItem(item1, 1);
        cart.clearCart();
        assertTrue(cart.isEmpty());
    }

    @Test
    public void DisplayCartEmpty() {
        cart.DisplayCart();
        assertTrue(true);
    }

    @Test
    public void DisplayCartWithItems() {
        cart.addItem(item1, 2);
        cart.addItem(item2, 3);
        
        cart.DisplayCart();
        
        String output = outContent.toString();
        assertTrue(output.contains("Laptop"));
        assertTrue(output.contains("Mouse"));
        assertTrue(output.contains("Quantity: 2"));
        assertTrue(output.contains("Quantity: 3"));
    }

    @Test
    public void GetCartItems() {
        cart.addItem(item1, 1);
        cart.addItem(item2, 2);
        
        assertEquals(2, cart.getCartItems().size());
        assertTrue(cart.getCartItems().contains(item1));
        assertTrue(cart.getCartItems().contains(item2));
    }

    @Test
    public void CartItemsSortedByItemCode() {
        cart.addItem(item3, 1);
        cart.addItem(item1, 1); 
        cart.addItem(item2, 1); 
        
        var items = cart.getCartItems();
        
        assertEquals(item1, items.get(0));
        assertEquals(item2, items.get(1));
        assertEquals(item3, items.get(2));
    }

    @Test
    public void AddItemWithDecimalPrice() {
        Item decimalItem = new Item(4, "Book", 19.99, "education", 100);
        cart.addItem(decimalItem, 3);
        
        double total = cart.calculateTotal();
        assertEquals(59.97, total, 0.01);
    }

    @Test
    public void ModifyItemNotInCart() {
        cart.addItem(item1, 2);
        boolean result = cart.modifyItem(item2, 5);
        
        assertTrue(result);
        assertEquals(2, cart.getCartItems().size());
        assertEquals(5, cart.getCartItemQuantity(item2));
    }

    @Test
    public void LargeQuantityCalculation() {
        Item cheapItem = new Item(5, "Sticker", 0.50, "stationery", 1000);
        cart.addItem(cheapItem, 100);
        
        double total = cart.calculateTotal();
        assertEquals(50.0, total, 0.01);
    }

    @Test
    public void MultipleOperationsSequence() {
        cart.addItem(item1, 2);
        cart.addItem(item2, 5);
        assertEquals(2, cart.getCartItems().size());
        
        cart.modifyItem(item1, 3);
        assertEquals(3, cart.getCartItemQuantity(item1));
        
        cart.removeItem(item2);
        assertEquals(1, cart.getCartItems().size());
 
        cart.addItem(item3, 1);
        assertEquals(2, cart.getCartItems().size());
        
        double total = cart.calculateTotal();
        assertEquals(3675.0, total, 0.01);

        cart.clearCart();
        assertTrue(cart.isEmpty());
    }
    
    @Test
    public void DisplayCartOutput() {
        cart.addItem(item1, 2);
        cart.addItem(item2, 5);
        
        outContent.reset();
        
        cart.DisplayCart();
        
        String output = outContent.toString();
        assertTrue(output.contains("Item code: 1"));
        assertTrue(output.contains("Laptop"));
        assertTrue(output.contains("Quantity: 2"));
    }

    @Test
    public void AddItemInsufficientStockOutput() {
        outContent.reset();
        boolean result = cart.addItem(item1, 11);
        
        assertFalse(result);
        assertTrue(outContent.toString().contains("Stock is not enough"));
    }
    
    @Test
    public void SortCartLogic() {

        cart.addItem(item3, 1);
        cart.addItem(item1, 1);
        cart.addItem(item2, 1); 
        
        List<Item> items = cart.getCartItems();
        
        assertEquals(1, items.get(0).getItemCode());
        assertEquals(2, items.get(1).getItemCode());
        assertEquals(3, items.get(2).getItemCode());
    }
}
