/**
 * Practical 8 — Part B: MiniBank Fault-Tolerant Exception Handling
 *
 * Demonstrates:
 *   - Checked BankException hierarchy (InsufficientFundsException with shortfall,
 *     InvalidAmountException, AccountNotFoundException)
 *   - withdraw / deposit / transfer throwing custom exceptions
 *   - try-catch-finally handling with user-friendly messages
 *   - AutoCloseable resource in try-with-resources
 */
public class MiniBank {

    static class BankSession implements AutoCloseable {
        private final String sessionId;

        public BankSession(String sessionId) {
            this.sessionId = sessionId;
            System.out.println("[SESSION OPENED] Secure transaction session: " + sessionId);
        }

        public void logActivity(String action) {
            System.out.println("[AUDIT " + sessionId + "] Action: " + action);
        }

        @Override
        public void close() {
            System.out.println("[SESSION CLOSED] Secure transaction session " + sessionId + " terminated.");
        }
    }

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("   MiniBank — Practical 8 (Milestone 1 Exceptions) ");
        System.out.println("==================================================");

        Account acc1 = new SavingsAccount("Riya", 1000, 500);
        Account acc2 = new CurrentAccount("Aman", 2000, 1000);

        System.out.println("\nInitial Accounts:");
        System.out.println("  " + acc1);
        System.out.println("  " + acc2);

        // 1. Try-with-resources with AutoCloseable BankSession
        System.out.println("\n--- 1. AutoCloseable BankSession (Try-With-Resources) ---");
        try (BankSession session = new BankSession("SESS-8001")) {
            session.logActivity("Querying account balances");
            System.out.println("Session operational inside try-block.");
        }

        // 2. Withdrawal exceeding balance -> InsufficientFundsException with shortfall
        System.out.println("\n--- 2. Withdrawing 5000 from Savings (Balance: 1000, Min: 500) ---");
        try {
            System.out.println("Attempting withdraw(5000)...");
            acc1.withdraw(5000);
        } catch (InsufficientFundsException e) {
            System.out.println("[CAUGHT] Withdrawal failed: Short by " + e.getShortfall() + " (Shortfall preserved)");
            System.out.println("Exception message: " + e.getMessage());
        } catch (InvalidAmountException e) {
            System.out.println("[CAUGHT] Invalid amount: " + e.getMessage());
        } finally {
            System.out.println("[FINALLY] Balance after attempt: " + acc1.getBalance());
        }

        // 3. Invalid deposit amount -> InvalidAmountException
        System.out.println("\n--- 3. Depositing invalid amount (-500) ---");
        try {
            System.out.println("Attempting deposit(-500)...");
            acc1.deposit(-500);
        } catch (InvalidAmountException e) {
            System.out.println("[CAUGHT] Deposit rejected: " + e.getMessage());
        } finally {
            System.out.println("[FINALLY] Deposit verification completed.");
        }

        // 4. Successful deposit
        System.out.println("\n--- 4. Valid deposit (1500) ---");
        try {
            acc1.deposit(1500);
            System.out.println("[SUCCESS] Deposited 1500. New balance: " + acc1.getBalance());
        } catch (InvalidAmountException e) {
            System.out.println("[CAUGHT] " + e.getMessage());
        }

        // 5. Transfer with try-catch-finally
        System.out.println("\n--- 5. Inter-Account Transfer (acc1 -> acc2: 1200) ---");
        try {
            acc1.transfer(acc2, 1200);
            System.out.println("Transfer successful!");
        } catch (BankException e) {
            System.out.println("[TRANSFER ERROR] " + e.getMessage());
        }

        System.out.println("\nFinal Account States:");
        System.out.println("  " + acc1);
        System.out.println("  " + acc2);
    }
}
