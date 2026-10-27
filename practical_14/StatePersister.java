import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;

/** Saves/loads the account repository to/from a binary file via ObjectOutputStream. */
public class StatePersister {

    public static void save(Repository<Account> repo, Path file) throws IOException {
        ArrayList<Account> list = repo.findAll();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file.toFile()))) {
            oos.writeObject(list);
            System.out.println("[persist] Saved " + list.size() + " accounts to " + file.getFileName());
        }
    }

    @SuppressWarnings("unchecked")
    public static void load(Repository<Account> repo, Path file) throws IOException, ClassNotFoundException {
        if (!file.toFile().exists()) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file.toFile()))) {
            ArrayList<Account> list = (ArrayList<Account>) ois.readObject();
            list.forEach(repo::save);
            System.out.println("[persist] Loaded " + list.size() + " accounts from " + file.getFileName());
        }
    }
}
