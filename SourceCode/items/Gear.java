package items;

import utility.*;
import character.*;

public abstract class Gear extends Item {
    int item_level;
    boolean magical;
    Attributes magical_attributes;
    int min_strength_required;
    int min_dexterity_required;
    int min_intelligence_required;
    GearType gear_type;

    public int get_item_level() {
        return this.item_level;
    }

    public boolean is_magical() {
        return this.magical;
    }

    public Attributes get_magical_attributes() {
        return this.magical_attributes;
    }

    public boolean can_equip(PlayerStats stats) {
        return stats.get_dexterity() >= this.min_dexterity_required
        && stats.get_strength() >= this.min_strength_required
        && stats.get_intelligence() >= this.min_intelligence_required;
    }

    public GearType get_gear_type() {
        return this.gear_type;
    }
}
