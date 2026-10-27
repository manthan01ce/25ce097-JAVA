public class CurrentAccount extends Account {
    private final long overdraftLimit;

    public CurrentAccount(String holderName, long balance, long overdraftLimit) {
        super(holderName, balance);
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public void withdraw(long amount) throws InsufficientFundsException, InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException(amount);
        }
        if (balance + overdraftLimit < amount) {
            long shortfall = amount - (balance + overdraftLimit);
            throw new InsufficientFundsException(shortfall);
        }
        this.balance -= amount;
    }

    @Override
    public double interestRate() { return 0.0; }
}
