import java.lang.reflect.Method;

/**
 * Practical 14 — optional @Audit logger.
 * Call AuditLogger.log(service, "methodName") to record which BankService method ran.
 */
public class AuditLogger {

    public static void log(Object target, String methodName) {
        try {
            for (Method m : target.getClass().getDeclaredMethods()) {
                if (m.getName().equals(methodName) && m.isAnnotationPresent(Audit.class)) {
                    System.out.println("[AUDIT] " + target.getClass().getSimpleName()
                            + "." + methodName + "() invoked");
                    return;
                }
            }
        } catch (Exception ignored) {}
    }
}
