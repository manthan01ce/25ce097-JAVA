/**
 * Practical 9 — Part B: MiniBank with multithreading
 *
 * Demonstrates:
 *  1. Race condition on depositUnsafe — balance comes out wrong.
 *  2. Synchronized deposit  — balance is exactly right every run.
 *  3. Thread names printed to observe the lifecycle.
 *
 * Test setup: 10 threads × 1000 deposits of 1 each → expected balance = 10 000.
 */
public class MiniBank {

    private static long runWorkers(Account acc, boolean useSafe) throws InterruptedException {
        final int THREADS = 10;
        final int TIMES   = 1000;
        final long AMOUNT = 1;

        Thread[] workers = new Thread[THREADS];
        for (int i = 0; i < THREADS; i++) {
            workers[i] = new Thread(
                new AccountWorker(acc, TIMES, AMOUNT, useSafe),
                "Worker-" + i
            );
        }
        for (Thread t : workers) t.start();
        for (Thread t : workers) t.join();
        return acc.getBalance();
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Race condition (no synchronization) ===");
        Account unsafeAcc = new Account("RaceDemo", 0);
        long wrong = runWorkers(unsafeAcc, false);
        System.out.println("Expected: 10000  |  Got: " + wrong
                + (wrong == 10000 ? "  (lucky run)" : "  ← race condition visible"));

        System.out.println("\n=== Thread-safe (synchronized deposit) ===");
        Account safeAcc = new Account("SafeDemo", 0);
        long correct = runWorkers(safeAcc, true);
        System.out.println("Expected: 10000  |  Got: " + correct
                + (correct == 10000 ? "  ✓ correct" : "  ✗ bug!"));
    }
}
