/**
 * Practical 9 — Part B
 * Runnable worker that deposits a fixed amount into an account a fixed number of times.
 * Prints its thread name on start and finish to show the thread lifecycle.
 */
public class AccountWorker implements Runnable {

    private final Account account;
    private final int     times;
    private final long    amount;
    private final boolean useSafe; // toggle between safe and unsafe deposit

    public AccountWorker(Account account, int times, long amount, boolean useSafe) {
        this.account = account;
        this.times   = times;
        this.amount  = amount;
        this.useSafe = useSafe;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " started.");
        for (int i = 0; i < times; i++) {
            if (useSafe) account.deposit(amount);
            else         account.depositUnsafe(amount);
        }
        System.out.println(Thread.currentThread().getName() + " finished.");
    }
}
