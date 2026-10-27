import java.util.Random;
import java.util.Scanner;

public class RPSLS {

    enum Move { ROCK, PAPER, SCISSORS, LIZARD, SPOCK }

    /**
     * Returns  1 if a beats b,
     *         -1 if b beats a,
     *          0 for a tie.
     *
     * Rules:
     *   Scissors cuts  Paper
     *   Paper    covers Rock
     *   Rock     crushes Lizard
     *   Lizard   poisons Spock
     *   Spock    smashes Scissors
     *   Scissors decapitates Lizard
     *   Lizard   eats    Paper
     *   Paper    disproves Spock
     *   Spock    vaporizes Rock
     *   Rock     crushes Scissors
     */
    static int winner(Move a, Move b) {
        if (a == b) return 0;

        boolean aWins = switch (a) {
            case SCISSORS -> b == Move.PAPER    || b == Move.LIZARD;
            case PAPER    -> b == Move.ROCK     || b == Move.SPOCK;
            case ROCK     -> b == Move.LIZARD   || b == Move.SCISSORS;
            case LIZARD   -> b == Move.SPOCK    || b == Move.PAPER;
            case SPOCK    -> b == Move.SCISSORS || b == Move.ROCK;
        };

        return aWins ? 1 : -1;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random rng = new Random();
        Move[] moves = Move.values();

        int playerScore = 0, computerScore = 0;
        final int ROUNDS = 5;

        System.out.println("=== Rock-Paper-Scissors-Lizard-Spock ===");
        System.out.println("Moves: ROCK, PAPER, SCISSORS, LIZARD, SPOCK\n");

        for (int round = 1; round <= ROUNDS; round++) {
            Move computer = moves[rng.nextInt(moves.length)];

            System.out.print("Round " + round + " — Your move: ");
            String input = sc.nextLine().trim().toUpperCase();

            Move player;
            try {
                player = Move.valueOf(input);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid move. Skipping round.");
                continue;
            }

            int result = winner(player, computer);
            System.out.println("  Computer played: " + computer);

            if (result == 1) {
                System.out.println("  You win this round!");
                playerScore++;
            } else if (result == -1) {
                System.out.println("  Computer wins this round!");
                computerScore++;
            } else {
                System.out.println("  It's a tie!");
            }
        }

        System.out.println("\n=== Final Score ===");
        System.out.println("You: " + playerScore + " | Computer: " + computerScore);

        if (playerScore > computerScore)
            System.out.println("You win " + playerScore + "–" + computerScore + "!");
        else if (computerScore > playerScore)
            System.out.println("Computer wins " + computerScore + "–" + playerScore + "!");
        else
            System.out.println("It's a draw " + playerScore + "–" + computerScore + "!");

        sc.close();
    }
}
