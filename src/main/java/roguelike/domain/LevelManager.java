package roguelike.domain;

import java.util.HashMap;

import roguelike.dao.FileLevelDao;

public class LevelManager {
    private Level currentLevel;
    private int currentLevelNumber;
    private FileLevelDao fileLevelDao;
    private HashMap<Integer, Level> levels;

    public LevelManager() {
        this.currentLevelNumber = 1;
        this.fileLevelDao = new FileLevelDao();
        this.levels = new HashMap<>();

        this.initLevels();
    }

    /**
     * @return Level instance of the level the player is on
     */
    public Level getCurrentLevel() {
        return this.currentLevel;
    }

    /**
     * Changes the current level
     * @param level The level that should be changed to
     */
    public void changeLevel(int level) {
        if (!this.levels.keySet().contains(level)) {
            return;
        }

        this.currentLevelNumber = level;
        this.currentLevel = this.levels.get(level);
    }

    /**
     * Loads and stores all levels of the game
     */
    private void initLevels() {
        this.levels.put(1, fileLevelDao.loadLevel(1));

        this.currentLevel = this.levels.get(this.currentLevelNumber);
    }

    /**
     * Moves the player in the game world
     * @param yDiff The amount the player should be moved in the y position
     * @param xDiff The amount the player should be moved in the x position
     */
    public boolean movePlayer(int yDiff, int xDiff) {
        if (this.currentLevel == null) {
            return false;
        }

        return this.currentLevel.player.move(yDiff, xDiff);
    }
}