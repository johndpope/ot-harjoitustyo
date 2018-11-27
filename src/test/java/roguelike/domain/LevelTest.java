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
        {'#', '#', '#', '#'}
    };

    private char[][] playerMap = {
        {'#', '#', '#', '#'},
        {'#', '.', '.', '#'},
        {'#', '@', '.', '#'},
        {'#', '.', '.', '#'},
        {'#', '#', '#', '#'}
    };

    private char[][] zombieMap = {
        {'#', '#', '#', '#'},
        {'#', '.', 'Z', '#'},
        {'#', '.', '.', '#'},
        {'#', 'Z', '.', '#'},
        {'#', '#', '#', '#'}
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
    public void createsZombiesIfPresent() {
        Level level = new Level(this.zombieMap);
        assertEquals(level.zombies.size(), 2);
        assertEquals(level.zombies.get(0).y, 1);
        assertEquals(level.zombies.get(0).x, 2);
        assertEquals(level.zombies.get(1).y, 3);
        assertEquals(level.zombies.get(1).x, 1);
    }

    @Test
    public void zombiesAreNotCreatedIfNotPresent() {
        Level level = new Level(this.emptyMap);
        assertEquals(level.zombies.size(), 0);
    }

    @Test
    public void levelHeightIsCorrect() {
        Level level = new Level(this.emptyMap);
        assertEquals(level.getHeight(), 5);
    }

    @Test
    public void levelWidthIsCorrect() {
        Level level = new Level(this.emptyMap);
        assertEquals(level.getWidth(), 4);
    }

    @Test
    public void zombieIsRemovedIfPresent() {
        Level level = new Level(this.zombieMap);
        level.removeUnit(level.zombies.get(1));
        assertEquals(level.zombies.size(), 1);
        assertEquals(level.getTile(3, 1), '.');
    }

    @Test
    public void playerIsRemovedIfPresent() {
        Level level = new Level(this.playerMap);
        level.removeUnit(level.player);
        assertEquals(level.player, null);
        assertEquals(level.getTile(2, 1), '.');
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