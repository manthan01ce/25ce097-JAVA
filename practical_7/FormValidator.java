import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Practical 7 — Part A, Program 1
 * Uses reflection to read @NotBlank and @MaxLength annotations and return validation errors.
 */
public class FormValidator {

    public static List<String> validate(Object obj) {
        List<String> errors = new ArrayList<>();
        Field[] fields = obj.getClass().getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(obj);

                if (field.isAnnotationPresent(NotBlank.class)) {
                    String msg = field.getAnnotation(NotBlank.class).message();
                    if (value == null || value.toString().isBlank()) {
                        errors.add(field.getName() + ": " + msg);
                    }
                }

                if (field.isAnnotationPresent(MaxLength.class)) {
                    MaxLength ml = field.getAnnotation(MaxLength.class);
                    if (value != null && value.toString().length() > ml.value()) {
                        errors.add(field.getName() + ": " + ml.message()
                                + " (max " + ml.value() + ")");
                    }
                }
            } catch (IllegalAccessException e) {
                errors.add(field.getName() + ": could not read field");
            }
        }
        return errors;
    }

    public static void main(String[] args) {
        System.out.println("=== Valid form ===");
        SignupForm valid = new SignupForm("riya", "riya@example.com", "9876543210");
        List<String> errs = validate(valid);
        System.out.println(errs.isEmpty() ? "No errors." : errs);

        System.out.println("\n=== Invalid form (blank username, phone too long) ===");
        SignupForm invalid = new SignupForm("", "riya@example.com", "98765432109876543210X");
        errs = validate(invalid);
        errs.forEach(e -> System.out.println("  ERROR: " + e));
    }
}
