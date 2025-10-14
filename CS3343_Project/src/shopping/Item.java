package shopping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Item {
    private int itemCode;
    private String name;
    private double price;
    private String category;
    private int quantity;
    private double averageRating;
    private List<Review> reviews;

    public Item(int itemCode, String name, double price, String category, int quantity) {
        this.itemCode = itemCode;
        this.name = name;
        this.price = price;
        this.category = category;
        this.quantity = quantity;
        this.reviews = new ArrayList<>();
        this.averageRating = 0.0;
    }

    public String getDetails() {
        StringBuilder details = new StringBuilder();
        details.append("Item code:").append(itemCode).append("\t\tItem name: ").append(name).append("\t\tPrice: $").append(price).append("\n");
        details.append("Category: ").append(category).append("\t\tAverage rating: ").append(String.format("%.1f", averageRating)).append("/ 5.0\t\tQuantity: ").append(quantity).append("\n");
        details.append("Most recent 3 ratings\n");
        
        if (reviews.isEmpty()) {
            details.append("No reviews yet.\n");
        } else {
            reviews.stream()
                .sorted((r1, r2) -> r2.getDate().compareTo(r1.getDate()))
                .limit(3)
                .forEach(review -> details.append(review.getReviewDetails()).append("\n"));
        }

        return details.toString();
    }

    public boolean updateQuantity(int updatedQuantity) {
        if (updatedQuantity >= 0) {
            this.quantity = updatedQuantity;
            return true;
        }
        return false;
    }

    public double calculateAverageRating() {
        if (reviews.isEmpty()) {
            return 0.0;
        }
        double sum = reviews.stream().mapToDouble(Review::getRating).sum();
        this.averageRating = sum / reviews.size();
        return this.averageRating;
    }

    public void addReview(Review review) {
        this.reviews.add(review);
        calculateAverageRating();
    }

    // Getters
    public int getItemCode() { return itemCode; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public String getCategory() { return category; }
    public int getQuantity() { return quantity; }
    public double getAverageRating() { return averageRating; }
    public List<Review> getReviews() { return reviews; }
}
