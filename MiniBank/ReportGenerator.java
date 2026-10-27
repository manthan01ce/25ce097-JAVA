import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/** Walks the logs folder with NIO, totals deposits/withdrawals, writes a report file. */
public class ReportGenerator {

    public static void generate(Path logsDir, Path reportFile) throws IOException {
        long totalDeposits = 0, totalWithdrawals = 0;
        int  fileCount     = 0;

        Files.createDirectories(logsDir);

        try (DirectoryStream<Path> ds = Files.newDirectoryStream(logsDir, "*.log")) {
            for (Path logFile : ds) {
                fileCount++;
                BasicFileAttributes attrs = Files.readAttributes(logFile, BasicFileAttributes.class);
                System.out.println("  [log] " + logFile.getFileName()
                        + "  size=" + attrs.size()
                        + "  lastModified=" + attrs.lastModifiedTime());

                try (BufferedReader br = Files.newBufferedReader(logFile)) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        String[] p = line.trim().split("\\s+");
                        if (p.length < 3) continue;
                        try {
                            long amt = Long.parseLong(p[2]);
                            if (p[0].equalsIgnoreCase("DEPOSIT"))  totalDeposits    += amt;
                            if (p[0].equalsIgnoreCase("WITHDRAW")) totalWithdrawals += amt;
                        } catch (NumberFormatException ignored) {}
                    }
                }
            }
        }

        String report = "=== End-of-Day Report ===\n"
                + "Files processed  : " + fileCount + "\n"
                + "Total deposits   : " + totalDeposits + "\n"
                + "Total withdrawals: " + totalWithdrawals + "\n"
                + "Net change       : " + (totalDeposits - totalWithdrawals) + "\n";

        Files.writeString(reportFile, report,
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        System.out.println(report);
    }
}
