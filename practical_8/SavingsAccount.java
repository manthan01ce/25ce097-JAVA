public class SavingsAccount extends Account {
    private final long minBalance;

    public SavingsAccount(String holderName, long balance, long minBalance) {
        super(holderName, balance);
        this.minBalance = minBalance;
    }

    @Override
    public void withdraw(long amount) throws InsufficientFundsException, InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException(amount);
        }
        if (balance - amount < minBalance) {
            long shortfall = (minBalance + amount) - balance;
            throw new InsufficientFundsException(shortfall);
        }
        this.balance -= amount;
    }

    @Override
    public double interestRate() { return 4.0; }
}
