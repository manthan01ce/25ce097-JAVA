public class Account {

    private final String accountNumber;
    private String ownerName;
    private long balance;
    private boolean active;

    private static long accountCounter = 0;

    private static String generateAccountNumber() {
        accountCounter++;
        return String.format("AC%04d", accountCounter);
    }

    public Account(String ownerName, long openingBalance) {
        this.accountNumber = generateAccountNumber();
        this.ownerName = ownerName;
        this.balance = openingBalance;
        this.active = true;
    }

    public Account(String ownerName) {
        this(ownerName, 0);
    }

    public void deposit(long amount) {
        if (amount > 0) balance += amount;
    }

    public boolean withdraw(long amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            return true;
        }
        return false;
    }

    public String getAccountNumber() { return accountNumber; }
    public String getOwnerName() { return ownerName; }
    public long getBalance() { return balance; }
    public boolean isActive() { return active; }

    public static void main(String[] args) {
        Account[] accounts = {
            new Account("Riya", 5000),
            new Account("Aman", 3000),
            new Account("Priya")
        };

        accounts[0].deposit(2000);
        System.out.println(accounts[0].getOwnerName() + " balance after deposit 2000: " + accounts[0].getBalance());

        boolean result = accounts[0].withdraw(3000);
        System.out.println("Withdraw 3000: " + result + " | Balance: " + accounts[0].getBalance());

        result = accounts[0].withdraw(10000);
        System.out.println("Withdraw 10000: " + result + " | Balance: " + accounts[0].getBalance());

        System.out.println();
        for (Account acc : accounts) {
            System.out.println(acc.getAccountNumber() + " | " + acc.getOwnerName() + " | " + acc.getBalance());
        }
    }
}
