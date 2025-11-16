package Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Objects.Item;
import Objects.Review;

import java.util.Date;

public class ItemTest {
    Date date = new Date();
	
    @Test
    public void ItemDetails() {
    	Item item = new Item(1, "Smartphone", 699.99, "electronics", 50);
    	assertEquals(1, item.getItemCode());
    	assertEquals("Smartphone", item.getName());
    	assertEquals(699.99, item.getPrice());
    	assertEquals("electronics", item.getCategory());
    	item.updateQuantity(60);
    	assertEquals(60, item.getQuantity());
    	item.setCanReview(true);
    	assertTrue(item.getCanReview());
    	assertEquals("Item code:1\tItem name: Smartphone\tPrice: $699.99\nCategory: electronics\tAverage rating: 0.0/ 5.0\tQuantity: 60", item.getDetails());
    }
    
    @Test
	public void EmptyReviews() {
		Item item = new Item(1, "Smartphone", 699.99, "electronics", 50);
		assertEquals("No reviews.", item.showReviews());
	}
    
    @Test
    public void ReviewsLessThan3() {
		Item item = new Item(1, "Smartphone", 699.99, "electronics", 50);
		item.setReviews(new java.util.ArrayList<Review>());
		Review r1 = new Review(001, "Charlie", 5, "Good", date);
		Review r2 = new Review(002, "Diana", 3, "Nice", date);
		item.addReview(r1);
		item.addReview(r2);

		assertEquals("Rating: 5 by User Charlie on " + date + "\n" + "Good", item.getReviews().get(0).getReviewDetails());
		assertEquals(5, item.getReviews().get(0).getRating());
		assertEquals(001, item.getReviews().get(0).getReviewId());
		assertEquals("Rating: 3 by User Diana on " + date + "\n" + "Nice", item.getReviews().get(1).getReviewDetails());
		assertEquals(3, item.getReviews().get(1).getRating());
		assertEquals(002, item.getReviews().get(1).getReviewId());
		assertEquals("Rating: 5 by User Charlie on " + date + "\n" + "Good" + "\n" + "Rating: 3 by User Diana on " + date
						+ "\n" + "Nice" +"\n",item.showReviews());
		assertEquals(4.0, item.calculateAverageRating());
		assertEquals(4.0, item.getAverageRating());
		assertEquals(2, item.getReviews().size());
    }
	
    @Test
    public void ReviewsMoreThan3() {
    	Item item = new Item(1, "Smartphone", 699.99, "electronics", 50);
    	item.setReviews(new java.util.ArrayList<Review>());
    	Review r1 = new Review(001, "Charlie", 5, "Good", date);
    	Review r2 = new Review(002, "Diana", 3, "Nice", date);
    	Review r3 = new Review(003, "Eve", 3, "Great", date);
    	Review r4 = new Review(004, "Frank", 1, "Bad", date);
    	item.addReview(r1);
    	item.addReview(r2);
    	item.addReview(r3);
    	item.addReview(r4);
    	
    	assertEquals("Rating: 5 by User Charlie on " + date + "\n" + "Good" + "\n" + "Rating: 3 by User Diana on " + date + "\n" + "Nice" + "\n" +
    			"Rating: 3 by User Eve on " + date + "\n" + "Great" + "\n" + "Rating: 1 by User Frank on " + date + "\n" + "Bad" + "\n", item.showReviews());
    }
    
	@Test
	public void averageRatingCalculation2() {
		Item item = new Item(1, "Smartphone", 699.99, "electronics", 50);
		item.setReviews(null);
		assertEquals(0.0, item.calculateAverageRating());
	}
	
	@Test
	public void averageRatingCalculation3() {
		Item item = new Item(1, "Smartphone", 699.99, "electronics", 50);
		assertEquals(0.0, item.calculateAverageRating());
	}
	
	@Test
	public void itemSorting() {
		Item item1 = new Item(1, "Smartphone", 699.99, "electronics", 50);
		Item item2 = new Item(2, "Laptop", 999.99, "electronics", 30);
		Item item3 = new Item(3, "Headphones", 199.99, "electronics", 100);

		java.util.List<Item> itemList = new java.util.ArrayList<Item>();
		itemList.add(item1);
		itemList.add(item2);
		itemList.add(item3);
		
		Review r1 = new Review(001, "Charlie", 5, "Good", date);
    	Review r2 = new Review(001, "Diana", 3, "Nice", date);
    	Review r3 = new Review(001, "Eve", 2, "Great", date);
    	item1.addReview(r1);
    	item2.addReview(r2);
    	item3.addReview(r3);

		Item.sortByPrice(itemList, "asc");
		assertEquals("Headphones", itemList.get(0).getName());
		assertEquals("Smartphone", itemList.get(1).getName());
		assertEquals("Laptop", itemList.get(2).getName());
		
		Item.sortByPrice(itemList, "desc");
		assertEquals("Laptop", itemList.get(0).getName());
		assertEquals("Smartphone", itemList.get(1).getName());
		assertEquals("Headphones", itemList.get(2).getName());
		
		Item.sortByRating(itemList, "asc");
		assertEquals("Headphones", itemList.get(0).getName());
		assertEquals("Laptop", itemList.get(1).getName());
		assertEquals("Smartphone", itemList.get(2).getName());
		
		Item.sortByRating(itemList, "desc");
		assertEquals("Smartphone", itemList.get(0).getName());
		assertEquals("Laptop", itemList.get(1).getName());
		assertEquals("Headphones", itemList.get(2).getName());
	}
}
