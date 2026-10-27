import java.io.Serializable;

/**
 * Practical 11 — Part B: Serializable Account
 * Base class for MiniBank accounts. Implements Serializable so objects
 * can be saved/loaded with ObjectOutputStream/ObjectInputStream.
 */
public abstract class Account implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String  accountNumber;
    private final String  ownerName;
    private       long    balance;
    private       boolean active;

    private static long counter = 0;

    public Account(String ownerName, long openingBalance) {
        this.accountNumber = String.format("AC%04d", ++counter);
        this.ownerName     = ownerName;
        this.balance       = openingBalance;
        this.active        = true;
    }

    public abstract double interestRate();

    public void deposit(long amount) {
        if (amount > 0) balance += amount;
    }

    public boolean withdraw(long amount) {
        if (amount > 0 && balance >= amount) { balance -= amount; return true; }
        return false;
    }

    public String getAccountNumber() { return accountNumber; }
    public String getOwnerName()     { return ownerName; }
    public long   getBalance()       { return balance; }
    public boolean isActive()        { return active; }

    @Override
    public String toString() {
        return accountNumber + " | " + ownerName + " | balance=" + balance
                + " | rate=" + interestRate() + "%";
    }
}
