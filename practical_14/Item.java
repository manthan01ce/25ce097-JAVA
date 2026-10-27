/**
 * Practical 14 — Part A: tiny layered app — model class.
 */
public class Item {
    private final String name;
    private final double price;

    public Item(String name, double price) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Name is blank");
        if (price < 0)                      throw new IllegalArgumentException("Price is negative");
        this.name  = name;
        this.price = price;
    }

    public String getName()  { return name; }
    public double getPrice() { return price; }

    @Override public String toString() { return name + " ($" + price + ")"; }
}
