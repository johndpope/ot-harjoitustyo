package roguelike.domain;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;

import roguelike.util.Util;

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
        {'#', '.', '#', '#'},
        {'#', '@', '.', '#'},
        {'#', '.', 'O', '#'},
        {'#', '#', '#', '#'}
    };

    private char[][] zombieMap = {
        {'#', '#', '#', '#'},
        {'#', '.', 'Z', '#'},
        {'#', '.', '.', '#'},
        {'#', 'Z', '.', '#'},
        {'#', '#', '#', '#'}
    };

    private char[][] mazeMap = {
        {'#', '#', '#', '#', '#'},
        {'#', '.', '#', '.', '#'},
        {'#', '.', '#', '.', '#'},
        {'#', '.', '.', '.', '#'},
        {'#', '#', '#', '#', '#'}
    };

    private char[][] impossibleMazeMap = {
        {'#', '#', '#', '#', '#'},
        {'#', '.', '#', '.', '#'},
        {'#', '.', '#', '.', '#'},
        {'#', '.', '#', '.', '#'},
        {'#', '#', '#', '#', '#'}
    };

    private char[][] zombiePlayerMap = {
        {'#', '#', '#', '#', '#'},
        {'#', '.', '.', 'Z', '#'},
        {'#', '.', '@', '.', '#'},
        {'#', ' ', '.', '.', '#'},
        {'#', '#', '#', '#', '#'}
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

    @Test
    public void pathFindingWorksWithValidPath() {
        Level level = new Level(this.mazeMap);
        ArrayList<Integer> path = level.findPathBetweenCoordinates(1, 1, 1, 3);

        assertEquals(path.size(), 6);
        assertEquals(path.get(0), (Integer)Util.getEncodedCoordinate(2, 1, level.getWidth()));
        assertEquals(path.get(1), (Integer)Util.getEncodedCoordinate(3, 1, level.getWidth()));
        assertEquals(path.get(2), (Integer)Util.getEncodedCoordinate(3, 2, level.getWidth()));
        assertEquals(path.get(3), (Integer)Util.getEncodedCoordinate(3, 3, level.getWidth()));
        assertEquals(path.get(4), (Integer)Util.getEncodedCoordinate(2, 3, level.getWidth()));
        assertEquals(path.get(5), (Integer)Util.getEncodedCoordinate(1, 3, level.getWidth()));
    }

    @Test
    public void pathNotFoundWithInvalidPath() {
        Level level = new Level(this.impossibleMazeMap);
        ArrayList<Integer> path = level.findPathBetweenCoordinates(1, 1, 1, 3);

        assertEquals(path.size(), 0);
    }

    @Test
    public void bombDoesNothingIfNoDetonator() {
        Level level = new Level(this.impossibleMazeMap);
        level.detonateBomb(1, 1, new Bomb(null), null);

        assertEquals(level.getTile(0, 0), '#');
    }

    @Test
    public void bombDestroysWalls() {
        Level level = new Level(this.playerMap);
        level.detonateBomb(2, 1, new Bomb(level.player), level.player);

        assertEquals(level.getTile(1, 2), '.');
        assertEquals(level.getTile(2, 0), ' ');
    }

    @Test
    public void bombDealsNoDamageToDetonator() {
        Level level = new Level(this.playerMap);

        int health = level.player.health;
        level.detonateBomb(2, 1, new Bomb(level.player), level.player);

        assertEquals(level.player.health, health);
    }

    @Test
    public void bombDealsDamageToOtherUnits() {
        Level level = new Level(this.zombiePlayerMap);

        assertEquals(level.zombies.size(), 1);

        int zombieHealth = level.zombies.get(0).health;
        int playerHealth = level.player.health;

        Bomb playerBomb = new Bomb(level.player);
        Bomb zombieBomb = new Bomb(level.zombies.get(0));
        level.detonateBomb(2, 2, playerBomb, level.player);
        assertEquals(level.zombies.get(0).health, zombieHealth - playerBomb.blastDamage);

        level.detonateBomb(1, 3, zombieBomb, level.zombies.get(0));
        assertEquals(level.player.health, playerHealth - zombieBomb.blastDamage);
    }

    @Test
    public void swappingTilesWorks() {
        Level level = new Level(this.playerMap);
        level.swapTiles(2, 1, 2, 2);
        
        assertEquals(level.getTile(2, 1), '.');
        assertEquals(level.getTile(2, 2), '@');
    }

    @Test
    public void noUnitIsFoundOnTileIfNotExist() {
        Level level = new Level(this.emptyMap);
        Unit u = level.getUnitAtTile(1, 1);

        assertEquals(u, null);
    }

    @Test
    public void unitIsFoundOnTileIfExist() {
        Level level = new Level(this.zombiePlayerMap);
        Unit p = level.getUnitAtTile(2, 2);
        Unit z = level.getUnitAtTile(1, 3);

        assertEquals(p.name, "Player");
        assertEquals(z.name, "Zombie 1");
    }

    @Test
    public void unitMoveValidationWorks() {
        Level level = new Level(this.zombiePlayerMap);
        
        assertFalse(level.isValidUnitMove(0, 0));
        assertFalse(level.isValidUnitMove(-1, -1));
        assertFalse(level.isValidUnitMove(level.getHeight(), 0));
        assertFalse(level.isValidUnitMove(2, 2));
        assertFalse(level.isValidUnitMove(1, 3));
        assertFalse(level.isValidUnitMove(3, 1));
        assertTrue(level.isValidUnitMove(1, 1));
    }

    @Test
    public void gettingRowWorks() {
        Level level = new Level(this.playerMap);
        char[] rowInvalid1 = level.getRow(-1);
        char[] rowInvalid2 = level.getRow(level.getHeight());
        char[] middleRow = level.getRow(2);

        assertEquals(rowInvalid1.length, 0);
        assertEquals(rowInvalid2.length, 0);
        
        assertEquals(middleRow[0], '#');
        assertEquals(middleRow[1], '@');
        assertEquals(middleRow[2], '.');
        assertEquals(middleRow[3], '#');
    }

    @Test
    public void exitNotFoundIfNotExist() {
        Level level = new Level(this.playerMap);
        assertFalse(level.isExitAtTile(1, 1));
        assertFalse(level.isExitAtTile(0, 0));
        assertFalse(level.isExitAtTile(-1, -1));
        assertFalse(level.isExitAtTile(level.getHeight(), level.getWidth()));
    }

    @Test
    public void exitFoundIfExist() {
        Level level = new Level(this.playerMap);
        assertTrue(level.isExitAtTile(3, 2));
    }
}