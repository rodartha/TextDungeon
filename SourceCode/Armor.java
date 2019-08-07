import java.util.Random;

public class Armor extends Gear {
    int armor;
    Random random;

    public Armor(String name, GearType gear_type, int max_armor, int item_level, boolean magical) {
        this.name = name;
        random = new Random();
        this.type = ItemType.ARMOR;
        this.gear_type = gear_type;
        this.item_level = item_level;

        this.armor = set_armor(max_armor);

        this.magical = magical;
        if (this.magical) {
            this.magical_attributes = new Attributes(this.item_level, this.type);
        }
        this.price = calculate_price();

        //FIXME shit logic:
        this.min_strength_required = this.item_level;
        this.min_dexterity_required = this.item_level;
        this.min_intelligence_required = this.item_level;
    }

    public int set_armor(int max_armor) {
        int min_armor = (this.item_level * 2) + 1;
        return random.nextInt(max_armor - min_armor) + min_armor;
    }

    public int calculate_price() {
        int modifier = 2;
        if (this.is_magical()) {
            modifier *= 3;
        }
        return (this.item_level * 2) + 1 + modifier;
    }

    public GearType get_gear_type() {
        return this.gear_type;
    }

    public int get_base_armor() {
        return this.armor;
    }

    public int get_full_armor() {
        SingleAttribute def = this.magical_attributes.get_attribute_of_type(AttributeType.DEFENSE);
        if (def.get_type() == AttributeType.NONE) {
            return get_base_armor();
        }

        return def.get_modifier() + get_base_armor();
    }

    public String toString() {
        String magical_attributes_string = "";
        if (is_magical()) {
            magical_attributes_string = this.magical_attributes.toString();
        }
        return this.name + '\n'
        + "Armor: " + this.armor + '\n'
        + "Item Level: " + this.item_level + '\n'
        + magical_attributes_string + "\n"
        + "Price: " + get_price();
    }
}
