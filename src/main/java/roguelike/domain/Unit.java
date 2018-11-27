package roguelike.domain;

import roguelike.util.Util;
import roguelike.util.Logger;

public class Unit {
    public int startY, startX;
    public int y, x;
    public int health;
    public int damage;
    public String name;
    public Level level;

    public Unit(int y, int x, String name, Level level) {
        this.y = y;
        this.x = x;
        this.startY = y;
        this.startX = x;
        this.level = level;
        this.name = name;

        // Default health and damage values
        this.health = 0;
        this.damage = 0;
    }

    /**
     * Moves this unit by specified amount, if possible
     * @param yDiff Amount the unit should be moved in y direction
     * @param xDiff Amount the unit should be moved in x direction
     * @return True if the move was successful, false otherwise
     */
    public boolean move(int yDiff, int xDiff) {
        Unit u = this.level.getUnitAtTile(this.y + yDiff, this.x + xDiff);
        if (u != null) {
            attack(u);
            return true;
        }

        if (!this.level.isValidUnitMove(this.y + yDiff, this.x + xDiff)) {
            return false;
        }

        this.level.swapTiles(this.y, this.x, this.y + yDiff, this.x + xDiff);
        this.y += yDiff;
        this.x += xDiff;
        return true;
    }

    /**
     * Attack a unit on another tile
     * @param u The unit that should be attacked
     * @return True if the attack went through, false otherwise
     */
    public boolean attack(Unit u) {
        if (Util.totalDistanceBetweenCoordinates(u.y, u.x, this.y, this.x) == 1) {
            u.health -= this.damage;
            Logger.log(this.name + " hit " + u.name + " for " + this.damage + " damage!");

            // Deth
            if (u.health <= 0) {
                Logger.log(u.name + " was killed!");
                this.level.removeUnit(u);
            }

            return true;
        } else {
            return false;
        }
    }
}