import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * The main logic part of the game, responding to input fromm the user, moving the human player and the bot player.
 */
public class GameLogic
{
	private Map map;
	private HumanPlayer player;
	private BotPlayer bot;
    private boolean quit;

	/**
     * Creates and configures the game with player, map and bot.
     * Sets the starting position of bot and player.
	 */
    public GameLogic() throws FileNotFoundException
    {
        player = new HumanPlayer();
	    chooseMap();
		bot = new BotPlayer();
		startPos();
	}

    /**
     * Runs the game once the players and map have been created.
     * Starts by allowing the bot to take a turn, then allows player to input their command.
     * Finally checks if the player has died yet.
     */
	public void run(){
        while(!quit) {
            botController();
            System.out.println("Bot: " + bot.getXPos() + " " + bot.getYPos());
            Action next = player.getNextAction();
            processInput(next);
            checkIfDead();
        }
    }

    /*
     * This controls the main functionality of the instance of <code>BotPlayer</code>.
     * It determines whether or not it can see the instance of <code>HumanPlayer</code>, based on if it's in a
     * radius of 2 around the bot. If it can't see the player (canSeePlayer == true), it alternates moving randomly and
     * looking for the player. Otherwise, it attempts to reach the player's last noted location (targetPos).
     */
    private void botController()
    {
        int YDist = bot.getYPos()-player.getYPos();
        int XDist = bot.getXPos()-player.getXPos();
        if(!bot.isCanSeePlayer())
        {
            if(bot.doBlindSwitch())
            {
                if((Math.abs(YDist) <= 2) && (Math.abs(XDist) <= 2))
                {
                    bot.changeCanSeePlayer();
                }
            }
            else
            {
                bot.moveRandom(map);
            }
        }
        else
        {
            if (!bot.getSearching())
            {
                bot.setTargetPos(player.getXPos(), player.getYPos());
                bot.switchSearching();
            }
            if(Math.abs(YDist) > Math.abs(XDist) && YDist < 0 && map.getPositionValue(bot.YPos+1, bot.XPos)!= '#')
            {
                bot.goSouth();
            }
            else if((Math.abs(YDist) > Math.abs(XDist)) && YDist > 0 && map.getPositionValue(bot.YPos-1, bot.XPos)!= '#')
            {
                bot.goNorth();
            }
            else if((Math.abs(XDist) >= Math.abs(YDist)) && XDist > 0 && map.getPositionValue(bot.YPos, bot.XPos-1)!= '#')
            {
                bot.goWest();
            }
            else if((Math.abs(XDist) >= Math.abs(YDist)) && XDist < 0 && map.getPositionValue(bot.YPos, bot.XPos+1)!= '#')
            {
                bot.goEast();
            }
        }
    }

    private void checkIfDead()
    {
        quit = (player.getXPos() == bot.getXPos()) && (player.getYPos() == bot.getYPos());
        if (quit)
        {
            System.out.println("DEAD");
            System.out.println("LOSE");
        }
    }

    private void chooseMap() throws FileNotFoundException
    {
        System.out.println("Select a map:");
        System.out.println("1: Small Dungeon of Doom.");
        System.out.println("2: Medium Dungeon of Disaster.");
        System.out.println("3: Large Dungeon of Despair.");
        int mapChoice;
        boolean valid = false;
        while(!valid)
        {
            Scanner scanner = new Scanner(System.in);
            mapChoice = scanner.nextInt();
            System.out.println(mapChoice);
            if(mapChoice == 1)
            {
                map = new Map("map1.txt", 9, 18, 2);
                valid = true;
            }
            else if(mapChoice == 2)
            {
                map = new Map("map2.txt", 13, 24, 4);
                valid = true;
            }
            else if(mapChoice == 3)
            {
                map = new Map("map3.txt", 30, 49, 5);
                valid = true;
            }
            else
            {
                System.out.println("Invalid map choice");
            }
        }
        System.out.println("Map Ready. You may begin the game.");
    }

    /*
     * Returns the gold currently owned by the player.
     */
    private void gold()
    {
        System.out.println("Gold owned: " + player.getPlayerGold());
    }

    /*
	 * Returns the gold required to win.
     */
    private void hello()
    {
        System.out.println("Gold to win: " + map.getGoldRequired());
    }

    /*
     * Converts the map from a 2D char array to a single string.
     */
    private void look()
    {
        char[][] miniMap = map.getMiniMap(player, bot);
        for(int i = 0; i < 4; i++)
        {
            System.out.println(miniMap[i]);
        }
    }

    /*
     * Checks if movement is legal and updates player's location on the map.
     */
    private void move(Direction direction)
    {
        int XPos =  player.getXPos();
        int YPos =  player.getYPos();
        if(direction == Direction.NORTH && map.getPositionValue(YPos-1, XPos) != '#')
        {
            player.goNorth();
            System.out.println("SUCCESS");
        }
        else if(direction == Direction.EAST && map.getPositionValue(YPos, XPos+1) != '#')
        {
            player.goEast();
            System.out.println("SUCCESS");
        }
        else if(direction == Direction.SOUTH && map.getPositionValue(YPos+1, XPos)!= '#')
        {
            player.goSouth();
            System.out.println("SUCCESS");
        }
        else if (direction == Direction.WEST && map.getPositionValue(YPos , XPos-1) != '#')
        {
            player.goWest();
            System.out.println("SUCCESS");
        }
        else
        {
            System.out.println("FAIL");
        }
        XPos = player.getXPos();
        YPos = player.getXPos();
    }

    private void pickup()
    {
        if(map.getPositionValue(player.getYPos(), player.getXPos()) == 'G')
        {
            player.collectGold();
            System.out.println("SUCCESS. Gold owned: " + player.getPlayerGold());
            map.updateMap(player.getYPos(), player.getXPos());
        }
        else
        {
            System.out.println("FAIL. Gold owned: " + player.getPlayerGold());
        }
    }

    private void processInput(Action action)
    {
        switch (action)
        {
            case HELLO :
                hello();
                break;
            case GOLD :
                gold();
                break;
            case PICKUP :
                pickup();
                break;
            case LOOK :
                look();
                break;
            case QUIT:
                quitGame();
                break;
            case MOVE_N :
                move(Direction.NORTH);
                break;
            case MOVE_E:
                move(Direction.EAST);
                break;
            case MOVE_S :
                move(Direction.SOUTH);
                break;
            case MOVE_W :
                move(Direction.WEST);
                break;
            default:
                System.out.println("Invalid");
        }
    }

    private void quitGame()
    {
        if((player.getPlayerGold() == map.getGoldRequired()) && (map.getPositionValue(player.getYPos(), player.getXPos()) == 'E'))
        {
            System.out.println("WIN");
            System.out.println("Congratulations!");
            quit = true;
        }
        else
        {
            System.out.println("LOSE");
            quit = true;
        }
    }

    private void startPos()
    {
        bot.setRandomPosition(map);
        player.setRandomPosition(map);
        while((map.getPositionValue(player.getYPos(), player.getXPos()) == 'G')
                || (map.getPositionValue(player.getYPos(), player.getXPos()) == '#')
                || ((player.getXPos() == bot.getXPos()) && player.getYPos() == bot.getYPos()))
        {
            player.setRandomPosition(map);
        }
    }

    /**
     * Create and instance of the game and run it
     *
     * @param args command line arguments
     *
     * @throws FileNotFoundException : avoids error if file is not found
     */
	public static void main(String[] args) throws FileNotFoundException
    {
		GameLogic logic = new GameLogic();
		logic.run();
    }
}