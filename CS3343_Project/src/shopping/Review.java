package shopping;

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
        return getRatingStars() + " By Unknown User | Date: " + date + "\n" + comment;
    }

    private String getRatingStars() {
        StringBuilder stars = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            if (i < rating) {
                stars.append("*");
            } else {
                stars.append(" ");
            }
        }
        return stars.toString();
    }
    
    public int getRating() {
        return rating;
    }

    public Date getDate() {
        return date;
    }
}
