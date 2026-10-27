/**
 * Practical 13 — Part B: MiniBank with Generic Repository<T>
 *
 * Demonstrates:
 *  1. Repository<Account> — save, findById, findAll, delete via @Id reflection.
 *  2. saveAll() @SafeVarargs batch save.
 *  3. Same Repository<T> class reused for Customer without any changes.
 *  4. Bounded average() generic method.
 */
public class MiniBank {

    public static void main(String[] args) {

        // ---------- 1. Repository<Account> ----------
        System.out.println("=== Repository<Account> ===");
        Repository<Account> accountRepo = new Repository<>();

        Account a1 = new SavingsAccount("Riya",  10000, 2000);
        Account a2 = new CurrentAccount("Aman",   5000, 3000);
        Account a3 = new FixedDepositAccount("Priya", 50000);

        // saveAll() — @SafeVarargs
        accountRepo.saveAll(a1, a2, a3);
        System.out.println("Stored " + accountRepo.size() + " accounts.");

        // findById
        System.out.println("\nfindById(" + a2.getAccountNumber() + "):");
        System.out.println("  " + accountRepo.findById(a2.getAccountNumber()));

        // findAll
        System.out.println("\nfindAll():");
        accountRepo.findAll().forEach(a -> System.out.println("  " + a));

        // delete
        accountRepo.delete(a3.getAccountNumber());
        System.out.println("\nAfter deleting " + a3.getAccountNumber()
                + ", size=" + accountRepo.size());

        // save updated
        accountRepo.save(a1);   // re-save (idempotent — same key)
        System.out.println("Re-saved " + a1.getAccountNumber() + ", size=" + accountRepo.size());

        // ---------- 2. Repository<Customer> — same class, different type ----------
        System.out.println("\n=== Repository<Customer> (same Repository<T> class) ===");
        Repository<Customer> customerRepo = new Repository<>();
        customerRepo.saveAll(
            new Customer("Alice", "alice@example.com"),
            new Customer("Bob",   "bob@example.com")
        );
        System.out.println("Stored " + customerRepo.size() + " customers.");
        customerRepo.findAll().forEach(c -> System.out.println("  " + c));

        // ---------- 3. Bounded average ----------
        System.out.println("\n=== Bounded average() ===");
        Long[] balances = { a1.getBalance(), a2.getBalance() };
        System.out.printf("avg balance = %.2f%n", Repository.average(balances));
    }
}
