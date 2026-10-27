import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Practical 10 — Part B
 * Manages a fixed thread pool (4 threads) for processing deposit/withdraw tasks.
 */
public class TransactionProcessor {

    private final ExecutorService pool;

    public TransactionProcessor() {
        this.pool = Executors.newFixedThreadPool(4);
    }

    /** Submit a task for async execution. */
    public void submit(Runnable task) {
        pool.execute(task);
    }

    /** Shut down and wait for all submitted tasks to complete. */
    public void stop() throws InterruptedException {
        pool.shutdown();
        if (!pool.awaitTermination(30, TimeUnit.SECONDS)) {
            System.err.println("Pool did not terminate in time.");
            pool.shutdownNow();
        }
    }
}
