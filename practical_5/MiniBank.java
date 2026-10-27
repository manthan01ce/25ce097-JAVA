public class MiniBank {

    public static void main(String[] args) {
        Account[] accounts = {
            new SavingsAccount("Riya", 10000, 2000),
            new CurrentAccount("Aman", 5000, 3000),
            new FixedDepositAccount("Priya", 50000)
        };

        System.out.println("=== Account Interest Rates (Polymorphism) ===");
        for (Account acc : accounts) {
            System.out.printf("%s — Interest Rate: %.1f%%%n", acc, acc.interestRate());
        }

        System.out.println("\n=== Withdrawal Tests ===");
        System.out.println("Savings withdraw 9000 (would leave < minBalance): " + accounts[0].withdraw(9000));
        System.out.println("Savings withdraw 5000 (OK): " + accounts[0].withdraw(5000));
        System.out.println("Savings balance: " + accounts[0].getBalance());

        System.out.println("Current withdraw 7000 (uses overdraft): " + accounts[1].withdraw(7000));
        System.out.println("Current balance: " + accounts[1].getBalance());

        System.out.println("FD withdraw 1000 (locked): " + accounts[2].withdraw(1000));
        System.out.println("FD balance: " + accounts[2].getBalance());

        System.out.println("\n=== Pattern instanceof ===");
        for (Account acc : accounts) {
            if (acc instanceof SavingsAccount sa) {
                System.out.println(sa.getOwnerName() + " has a Savings Account with rate " + sa.interestRate() + "%");
            }
        }
    }
}
