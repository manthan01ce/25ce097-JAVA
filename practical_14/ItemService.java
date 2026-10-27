import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Practical 14 — Part A: tiny layered app — service class.
 * Uses an ArrayList as its backing store.
 */
public class ItemService {

    private final List<Item> items = new ArrayList<>();

    /** Adds item; throws IllegalArgumentException if item is null. */
    public void add(Item item) {
        if (item == null) throw new IllegalArgumentException("Item cannot be null");
        items.add(item);
    }

    public int count() { return items.size(); }

    public List<Item> getAll() { return Collections.unmodifiableList(items); }

    /** Removes item by name; returns true if found and removed. */
    public boolean remove(String name) {
        return items.removeIf(i -> i.getName().equalsIgnoreCase(name));
    }
}
