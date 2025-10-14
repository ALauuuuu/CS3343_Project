package shopping;

import java.util.List;
import java.util.Map;

public class ShoppingCart {
    private Map<Item, Integer> cartItems;

    public boolean addItem(Item item, int quantity) {
        return false;
    }

    public boolean removeItem(Item item) {
        return false;
    }

    public boolean clearCart() {
        return false;
    }

    public List<Item> viewCart() {
        return null;
    }

    public double calculateTotal() {
        return 0.0;
    }
}
