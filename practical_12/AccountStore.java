import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Practical 12 — Part B
 * AccountStore: accounts in a HashMap keyed by accountNumber.
 * Also keeps a transaction history in an ArrayList<String>.
 * sortedByBalance() returns accounts sorted highest-balance-first via Comparator.
 * A second ConcurrentHashMap-backed view is exposed for thread-safe concurrent access.
 */
public class AccountStore {

    private final Map<String, Account> store   = new HashMap<>();
    private final List<String>         history = new ArrayList<>();

    // Thread-safe map for the TransactionProcessor (Practical 12 spec item 5)
    private final Map<String, Account> concurrentStore = new ConcurrentHashMap<>();

    // --- basic CRUD ---

    public void add(Account account) {
        store.put(account.getAccountNumber(), account);
        concurrentStore.put(account.getAccountNumber(), account);
    }

    public Account get(String accountNumber) {
        return store.get(accountNumber);
    }

    public Collection<Account> getAll() {
        return store.values();
    }

    /** Returns all accounts with balance above the given threshold. */
    public List<Account> getAbove(long threshold) {
        List<Account> result = new ArrayList<>();
        for (Account a : store.values())
            if (a.getBalance() > threshold) result.add(a);
        return result;
    }

    // --- transaction history ---

    public void recordHistory(String entry) { history.add(entry); }

    public List<String> getHistory() { return Collections.unmodifiableList(history); }

    // --- sorting ---

    /** Returns accounts sorted by balance descending (Comparator). */
    public List<Account> sortedByBalance() {
        List<Account> list = new ArrayList<>(store.values());
        list.sort(Comparator.comparingLong(Account::getBalance).reversed());
        return list;
    }

    /** Returns accounts in natural order (Comparable — by account number). */
    public List<Account> sortedByNumber() {
        List<Account> list = new ArrayList<>(store.values());
        Collections.sort(list);   // uses Account.compareTo()
        return list;
    }

    // --- thread-safe view ---

    public Map<String, Account> getConcurrentStore() { return concurrentStore; }
}
