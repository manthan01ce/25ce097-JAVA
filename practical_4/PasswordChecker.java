public class PasswordChecker {

    public static String strength(String pw) {
        int score = 0;
        if (pw.length() >= 8) score++;
        if (pw.matches(".*[A-Z].*")) score++;
        if (pw.matches(".*[0-9].*")) score++;
        if (pw.matches(".*[^a-zA-Z0-9].*")) score++;

        return switch (score) {
            case 0, 1 -> "Weak";
            case 2, 3 -> "Medium";
            case 4    -> "Strong";
            default   -> "Unknown";
        };
    }
}
