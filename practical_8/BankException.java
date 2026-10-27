/** Checked base exception for MiniBank practical 8. */
public class BankException extends Exception {
    public BankException(String msg) {
        super(msg);
    }

    public BankException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
