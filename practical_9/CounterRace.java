/**
 * Practical 9 — Part A, Program 1
 * Counter race: multiple threads increment a shared counter.
 * First run without synchronization to show the wrong total,
 * then with synchronization to confirm it is always exact.
 */
public class CounterRace {

    static int unsafeCount = 0;
    static int safeCount   = 0;
    static final Object lock = new Object();

    static void unsafeIncrement() {
        unsafeCount++;
    }

    static void safeIncrement() {
        synchronized (lock) {
            safeCount++;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final int THREADS = 10;
        final int TIMES   = 1000;

        // --- Unsafe run ---
        Thread[] unsafeThreads = new Thread[THREADS];
        for (int i = 0; i < THREADS; i++) {
            unsafeThreads[i] = new Thread(() -> {
                for (int j = 0; j < TIMES; j++) unsafeIncrement();
            });
        }
        for (Thread t : unsafeThreads) t.start();
        for (Thread t : unsafeThreads) t.join();
        System.out.println("=== Without synchronization ===");
        System.out.println("Expected: " + (THREADS * TIMES));
        System.out.println("Got     : " + unsafeCount + "  (may be less due to race)");

        // --- Safe run ---
        Thread[] safeThreads = new Thread[THREADS];
        for (int i = 0; i < THREADS; i++) {
            safeThreads[i] = new Thread(() -> {
                for (int j = 0; j < TIMES; j++) safeIncrement();
            });
        }
        for (Thread t : safeThreads) t.start();
        for (Thread t : safeThreads) t.join();
        System.out.println("\n=== With synchronization ===");
        System.out.println("Expected: " + (THREADS * TIMES));
        System.out.println("Got     : " + safeCount + "  (always exact)");
    }
}
