package Objects;

import java.util.Date;

public class Review {
    private int reviewId;
    private int userId;
    private int rating;
    private String comment;
    private Date date;

    public Review(int reviewId, int userId, int rating, String comment, Date date) {
        this.reviewId = reviewId;
        this.userId = userId;
        this.rating = rating;
        this.comment = comment;
        this.date = date;
    }

    public String getReviewDetails() {
        return "Rating: " + rating + " by User " + userId + " on " + date + "\n" + comment;
    }

    // Getters
    public int getRating() {
        return rating;
    }

    public int getReviewId() {
        return reviewId;
    }
}
