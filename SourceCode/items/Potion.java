package items;

import utility.*;
import character.*;

public class Potion extends Item {
    PotionType potion_type;
    int potion_stat;

    public Potion(PotionType potion_type, int potion_stat, int price) {
        this.type = ItemType.CONSUMABLE;
        this.potion_type = potion_type;
        this.price = price;

        if (this.potion_type == PotionType.HEALTH) {
            this.name = "Health Potion";
        } else if (this.potion_type == PotionType.POISON) {
            this.name = "Poison Potion";
        }

        this.potion_stat = potion_stat;
    }

    public String toString() {
        return this.name + " Potency: " + this.potion_stat + " Price: " + get_price();
    }
}
