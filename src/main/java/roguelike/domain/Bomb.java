package roguelike.domain;

/**
 * A bomb item
 */
public class Bomb extends Item {
    public int blastRadius;
    public int blastDamage;

    /**
     * Create a new bomb
     * @param holder The unit that has this bomb
     */
    public Bomb(Unit holder) {
        super("Bomb", holder);
        this.blastRadius = 2;
        this.blastDamage = 6;
    }
}