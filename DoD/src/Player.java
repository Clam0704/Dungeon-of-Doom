import java.util.Random;

/**
 * Base class for all player classes.
 */
public abstract class Player {
    protected int XPos = 0;
    protected int YPos = 0;

    /**
     * @return current X position of player
     */
    public int getXPos()
    {
        return XPos;
    }

    /**
     * @return current Y position of player
     */
    public int getYPos()
    {
        return YPos;
    }

    /**
     * Increment the x position by 1 - moving East
     */
    public void goEast()
    {
        XPos++;
    }

    /**
     * Decrement the y position by 1 - moving North
     */
    public void goNorth()
    {
        YPos--;
    }

    /**
     * Increment the y position by 1 - moving South
     */
    public void goSouth()
    {
        YPos++;
    }

    /**
     * Decrement the x pos by 1 - moving West
     */
    public void goWest()
    {
        XPos--;
    }

    /**
     * Sets the random start position of a player.
     *
     * @param map map that the players will be placed in
     */
    public void setRandomPosition(Map map)
    {
        Random randX;
        Random randY;
        do {
            randX = new Random();
            randY = new Random();
            XPos = randX.nextInt(map.getMapLength());
            YPos = randY.nextInt(map.getMapHeight());
        } while(XPos == 0 || YPos == 0);
    }
}
