import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Generic Repository<T> — thread-safe ConcurrentHashMap backing store.
 * Key is discovered at runtime via the @Id-annotated field.
 */
public class Repository<T> {

    private final ConcurrentHashMap<String, T> store = new ConcurrentHashMap<>();

    public T save(T entity) {
        store.put(extractId(entity), entity);
        return entity;
    }

    @SafeVarargs
    public final void saveAll(T... items) { for (T item : items) save(item); }

    public T findById(String id)          { return store.get(id); }

    public ArrayList<T> findAll()         { return new ArrayList<>(store.values()); }

    public boolean delete(String id)      { return store.remove(id) != null; }

    public int size()                     { return store.size(); }

    public boolean contains(String id)    { return store.containsKey(id); }

    private String extractId(T entity) {
        Class<?> cls = entity.getClass();
        while (cls != null) {
            for (Field f : cls.getDeclaredFields()) {
                if (f.isAnnotationPresent(Id.class)) {
                    f.setAccessible(true);
                    try {
                        Object v = f.get(entity);
                        if (v == null) throw new IllegalStateException("@Id field is null");
                        return v.toString();
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException("Cannot read @Id field", e);
                    }
                }
            }
            cls = cls.getSuperclass();
        }
        throw new IllegalArgumentException(entity.getClass().getSimpleName() + " has no @Id field");
    }
}
