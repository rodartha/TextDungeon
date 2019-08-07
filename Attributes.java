import java.util.ArrayList;

public class Attributes {

    // CAN ONLY HOLD ONE ATTRIBUTE OF EACH TYPE
    // need to find a way to enforce this
    ArrayList <SingleAttribute> attributes;

    public Attributes(int item_level, ItemType type) {
        if (type == ItemType.ARMOR) {
            generate_armor_attributes(item_level);
        } else if (type == ItemType.WEAPON) {
            generate_weapon_attributes(item_level);
        }
    }

    public void generate_armor_attributes(int item_level) {
        // FIXME should use item level more and assign multiple attributes

        // FIXME: this logic
        int modifier = (item_level * 2) + 1;
        this.attributes.add(new DefenseAttribute(modifier));
    }

    public void generate_weapon_attributes(int item_level) {
        // FIXME should use item level more and assign multiple attributes

        // FIXME: this logic
        int modifier = (item_level * 2) + 1;
        this.attributes.add(new DamageAttribute(modifier));
    }

    public SingleAttribute get_attribute_of_type(AttributeType type) {
        for (int i = 0; i < this.attributes.size(); i++) {
            if (this.attributes.get(i).get_type() == type) {
                return this.attributes.get(i);
            }
        }

        return new NoneTypeAttribute();
    }

    public String toString() {
        String print_string = "";
        for (int i = 0; i < this.attributes.size(); i++) {
            print_string += this.attributes.get(i).toString() + '\n';
        }

        return print_string;
    }
}
