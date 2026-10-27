import java.io.Serializable;
import java.util.Objects;

public abstract class Account implements Transactable, InterestBearing, Comparable<Account>, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private final String accountNumber;

    @MaxLength(100)
    private final String ownerName;

    @Positive
    private long balance;

    private boolean active;

    private static long counter = 0;

    public static void resetCounter() { counter = 0; } // for tests

    public Account(String ownerName, long openingBalance) {
        this.accountNumber = String.format("AC%04d", ++counter);
        this.ownerName     = ownerName;
        this.balance       = openingBalance;
        this.active        = true;
    }

    public Account(String ownerName) { this(ownerName, 0); }

    @Override
    public abstract double  interestRate();

    public abstract boolean canWithdraw(long amount);

    @Override
    public double yearlyInterest() {
        return getBalance() * interestRate() / 100.0;
    }

    @Override
    public synchronized void deposit(long amount) {
        if (amount > 0) balance += amount;
    }

    @Override
    public synchronized boolean withdraw(long amount) {
        if (canWithdraw(amount)) {
            balance -= amount;
            return true;
        }
        return false;
    }

    public String  getAccountNumber() { return accountNumber; }
    public String  getOwnerName()     { return ownerName; }
    public synchronized long getBalance() { return balance; }
    public boolean isActive()         { return active; }
    public void    setActive(boolean v){ active = v; }

    @Override
    public int compareTo(Account o) {
        return accountNumber.compareTo(o.accountNumber);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account a)) return false;
        return accountNumber.equals(a.accountNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber);
    }

    @Override
    public String toString() {
        return accountNumber + " | " + ownerName + " | balance=" + balance
                + " | rate=" + interestRate() + "%" + (active ? "" : " [CLOSED]");
    }
}
