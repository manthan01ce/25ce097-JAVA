import java.util.Scanner;

/**
 * Practical 8 — Part A, Program 1
 * Guarded calculator: loops until a valid result; custom DivideByZeroException;
 * separate catch for invalid number input; finally logs each attempt.
 */
public class Calculator {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean done = false;

        System.out.println("=== Guarded Calculator ===");
        System.out.println("Enter: <number> <operator> <number>  (operators: + - * /)");

        while (!done) {
            System.out.print("\n> ");
            String line = sc.nextLine().trim();
            String[] parts = line.split("\\s+");

            try {
                if (parts.length != 3) throw new IllegalArgumentException("Need: num op num");

                double a  = Double.parseDouble(parts[0]);
                String op = parts[1];
                double b  = Double.parseDouble(parts[2]);

                if (op.equals("/") && b == 0) throw new DivideByZeroException();

                double result = switch (op) {
                    case "+" -> a + b;
                    case "-" -> a - b;
                    case "*" -> a * b;
                    case "/" -> a / b;
                    default  -> throw new IllegalArgumentException("Unknown operator: " + op);
                };

                System.out.println("Result: " + result);
                done = true;

            } catch (NumberFormatException e) {
                System.out.println("Invalid number: " + e.getMessage());
            } catch (DivideByZeroException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Bad input: " + e.getMessage());
            } finally {
                System.out.println("[LOG] attempt processed.");
            }
        }
        sc.close();
    }
}
