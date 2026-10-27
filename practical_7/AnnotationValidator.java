import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Validates any object by reading @Positive and @MaxLength annotations at runtime.
 */
public class AnnotationValidator {

    public static String[] validate(Object obj) {
        List<String> errors = new ArrayList<>();
        Field[] fields = obj.getClass().getDeclaredFields();

        for (Field field : fields) {
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
                                + " (max " + ml.value() + ", got " + s.length() + ")");
                    }
                }
            } catch (IllegalAccessException e) {
                errors.add(field.getName() + ": inaccessible");
            }
        }
        return errors.toArray(new String[0]);
    }
}
