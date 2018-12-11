package roguelike.dao;

import roguelike.domain.Level;

/**
 * Interface for a level loader class
 */
public interface LevelDao {
    /**
     * Loads a level from disk from a file with name levelName and returns a
     * new Level instance with the contents of the loaded level
     * @param level The number of the level that should be loaded
     * @return A Level class instance if level file was found and it contained level data. Otherwise null.
     * @throws Exception If an exception occurs while loading the level
     */
    Level loadLevel(int level) throws Exception;
}