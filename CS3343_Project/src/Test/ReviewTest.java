package Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Objects.Review;

import java.util.Date;

public class ReviewTest {
    Date date = new Date();
    
    @Test
	public void ReviewDetails() {
		Review review = new Review(1, "Alice", 4, "Great product!", date);
		assertEquals(1, review.getReviewId());;
		assertEquals(4.0, review.getRating());
		assertEquals("Rating: 4 by User Alice on " + date + "\n" + "Great product!", review.getReviewDetails());
	}
}
