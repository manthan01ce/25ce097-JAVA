import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Practical 14 — Part B: BankService
 *
 * Wires together:
 *   Repository<Account>  — storage (ConcurrentHashMap under the hood)
 *   Validator            — input validation
 *   BankException hierarchy — explicit, informative failures
 *   TransactionLog       — append-only NIO log
 *
 * Methods: openAccount, deposit, withdraw, transfer, statement, listAccounts.
 */
public class BankService {

    private final Repository<Account> repo;
    private final Path                logFile;

    public BankService(Repository<Account> repo, Path logFile) {
        this.repo    = repo;
        this.logFile = logFile;
    }

    // ------------------------------------------------------------------ //
    //  openAccount                                                         //
    // ------------------------------------------------------------------ //

    @Audit
    public Account openAccount(String ownerName, String type, long openingBalance)
            throws BankException {
        AuditLogger.log(this, "openAccount");
        if (ownerName == null || ownerName.isBlank())
            throw new BankException("Owner name cannot be blank");
        if (!Validator.isValidAmount(openingBalance))
            throw new InvalidAmountException(openingBalance);

        Account acc = switch (type.toUpperCase()) {
            case "SAVINGS" -> new SavingsAccount(ownerName, openingBalance, 500);
            case "CURRENT" -> new CurrentAccount(ownerName, openingBalance, 2000);
            case "FD"      -> new FixedDepositAccount(ownerName, openingBalance);
            default        -> throw new BankException("Unknown account type: " + type);
        };
        repo.save(acc);
        try { TransactionLog.logDeposit(logFile, acc.getAccountNumber(), openingBalance); }
        catch (IOException ignored) {}
        System.out.println("[service] Opened " + acc);
        return acc;
    }

    // ------------------------------------------------------------------ //
    //  deposit                                                             //
    // ------------------------------------------------------------------ //

    @Audit
    public long deposit(String accountNumber, long amount)
            throws BankException {
        AuditLogger.log(this, "deposit");
        if (!Validator.isValidAmount(amount)) throw new InvalidAmountException(amount);
        Account acc = requireAccount(accountNumber);
        acc.deposit(amount);
        try { TransactionLog.logDeposit(logFile, accountNumber, amount); }
        catch (IOException ignored) {}
        return acc.getBalance();
    }

    // ------------------------------------------------------------------ //
    //  withdraw                                                            //
    // ------------------------------------------------------------------ //

    @Audit
    public long withdraw(String accountNumber, long amount)
            throws BankException {
        AuditLogger.log(this, "withdraw");
        if (!Validator.isValidAmount(amount)) throw new InvalidAmountException(amount);
        Account acc = requireAccount(accountNumber);
        if (!acc.canWithdraw(amount))
            throw new InsufficientFundsException(amount - acc.getBalance());
        acc.withdraw(amount);
        try { TransactionLog.logWithdraw(logFile, accountNumber, amount); }
        catch (IOException ignored) {}
        return acc.getBalance();
    }

    // ------------------------------------------------------------------ //
    //  transfer                                                            //
    // ------------------------------------------------------------------ //

    @Audit
    public void transfer(String fromNum, String toNum, long amount)
            throws BankException {
        AuditLogger.log(this, "transfer");
        if (!Validator.isValidAmount(amount)) throw new InvalidAmountException(amount);
        Account from = requireAccount(fromNum);
        Account to   = requireAccount(toNum);

        // consistent lock order to avoid deadlock
        Account first  = from.getAccountNumber().compareTo(to.getAccountNumber()) < 0 ? from : to;
        Account second = (first == from) ? to : from;

        synchronized (first) {
            synchronized (second) {
                if (!from.canWithdraw(amount))
                    throw new InsufficientFundsException(amount - from.getBalance());
                from.withdraw(amount);
                to.deposit(amount);
            }
        }
        try { TransactionLog.logTransfer(logFile, fromNum, toNum, amount); }
        catch (IOException ignored) {}
    }

    // ------------------------------------------------------------------ //
    //  statement                                                           //
    // ------------------------------------------------------------------ //

    public String statement(String accountNumber) throws BankException {
        Account acc = requireAccount(accountNumber);
        return "=== Statement ===\n"
             + "Account : " + acc.getAccountNumber() + "\n"
             + "Owner   : " + acc.getOwnerName()     + "\n"
             + "Balance : " + acc.getBalance()        + "\n"
             + "Rate    : " + acc.interestRate()      + "%\n"
             + "Status  : " + (acc.isActive() ? "Active" : "Closed") + "\n";
    }

    // ------------------------------------------------------------------ //
    //  listAccounts                                                        //
    // ------------------------------------------------------------------ //

    public List<Account> listAccounts() {
        List<Account> list = new ArrayList<>(repo.findAll());
        list.sort(Comparator.comparing(Account::getAccountNumber));
        return list;
    }

    // ------------------------------------------------------------------ //
    //  helper                                                              //
    // ------------------------------------------------------------------ //

    public Repository<Account> getRepo() { return repo; }

    private Account requireAccount(String number) throws AccountNotFoundException {
        Account acc = repo.findById(number);
        if (acc == null) throw new AccountNotFoundException(number);
        return acc;
    }
}
