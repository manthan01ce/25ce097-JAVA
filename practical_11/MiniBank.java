import java.nio.file.*;

/**
 * Practical 11 — Part B: MiniBank — Serialization + File I/O + NIO
 *
 * Demonstrates:
 *  1. Make Account (and subclasses) Serializable with serialVersionUID.
 *  2. StatePersister — save/load Account[] with ObjectOutputStream/InputStream.
 *  3. TransactionLog — append-only log using Files.writeString + CREATE + APPEND.
 *  4. ReportGenerator — NIO directory walk, BufferedReader per file, summary report.
 */
public class MiniBank {

    public static void main(String[] args) throws Exception {
        // ---------- setup: data directory ----------
        Path dataDir = Path.of("data_p11");
        Path logsDir = dataDir.resolve("logs");
        Files.createDirectories(logsDir);

        // ---------- 1. Create accounts ----------
        Account[] accounts = {
            new SavingsAccount("Riya",  10000, 2000),
            new CurrentAccount("Aman",   5000, 3000),
            new FixedDepositAccount("Priya", 50000)
        };

        System.out.println("=== Original accounts ===");
        for (Account a : accounts) System.out.println("  " + a);

        // ---------- 2. StatePersister — save ----------
        Path stateFile = dataDir.resolve("accounts.dat");
        StatePersister.save(accounts, stateFile);

        // ---------- 3. TransactionLog — log some operations ----------
        Path logFile = logsDir.resolve("transactions.log");
        accounts[0].deposit(1500);
        TransactionLog.logDeposit(logFile, accounts[0].getAccountNumber(), 1500);

        accounts[1].deposit(2000);
        TransactionLog.logDeposit(logFile, accounts[1].getAccountNumber(), 2000);

        accounts[0].withdraw(500);
        TransactionLog.logWithdraw(logFile, accounts[0].getAccountNumber(), 500);

        // ---------- 4. StatePersister — reload ----------
        Account[] reloaded = StatePersister.load(stateFile);
        System.out.println("\n=== Reloaded accounts (before in-memory changes) ===");
        for (Account a : reloaded) System.out.println("  " + a);

        // ---------- 5. ReportGenerator ----------
        System.out.println("\n=== Generating end-of-day report ===");
        Path reportFile = dataDir.resolve("report.txt");
        ReportGenerator.generate(logsDir, reportFile);
    }
}
