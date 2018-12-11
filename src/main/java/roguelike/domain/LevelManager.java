package roguelike.domain;

import java.util.HashMap;

import roguelike.util.Logger;

import roguelike.dao.FileLevelDao;

public class LevelManager {
    private Level currentLevel;
    private int currentLevelNumber;
    private FileLevelDao fileLevelDao;
    private HashMap<Integer, Level> levelMap;
    private Runnable onLevelChangeFunc;

    public LevelManager() {
        this.currentLevelNumber = 1;
        this.fileLevelDao = new FileLevelDao();
        this.levelMap = new HashMap<>();
        this.onLevelChangeFunc = null;

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
        if (!this.levelMap.keySet().contains(level)) {
            return;
        }

        this.currentLevelNumber = level;
        this.currentLevel = this.levelMap.get(level);
    }

    /**
     * Adds an event listener for when the level is changed
     * @param cb Callback function to be called when this event fires
     */
    public void onLevelChange(Runnable cb) {
        this.onLevelChangeFunc = cb;
    }

    /**
     * Loads and stores all levels of the game
     */
    private void initLevels() {
        this.levelMap.put(1, fileLevelDao.loadLevel(1));
        this.levelMap.get(1).onLevelExit(() -> {
            this.levelMap.get(2).player.setArmor(this.levelMap.get(1).player.armor);
            this.levelMap.get(2).player.setWeapon(this.levelMap.get(1).player.weapon);
            this.changeLevel(2);
            if (this.onLevelChangeFunc != null) {
                this.onLevelChangeFunc.run();
            }
        });

        this.levelMap.put(2, fileLevelDao.loadLevel(2));
        this.levelMap.get(2).onLevelExit(() -> {
            Logger.log("You finished the game! Press any arrow key to exit...");
            this.levelMap.get(2).player = null;
        });

        this.currentLevel = this.levelMap.get(this.currentLevelNumber);
    }

    /**
     * Moves the player in the game world
     * @param yDiff The amount the player should be moved in the y position
     * @param xDiff The amount the player should be moved in the x position
     */
    public boolean movePlayer(int yDiff, int xDiff) {
        if (this.currentLevel == null || this.currentLevel.player == null) {
            return false;
        }

        return this.currentLevel.player.move(yDiff, xDiff);
    }

    /**
     * Uses a player's bomb if the player has bombs
     * @return True if using the bomb was successful, false otherwise
     */
    public boolean useBomb() {
        if (this.currentLevel == null) {
            return false;
        }

        return this.currentLevel.player.useBomb();
    }
}