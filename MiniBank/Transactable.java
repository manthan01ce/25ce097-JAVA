/**
 * Interface expressing that an object can accept deposits and withdrawals.
 * Account implements this; any service can program to this type.
 */
public interface Transactable {
    void deposit(long amount);
    boolean withdraw(long amount);
}
