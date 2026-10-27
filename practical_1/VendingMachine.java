import java.util.Scanner;

public class VendingMachine
{
    public enum Coin {
        ONE, TWO, FIVE, TEN
    }

    public static void main(String args[])
    {
        Scanner sc = new Scanner(System.in);
        int total=0;
        final int price=15;
        String selection;
        while(total<price)
        {
            System.out.println("Please enter coin name");
            selection=sc.nextLine().toUpperCase();
            Coin coin = Coin.valueOf(selection);
            int value = switch (coin) {
                case ONE  -> 1;
                case TWO  -> 2;
                case FIVE -> 5;
                case TEN  -> 10;
            };
            total += value;
            System.out.println("Total till now is: " + total);

        }
        System.out.println("Remaining Change is " + (total-price));
    }
}