package interfaces;

import models.Item;
import java.util.List;

public interface SearchCriteria {
    List<Item> searchByName(String name);
    Item searchByCode(int code);
    List<Item> searchByCategory(String category);
}
