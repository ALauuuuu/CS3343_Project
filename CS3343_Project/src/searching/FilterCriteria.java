package searching;

import shopping.Item;
import java.util.List;

public interface FilterCriteria {
    List<Item> filterByPriceRange(double min, double max);
    List<Item> filterByAvailability();
    List<Item> filterByRating(double minRating);
}
