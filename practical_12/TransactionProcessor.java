import java.util.Map;
import java.util.concurrent.*;

/**
 * Practical 12 — Part B (item 5)
 * TransactionProcessor backed by a ConcurrentHashMap so concurrent
 * deposit/withdraw tasks are thread-safe.
 */
public class TransactionProcessor {

    private final ExecutorService          pool;
    private final Map<String, Account>     accounts; // ConcurrentHashMap from AccountStore

    public TransactionProcessor(Map<String, Account> concurrentAccounts) {
        this.pool     = Executors.newFixedThreadPool(4);
        this.accounts = concurrentAccounts;
    }

    public void submit(Runnable task) { pool.execute(task); }

    public void stop() throws InterruptedException {
        pool.shutdown();
        if (!pool.awaitTermination(30, TimeUnit.SECONDS)) pool.shutdownNow();
    }

    /** Convenience: submit a deposit task by account number. */
    public void submitDeposit(String accountNumber, long amount) {
        submit(() -> {
            Account acc = accounts.get(accountNumber);
            if (acc != null) {
                acc.deposit(amount);
                System.out.println("[pool:" + Thread.currentThread().getName()
                        + "] deposited " + amount + " -> " + accountNumber);
            }
        });
    }
}
