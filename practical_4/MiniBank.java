public class MiniBank {

    public static void main(String[] args) {
        System.out.println("=== Validator Tests ===");
        System.out.println("Mobile 9876543210: " + Validator.isValidMobile("9876543210"));
        System.out.println("Mobile 12345:      " + Validator.isValidMobile("12345"));
        System.out.println("Email abc@xyz.com:  " + Validator.isValidEmail("abc@xyz.com"));
        System.out.println("Email not-an-email: " + Validator.isValidEmail("not-an-email"));
        System.out.println("PAN ABCDE1234F:     " + Validator.isValidPan("ABCDE1234F"));
        System.out.println("PAN 12345:          " + Validator.isValidPan("12345"));
        System.out.println("IFSC SBIN0001234:   " + Validator.isValidIfsc("SBIN0001234"));
        System.out.println("IFSC INVALID:       " + Validator.isValidIfsc("INVALID"));

        System.out.println("\n=== Command Parser ===");
        Command cmd = CommandParser.parse("DEPOSIT AC0001 500");
        System.out.println("Type:    " + cmd.type());
        System.out.println("Account: " + cmd.accountNumber());
        System.out.println("Amount:  " + cmd.amount());

        System.out.println("\n=== Statement Formatter ===");
        Account acc = new Account("Riya", 5000);
        acc.deposit(2000);
        acc.withdraw(3000);
        System.out.print(StatementFormatter.buildStatement(acc));
    }
}
