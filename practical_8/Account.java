public abstract class Account {
    private static int nextId = 1;
    protected final String accountNumber;
    protected String holderName;
    protected long balance;

    public Account(String holderName, long balance) {
        this.accountNumber = String.format("AC%04d", nextId++);
        this.holderName = holderName;
        this.balance = balance;
    }

    public String getAccountNumber() { return accountNumber; }
    public String getHolderName()    { return holderName; }
    public long   getBalance()       { return balance; }

    public void deposit(long amount) throws InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException(amount);
        }
        this.balance += amount;
    }

    public void withdraw(long amount) throws InsufficientFundsException, InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException(amount);
        }
        if (amount > balance) {
            long shortfall = amount - balance;
            throw new InsufficientFundsException(shortfall);
        }
        this.balance -= amount;
    }

    public void transfer(Account to, long amount) throws BankException {
        if (to == null) {
            throw new AccountNotFoundException("null destination account");
        }
        System.out.printf("[TRANSFER START] Initiating transfer of %d from %s to %s%n", amount, this.accountNumber, to.accountNumber);
        try {
            this.withdraw(amount);
            to.deposit(amount);
            System.out.println("[TRANSFER SUCCESS] Transfer completed.");
        } catch (BankException e) {
            System.out.println("[TRANSFER FAILED] " + e.getMessage());
            throw e;
        } finally {
            System.out.println("[TRANSFER LOG] Transfer operation finalized.");
        }
    }

    public abstract double interestRate();

    @Override
    public String toString() {
        return String.format("%s | %s | balance=%d | rate=%.1f%%",
                accountNumber, holderName, balance, interestRate());
    }
}
