package interfaces;

import models.Item;
import java.util.List;

public interface SortingCriteria {
    List<Item> sortByPrice(List<Item> list, String order);
    List<Item> sortByRating(List<Item> list, String order);
}
