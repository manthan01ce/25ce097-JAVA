import java.io.Serializable;

public class FixedDepositAccount extends Account implements Serializable {
    private static final long serialVersionUID = 1L;

    public FixedDepositAccount(String owner, long balance) { super(owner, balance); }

    @Override public double  interestRate()        { return 7.0; }
    @Override public boolean canWithdraw(long amt) { return false; }
}
