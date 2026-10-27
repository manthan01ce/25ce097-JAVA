import java.util.*;

/**
 * Practical 12 — Part A, Program 2
 * Unique visitors: from a login list with repeats,
 * use HashSet to count unique users and LinkedHashSet to keep first-seen order.
 * Print the unique count and the first three distinct names.
 */
public class UniqueVisitors {

    public static void main(String[] args) {
        List<String> logins = Arrays.asList(
            "alice", "bob", "alice", "charlie", "bob",
            "dave", "alice", "eve", "charlie", "dave",
            "frank", "bob", "alice"
        );

        Set<String> unique      = new HashSet<>(logins);
        Set<String> firstSeen   = new LinkedHashSet<>(logins); // preserves insertion order

        System.out.println("=== Unique Visitors ===");
        System.out.println("Total logins   : " + logins.size());
        System.out.println("Unique users   : " + unique.size());

        List<String> ordered = new ArrayList<>(firstSeen);
        System.out.println("First 3 distinct names: " + ordered.subList(0, Math.min(3, ordered.size())));
        System.out.println("All distinct (first-seen order): " + ordered);
    }
}
