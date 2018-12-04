package roguelike.domain;

public class Player extends Unit {
    public Player(int y, int x, String name, Level level) {
        super(y, x, name, level);
        this.health = 12;
        this.damage = 5;
    }

    @Override
    public boolean move(int yDiff, int xDiff) {
        boolean success = super.move(yDiff, xDiff);

        return success;
    }
}