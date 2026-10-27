import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/** Append-only transaction log using NIO Files.writeString with CREATE + APPEND. */
public class TransactionLog {

    private static final DateTimeFormatter FMT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void append(Path file, String line) throws IOException {
        String entry = line + "  " + LocalDateTime.now().format(FMT) + System.lineSeparator();
        Files.writeString(file, entry, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }

    public static void logDeposit(Path file, String accNum, long amount) throws IOException {
        append(file, "DEPOSIT " + accNum + " " + amount);
    }

    public static void logWithdraw(Path file, String accNum, long amount) throws IOException {
        append(file, "WITHDRAW " + accNum + " " + amount);
    }

    public static void logTransfer(Path file, String from, String to, long amount) throws IOException {
        append(file, "TRANSFER " + from + "->" + to + " " + amount);
    }
}
