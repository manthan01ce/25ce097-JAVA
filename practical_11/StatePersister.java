import java.io.*;
import java.nio.file.Path;

/**
 * Practical 11 — Part B
 * Saves and loads an Account[] using ObjectOutputStream / ObjectInputStream,
 * both wrapped in try-with-resources.
 */
public class StatePersister {

    /** Serialise the accounts array to the given file. */
    public static void save(Account[] accounts, Path file) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(file.toFile()))) {
            oos.writeObject(accounts);
            System.out.println("[StatePersister] Saved " + accounts.length
                    + " accounts to " + file.getFileName());
        }
    }

    /** Deserialise and return the Account[] from the given file. */
    @SuppressWarnings("unchecked")
    public static Account[] load(Path file) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(file.toFile()))) {
            Account[] accounts = (Account[]) ois.readObject();
            System.out.println("[StatePersister] Loaded " + accounts.length
                    + " accounts from " + file.getFileName());
            return accounts;
        }
    }
}
