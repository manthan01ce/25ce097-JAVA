import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Validates any object by reading @Positive and @MaxLength annotations at runtime.
 */
public class AnnotationValidator {

    public static String[] validate(Object obj) {
        List<String> errors = new ArrayList<>();
        for (Field field : obj.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                Object value = field.get(obj);

                if (field.isAnnotationPresent(Positive.class)) {
                    String msg = field.getAnnotation(Positive.class).message();
                    if (value instanceof Number n && n.longValue() <= 0) {
                        errors.add(field.getName() + ": " + msg);
                    }
                }

                if (field.isAnnotationPresent(MaxLength.class)) {
                    MaxLength ml = field.getAnnotation(MaxLength.class);
                    if (value instanceof String s && s.length() > ml.value()) {
                        errors.add(field.getName() + ": " + ml.message()
                                + " (max " + ml.value() + ")");
                    }
                }
            } catch (IllegalAccessException ignored) {}
        }
        return errors.toArray(new String[0]);
    }
}
