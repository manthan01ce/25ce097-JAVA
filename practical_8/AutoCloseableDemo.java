/**
 * Practical 8 — Part A, Program 3
 * Proves AutoCloseable resource always closes via try-with-resources,
 * even when an exception is thrown inside the block, and original error is reported.
 */
public class AutoCloseableDemo {

    static class DatabaseConnection implements AutoCloseable {
        private final String connectionId;

        public DatabaseConnection(String connectionId) {
            this.connectionId = connectionId;
            System.out.println("[RESOURCE OPENED] Database connection " + connectionId + " established.");
        }

        public void executeQuery(String query) {
            System.out.println("[EXECUTE] Running: " + query);
            if (query.contains("FAIL")) {
                throw new RuntimeException("Query execution failed: Syntax error in SQL query");
            }
            System.out.println("[SUCCESS] Query executed successfully.");
        }

        @Override
        public void close() {
            System.out.println("[RESOURCE CLOSED] Database connection " + connectionId + " closed automatically.");
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Case 1: Normal Execution with Try-With-Resources ===");
        try (DatabaseConnection conn = new DatabaseConnection("DB-POOL-01")) {
            conn.executeQuery("SELECT * FROM accounts");
        } catch (Exception e) {
            System.out.println("[CAUGHT] " + e.getMessage());
        }

        System.out.println("\n=== Case 2: Exception Inside Try Block (Resource Guaranteed Closed) ===");
        try (DatabaseConnection conn = new DatabaseConnection("DB-POOL-02")) {
            conn.executeQuery("SELECT * FAIL FROM corrupted_table");
        } catch (Exception e) {
            System.out.println("[CAUGHT ORIGINAL ERROR] " + e.getMessage());
        }
    }
}
