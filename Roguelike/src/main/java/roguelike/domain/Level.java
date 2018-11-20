package roguelike.domain;

public class Level {
    public int number;
    public char[][] levelData;
    public int playerY;
    public int playerX;

    private char[][] initialLevelData;

    /**
     * Creates a new level object
     * @param levelData Character data of the level
     * @param startY Starting Y location of the player in the level
     * @param startX Starting X location of the player in the level
     */
    public Level(char[][] levelData) {
        this.levelData = levelData;

        // Copy level to a backup object for level resetting
        this.initialLevelData = new char[levelData.length][levelData[0].length];
        for (int y = 0; y < levelData.length; y++) {
            for (int x = 0; x < levelData[0].length; x++) {
                this.initialLevelData[y][x] = levelData[y][x];
            }
        }
        
        this.resetLevel();
    }

    /**
     * Resets the level to its initial state
     */
    public void resetLevel() {
        // Copy values from backup object
        for (int y = 0; y < this.levelData.length; y++) {
            for (int x = 0; x < this.levelData[0].length; x++) {
                this.levelData[y][x] = this.initialLevelData[y][x];
            }
        }
        
        // Find player's location
        for (int y = 0; y < this.levelData.length; y++) {
            for (int x = 0; x < this.levelData[0].length; x++) {
                if (this.levelData[y][x] == '@') {
                    this.playerY = y;
                    this.playerX = x;
                    return;
                }
            }
        }

        this.playerY = -1;
        this.playerX = -1;
    }
}