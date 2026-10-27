import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Practical 11 — Part A, Program 2
 * Log analyser: reads each line of several text files using Path/Files,
 * counts total lines and how many contain a given keyword across all files,
 * and prints each matching line.
 */
public class LogAnalyzer {

    public static void main(String[] args) throws IOException {
        // Create sample log files in a temp dir
        Path logDir = Path.of(System.getProperty("java.io.tmpdir"), "p11_logs");
        Files.createDirectories(logDir);

        String[] logData = {
            "INFO  server started on port 8080\nERROR failed to connect to DB\nINFO  request received\n",
            "WARN  disk space low\nERROR timeout on connection\nINFO  backup complete\n",
            "INFO  user login\nINFO  user logout\nERROR invalid credentials\n"
        };

        Path[] files = new Path[logData.length];
        for (int i = 0; i < logData.length; i++) {
            files[i] = logDir.resolve("app" + (i + 1) + ".log");
            Files.writeString(files[i], logData[i]);
        }

        String keyword = "ERROR";
        int totalLines = 0, matchCount = 0;

        System.out.println("=== Log Analyser — keyword: \"" + keyword + "\" ===");
        for (Path file : files) {
            System.out.println("\n[File: " + file.getFileName() + "]");
            try (BufferedReader br = Files.newBufferedReader(file)) {
                String line;
                while ((line = br.readLine()) != null) {
                    totalLines++;
                    if (line.contains(keyword)) {
                        matchCount++;
                        System.out.println("  MATCH: " + line);
                    }
                }
            }
        }

        System.out.println("\nTotal lines : " + totalLines);
        System.out.println("Matches     : " + matchCount);
    }
}
