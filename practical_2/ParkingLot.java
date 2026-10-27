public class ParkingLot {

    private int twoWheelers;
    private int fourWheelers;
    private final int twoCap;
    private final int fourCap;
    private static long revenue = 0;

    public ParkingLot(int twoCap, int fourCap) {
        this.twoCap = twoCap;
        this.fourCap = fourCap;
        this.twoWheelers = 0;
        this.fourWheelers = 0;
    }

    public boolean park(String type) {
        switch (type) {
            case "two" -> {
                if (twoWheelers < twoCap) {
                    twoWheelers++;
                    revenue += 20;
                    return true;
                }
                System.out.println("Full — no two-wheeler spots left.");
                return false;
            }
            case "four" -> {
                if (fourWheelers < fourCap) {
                    fourWheelers++;
                    revenue += 40;
                    return true;
                }
                System.out.println("Full — no four-wheeler spots left.");
                return false;
            }
            default -> {
                System.out.println("Unknown type: " + type);
                return false;
            }
        }
    }

    public void leave(String type) {
        switch (type) {
            case "two"  -> { if (twoWheelers > 0) twoWheelers--; }
            case "four" -> { if (fourWheelers > 0) fourWheelers--; }
        }
    }

    public int getTwoWheelers() { return twoWheelers; }
    public int getFourWheelers() { return fourWheelers; }
    public static long getRevenue() { return revenue; }

    public static void main(String[] args) {
        ParkingLot lot = new ParkingLot(3, 2);

        System.out.println("Park two-wheeler: " + lot.park("two"));
        System.out.println("Park two-wheeler: " + lot.park("two"));
        System.out.println("Park four-wheeler: " + lot.park("four"));
        System.out.println("Park four-wheeler: " + lot.park("four"));
        System.out.println("Park four-wheeler: " + lot.park("four"));

        lot.leave("two");
        System.out.println("One two-wheeler left.");

        System.out.println("Park two-wheeler: " + lot.park("two"));
        System.out.println("Park two-wheeler: " + lot.park("two"));
        System.out.println("Park two-wheeler: " + lot.park("two"));

        System.out.println("\nTwo-wheelers parked: " + lot.getTwoWheelers());
        System.out.println("Four-wheelers parked: " + lot.getFourWheelers());
        System.out.println("Total revenue: " + ParkingLot.getRevenue());
    }
}
