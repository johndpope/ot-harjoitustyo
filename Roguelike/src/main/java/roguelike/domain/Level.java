package roguelike.domain;

public class Level {
    public int number;
    public char[][] levelData;
    public int playerY;
    public int playerX;

    private char[][] initialLevelData;

    /**
     * Creates a new level object
     * @param number This level's number
     * @param levelData Character data of the level
     * @param startY Starting Y location of the player in the level
     * @param startX Starting X location of the player in the level
     */
    public Level(int number, char[][] levelData) {
        this.number = number;
        this.initialLevelData = levelData;
        this.resetLevel();
    }

    /**
     * Resets the level to its initial state
     */
    public void resetLevel() {
        this.levelData = this.initialLevelData;
        
        for (int y = 0; y < this.levelData.length; y++) {
            for (int x = 0; x < this.levelData[0].length; x++) {
                if (this.levelData[y][x] == '@') {
                    this.playerY = y;
                    this.playerX = x;
                    return;
                }
            }
        }
    }
}