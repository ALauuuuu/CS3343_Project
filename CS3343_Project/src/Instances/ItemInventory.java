package Instances;

import java.util.ArrayList;

import java.util.List;
import java.util.stream.Collectors;

import Objects.Item;

public class ItemInventory {
    private List<Item> items;
    private int itemCount;

    private static ItemInventory instance = new ItemInventory();
    private ItemInventory() {
        items = new ArrayList<Item>();
        itemCount = 0;
    }

    public static ItemInventory getInstance() { return instance; }
    public static List<Item> getItems() {
        return instance.items;
    }

    public static int getItemCount() {
        return instance.itemCount;
    }

    public static void addItem(Item item) {
        instance.items.add(item);
        instance.itemCount++;
    }

    public static void removeItem(int itemCode) {
        if (instance.items.removeIf(item -> item.getItemCode() == itemCode)) {
            instance.itemCount--;
        }
    }

    public static Item findItemByCode(int itemCode) {
        return instance.items.stream().filter(item -> item.getItemCode() == itemCode).findFirst().orElse(null);
    }

    public static void showItems(int start, int end) {
        for (int i = start; i < end && i < instance.items.size(); i++) {
            Item item = instance.items.get(i);
            System.out.print("Item code:" + item.getItemCode() + " " + item.getName() + " $" + item.getPrice() + " | ");
            if ((i + 1) % 3 == 0) {
                System.out.println();
            }
        }
    }

    public static List<String> getAllCategory(){
        List<String> output = new ArrayList<String>();
        int i = 0;
        while (i < instance.items.size()) {
            output.add(instance.items.get(i).getCategory());
            i++;
        }
        output = output.stream().distinct().collect(Collectors.toList()); //remove duplicates
        return output;
    }

    public static List<Item> searchByName(String name) {
        List<Item> output = new ArrayList<Item>();
        int i = 0;
        while (i < instance.items.size()) {
            if(instance.items.get(i).getName().toLowerCase().contains(name.toLowerCase())){
                output.add(instance.items.get(i));
            }
            i++;
        }
        return output;
    }

    public static Item searchByCode(int code) {
        return findItemByCode(code);
    }

    public static List<Item> searchByCategory(String category) {
        List<Item> output = new ArrayList<Item>();
        int i = 0;
        while (i < instance.items.size()) {
            if(instance.items.get(i).getCategory().toLowerCase().contains(category.toLowerCase())){
                output.add(instance.items.get(i));
            }
            i++;
        }
        return output;
    }
}
