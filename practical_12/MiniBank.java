import java.util.*;

/**
 * Practical 12 — Part B: MiniBank with Collections Framework
 *
 * Demonstrates:
 *  1. AccountStore (HashMap) — add, get by account number.
 *  2. Transaction history in ArrayList<String>.
 *  3. sortedByBalance() using a Comparator (highest first).
 *  4. Account implements Comparable<Account> (natural order by account number).
 *  5. TransactionProcessor backed by ConcurrentHashMap for thread-safe batch deposits.
 *  6. Supplementary: getAbove() filter and TreeMap-keyed sorted store.
 */
public class MiniBank {

    public static void main(String[] args) throws InterruptedException {

        // ---------- 1. Build the store ----------
        AccountStore store = new AccountStore();
        Account a1 = new SavingsAccount("Riya",  5000, 1000);
        Account a2 = new CurrentAccount("Aman",  2000, 1000);
        Account a3 = new FixedDepositAccount("Priya", 9000);
        store.add(a1); store.add(a2); store.add(a3);

        store.recordHistory("DEPOSIT "  + a1.getAccountNumber() + " 5000");
        store.recordHistory("DEPOSIT "  + a2.getAccountNumber() + " 2000");
        store.recordHistory("DEPOSIT "  + a3.getAccountNumber() + " 9000");

        // ---------- 2. get() lookup ----------
        System.out.println("=== get() by account number ===");
        System.out.println(store.get(a2.getAccountNumber()));

        // ---------- 3. sortedByBalance (Comparator) ----------
        System.out.println("\n=== Sorted by balance (highest first) ===");
        for (Account a : store.sortedByBalance()) System.out.println("  " + a);

        // ---------- 4. Natural order (Comparable) ----------
        System.out.println("\n=== Natural order (Comparable — by account number) ===");
        for (Account a : store.sortedByNumber()) System.out.println("  " + a);

        // ---------- 5. ConcurrentHashMap + TransactionProcessor ----------
        System.out.println("\n=== Concurrent batch deposits (10 × 100 on " + a1.getAccountNumber() + ") ===");
        long before = a1.getBalance();
        TransactionProcessor proc = new TransactionProcessor(store.getConcurrentStore());
        for (int i = 0; i < 10; i++) proc.submitDeposit(a1.getAccountNumber(), 100);
        proc.stop();
        System.out.println("Balance before: " + before + "  after: " + a1.getBalance()
                + "  (expected " + (before + 1000) + ")");

        // ---------- 6. Supplementary: accounts above threshold ----------
        System.out.println("\n=== Accounts with balance > 3000 ===");
        for (Account a : store.getAbove(3000)) System.out.println("  " + a);

        // ---------- 7. Supplementary: TreeMap keyed store ----------
        System.out.println("\n=== TreeMap (accounts in sorted key order) ===");
        TreeMap<String, Account> treeMap = new TreeMap<>();
        store.getAll().forEach(a -> treeMap.put(a.getAccountNumber(), a));
        treeMap.forEach((k, v) -> System.out.println("  " + k + " -> " + v.getOwnerName()));

        // ---------- 8. Transaction history ----------
        System.out.println("\n=== Transaction history ===");
        store.getHistory().forEach(h -> System.out.println("  " + h));
    }
}
