/**
 * Practical 7 — Part B: MiniBank with custom annotations & reflection-based validation.
 *
 * New additions this lab:
 *   • @Id, @Positive, @MaxLength custom annotations
 *   • AnnotationValidator reads them by reflection
 *   • @Override added on all overridden methods (documented in Account / subclasses)
 *   • @FunctionalInterface on WithdrawRule
 */
public class MiniBank {

    public static void main(String[] args) {
        System.out.println("=== AnnotationValidator on valid SavingsAccount ===");
        Account good = new SavingsAccount("Riya", 10000, 2000);
        String[] errs = AnnotationValidator.validate(good);
        System.out.println(errs.length == 0 ? "No errors." : java.util.Arrays.toString(errs));

        System.out.println("\n=== AnnotationValidator on Account with negative balance ===");
        // Directly create a CurrentAccount with negative opening balance to trigger @Positive
        Account bad = new CurrentAccount("BadActor", -100, 0);
        errs = AnnotationValidator.validate(bad);
        if (errs.length == 0) {
            System.out.println("No errors (balance field not annotated at this level).");
        } else {
            for (String e : errs) System.out.println("  ERROR: " + e);
        }

        System.out.println("\n=== @Run mini-test runner ===");
        try {
            TestSuite suite = new TestSuite();
            int ran = MiniTestRunner.run(suite);
            System.out.println("Ran " + ran + " test(s).");
        } catch (Exception e) {
            System.out.println("Runner error: " + e.getMessage());
        }

        System.out.println("\n=== Existing account behaviour ===");
        Account[] accounts = {
            new SavingsAccount("Riya",  10000, 2000),
            new CurrentAccount("Aman",  5000,  3000),
            new FixedDepositAccount("Priya", 50000)
        };
        for (Account acc : accounts) {
            System.out.printf("%s — rate: %.1f%%%n", acc, acc.interestRate());
        }
    }
}
