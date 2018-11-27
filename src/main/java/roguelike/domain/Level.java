package roguelike.domain;

import java.util.ArrayList;

public class Level {
    public int number;
    private char[][] levelData;
    public Player player;
    public ArrayList<Zombie> zombies;

    private char[][] m_initialLevelData;

    /**
     * Creates a new level object
     * @param levelData Character data of the level
     * @param startY Starting Y location of the player in the level
     * @param startX Starting X location of the player in the level
     */
    public Level(char[][] levelData) {
        this.levelData = levelData;
        this.zombies = new ArrayList<>();

        // Copy level to a backup object for level resetting
        this.m_initialLevelData = new char[levelData.length][levelData[0].length];
        for (int y = 0; y < levelData.length; y++) {
            for (int x = 0; x < levelData[0].length; x++) {
                this.m_initialLevelData[y][x] = levelData[y][x];
            }
        }
        
        this.resetLevel();
    }

    /**
     * Resets the level to its initial state
     */
    public void resetLevel() {
        this.player = null;
        this.zombies.clear();

        // Copy values from backup object
        for (int y = 0; y < this.levelData.length; y++) {
            for (int x = 0; x < this.levelData[0].length; x++) {
                this.levelData[y][x] = this.m_initialLevelData[y][x];
            }
        }
        
        // Find units on the map and create them
        for (int y = 0; y < this.levelData.length; y++) {
            for (int x = 0; x < this.levelData[0].length; x++) {
                char tile = this.levelData[y][x];

                if (tile == '@' && this.player == null) {
                    this.player = new Player(y, x, this);
                } else if (tile == 'Z') {
                    this.zombies.add(new Zombie(y, x, this));
                }
            }
        }
    }

    /**
     * Sets a tile in the map to a new character
     * @param y Y coordinate of the tile
     * @param x X coordinate of the tile
     * @param c The character that the tile should be set to
     */
    public void setTile(int y, int x, char c) {
        if (isOutOfBounds(y, x)) {
            return;
        }

        this.levelData[y][x] = c;
    }

    /**
     * @param y Y coordinate of wanted tile
     * @param x X coordinate of wanted tile
     * @return Character at the wanted coordinates in the level's map
     */
    public char getTile(int y, int x) {
        if (isOutOfBounds(y, x)) {
            return 'N';
        }

        return this.levelData[y][x];
    }

    /**
     * Swaps two tiles in this level's map with each other
     * @param y1 First Y coordinate
     * @param x1 First X coordinate
     * @param y2 Second Y coordinate
     * @param x2 Second X coordinate
     */
    public void swapTiles(int y1, int x1, int y2, int x2) {
        if (isOutOfBounds(y1, x1) || isOutOfBounds(y2, x2)) {
            return;
        }

        char c = this.levelData[y1][x1];
        this.levelData[y1][x1] = this.levelData[y2][x2];
        this.levelData[y2][x2] = c;
    }

    /**
     * Checks whether a coordinate is out of bounds of the map
     * @param y Y coordinate
     * @param x X coodinate
     * @return True if the coordinate is out of bounds, false otherwise
     */
    public boolean isOutOfBounds(int y, int x) {
        return (y < 0 || y >= this.levelData.length || x < 0 || x >= this.levelData[0].length);
    }

    /**
     * @return The height of the level
     */
    public int getHeight() {
        return this.levelData.length;
    }

    /**
     * @return The width of the level
     */
    public int getWidth() {
        return (this.levelData.length == 0) ? 0 : this.levelData[0].length;
    }

    public char[] getRow(int row) {
        if (row < 0 || row >= this.levelData.length) {
            return new char[0];
        }

        return this.levelData[row];
    }
}