package Objects;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ShoppingCart {
    private Map<Item, Integer> cartItems = new HashMap<>();

    private void sortCartByItemCode(){
        if (cartItems.isEmpty()) 
            return;
        Map<Item, Integer> sorted = cartItems.entrySet()
        .stream()
        .sorted(Comparator.comparingInt(e -> e.getKey().getItemCode()))
        .collect(Collectors.toMap(
            Map.Entry::getKey,
            Map.Entry::getValue,
            (v1, v2) -> v1,
            LinkedHashMap::new
        ));
        cartItems = sorted;
    }
    public void addItem(Item item, int quantity) {
        cartItems.put(item, cartItems.getOrDefault(item, 0) + quantity);
        sortCartByItemCode();
        return;
    }

    public boolean removeItem(Item item) {
        if (cartItems.containsKey(item)) {
            cartItems.remove(item);
            sortCartByItemCode();
            return true;
        }
        return false;
    }

    public void modifyItem(Item item, int quantity){
        this.cartItems.remove(item);
        this.cartItems.put(item, cartItems.getOrDefault(item, 0) + quantity);
        this.sortCartByItemCode();
    }

    public void clearCart() {
        cartItems.clear();
    }
    public void DisplayCart(){
        int i = 1;
        for (Item item : cartItems.keySet()) {
            Integer qty = cartItems.get(item);
            System.out.println(i+"| Item code: " + item.getItemCode()+ " | "+ item.getName() + " $"+ item.getPrice() + " | Quantity: " + qty);
            i++;
        }
    }

    public List<Item> getCartItems() {
        return cartItems.keySet().stream().collect(Collectors.toList());
    }
    
    public double calculateTotal() {
        return cartItems.entrySet().stream()
                .mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue())
                .sum();
    }

    public boolean isEmpty(){
        if (cartItems.isEmpty()) {
            return true;
        }
        return false;
    }
}
