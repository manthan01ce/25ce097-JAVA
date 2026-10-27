/** Custom unchecked exception thrown when a division-by-zero is attempted. */
public class DivideByZeroException extends RuntimeException {
    public DivideByZeroException() {
        super("Division by zero is not allowed.");
    }
}
