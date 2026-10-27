import java.io.Serializable;

public class SavingsAccount extends Account implements Serializable {
    private static final long serialVersionUID = 1L;
    private final long minBalance;

    public SavingsAccount(String owner, long balance, long minBalance) {
        super(owner, balance);
        this.minBalance = minBalance;
    }

    @Override public double  interestRate()        { return 4.0; }
    @Override public boolean canWithdraw(long amt) { return amt > 0 && getBalance() - amt >= minBalance; }
}
