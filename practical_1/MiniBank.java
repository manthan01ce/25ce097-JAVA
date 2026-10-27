import java.util.Scanner;

public class MiniBank {

    record BankInfo(String name, String branch) {
        @Override
        public String toString() {
            return "==============================\n"
                 + "  Bank  : " + name + "\n"
                 + "  Branch: " + branch + "\n"
                 + "==============================";
        }
    }

    enum MenuOption {
        OPEN_ACCOUNT,
        DEPOSIT,
        WITHDRAW,
        TRANSFER,
        EXIT
    }

    public static void main(String[] args) {
        BankInfo bank = new BankInfo("MiniBank", "Main Branch");
        System.out.println(bank);

        Scanner sc = new Scanner(System.in);
        boolean running = true;

        while (running) {
            printMenu();
            System.out.print("Enter choice: ");

            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.\n");
                continue;
            }

            MenuOption option;
            try {
                option = MenuOption.values()[choice - 1];
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Invalid choice. Please select 1–" + MenuOption.values().length + ".\n");
                continue;
            }

            switch (option) {
                case OPEN_ACCOUNT -> System.out.println("Open Account — to be implemented in a later lab.\n");
                case DEPOSIT      -> System.out.println("Deposit — to be implemented in a later lab.\n");
                case WITHDRAW     -> System.out.println("Withdraw — to be implemented in a later lab.\n");
                case TRANSFER     -> System.out.println("Transfer — to be implemented in a later lab.\n");
                case EXIT         -> {
                    System.out.println("Thank you for using MiniBank. Goodbye!");
                    running = false;
                }
            }
        }

        sc.close();
    }

    private static void printMenu() {
        System.out.println("--- MiniBank Menu ---");
        MenuOption[] options = MenuOption.values();
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ". " + options[i]);
        }
    }
}
