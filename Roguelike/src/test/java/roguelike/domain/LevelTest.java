package roguelike.domain;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class LevelTest {
    private char[][] emptyMap = {
        {'#', '#', '#', '#'},
        {'#', '.', '.', '#'},
        {'#', '.', '.', '#'},
        {'#', '.', '.', '#'},
        {'#', '#', '#', '#'},
    };

    private char[][] playerMap = {
        {'#', '#', '#', '#'},
        {'#', '.', '.', '#'},
        {'#', '@', '.', '#'},
        {'#', '.', '.', '#'},
        {'#', '#', '#', '#'},
    };

    @Test
    public void setsPlayerCoordinatesIfPlayerIsPresent() {
        Level level = new Level(this.playerMap);
        assertEquals(level.playerX, 1);
        assertEquals(level.playerY, 2);
    }

    @Test
    public void playerCoordinatesAreNotSetIfPlayerIsMissing() {
        Level level = new Level(this.emptyMap);
        assertEquals(level.playerX, -1);
        assertEquals(level.playerY, -1);
    }

    @Test
    public void resetLevelResetsLevelToInitialState() {
        Level level = new Level(this.playerMap);
        level.levelData[0][0] = '.';
        level.levelData[1][2] = '.';
        level.levelData[3][2] = '@';
        level.resetLevel();

        assertEquals(level.playerX, 1);
        assertEquals(level.playerY, 2);
        assertEquals(level.levelData[0][0], '#');
    }
}