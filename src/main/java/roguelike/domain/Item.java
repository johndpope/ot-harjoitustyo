package roguelike.domain;

public class Item {
    public String name;
    public Unit holder;
    public int armor, damage, health;

    /**
     * Create a new item
     * @param name Name of the item
     * @param holder The unit that has this item
     */
    public Item(String name, Unit holder) {
        this.name = name;
        this.holder = holder;
        this.armor = 0;
        this.damage = 0;
        this.health = 0;
    }
}