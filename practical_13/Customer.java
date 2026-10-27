import java.io.Serializable;

/**
 * Practical 13 — Customer with @Id on customerId.
 * Used to demonstrate Repository<Customer> reuse without changes to Repository<T>.
 */
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private final String customerId;
    private final String name;
    private final String email;

    private static long counter = 0;

    public Customer(String name, String email) {
        this.customerId = String.format("CU%04d", ++counter);
        this.name       = name;
        this.email      = email;
    }

    public String getCustomerId() { return customerId; }
    public String getName()       { return name; }
    public String getEmail()      { return email; }

    @Override
    public String toString() { return customerId + " | " + name + " | " + email; }
}
