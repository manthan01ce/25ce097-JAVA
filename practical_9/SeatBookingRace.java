import java.util.concurrent.atomic.AtomicInteger;

/**
 * Practical 9 — Part A, Program 2
 * Seat booking race: 10 threads each try to book 1 of 5 seats.
 * Without synchronization more than 5 can succeed (oversell).
 * With a synchronized book() exactly 5 succeed.
 */
public class SeatBookingRace {

    static int unsafeSeats = 5;
    static int unsafeBooked = 0;

    static int safeSeats = 5;
    static final Object safeLock = new Object();
    static int safeBooked = 0;

    static void tryBookUnsafe(String name) {
        if (unsafeSeats > 0) {
            // simulated delay so the race condition is visible
            try { Thread.sleep(1); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            unsafeSeats--;
            unsafeBooked++;
            System.out.println("[UNSAFE] " + name + " booked. Remaining: " + unsafeSeats);
        }
    }

    static void tryBookSafe(String name) {
        synchronized (safeLock) {
            if (safeSeats > 0) {
                safeSeats--;
                safeBooked++;
                System.out.println("[SAFE]   " + name + " booked. Remaining: " + safeSeats);
            } else {
                System.out.println("[SAFE]   " + name + " — no seats left.");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final int THREADS = 10;

        System.out.println("=== Without synchronization (expect oversell) ===");
        Thread[] unsafe = new Thread[THREADS];
        for (int i = 0; i < THREADS; i++) {
            final String name = "T" + i;
            unsafe[i] = new Thread(() -> tryBookUnsafe(name));
        }
        for (Thread t : unsafe) t.start();
        for (Thread t : unsafe) t.join();
        System.out.println("Total booked (unsafe): " + unsafeBooked + " (may exceed 5)");

        System.out.println("\n=== With synchronization (exactly 5) ===");
        Thread[] safe = new Thread[THREADS];
        for (int i = 0; i < THREADS; i++) {
            final String name = "T" + i;
            safe[i] = new Thread(() -> tryBookSafe(name));
        }
        for (Thread t : safe) t.start();
        for (Thread t : safe) t.join();
        System.out.println("Total booked (safe)  : " + safeBooked);
    }
}
