/**
 * Practical 10 — Part B: Account
 * Supports synchronized deposit/withdraw and an explicit lock object
 * so the MiniBank can demonstrate deadlock + consistent-lock-order fix.
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

    public synchronized void deposit(long amount) {
        if (amount > 0) balance += amount;
    }

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
