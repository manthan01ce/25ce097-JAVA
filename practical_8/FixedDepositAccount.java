public class FixedDepositAccount extends Account {
    public FixedDepositAccount(String holderName, long balance) {
        super(holderName, balance);
    }

    @Override
    public void withdraw(long amount) throws InsufficientFundsException {
        throw new InsufficientFundsException(amount);
    }

    @Override
    public double interestRate() { return 7.0; }
}
