package items;

import utility.*;
import character.*;

public abstract class SingleAttribute {
	AttributeType type;

	public AttributeType get_type() {
		return this.type;
	}

	public abstract int get_modifier();
}
