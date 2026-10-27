/**
 * Interface for accounts that carry an interest rate.
 * Provides a default method so subclasses don't need to repeat the formula.
 */
public interface InterestBearing {
    double interestRate();

    /** Returns the amount of interest earned in one year on the current balance. */
    default double yearlyInterest() {
        // Requires access to balance; downcast to Account if needed.
        // Defined here as a template — Account overrides using its own balance field.
        return 0;
    }
}
