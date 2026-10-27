public class MiniBank {

    public static void main(String[] args) {
        Account a1 = new Account("Riya", 5000);
        Account a2 = new Account("Aman", 3000);
        Account a3 = new Account("Priya");

        a1.deposit(2000);
        a1.withdraw(3000);

        System.out.println("=== Accounts (toString) ===");
        System.out.println(a1);
        System.out.println(a2);
        System.out.println(a3);

        System.out.println("\n=== equals test ===");
        System.out.println("a1.equals(a2): " + a1.equals(a2));
        System.out.println("a1.equals(a1): " + a1.equals(a1));

        System.out.println("\n=== instanceof check ===");
        Object obj = a1;
        System.out.println("obj instanceof Account: " + (obj instanceof Account));

        System.out.println("\n=== Customer with Address ===");
        Customer.Address addr = new Customer.Address("12 MG Road", "Anand", "388001");
        Customer c1 = new Customer("Riya", "riya@email.com", "9876543210", addr);
        System.out.println(c1);
        System.out.println("Address: " + c1.getAddress());

        Customer c2 = c1.clone();
        System.out.println("\nCloned customer: " + c2);
        System.out.println("c1 == c2: " + (c1 == c2));
    }
}
