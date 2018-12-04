package roguelike.domain;

import java.util.ArrayList;

import roguelike.util.Util;
import roguelike.util.Logger;

public class Unit {
    public int startY, startX;
    public int y, x;
    public int health;
    public int damage;
    public int armor;
    public String name;
    public Level level;
    public ArrayList<Bomb> bombs;
    private Runnable onStatsChangeFunc;

    public Unit(int y, int x, String name, Level level) {
        this.y = y;
        this.x = x;
        this.startY = y;
        this.startX = x;
        this.level = level;
        this.name = name;
        this.bombs = new ArrayList<>();
        this.onStatsChangeFunc = null;

        // Default values
        this.health = 0;
        this.damage = 0;
        this.armor = 0;
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

        if (this.level.isExitAtTile(this.y + yDiff, this.x + xDiff) && this.name.equals("Player")) {
            this.level.exitLevel();
            return true;
        }

        if (!this.level.isValidUnitMove(this.y + yDiff, this.x + xDiff)) {
            return false;
        }

        this.level.swapTiles(this.y, this.x, this.y + yDiff, this.x + xDiff);
        this.checkForItemPickup(this.y, this.x);

        this.y += yDiff;
        this.x += xDiff;
        return true;
    }

    /**
     * Checks for possible item pickups on a tile
     * @param y Y coordinate of the tile to be checked
     * @param x X coordinate of the tile to be checked
     */
    private void checkForItemPickup(int y, int x) {
        char tile = this.level.getTile(y, x);
        if (tile == 'B') {
            this.addBomb();
            level.setTile(y, x, '.');
            Logger.log(this.name + " picked up a bomb.");
            
            this.onStatsChange();
        }
    }

    /**
     * Run when a stat of this unit has changed
     */
    private void onStatsChange() {
        if (this.onStatsChangeFunc != null) {
            this.onStatsChangeFunc.run();
        }
    }

    /**
     * Uses a bomb if this unit has a bomb
     * @return True if using the bomb was successful
     */
    public boolean useBomb() {
        if (this.bombs.size() == 0) {
            return false;
        }

        this.level.detonateBomb(this.y, this.x, this.bombs.get(0), this);
        this.bombs.remove(0);
        this.onStatsChange();
        return true;
    }

    /**
     * Attack a unit on another tile
     * @param u The unit that should be attacked
     * @return True if the attack went through, false otherwise
     */
    public boolean attack(Unit u) {
        if (Util.totalDistanceBetweenCoordinates(u.y, u.x, this.y, this.x) == 1) {
            u.takeHit(this.damage, this.name + " hit " + u.name + " for " + this.damage + " damage!");

            return true;
        } else {
            return false;
        }
    }

    /**
     * Reduces this unit's health by an amount and kills it if health goes to 0 or less
     * @param damage The amount of damage taken
     */
    public void takeHit(int damage, String logStr) {
        this.health -= damage;
        Logger.log(logStr);

        // Deth
        if (this.health <= 0) {
            Logger.log(this.name + " was killed!");
            this.level.removeUnit(this);
        }

        this.onStatsChange();
    }

    /**
     * Sets an event listener function to be run when this unit's stats have changed
     * @param cb Callback function to be called when this event fires
     */
    public void setOnStatsUpdate(Runnable cb) {
        this.onStatsChangeFunc = cb;
    }

    /**
     * Adds a bomb to this unit's inventory
     */
    public void addBomb() {
        this.bombs.add(new Bomb(this));
    }
}