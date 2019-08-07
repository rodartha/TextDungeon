package character;

import utility.*;
import items.*;

public abstract class NPC extends Character{
    NPCType type;

    public NPCType get_type() {
        return this.type;
    }
}
