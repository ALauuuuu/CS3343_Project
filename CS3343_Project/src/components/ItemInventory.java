package components;

import interfaces.SearchCriteria;
import interfaces.SortingCriteria;
import models.Item;

import java.util.List;

public class ItemInventory implements SearchCriteria, SortingCriteria {
    private List<Item> items;

    public ItemInventory(List<Item> items) {
        this.items = items;
    }

    public List<Item> getItems() {
        return items;
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
        for (int i = start; i < end && i < items.size(); i++) {
            Item item = items.get(i);
            System.out.print("Item code:" + item.getItemCode() + " " + item.getName() + " $" + item.getPrice() + " | ");
            if ((i + 1) % 3 == 0) {
                System.out.println();
            }
        }
    }

    @Override
    public List<Item> searchByName(String name) {
        return null;
    }

    @Override
    public Item searchByCode(int code) {
        return findItemByCode(code);
    }

    @Override
    public List<Item> searchByCategory(String category) {
        return null;
    }

    @Override
    public List<Item> sortByPrice(List<Item> list, String order) {
        return null;
    }

    @Override
    public List<Item> sortByRating(List<Item> list, String order) {
        return null;
    }
}
