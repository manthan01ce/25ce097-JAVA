/**
 * Practical 6 — Part A, Program 1
 * Remote control: Switchable interface with Fan, Light;
 * functional interface for an "allowed to switch" rule as anonymous class & lambda.
 */
@FunctionalInterface
interface SwitchOnRule {
    boolean canSwitchOn(Switchable device, int hour);
}

public class SwitchableDriver {

    public static void main(String[] args) {
        // --- Part 1: toggle each device ---
        Switchable[] devices = { new Fan("Ceiling"), new Light("Bedroom"), new Fan("Table") };
        boolean[] states     = { false, false, false };

        System.out.println("=== Toggling devices ===");
        for (int i = 0; i < devices.length; i++) {
            devices[i].toggle(states[i]);   // all currently off → turn on
            states[i] = true;
        }
        System.out.println();
        for (int i = 0; i < devices.length; i++) {
            devices[i].toggle(states[i]);   // now on → turn off
        }

        // --- Part 2: functional interface — anonymous class ---
        System.out.println("\n=== SwitchOnRule (anonymous class) ===");
        SwitchOnRule rule1 = new SwitchOnRule() {
            @Override
            public boolean canSwitchOn(Switchable device, int hour) {
                // devices allowed between 6:00 and 22:00
                return hour >= 6 && hour < 22;
            }
        };
        System.out.println("Hour 8,  may switch on? " + rule1.canSwitchOn(devices[1], 8));
        System.out.println("Hour 23, may switch on? " + rule1.canSwitchOn(devices[1], 23));

        // --- Part 3: functional interface — lambda ---
        System.out.println("\n=== SwitchOnRule (lambda) ===");
        SwitchOnRule rule2 = (device, hour) -> hour >= 6 && hour < 22;
        System.out.println("Hour 14, may switch on? " + rule2.canSwitchOn(devices[0], 14));
        System.out.println("Hour 2,  may switch on? " + rule2.canSwitchOn(devices[0], 2));
    }
}
