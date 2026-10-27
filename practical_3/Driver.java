public class Driver {

    public static void main(String[] args) {
        System.out.println("=== Distinct Points ===");
        Point[] points = {
            new Point(1, 2),
            new Point(3, 4),
            new Point(1, 2),
            new Point(5, 6),
            new Point(3, 4)
        };

        int distinct = 0;
        for (int i = 0; i < points.length; i++) {
            boolean duplicate = false;
            for (int j = 0; j < i; j++) {
                if (points[i].equals(points[j])) {
                    duplicate = true;
                    break;
                }
            }
            if (!duplicate) distinct++;
        }
        System.out.println("Distinct: " + distinct);

        System.out.println("\n=== Duplicate Card Check ===");
        Card[] cards = new Card[5];
        String[][] data = {
            {"Ace", "Spades"},
            {"King", "Hearts"},
            {"Queen", "Diamonds"},
            {"Ace", "Spades"},
            {"Ten", "Clubs"}
        };

        int count = 0;
        for (String[] d : data) {
            Card c = new Card(d[0], d[1]);
            boolean found = false;
            for (int j = 0; j < count; j++) {
                if (c.equals(cards[j])) {
                    System.out.println("Duplicate found: " + c);
                    found = true;
                    break;
                }
            }
            if (!found) {
                cards[count++] = c;
            }
        }

        System.out.println("\n=== Fractions ===");
        Fraction f1 = new Fraction(1, 2);
        Fraction f2 = new Fraction(2, 4);
        Fraction f3 = new Fraction(3, 6);

        System.out.println("f1 = " + f1);
        System.out.println("f2 = " + f2);
        System.out.println("f3 = " + f3);
        System.out.println("f1.equals(f2): " + f1.equals(f2));
        System.out.println("f1.equals(f3): " + f1.equals(f3));
    }
}
