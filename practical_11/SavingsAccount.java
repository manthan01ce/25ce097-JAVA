import java.io.Serializable;

public class SavingsAccount extends Account implements Serializable {
    private static final long serialVersionUID = 1L;
    private final long minBalance;

    public SavingsAccount(String ownerName, long openingBalance, long minBalance) {
        super(ownerName, openingBalance);
        this.minBalance = minBalance;
    }

    @Override public double interestRate() { return 4.0; }

    @Override
    public boolean withdraw(long amount) {
        if (amount > 0 && getBalance() - amount >= minBalance) {
            super.withdraw(amount); return true;
        }
        return false;
    }
}
