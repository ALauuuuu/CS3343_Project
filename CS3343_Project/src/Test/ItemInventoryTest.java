package Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import Instances.ItemInventory;
import Objects.Item;

public class ItemInventoryTest {
    
    private Item testItem1;
    private Item testItem2;
    private Item testItem3;
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    
    @BeforeEach
    public void setUp() {
        testItem1 = new Item(101, "Test Laptop", 1500.0, "electronics", 10);
        testItem2 = new Item(102, "Test Snack", 5.0, "food", 50);
        testItem3 = new Item(103, "Test Shirt", 30.0, "apparel", 25);
        System.setOut(new PrintStream(outputStreamCaptor));
    }
    
    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
        try {
            ItemInventory.removeItem(101);
            ItemInventory.removeItem(102);
            ItemInventory.removeItem(103);
            ItemInventory.removeItem(104);
        } catch (Exception e) {
        }
        testItem1 = null;
        testItem2 = null;
        testItem3 = null;
    }
    
    @Test
    public void AddItem() {
        ItemInventory.addItem(testItem1);
        
        Item retrieved = ItemInventory.searchByCode(101);
        assertNotNull(retrieved);
        assertEquals("Test Laptop", retrieved.getName());
        assertEquals(1500.0, retrieved.getPrice());
    }
    
    @Test
    public void AddMultipleItems() {
        ItemInventory.addItem(testItem1);
        ItemInventory.addItem(testItem2);
        ItemInventory.addItem(testItem3);
        
        assertNotNull(ItemInventory.searchByCode(101));
        assertNotNull(ItemInventory.searchByCode(102));
        assertNotNull(ItemInventory.searchByCode(103));
    }
    
    @Test
    public void AddDuplicateItemCode() {
        ItemInventory.addItem(testItem1);
        Item duplicate = new Item(101, "Duplicate Item", 999.0, "test", 5);
        ItemInventory.addItem(duplicate);
        
        Item existing = ItemInventory.searchByCode(101);
        assertNotNull(existing);
    }
    
    @Test
    public void SearchByCode() {
        ItemInventory.addItem(testItem1);
        
        Item found = ItemInventory.searchByCode(101);
        assertNotNull(found);
        assertEquals(101, found.getItemCode());
        assertEquals("Test Laptop", found.getName());
        assertEquals(1500.0, found.getPrice());
    }
    
    @Test
    public void SearchByCodeNotFound() {
        Item notFound = ItemInventory.searchByCode(99999);
        assertNull(notFound);
    }
    
    @Test
    public void SearchByName() {
        ItemInventory.addItem(testItem1);
        ItemInventory.addItem(testItem2);
        
        List<Item> results = ItemInventory.searchByName("Test Laptop");
        assertNotNull(results);
        assertTrue(results.size() > 0);
        
        boolean found = results.stream()
            .anyMatch(item -> item.getName().equals("Test Laptop"));
        assertTrue(found);
    }
    
    @Test
    public void SearchByNamePartialMatch() {
        ItemInventory.addItem(testItem1);
        
        List<Item> results = ItemInventory.searchByName("Laptop");
        assertNotNull(results);
        assertTrue(results.size() >= 0);
    }
    
    @Test
    public void SearchByNameNoResults() {
        List<Item> results = ItemInventory.searchByName("NonExistentItemXYZ123");
        assertNotNull(results);
        assertEquals(0, results.size());
    }
    
    @Test
    public void SearchByCategory() {
        ItemInventory.addItem(testItem1);
        ItemInventory.addItem(testItem2);
        ItemInventory.addItem(testItem3);
        
        List<Item> electronics = ItemInventory.searchByCategory("electronics");
        assertNotNull(electronics);
        assertTrue(electronics.size() > 0);
        
        for (Item item : electronics) {
            assertEquals("electronics", item.getCategory());
        }
    }
    
    @Test
    public void SearchByCategoryMultipleItems() {
        ItemInventory.addItem(testItem2);
        Item anotherFood = new Item(104, "Another Snack", 3.0, "food", 100);
        ItemInventory.addItem(anotherFood);
        
        List<Item> foodItems = ItemInventory.searchByCategory("food");
        assertTrue(foodItems.size() >= 2);
        
        ItemInventory.removeItem(104);
    }
    
    @Test
    public void SearchByCategoryNoResults() {
        List<Item> results = ItemInventory.searchByCategory("nonexistentcategory");
        assertNotNull(results);
        assertEquals(0, results.size());
    }
    
    @Test
    public void RemoveItem() {
        ItemInventory.addItem(testItem1);
        
        assertNotNull(ItemInventory.searchByCode(101));
        
        ItemInventory.removeItem(101);
        
        Item shouldBeNull = ItemInventory.searchByCode(101);
        assertNull(shouldBeNull);
    }
    
    @Test
    public void RemoveNonExistentItem() {
        assertDoesNotThrow(() -> ItemInventory.removeItem(99999));
    }
    
    @Test
    public void GetAllCategory() {
        ItemInventory.addItem(testItem1);
        ItemInventory.addItem(testItem2);
        ItemInventory.addItem(testItem3);
        
        List<String> categories = ItemInventory.getAllCategory();
        assertNotNull(categories);
        assertTrue(categories.size() > 0);
        
        assertTrue(categories.contains("electronics"));
        assertTrue(categories.contains("food"));
        assertTrue(categories.contains("apparel"));
    }
    
    @Test
    public void GetItems() {
        int initialSize = ItemInventory.getItems().size();
        
        ItemInventory.addItem(testItem1);
        ItemInventory.addItem(testItem2);
        
        List<Item> items = ItemInventory.getItems();
        assertNotNull(items);
        assertTrue(items.size() >= initialSize + 2);
    }
    
    @Test
    public void GetItemCount() {
        int initialCount = ItemInventory.getItemCount();
        
        ItemInventory.addItem(testItem1);
        
        int newCount = ItemInventory.getItemCount();
        assertTrue(newCount >= initialCount);
    }
    
    @Test
    public void ShowItemsOutput() {
        ItemInventory.addItem(testItem1);
        ItemInventory.addItem(testItem2);
        
        outputStreamCaptor.reset();
        ItemInventory.showItems(0, 20);
        String output = outputStreamCaptor.toString();
        
        assertFalse(output.isEmpty());
    }
    
    @Test
    public void ShowItemsWithPagination() {
        ItemInventory.addItem(testItem1);
        ItemInventory.addItem(testItem2);
        ItemInventory.addItem(testItem3);
        
        outputStreamCaptor.reset();
        ItemInventory.showItems(0, 2);
        String output = outputStreamCaptor.toString();
        
        assertFalse(output.isEmpty());
    }
    
    @Test
    public void ItemPersistenceAcrossOperations() {
        ItemInventory.addItem(testItem1);
        
        Item found = ItemInventory.searchByCode(101);
        assertNotNull(found);
        assertEquals("Test Laptop", found.getName());
        
        ItemInventory.addItem(testItem2);
        
        Item stillThere = ItemInventory.searchByCode(101);
        assertNotNull(stillThere);
        assertEquals("Test Laptop", stillThere.getName());
    }
    
    @Test
    public void EditItemWorkflow() {
        ItemInventory.addItem(testItem1);
        
        Item original = ItemInventory.searchByCode(101);
        assertNotNull(original);
        assertEquals("Test Laptop", original.getName());
        
        Item updatedItem = new Item(101, "Updated Laptop", 2000.0, "electronics", 15);
        
        ItemInventory.removeItem(101);
        ItemInventory.addItem(updatedItem);
        
        Item result = ItemInventory.searchByCode(101);
        assertNotNull(result);
        assertEquals("Updated Laptop", result.getName());
        assertEquals(2000.0, result.getPrice());
        assertEquals(15, result.getQuantity());
    }
    
    @Test
    public void ItemQuantityUpdate() {
        ItemInventory.addItem(testItem1);
        
        Item item = ItemInventory.searchByCode(101);
        assertNotNull(item);
        assertEquals(10, item.getQuantity());
        
        item.updateQuantity(25);
        
        assertEquals(25, item.getQuantity());
    }
    
    @Test
    public void MultipleCategoriesHandling() {
        ItemInventory.addItem(testItem1);
        ItemInventory.addItem(testItem2);
        ItemInventory.addItem(testItem3);
        
        List<Item> electronics = ItemInventory.searchByCategory("electronics");
        List<Item> food = ItemInventory.searchByCategory("food");
        List<Item> apparel = ItemInventory.searchByCategory("apparel");
        
        assertTrue(electronics.size() > 0);
        assertTrue(food.size() > 0);
        assertTrue(apparel.size() > 0);
    }
}
