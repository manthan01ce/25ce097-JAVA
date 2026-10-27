/**
 * Practical 10 — Part B: MiniBank — Thread Pool + Producer-Consumer + Deadlock Demo
 *
 * Demonstrates:
 *  1. TransactionProcessor (ExecutorService pool of 4) submitting deposit tasks.
 *  2. Simple producer-consumer with wait/notify for a transaction buffer.
 *  3. Deadlock: two threads transfer A→B and B→A locking in opposite order.
 *  4. Fix: always lock the account with the smaller accountNumber first.
 */
public class MiniBank {

    // ------------------------------------------------------------------ //
    //  1. Thread-pool batch deposits                                       //
    // ------------------------------------------------------------------ //
    static void runPoolDemo() throws InterruptedException {
        System.out.println("=== Thread-Pool Processor ===");
        Account acc = new Account("BatchAccount", 0);
        TransactionProcessor proc = new TransactionProcessor();

        for (int i = 0; i < 20; i++) {
            final int idx = i;
            proc.submit(() -> {
                acc.deposit(100);
                System.out.println("[pool] task " + idx + " deposited 100, thread="
                        + Thread.currentThread().getName());
            });
        }
        proc.stop();
        System.out.println("Final balance after 20×100 deposits: " + acc.getBalance()
                + "  (expected 2000)");
    }

    // ------------------------------------------------------------------ //
    //  2. Producer-consumer with wait/notify                               //
    // ------------------------------------------------------------------ //
    static final int[]    txBuf  = new int[4];
    static int            txHead = 0, txTail = 0, txCount = 0;
    static final Object   txLock = new Object();
    static final int      TOTAL  = 10;

    static void runProducerConsumer() throws InterruptedException {
        System.out.println("\n=== Producer-Consumer (wait/notify) ===");
        Account acc2 = new Account("PCAccount", 0);

        Thread producer = new Thread(() -> {
            for (int i = 1; i <= TOTAL; i++) {
                synchronized (txLock) {
                    while (txCount == txBuf.length) {
                        try { txLock.wait(); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                    }
                    txBuf[txTail] = i * 50;
                    txTail = (txTail + 1) % txBuf.length;
                    txCount++;
                    System.out.println("[producer] added tx=" + (i * 50));
                    txLock.notifyAll();
                }
            }
        }, "Producer");

        Thread consumer = new Thread(() -> {
            for (int i = 0; i < TOTAL; i++) {
                synchronized (txLock) {
                    while (txCount == 0) {
                        try { txLock.wait(); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                    }
                    int amount = txBuf[txHead];
                    txHead = (txHead + 1) % txBuf.length;
                    txCount--;
                    acc2.deposit(amount);
                    System.out.println("[consumer] deposited " + amount
                            + ", balance=" + acc2.getBalance());
                    txLock.notifyAll();
                }
            }
        }, "Consumer");

        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        System.out.println("PC done. Balance: " + acc2.getBalance());
    }

    // ------------------------------------------------------------------ //
    //  3. Deadlock demo + fix                                              //
    // ------------------------------------------------------------------ //

    /** Transfer with arbitrary lock order — can deadlock. */
    static void transferDeadlock(Account from, Account to, long amount) {
        synchronized (from) {
            System.out.println(Thread.currentThread().getName()
                    + " locked " + from.getAccountNumber());
            try { Thread.sleep(50); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            synchronized (to) {
                from.withdraw(amount);
                to.deposit(amount);
                System.out.println(Thread.currentThread().getName()
                        + " transferred " + amount);
            }
        }
    }

    /** Transfer always locking the lower account-number first — deadlock-free. */
    static void transferSafe(Account a, Account b, long amount) {
        Account first  = a.getAccountNumber().compareTo(b.getAccountNumber()) < 0 ? a : b;
        Account second = (first == a) ? b : a;
        Account from   = (a == first) ? a : b;
        Account to     = (a == first) ? b : a;

        synchronized (first) {
            synchronized (second) {
                from.withdraw(amount);
                to.deposit(amount);
                System.out.println(Thread.currentThread().getName()
                        + " safe-transferred " + amount);
            }
        }
    }

    static void runDeadlockDemo() throws InterruptedException {
        System.out.println("\n=== Deadlock Demo (will hang briefly — using interrupt to recover) ===");
        Account A = new Account("Alice", 5000);
        Account B = new Account("Bob",   5000);

        Thread t1 = new Thread(() -> transferDeadlock(A, B, 100), "T1-A->B");
        Thread t2 = new Thread(() -> transferDeadlock(B, A, 100), "T2-B->A");
        t1.setDaemon(true);
        t2.setDaemon(true);
        t1.start(); t2.start();
        // Wait 1 second to observe deadlock
        Thread.sleep(1000);
        System.out.println("[DEADLOCK OBSERVED] T1 holds " + A.getAccountNumber() + " waiting for " + B.getAccountNumber()
                + "; T2 holds " + B.getAccountNumber() + " waiting for " + A.getAccountNumber());
        System.out.println("Resolving deadlock via consistent lock order below...");

        System.out.println("\n=== Fixed Transfer (consistent lock order) ===");
        Account C = new Account("Carol", 5000);
        Account D = new Account("Dave",  5000);
        System.out.println("Before: " + C + " | " + D);

        Thread t3 = new Thread(() -> transferSafe(C, D, 500), "T3-C->D");
        Thread t4 = new Thread(() -> transferSafe(D, C, 300), "T4-D->C");
        t3.start(); t4.start();
        t3.join();  t4.join();
        System.out.println("After : " + C + " | " + D);
    }

    public static void main(String[] args) throws InterruptedException {
        runPoolDemo();
        runProducerConsumer();
        runDeadlockDemo();
    }
}
