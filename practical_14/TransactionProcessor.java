import java.util.concurrent.*;

/**
 * Practical 14 — TransactionProcessor
 * Manages a fixed thread pool (4 threads) for batch deposit/withdraw tasks.
 * Backed by a ConcurrentHashMap store (via Repository<Account>).
 */
public class TransactionProcessor {

    private final ExecutorService    pool;
    private final Repository<Account> repo;

    public TransactionProcessor(Repository<Account> repo) {
        this.pool = Executors.newFixedThreadPool(4);
        this.repo = repo;
    }

    public void submit(Runnable task) { pool.execute(task); }

    /** Submit a deposit task by account number. */
    public void submitDeposit(String accountNumber, long amount) {
        submit(() -> {
            Account acc = repo.findById(accountNumber);
            if (acc != null) {
                acc.deposit(amount);
                System.out.println("[pool:" + Thread.currentThread().getName()
                        + "] deposited " + amount + " -> " + accountNumber);
            }
        });
    }

    public void stop() throws InterruptedException {
        pool.shutdown();
        if (!pool.awaitTermination(30, TimeUnit.SECONDS)) pool.shutdownNow();
    }
}
