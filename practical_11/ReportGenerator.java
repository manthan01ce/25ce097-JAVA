import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Practical 11 — Part B
 * Walks the logs folder using Files.newDirectoryStream(),
 * reads each .log file line by line with BufferedReader,
 * totals deposits and withdrawals, and writes a summary report.
 */
public class ReportGenerator {

    public static void generate(Path logsDir, Path reportFile) throws IOException {
        long totalDeposits    = 0;
        long totalWithdrawals = 0;
        int  fileCount        = 0;

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(logsDir, "*.log")) {
            for (Path logFile : stream) {
                fileCount++;
                BasicFileAttributes attrs = Files.readAttributes(logFile, BasicFileAttributes.class);
                System.out.println("  [log] " + logFile.getFileName()
                        + "  size=" + attrs.size()
                        + "  lastModified=" + attrs.lastModifiedTime());

                try (BufferedReader br = Files.newBufferedReader(logFile)) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        line = line.trim();
                        if (line.isEmpty()) continue;
                        String[] parts = line.split("\\s+");
                        if (parts.length < 3) continue;
                        try {
                            long amount = Long.parseLong(parts[2]);
                            if (parts[0].equalsIgnoreCase("DEPOSIT"))  totalDeposits    += amount;
                            if (parts[0].equalsIgnoreCase("WITHDRAW")) totalWithdrawals += amount;
                        } catch (NumberFormatException ignored) { /* skip malformed */ }
                    }
                }
            }
        }

        long net = totalDeposits - totalWithdrawals;
        String report = "=== End-of-Day Report ===\n"
                + "Log files processed : " + fileCount + "\n"
                + "Total deposits      : " + totalDeposits + "\n"
                + "Total withdrawals   : " + totalWithdrawals + "\n"
                + "Net change          : " + net + "\n";

        Files.writeString(reportFile, report,
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

        System.out.println("\n" + report);
        System.out.println("[ReportGenerator] Written to " + reportFile);
    }
}
