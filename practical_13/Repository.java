import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Practical 13 — Part B: Generic Repository<T>
 *
 * Stores any type T in a HashMap<String, T>.
 * The map key is found at runtime by reading the field annotated @Id via reflection.
 *
 * Methods:
 *   T            save(T entity)
 *   T            findById(String id)
 *   ArrayList<T> findAll()
 *   boolean      delete(String id)
 *   void         saveAll(T... items)       — @SafeVarargs
 *   int          size()
 *   static double average(N[] values)     — bounded type parameter
 */
public class Repository<T> {

    private final HashMap<String, T> store = new HashMap<>();

    // ------------------------------------------------------------------ //
    //  Core CRUD                                                           //
    // ------------------------------------------------------------------ //

    /** Saves entity; key is the @Id-annotated field value. Returns the saved entity. */
    public T save(T entity) {
        String id = extractId(entity);
        store.put(id, entity);
        return entity;
    }

    public T findById(String id) {
        return store.get(id);
    }

    public ArrayList<T> findAll() {
        return new ArrayList<>(store.values());
    }

    public boolean delete(String id) {
        return store.remove(id) != null;
    }

    public int size() { return store.size(); }

    // ------------------------------------------------------------------ //
    //  @SafeVarargs batch save                                             //
    // ------------------------------------------------------------------ //

    @SafeVarargs
    public final void saveAll(T... items) {
        for (T item : items) save(item);
    }

    // ------------------------------------------------------------------ //
    //  Bounded generic method                                              //
    // ------------------------------------------------------------------ //

    /** Average of any Number subtype array — bounded type parameter. */
    public static <N extends Number> double average(N[] values) {
        if (values == null || values.length == 0)
            throw new IllegalArgumentException("Array is empty");
        double sum = 0;
        for (N n : values) sum += n.doubleValue();
        return sum / values.length;
    }

    // ------------------------------------------------------------------ //
    //  Reflection helper — read @Id field value                           //
    // ------------------------------------------------------------------ //

    private String extractId(T entity) {
        Class<?> cls = entity.getClass();
        // walk up the class hierarchy to find @Id on any declared field
        while (cls != null) {
            for (Field field : cls.getDeclaredFields()) {
                if (field.isAnnotationPresent(Id.class)) {
                    field.setAccessible(true);
                    try {
                        Object val = field.get(entity);
                        if (val == null)
                            throw new IllegalStateException("@Id field is null in " + entity);
                        return val.toString();
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException("Cannot read @Id field", e);
                    }
                }
            }
            cls = cls.getSuperclass();
        }
        throw new IllegalArgumentException(
                entity.getClass().getSimpleName() + " has no @Id-annotated field");
    }
}
