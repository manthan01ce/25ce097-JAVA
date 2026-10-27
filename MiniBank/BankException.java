/** Checked base exception for all MiniBank failures. */
public class BankException extends Exception {
    public BankException(String msg)              { super(msg); }
    public BankException(String msg, Throwable t) { super(msg, t); }
}
