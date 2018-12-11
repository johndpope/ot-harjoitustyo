package roguelike.domain;

import java.util.Random;

/**
 * A weapon item
 */
public class Weapon extends Item {
    private Random randomEngine;
    private int critChance;

    /**
     * Creates a new armor item
     * @param name Name of the armor
     * @param holder The unit that is holding the armor
     * @param damage Damage that this weapon deals
     */
    public Weapon(String name, Unit holder, int damage) {
        super(name, holder);
        this.damage = damage;
        this.randomEngine = new Random();

        // Pick a critical hit chance randomly between a range of [10-20]
        this.critChance = this.randomEngine.nextInt(10) + 10;
    }

    /**
     * Calculates whether or not this weapon should critical hit
     * @return True if the weapon did a critical hit, false otherwise
     */
    public boolean criticalHit() {
        return (this.randomEngine.nextInt(100) < this.critChance);
    }
}