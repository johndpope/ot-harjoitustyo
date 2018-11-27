package roguelike.domain;

public class Zombie extends Unit {
    public Zombie(int y, int x, Level level) {
        super(y, x, level);
        this.health = 13;
        this.damage = 2;
    }
    
    
}