package components;

import interfaces.SearchCriteria;
import interfaces.SortingCriteria;
import models.Item;

import java.util.ArrayList;
import java.util.List;

//this class should be singleton
public class ItemInventory implements SearchCriteria, SortingCriteria {
    private List<Item> items;

    private static ItemInventory instance = new ItemInventory();
    private ItemInventory() {
        items = new ArrayList<Item>();
    }
    public static ItemInventory getInstance() { return instance; }
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
        List<Item> output = new ArrayList<Item>();
        int i = 0;
        while (i < items.size()) {
            if(items.get(i).getName().toLowerCase().contains(name.toLowerCase())){
                output.add(items.get(i));
            }
        }
        return output;
    }

    @Override
    public Item searchByCode(int code) {
        return findItemByCode(code);
    }

    @Override
    public List<Item> searchByCategory(String category) {
        List<Item> output = new ArrayList<Item>();
        int i = 0;
        while (i < items.size()) {
            if(items.get(i).getName().toLowerCase().contains(category.toLowerCase())){
                output.add(items.get(i));
            }
        }
        return output;
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
