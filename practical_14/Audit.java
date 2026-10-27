import java.lang.annotation.*;

/**
 * Practical 14 — optional @Audit annotation.
 * Mark a method to have its invocation logged by AuditLogger.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Audit {}
