/**
 * Functional interface for a pluggable withdrawal rule.
 * Can be supplied as a lambda: (account, amount) -> account.canWithdraw(amount)
 */
@FunctionalInterface
public interface WithdrawRule {
    boolean allow(Account account, long amount);
}
