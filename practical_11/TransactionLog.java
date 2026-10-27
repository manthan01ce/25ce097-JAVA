import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Practical 11 — Part B
 * Append-only transaction log using Files.write() with CREATE + APPEND.
 * Each line format: "DEPOSIT|WITHDRAW  accountNumber  amount  timestamp"
 */
public class TransactionLog {

    private static final DateTimeFormatter FMT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void append(Path file, String line) throws IOException {
        String entry = line + "  " + LocalDateTime.now().format(FMT) + System.lineSeparator();
        Files.writeString(file, entry,
                StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }

    /** Convenience helpers */
    public static void logDeposit(Path file, String accNum, long amount) throws IOException {
        append(file, "DEPOSIT " + accNum + " " + amount);
    }

    public static void logWithdraw(Path file, String accNum, long amount) throws IOException {
        append(file, "WITHDRAW " + accNum + " " + amount);
    }
}
