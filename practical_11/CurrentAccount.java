import java.io.Serializable;

public class CurrentAccount extends Account implements Serializable {
    private static final long serialVersionUID = 1L;
    private final long overdraftLimit;

    public CurrentAccount(String ownerName, long openingBalance, long overdraftLimit) {
        super(ownerName, openingBalance);
        this.overdraftLimit = overdraftLimit;
    }

    @Override public double interestRate() { return 0.0; }

    @Override
    public boolean withdraw(long amount) {
        if (amount > 0 && getBalance() + overdraftLimit >= amount) {
            super.withdraw(amount); return true;
        }
        return false;
    }
}
