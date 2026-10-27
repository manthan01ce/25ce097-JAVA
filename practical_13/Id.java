import java.lang.annotation.*;

/**
 * Practical 13 — @Id annotation (same contract as Practical 7).
 * Marks the field that acts as the unique key in the generic Repository.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Id {}
