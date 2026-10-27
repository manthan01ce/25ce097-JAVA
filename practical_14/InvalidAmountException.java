public class InvalidAmountException extends BankException {
    public InvalidAmountException(long amount) {
        super("Invalid amount: " + amount + ". Must be > 0.");
    }
}
