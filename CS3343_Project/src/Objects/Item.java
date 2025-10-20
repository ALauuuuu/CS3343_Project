package Objects;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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
    }
 
    public String getDetails() {
        return "Item code:" + itemCode + "\tItem name: " + name + "\tPrice: $" + price +
               "\nCategory: " + category + "\tAverage rating: " + averageRating + "/ 5.0\tQuantity: " + quantity;
    }

    public boolean updateQuantity(int updatedQuantity) {
        this.quantity = updatedQuantity;
        return true;
    }

    public double calculateAverageRating() {
        if (reviews == null || reviews.isEmpty()) {
            return 0.0;
        }
        double sum = 0;
        for (Review review : reviews) {
            sum += review.getRating();
        }
        this.averageRating = sum / reviews.size();
        return this.averageRating;
    }

    public static List<Item> sortByPrice(List<Item> list, String order) {
        List<Item> output = new ArrayList<Item>(list);
        if(order.equals("asc")){
            output.sort(Comparator.comparingDouble(Item::getPrice));
        }else{
            output.sort(Comparator.comparingDouble(Item::getPrice).reversed());
        }
        return output;
    }

    public static List<Item> sortByRating(List<Item> list, String order) {
        List<Item> output = new ArrayList<Item>(list);
        if(order.equals("asc")){
            output.sort(Comparator.comparingDouble(Item::getAverageRating));
        }else{
            output.sort(Comparator.comparingDouble(Item::getAverageRating).reversed());
        }
        return output;
    }

    // Getters and Setters
    public int getItemCode() {
        return itemCode;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public List<Review> getReviews() {
        return reviews;
    }
    public void addReview(Review review){
        reviews.add(review);
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
}
