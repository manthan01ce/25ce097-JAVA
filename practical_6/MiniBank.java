/**
 * Practical 6 — Part B: MiniBank with interfaces & packages.
 *
 * New additions this lab:
 *   • Account implements Transactable
 *   • Account implements InterestBearing (with default yearlyInterest())
 *   • WithdrawRule functional interface (anonymous class + lambda)
 *   • Premium marker interface
 *   • Static import of Validator.isValidMobile (shown inline here)
 *
 * Note: For the full package-organised version (model/util/service packages)
 * see the MiniBank/ folder at the project root.
 */
public class MiniBank {

    // ---------- interfaces ----------

    interface Transactable {
        void deposit(long amount);
        boolean withdraw(long amount);
    }

    interface InterestBearing {
        double interestRate();
        default double yearlyInterest() {
            // requires getBalance() — cast at call site
            return 0; // overridden below
        }
    }

    @FunctionalInterface
    interface WithdrawRule {
        boolean allow(Account account, long amount);
    }

    /** Marker interface — no methods. */
    interface Premium { }

    // ---------- abstract base ----------

    static abstract class Account implements Transactable, InterestBearing {
        private final String accountNumber;
        private final String ownerName;
        private long balance;
        private static long counter = 0;

        Account(String ownerName, long openingBalance) {
            this.accountNumber = String.format("AC%04d", ++counter);
            this.ownerName = ownerName;
            this.balance = openingBalance;
        }

        @Override public void deposit(long amount)  { if (amount > 0) balance += amount; }
        @Override public boolean withdraw(long amount) {
            if (canWithdraw(amount)) { balance -= amount; return true; } return false;
        }
        public boolean canWithdraw(long amount) { return amount > 0 && amount <= balance; }

        @Override public double yearlyInterest() { return balance * interestRate() / 100.0; }

        public String getAccountNumber() { return accountNumber; }
        public String getOwnerName()     { return ownerName; }
        public long   getBalance()       { return balance; }

        @Override public String toString() {
            return accountNumber + "[" + ownerName + "]";
        }
    }

    // ---------- subclasses ----------

    static class SavingsAccount extends Account {
        private final long minBalance;
        SavingsAccount(String n, long bal, long min) { super(n, bal); this.minBalance = min; }
        @Override public double interestRate() { return 4.0; }
        @Override public boolean canWithdraw(long amt) {
            return amt > 0 && getBalance() - amt >= minBalance;
        }
    }

    static class CurrentAccount extends Account {
        private final long overdraftLimit;
        CurrentAccount(String n, long bal, long od) { super(n, bal); this.overdraftLimit = od; }
        @Override public double interestRate() { return 0.0; }
        @Override public boolean canWithdraw(long amt) {
            return amt > 0 && getBalance() + overdraftLimit >= amt;
        }
    }

    static class FixedDepositAccount extends Account implements Premium {
        FixedDepositAccount(String n, long bal) { super(n, bal); }
        @Override public double interestRate()    { return 7.0; }
        @Override public boolean canWithdraw(long amt) { return false; }
    }

    // ---------- main ----------

    public static void main(String[] args) {
        Account[] accounts = {
            new SavingsAccount("Riya",  10000, 2000),
            new CurrentAccount("Aman",  5000,  3000),
            new FixedDepositAccount("Priya", 50000)
        };

        System.out.println("=== Interest Rates & Yearly Interest ===");
        for (Account acc : accounts) {
            System.out.printf("%s — rate: %.1f%%  yearly: %.2f%n",
                    acc, acc.interestRate(), acc.yearlyInterest());
        }

        // WithdrawRule — anonymous class
        System.out.println("\n=== WithdrawRule (anonymous class) ===");
        WithdrawRule conservativeRule = new WithdrawRule() {
            @Override
            public boolean allow(Account account, long amount) {
                return amount <= account.getBalance() / 2;
            }
        };
        System.out.println("Allow 2000 from 10000? " + conservativeRule.allow(accounts[0], 2000));
        System.out.println("Allow 6000 from 10000? " + conservativeRule.allow(accounts[0], 6000));

        // WithdrawRule — lambda
        System.out.println("\n=== WithdrawRule (lambda) ===");
        WithdrawRule standardRule = (account, amount) -> account.canWithdraw(amount);
        System.out.println("Standard allow 3000? " + standardRule.allow(accounts[0], 3000));
        System.out.println("Standard allow 9500? " + standardRule.allow(accounts[0], 9500));

        // isValidMobile inline (static import equivalent)
        System.out.println("\n=== Mobile validation ===");
        System.out.println("isValidMobile(9876543210): " + isValidMobile("9876543210"));
        System.out.println("isValidMobile(12345):       " + isValidMobile("12345"));

        // Premium marker interface
        System.out.println("\n=== Premium accounts ===");
        for (Account acc : accounts) {
            if (acc instanceof Premium) {
                System.out.println(acc.getOwnerName() + " is a Premium account holder.");
            }
        }
    }

    static boolean isValidMobile(String m) {
        return m != null && m.matches("[6-9][0-9]{9}");
    }
}
