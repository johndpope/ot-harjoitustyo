package roguelike.domain;

public class Unit {
    public int startY, startX;
    public int y, x;
    public int health;
    public int damage;
    public Level level;

    public Unit(int y, int x, Level level) {
        this.y = y;
        this.x = x;
        this.startY = y;
        this.startX = x;
        this.level = level;

        // Default health and damage values
        this.health = 0;
        this.damage = 0;
    }

    public void move(int yDiff, int xDiff) {
        if (this.level.isOutOfBounds(this.y + yDiff, this.x + xDiff)) {
            return;
        }

        this.level.swapTiles(this.y, this.x, this.y + yDiff, this.x + xDiff);
        this.y += yDiff;
        this.x += xDiff;
    }
}