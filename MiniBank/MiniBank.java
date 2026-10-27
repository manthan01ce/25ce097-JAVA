import java.nio.file.*;
import java.util.*;
import java.util.concurrent.*;

/**
 * Practical 14 — Part B: MiniBank — Final Capstone
 *
 * Assembles every layer:
 *   - Repository<Account> (ConcurrentHashMap, @Id reflection)
 *   - BankService (openAccount, deposit, withdraw, transfer, statement, listAccounts)
 *   - Validator, BankException hierarchy
 *   - StatePersister  (load on startup, save on exit)
 *   - TransactionLog  (NIO append)
 *   - ReportGenerator (NIO directory walk)
 *   - TransactionProcessor (ExecutorService thread pool, ConcurrentHashMap store)
 *   - @Audit reflective logger
 *
 * Menu matches Practical 1 structure + extra options.
 */
public class MiniBank {

    static final Path DATA_DIR  = Path.of("data_p14");
    static final Path STATE_FILE = DATA_DIR.resolve("accounts.dat");
    static final Path LOG_FILE   = DATA_DIR.resolve("logs").resolve("transactions.log");
    static final Path REPORT_FILE= DATA_DIR.resolve("report.txt");

    // ------------------------------------------------------------------ //

    public static void main(String[] args) throws Exception {
        Files.createDirectories(DATA_DIR.resolve("logs"));

        Repository<Account> repo    = new Repository<>();
        BankService         service = new BankService(repo, LOG_FILE);

        // Load persisted state
        StatePersister.load(repo, STATE_FILE);

        Scanner sc = new Scanner(System.in);
        printHeader();
        boolean running = true;

        while (running) {
            printMenu();
            System.out.print("Choice: ");
            String input = sc.nextLine().trim();
            int choice;
            try { choice = Integer.parseInt(input); }
            catch (NumberFormatException e) { System.out.println("Enter a number.\n"); continue; }

            try {
                switch (choice) {
                    case 1  -> openAccountFlow(service, sc);
                    case 2  -> depositFlow(service, sc);
                    case 3  -> withdrawFlow(service, sc);
                    case 4  -> transferFlow(service, sc);
                    case 5  -> statementFlow(service, sc);
                    case 6  -> listAccounts(service);
                    case 7  -> runBatchDemo(repo);
                    case 8  -> runReport();
                    case 9  -> {
                        StatePersister.save(repo, STATE_FILE);
                        System.out.println("Thank you for using MiniBank. Goodbye!");
                        running = false;
                    }
                    default -> System.out.println("Choose 1–9.\n");
                }
            } catch (BankException e) {
                System.out.println("  ERROR: " + e.getMessage() + "\n");
            }
        }
        sc.close();
    }

    // ------------------------------------------------------------------ //
    //  Menu flows                                                          //
    // ------------------------------------------------------------------ //

    static void openAccountFlow(BankService svc, Scanner sc) throws BankException {
        System.out.print("  Owner name : "); String name = sc.nextLine().trim();
        System.out.print("  Type (SAVINGS/CURRENT/FD): "); String type = sc.nextLine().trim();
        System.out.print("  Opening balance: "); long bal = Long.parseLong(sc.nextLine().trim());
        Account acc = svc.openAccount(name, type, bal);
        System.out.println("  Account created: " + acc.getAccountNumber() + "\n");
    }

    static void depositFlow(BankService svc, Scanner sc) throws BankException {
        System.out.print("  Account number: "); String num = sc.nextLine().trim();
        System.out.print("  Amount: ");          long amt = Long.parseLong(sc.nextLine().trim());
        long bal = svc.deposit(num, amt);
        System.out.println("  Deposited. New balance: " + bal + "\n");
    }

    static void withdrawFlow(BankService svc, Scanner sc) throws BankException {
        System.out.print("  Account number: "); String num = sc.nextLine().trim();
        System.out.print("  Amount: ");          long amt = Long.parseLong(sc.nextLine().trim());
        long bal = svc.withdraw(num, amt);
        System.out.println("  Withdrawn. New balance: " + bal + "\n");
    }

    static void transferFlow(BankService svc, Scanner sc) throws BankException {
        System.out.print("  From account: "); String from = sc.nextLine().trim();
        System.out.print("  To account  : "); String to   = sc.nextLine().trim();
        System.out.print("  Amount      : "); long   amt  = Long.parseLong(sc.nextLine().trim());
        svc.transfer(from, to, amt);
        System.out.println("  Transfer complete.\n");
    }

    static void statementFlow(BankService svc, Scanner sc) throws BankException {
        System.out.print("  Account number: "); String num = sc.nextLine().trim();
        System.out.println(svc.statement(num));
    }

    static void listAccounts(BankService svc) {
        System.out.println("=== All Accounts ===");
        List<Account> list = svc.listAccounts();
        if (list.isEmpty()) { System.out.println("  No accounts.\n"); return; }
        list.forEach(a -> System.out.println("  " + a));
        System.out.println();
    }

    static void runBatchDemo(Repository<Account> repo) throws InterruptedException {
        System.out.println("=== Batch Processor Demo (10 concurrent deposits) ===");
        List<Account> accounts = repo.findAll();
        if (accounts.isEmpty()) { System.out.println("  No accounts to process.\n"); return; }
        Account target = accounts.get(0);
        long before = target.getBalance();

        ExecutorService pool = Executors.newFixedThreadPool(4);
        for (int i = 0; i < 10; i++) {
            pool.execute(() -> target.deposit(100));
        }
        pool.shutdown();
        pool.awaitTermination(10, TimeUnit.SECONDS);

        System.out.println("  Account: " + target.getAccountNumber()
                + "  before=" + before + "  after=" + target.getBalance()
                + "  (added " + (target.getBalance() - before) + ")\n");
    }

    static void runReport() throws Exception {
        System.out.println("=== Generating Report ===");
        ReportGenerator.generate(DATA_DIR.resolve("logs"), REPORT_FILE);
    }

    // ------------------------------------------------------------------ //
    //  UI helpers                                                          //
    // ------------------------------------------------------------------ //

    static void printHeader() {
        System.out.println("==============================");
        System.out.println("  MiniBank  —  Main Branch  ");
        System.out.println("==============================\n");
    }

    static void printMenu() {
        System.out.println("--- MiniBank Menu ---");
        System.out.println(" 1. Open Account");
        System.out.println(" 2. Deposit");
        System.out.println(" 3. Withdraw");
        System.out.println(" 4. Transfer");
        System.out.println(" 5. Statement");
        System.out.println(" 6. List All Accounts");
        System.out.println(" 7. Run Batch Demo");
        System.out.println(" 8. End-of-Day Report");
        System.out.println(" 9. Save & Exit");
    }
}
