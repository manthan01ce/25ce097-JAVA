import java.io.Serializable;
import java.util.Objects;

/**
 * Practical 13 — Account base class.
 * @Id on accountNumber so Repository<Account> can find its key by reflection.
 */
public abstract class Account implements Comparable<Account>, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private final String accountNumber;
    private final String ownerName;
    private long    balance;
    private boolean active;

    private static long counter = 0;

    public Account(String ownerName, long openingBalance) {
        this.accountNumber = String.format("AC%04d", ++counter);
        this.ownerName     = ownerName;
        this.balance       = openingBalance;
        this.active        = true;
    }

    public Account(String ownerName) { this(ownerName, 0); }

    public abstract double  interestRate();
    public abstract boolean canWithdraw(long amount);

    public void deposit(long amount) { if (amount > 0) balance += amount; }

    public boolean withdraw(long amount) {
        if (canWithdraw(amount)) { balance -= amount; return true; }
        return false;
    }

    public String  getAccountNumber() { return accountNumber; }
    public String  getOwnerName()     { return ownerName; }
    public long    getBalance()       { return balance; }
    public boolean isActive()         { return active; }

    @Override public int compareTo(Account o) { return accountNumber.compareTo(o.accountNumber); }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account a)) return false;
        return accountNumber.equals(a.accountNumber);
    }

    @Override public int hashCode() { return Objects.hash(accountNumber); }

    @Override public String toString() {
        return accountNumber + " | " + ownerName + " | balance=" + balance
                + " | rate=" + interestRate() + "%";
    }
}
