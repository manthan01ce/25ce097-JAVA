import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Practical 10 — Part A, Program 1
 * Thread-pool runner: fixed pool of 3 threads runs 10 tasks.
 * Observes how a few threads handle many tasks (thread reuse).
 */
public class ThreadPoolRunner {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(3);

        System.out.println("=== Thread Pool Runner (3 threads, 10 tasks) ===");
        for (int i = 1; i <= 10; i++) {
            final int taskId = i;
            pool.execute(() -> {
                System.out.println("Task " + taskId + " started  by " + Thread.currentThread().getName());
                try { Thread.sleep(50); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                System.out.println("Task " + taskId + " finished by " + Thread.currentThread().getName());
            });
        }

        pool.shutdown();
        pool.awaitTermination(10, TimeUnit.SECONDS);
        System.out.println("All tasks done.");
    }
}
