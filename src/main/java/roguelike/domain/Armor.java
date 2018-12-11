package roguelike.domain;

import java.util.Random;

public class Armor extends Item {
    private Random randomEngine;
    private int damageReductionChancePerc;

    /**
     * Creates a new armor item
     * @param name Name of the armor
     * @param holder The unit that is holding the armor
     * @param armorProtection Amount of protection this armor provides
     */
    public Armor(String name,  Unit holder, int armorProtection) {
        super(name, holder);
        this.armor = armorProtection;
        this.randomEngine = new Random();

        // Pick a chance to reduce damage randomly within a range of [60-80]
        this.damageReductionChancePerc = this.randomEngine.nextInt(20) + 60;
    }

    /**
     * Calculates a randomized damage reduction value based on the protection of this armor
     * @return The amount of damage reduction
     */
    public int calculateDamageReduction() {
        int reduction = 0;
        
        for (int i = 0; i < this.armor; i++) {
            if (this.randomEngine.nextInt(100) < this.damageReductionChancePerc) {
                reduction++;
            }
        }

        return reduction;
    }
}