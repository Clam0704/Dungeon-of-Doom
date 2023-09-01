import java.util.Scanner;
import java.util.Random;

/**
 * Represents a human controlled and contains code needed to read inputs.
 * It also sets the player up at a random point, and allows it to move.
 */
public class HumanPlayer extends Player
{
    private int playerGold;

    /**
     * Creates a human controlled player with player gold set to 0.
     */
    public HumanPlayer()
    {
       playerGold = 0;
    }

    /**
     * Increments the player gold by one.
     */

    public void collectGold()
    {
        playerGold++;
    }

    /**
     * Gets input from the console and converts it to an action.
     *
     * @return an <code>Action</code> matching the input or <code<INVALID</code> otherwise.
     */
    public Action getNextAction()
    {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine().toUpperCase();
        switch(input)
        {
            case "HELLO":
                return Action.HELLO;
            case "GOLD":
                return Action.GOLD;
            case "PICKUP":
                return Action.PICKUP;
            case "LOOK":
                return Action.LOOK;
            case "QUIT":
                return Action.QUIT;
            case "MOVE N":
                return Action.MOVE_N;
            case "MOVE E":
                return Action.MOVE_E;
            case "MOVE S":
                return Action.MOVE_S;
            case "MOVE W":
                return Action.MOVE_W;
            default:
                return Action.INVALID;
        }
    }

    /**
     * @return the current gold that human player has.
     */
    public int getPlayerGold()
    {
        return playerGold;
    }
}