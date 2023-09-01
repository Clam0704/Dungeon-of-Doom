import java.util.Random;

/**
 * An AI controlled player whose purpose is to find and chase the <code>HumanPlayer</code>.
 */
public class BotPlayer extends Player
{

    private boolean canSeePlayer = false;
    private boolean blindSwitch = false;
    private int targetXPos;
    private int targetYPos;
    private boolean searching = false;

    /**
     * Create a bot player.
     */
    public BotPlayer()
    {
        XPos = 1;
        YPos = 1;
        targetXPos = 0;
        targetYPos = 0;
    }

    /**
     * Move the bot in a random direction.
     *
     * @param map the map object with all attributes.
     */
    public void moveRandom(Map map)
    {
        Random randMove = new Random();
        int next = randMove.nextInt(4);
        if(next == 0 && map.getPositionValue(YPos-1, XPos) != '#')
        {
            goNorth();
        }
        else if(next == 1 && map.getPositionValue(YPos, XPos+1) != '#')
        {
            goEast();
        }
        else if(next == 2 && map.getPositionValue(YPos+1, XPos) != '#')
        {
            goSouth();
        }
        else if(next == 3 && map.getPositionValue(YPos, XPos-1) != '#')
        {
            goWest();
        }
    }

    public void changeCanSeePlayer()
    {
        canSeePlayer = !canSeePlayer;
    }

    /**
     * Toggles the blind switch to allow the bot to alternate its action each turn.
     *
     * @return the current state of blind switch
     */
    public boolean doBlindSwitch()
    {
        blindSwitch = !blindSwitch;
        return blindSwitch;
    }

    /**
     * @return true if the bot can see the human player and false otherwise.
     */
    public boolean isCanSeePlayer()
    {
        return canSeePlayer;
    }

    /**
     * @return true if the bot is chasing the human and false otherwise.
     */
    public boolean getSearching()
    {
        return searching;
    }

    /**
     * Sets the humman player's location as its target.
     *
     * @param playerX the current x position of the human player.
     * @param playerY the current y position of the human player.
     */
    public void setTargetPos(int playerX, int playerY)
    {
        targetXPos = playerX;
        targetYPos = playerY;
    }

    /**
     * Toggles whether the bot is currently tracking the human.
     */
    public void switchSearching()
    {
        searching = !searching;
    }
}
