import java.util.*;

/**
 * Practical 12 — Part A, Program 1
 * Word frequency: split a paragraph into words, count each in a HashMap,
 * then print sorted by frequency (highest first) using a Comparator.
 */
public class WordFrequency {

    public static void main(String[] args) {
        String text = "the quick brown fox jumps over the lazy dog the fox "
                    + "and the dog sat under the tree the tree was tall";

        Map<String, Integer> freq = new HashMap<>();
        for (String word : text.split("\\s+")) {
            freq.merge(word.toLowerCase(), 1, Integer::sum);
        }

        List<Map.Entry<String, Integer>> entries = new ArrayList<>(freq.entrySet());
        // Sort by frequency desc, then alphabetically for ties
        entries.sort((a, b) -> {
            int cmp = b.getValue().compareTo(a.getValue()); // desc by count
            return cmp != 0 ? cmp : a.getKey().compareTo(b.getKey()); // asc by word
        });

        System.out.println("=== Word Frequency (highest first) ===");
        for (Map.Entry<String, Integer> e : entries) {
            System.out.printf("  %-12s : %d%n", e.getKey(), e.getValue());
        }
    }
}
