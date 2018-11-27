package roguelike.domain;

public class Player extends Unit {
    public Player(int y, int x, Level level) {
        super(y, x, level);
        this.health = 10;
        this.damage = 4;
    }
}