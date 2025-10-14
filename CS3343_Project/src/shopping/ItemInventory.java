package shopping;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import searching.SearchCriteria;
import searching.SortingCriteria;

public class ItemInventory implements SearchCriteria, SortingCriteria {
    private List<Item> items;

    public ItemInventory() {
        this.items = new ArrayList<>();
        // Add default items
        items.add(new Item(1, "Toys", 100, "others", 10));
        items.add(new Item(2, "Snack", 20, "food", 20));
        items.add(new Item(3, "Shampoo", 100, "daily", 30));
        items.add(new Item(4, "Books", 150, "study", 40));
        items.add(new Item(5, "Pen", 20, "study", 50));
        items.add(new Item(6, "Shoes", 500, "daily", 60));
        items.add(new Item(7, "T-shirt", 150, "daily", 70));
        items.add(new Item(8, "Jacket", 300, "daily", 80));
        items.add(new Item(9, "Pants", 250, "daily", 90));
        items.add(new Item(10, "Socks", 50, "daily", 100));
        items.add(new Item(11, "Hat", 120, "daily", 110));
        items.add(new Item(12, "Gloves", 80, "daily", 120));
    }

    public boolean addItem(Item item) {
        return items.add(item);
    }

    public Boolean removeItem(int itemCode) {
        return items.removeIf(item -> item.getItemCode() == itemCode);
    }

    public Item findItemByCode(int itemCode) {
        return items.stream().filter(item -> item.getItemCode() == itemCode).findFirst().orElse(null);
    }

    public void showItems(int start, int end) {
        System.out.println("Online shopping > Home page");
        for (int i = start; i < end && i < items.size(); i++) {
            Item item = items.get(i);
            System.out.print("Item code:" + item.getItemCode() + " " + item.getName() + " $" + item.getPrice() + " | ");
            if ((i + 1) % 3 == 0) {
                System.out.println();
            }
        }
        System.out.println();
    }

    @Override
    public List<Item> searchByName(String name) {
        return items.stream().filter(item -> item.getName().equalsIgnoreCase(name)).collect(Collectors.toList());
    }

    @Override
    public Item searchByCode(int code) {
        return findItemByCode(code);
    }

    @Override
    public List<Item> searchByCategory(String category) {
        return items.stream().filter(item -> item.getCategory().equalsIgnoreCase(category)).collect(Collectors.toList());
    }

    @Override
    public List<Item> sortByPrice(List<Item> list, String order) {
        if (order.equalsIgnoreCase("Ascending")) {
            return list.stream().sorted(Comparator.comparingDouble(Item::getPrice)).collect(Collectors.toList());
        } else {
            return list.stream().sorted(Comparator.comparingDouble(Item::getPrice).reversed()).collect(Collectors.toList());
        }
    }

    @Override
    public List<Item> sortByRating(List<Item> list, String order) {
        if (order.equalsIgnoreCase("Ascending")) {
            return list.stream().sorted(Comparator.comparingDouble(Item::getAverageRating)).collect(Collectors.toList());
        } else {
            return list.stream().sorted(Comparator.comparingDouble(Item::getAverageRating).reversed()).collect(Collectors.toList());
        }
    }
}
