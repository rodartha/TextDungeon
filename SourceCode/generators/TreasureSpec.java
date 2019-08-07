package genrators;

import utility.*;
import items.*;
import character.*;
// NOTE: will hold info that will determine the specs for a treasure to be generated.
public class TreasureSpec {
    String name;
    double chance_picked;
    TreasureType type;

    // FIXME:
    public TreasureSpec(TreasureType type) {
    	this.type = type;
    }

    public double get_chance_picked() {
    	return this.chance_picked;
    }

    public TreasureType get_type() {
    	return this.type;
    }
}
