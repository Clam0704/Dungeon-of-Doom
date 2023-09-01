import java.io.*;
import java.util.Scanner;

/**
 * Initialises, reads in and contains in memory the map of the game.
 */
public class Map {

    /* Representation of the map */
    private char[][] map;
    private int mapLength;
    private int mapHeight;

    /* Gold required for the human player to win */
    private int goldRequired;

    /**
     * Creates the map as a 2D array based on
     *
     * @param fileName     the name of the file being used.
     * @param mapHeight    the height of the requested map.
     * @param mapLength    the length of the requested map.
     * @param goldRequired the gold required for the user to win.
     * @throws FileNotFoundException if requested file isn't found.
     */
    public Map(String fileName, int mapHeight, int mapLength, int goldRequired) throws FileNotFoundException {
        this.mapLength = mapLength;
        this.mapHeight = mapHeight;
        this.goldRequired = goldRequired;
        map = new char[mapLength+1][mapHeight+1];
        map = readMap(fileName);
        for(int i=0; i< mapHeight; i++)
        {
            System.out.println(map[i]);
        }
    }

    /**
     * @return the amount of gold required to exit the current map.
     */
    public int getGoldRequired() {
        return goldRequired;
    }

    /**
     * @return the height of map.
     */
    public int getMapHeight() {
        return mapHeight;
    }

    /**
     * @return the length of map.
     */
    public int getMapLength() {
        return mapLength;
    }

    /**
     * Generates a 5 by 5 mini-map surrounding the player's current location which included showing the position of
     * the bot.
     *
     * @param player human player.
     * @param bot    bot player.
     */
    public char[][] getMiniMap(HumanPlayer player, BotPlayer bot) {
        char[][] miniMap = new char[5][5];
        int XVal = player.getXPos();
        int YVal = player.getYPos();
        int botXVal = bot.getXPos();
        int botYVal = bot.getYPos();
        System.out.println("x = " + XVal + ", y = " + YVal);
        int i, j = 0;
        for (int y = YVal - 2; y <= YVal + 2; y++) {
            i = 0;
            for (int x = XVal - 2; x <= XVal + 2; x++) {
                if ((x < 0) || (y < 0) || (x >= mapLength) || (y >= mapHeight)) {
                    miniMap[j][i] = '#';
                } else {
                    if (y == YVal && x == XVal) {
                        miniMap[j][i] = 'P';
                    } else if (y == botYVal && x == botXVal) {
                        miniMap[j][i] = 'B';
                    } else {
                        miniMap[j][i] = map[y][x];
                    }
                }
                i++;
            }
            j++;
        }
        return miniMap;
    }

    /**
     * Gets the char value from the map at the x and y positions provided.
     *
     * @param XPos x position of the value on the map
     * @param YPos y position of the value to check
     *
     * @return : The value at the specific location in the map.
     */
    public char getPositionValue(int YPos, int XPos) {
        return map[YPos][XPos];
    }

    /**
     * Reads the map from file and converts it to a 2D array.
     *
     * @param fileName: Name of the map's file.
     * @return map: the map as a 2D array
     */
    private char[][] readMap(String fileName) throws FileNotFoundException {
        int totalRow = mapHeight+1;
        int totalColumn = mapLength+1;
        char[][] map = new char[totalRow][totalColumn];
        File file = new File(fileName);
        Scanner scanner = new Scanner(file);
        for (int row = 0; row < totalRow; row++) {
            if (scanner.hasNextLine()) {
                char[] chars = scanner.nextLine().toCharArray();
                for (int i = 0; i < totalColumn && i < chars.length; i++) {
                    map[row][i] = chars[i];
                }
            }
        }
        return map;
    }

    /**
     * Replaces the character at the map location with a '.'. This is used when gold is pccked up.
     *
     * @param x x position on the map.
     * @param y y position on the map.
     */
    public void updateMap(int x, int y) {
        map[x][y] = '.';
    }


}
