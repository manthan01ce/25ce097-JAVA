/**
 * Practical 6 — Part A, Program 2
 * Notification senders: functional interface Notifier; email and SMS as lambdas;
 * marker interface Urgent — urgent senders deliver twice.
 */
public class NotifierDriver {

    // Email notifier that is also Urgent (anonymous class implementing both interfaces)
    static class UrgentEmailNotifier implements Notifier, Urgent {
        @Override
        public void send(String message) {
            System.out.println("[EMAIL] " + message);
        }
    }

    public static void main(String[] args) {
        // lambdas for standard senders
        Notifier email = msg -> System.out.println("[EMAIL] " + msg);
        Notifier sms   = msg -> System.out.println("[SMS]   " + msg);

        // urgent sender via anonymous class
        Notifier urgentEmail = new UrgentEmailNotifier();

        Notifier[] senders = { email, sms, urgentEmail };

        String message = "Your OTP is 483920";

        System.out.println("=== Broadcasting notification ===");
        for (Notifier sender : senders) {
            sender.send(message);
            if (sender instanceof Urgent) {
                // urgent senders deliver twice
                sender.send(message);
            }
        }
    }
}
