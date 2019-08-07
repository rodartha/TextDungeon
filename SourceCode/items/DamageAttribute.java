package items;

import utility.*;
import character.*;

public class DamageAttribute extends SingleAttribute {
    int modifier;

    public DamageAttribute(int modifier) {
        this.type = AttributeType.DAMAGE;
        this.modifier = modifier;
    }

    public int get_modifier() {
        return this.modifier;
    }

    public String toString() {
        return "Damage+ " + this.modifier;
    }
}
