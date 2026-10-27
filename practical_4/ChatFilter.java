import java.util.Scanner;

public class ChatFilter {

    public static void main(String[] args) {
        String[] logs = {
            "10:05 alice Hello there",
            "10:07 bob How are you doing",
            "10:08 malformed line",
            "10:10 charlie Hello world",
            "10:12 alice Goodbye"
        };

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter keyword: ");
        String keyword = sc.nextLine().trim();

        int matchCount = 0;
        StringBuilder report = new StringBuilder();

        for (String line : logs) {
            String[] parts = line.split(" ", 3);
            if (parts.length < 3) continue;

            String time = parts[0];
            String user = parts[1];
            String message = parts[2];

            if (message.toLowerCase().contains(keyword.toLowerCase())) {
                matchCount++;
                report.append(time).append(" ").append(user).append(": ").append(message).append("\n");
            }
        }

        System.out.println("Matches: " + matchCount);
        if (matchCount > 0) System.out.print(report);

        sc.close();
    }
}
