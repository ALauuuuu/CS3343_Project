package Objects;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ShoppingCart {
    private Map<Item, Integer> cartItems = new HashMap<>();

    public void addItem(Item item, int quantity) {
        cartItems.put(item, cartItems.getOrDefault(item, 0) + quantity);
        return;
    }

    public boolean removeItem(Item item) {
        if (cartItems.containsKey(item)) {
            cartItems.remove(item);
            return true;
        }
        return false;
    }

    public boolean clearCart() {
        cartItems.clear();
        return true;
    }

    public List<Item> viewCart() {
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
