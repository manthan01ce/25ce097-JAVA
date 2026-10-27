public class InvalidQuantityException extends Exception {
    public InvalidQuantityException(int qty) {
        super("Invalid quantity requested: " + qty + ". Quantity must be > 0.");
    }
}
