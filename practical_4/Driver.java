public class Driver {

    public static void main(String[] args) {
        System.out.println("=== Password Strength ===");
        String[] passwords = {"abc", "Abcd1234!", "Hello123", "p@ss", "Str0ng!Pass"};
        for (String pw : passwords) {
            System.out.println("\"" + pw + "\" -> " + PasswordChecker.strength(pw));
        }
    }
}
