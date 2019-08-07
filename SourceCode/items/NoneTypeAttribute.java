package items;

import utility.*;
import character.*;

public class NoneTypeAttribute extends SingleAttribute {
    public NoneTypeAttribute() {
        this.type = AttributeType.NONE;
    }

    public int get_modifier() {
    	return -1;
    }
}
