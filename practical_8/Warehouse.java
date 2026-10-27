import java.util.HashMap;
import java.util.Map;

/**
 * Practical 8 — Part A, Program 2
 * Stock issue: Warehouse where issue(item, qty) throws custom checked
 * OutOfStockException carrying shortfall, and InvalidQuantityException for qty <= 0.
 * Catches and reports each failure without halting execution.
 */
public class Warehouse {
    private final Map<String, Integer> inventory = new HashMap<>();

    public void addStock(String item, int qty) {
        inventory.put(item, inventory.getOrDefault(item, 0) + qty);
    }

    public void issue(String item, int qty) throws OutOfStockException, InvalidQuantityException {
        if (qty <= 0) {
            throw new InvalidQuantityException(qty);
        }
        int available = inventory.getOrDefault(item, 0);
        if (available < qty) {
            int shortfall = qty - available;
            throw new OutOfStockException(item, shortfall);
        }
        inventory.put(item, available - qty);
        System.out.printf("[SUCCESS] Issued %d units of %s. Remaining: %d%n", qty, item, inventory.get(item));
    }

    public static void main(String[] args) {
        Warehouse warehouse = new Warehouse();
        warehouse.addStock("Laptop", 10);
        warehouse.addStock("Mouse", 5);

        System.out.println("=== Warehouse Stock Issue Demo ===");
        String[][] requests = {
            {"Laptop", "3"},
            {"Mouse", "-2"},
            {"Mouse", "8"},
            {"Keyboard", "1"},
            {"Laptop", "7"}
        };

        for (String[] req : requests) {
            String item = req[0];
            int qty = Integer.parseInt(req[1]);
            System.out.printf("Request: %d x %s -> ", qty, item);
            try {
                warehouse.issue(item, qty);
            } catch (InvalidQuantityException e) {
                System.out.println("[REJECTED] " + e.getMessage());
            } catch (OutOfStockException e) {
                System.out.println("[SHORTFALL] " + e.getMessage() + " (Short by " + e.getShortfall() + ")");
            }
        }
    }
}
