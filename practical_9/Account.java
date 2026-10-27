/**
 * Practical 9 — Part B: MiniBank Account
 * Thread-safe deposit and withdraw using synchronized methods.
 * First version (unsynchronized) intentionally left to demonstrate the race.
 */
public class Account {

    private final String accountNumber;
    private final String ownerName;
    private long balance;

    private static long counter = 0;

    public Account(String ownerName, long openingBalance) {
        this.accountNumber = String.format("AC%04d", ++counter);
        this.ownerName     = ownerName;
        this.balance       = openingBalance;
    }

    /** Unsynchronized — demonstrates race condition. */
    public void depositUnsafe(long amount) {
        if (amount > 0) balance += amount;
    }

    /** Thread-safe deposit. */
    public synchronized void deposit(long amount) {
        if (amount > 0) balance += amount;
    }

    /** Thread-safe withdraw. */
    public synchronized boolean withdraw(long amount) {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
            return true;
        }
        return false;
    }

    public synchronized long getBalance() { return balance; }
    public String getAccountNumber()      { return accountNumber; }
    public String getOwnerName()          { return ownerName; }

    @Override
    public String toString() {
        return accountNumber + " | " + ownerName + " | balance=" + balance;
    }
}
