package items;

import utility.*;
import character.*;

public class DefenseAttribute extends SingleAttribute {
    int modifier;

    public DefenseAttribute(int modifier) {
        this.type = AttributeType.DEFENSE;
        this.modifier = modifier;
    }

    public int get_modifier() {
        return this.modifier;
    }

    public String toString() {
        return "Defense+ " + this.modifier;
    }
}
