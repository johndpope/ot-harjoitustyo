package roguelike.domain;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

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
        assertNotEquals(level.player, null);
        assertEquals(level.player.x, 1);
        assertEquals(level.player.y, 2);
    }

    @Test
    public void playerIsNotCreatedIfPlayerIsMissing() {
        Level level = new Level(this.emptyMap);
        assertEquals(level.player, null);
    }

    @Test
    public void resetLevelResetsLevelToInitialState() {
        Level level = new Level(this.playerMap);
        level.setTile(0, 0, '.');
        level.setTile(1, 2, '.');
        level.setTile(3, 2, '@');
        level.resetLevel();

        assertNotEquals(level.player, null);
        assertEquals(level.player.x, 1);
        assertEquals(level.player.y, 2);
        assertEquals(level.getTile(0, 0), '#');
    }
}