package roguelike.domain;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class UnitTest {
    private char[][] playerZombieMap = {
        {'#', '#', '#', '#'},
        {'#', '.', '@', '#'},
        {'#', '.', 'Z', '#'},
        {'#', '.', '.', '#'},
        {'#', '#', '#', '#'}
    };

    private char[][] exitMap = {
        {'#', '#', '#', '#'},
        {'#', '.', '@', '#'},
        {'#', '.', 'O', '#'},
        {'#', '.', '.', '#'},
        {'#', '#', '#', '#'}
    };

    private char[][] bombMap = {
        {'#', '#', '#', '#'},
        {'#', '.', '.', '#'},
        {'#', '.', '@', '#'},
        {'#', '.', 'B', '#'},
        {'#', '#', '#', '#'}
    };

    @Test
    public void unitDoesNotMoveIfInvalidMove() {
        Level level = new Level(this.playerZombieMap);
        level.player.move(-1, 0);

        assertEquals(level.player.y, 1);
        assertEquals(level.player.x, 2);
        assertEquals(level.getTile(1, 2), '@');
    }

    @Test
    public void unitMovesIfValidMove() {
        Level level = new Level(this.playerZombieMap);
        level.player.move(0, -1);

        assertEquals(level.player.y, 1);
        assertEquals(level.player.x, 1);
        assertEquals(level.getTile(1, 1), '@');
        assertEquals(level.getTile(1, 2), '.');
    }

    @Test
    public void unitAttacksOtherUnit() {
        Level level = new Level(this.playerZombieMap);

        assertEquals(level.zombies.size(), 1);
        Zombie z = level.zombies.get(0);
        int zombieHealth = z.health;

        level.player.move(1, 0);

        assertEquals(level.player.y, 1);
        assertEquals(level.player.x, 2);
        assertEquals(z.health, zombieHealth - level.player.damage);
    }

    @Test
    public void usingBombWorks() {
        Level level = new Level(this.playerZombieMap);
        Player p = level.player;
        
        assertEquals(p.bombs.size(), 0);
        p.useBomb();
        assertEquals(p.bombs.size(), 0);
        p.addBomb();
        assertEquals(p.bombs.size(), 1);
        p.useBomb();
        assertEquals(p.bombs.size(), 0);
        assertEquals(level.getTile(0, 2), ' ');
    }

    @Test
    public void takingHitWorks() {
        Level level = new Level(this.playerZombieMap);
        Player p = level.player;

        int playerHealth = p.health;
        p.takeHit(1, "");
        assertEquals(p.health, playerHealth - 1);
        p.takeHit(playerHealth, "");
        assertEquals(p.health, -1);
        assertEquals(level.player, null);
    }

    @Test
    public void pickingUpItemWorks() {
        Level level = new Level(this.bombMap);

        assertEquals(level.player.bombs.size(), 0);
        level.player.move(1, 0);
        assertEquals(level.player.bombs.size(), 1);
    }
}