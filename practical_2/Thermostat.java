public class Thermostat {

    private String location;
    private int temperature;
    private static final int MIN = 16;
    private static final int MAX = 30;
    private static int activeCount = 0;

    // Constructor with location and starting temperature
    public Thermostat(String location, int startTemp) {
        this.location = location;
        this.temperature = (startTemp >= MIN && startTemp <= MAX) ? startTemp : 22;
        activeCount++;
    }

    // Constructor with just location — chains to the full constructor
    public Thermostat(String location) {
        this(location, 22);
    }

    public void raise() {
        if (temperature < MAX) {
            temperature++;
        } else {
            System.out.println("[" + location + "] Already at maximum (" + MAX + ")");
        }
    }

    public void lower() {
        if (temperature > MIN) {
            temperature--;
        } else {
            System.out.println("[" + location + "] Already at minimum (" + MIN + ")");
        }
    }

    public int getTemperature() {
        return temperature;
    }

    public static int getActiveCount() {
        return activeCount;
    }

    public String getLocation() {
        return location;
    }

    public static void main(String[] args) {
        Thermostat living = new Thermostat("Living Room", 24);
        Thermostat bedroom = new Thermostat("Bedroom");   // defaults to 22

        System.out.println("=== Raising Living Room temperature 10 times ===");
        for (int i = 0; i < 10; i++) {
            living.raise();
            System.out.println(living.getLocation() + ": " + living.getTemperature());
        }

        System.out.println("\n=== Lowering Living Room temperature 20 times ===");
        for (int i = 0; i < 20; i++) {
            living.lower();
            System.out.println(living.getLocation() + ": " + living.getTemperature());
        }

        System.out.println("\nActive thermostat count: " + Thermostat.getActiveCount());
    }
}
