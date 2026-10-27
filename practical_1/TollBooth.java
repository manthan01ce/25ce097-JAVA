import java.util.Scanner;

public class TollBooth {

    record Vehicle(String number, String type) {}

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int totalToll = 0;
        int bikeCount = 0, carCount = 0, truckCount = 0;

        while (true) {
            System.out.print("Enter vehicle number (or 'done' to stop): ");
            String number = sc.nextLine().trim();
            if (number.equalsIgnoreCase("done")) break;

            System.out.print("Enter vehicle type (bike/car/truck): ");
            String type = sc.nextLine().trim().toLowerCase();

            Vehicle v = new Vehicle(number, type);

            int toll = switch (v.type()) {
                case "bike"  -> 20;
                case "car"   -> 50;
                case "truck" -> 150;
                default -> {
                    System.out.println("Unknown vehicle type: " + v.type() + ". Skipping.");
                    yield 0;
                }
            };

            if (toll > 0) {
                totalToll += toll;
                switch (v.type()) {
                    case "bike"  -> bikeCount++;
                    case "car"   -> carCount++;
                    case "truck" -> truckCount++;
                }
                System.out.println("Toll collected: " + toll + " | Running total: " + totalToll);
            }
        }

        System.out.println("\nTotal toll collected: " + totalToll);

        // Find most frequent vehicle type
        int maxCount = Math.max(bikeCount, Math.max(carCount, truckCount));
        if (maxCount == 0) {
            System.out.println("No vehicles processed.");
        } else {
            String mostFrequent;
            if (maxCount == bikeCount)       mostFrequent = "bike";
            else if (maxCount == carCount)   mostFrequent = "car";
            else                              mostFrequent = "truck";
            System.out.println("Most frequent: " + mostFrequent);
        }

        sc.close();
    }
}
