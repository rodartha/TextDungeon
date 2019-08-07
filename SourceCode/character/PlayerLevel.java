package character;

import utility.*;
import items.*;

public class PlayerLevel {
    int level;
    int xp_to_next_level;
    int current_xp;

    public PlayerLevel() {
        this.level = 1;
        this.xp_to_next_level = 500;
        this.current_xp = 0;
    }

    public int get_level() {
        return this.level;
    }

    public int get_current_xp() {
        return this.current_xp;
    }

    public int get_xp_to_next_level() {
        return this.xp_to_next_level;
    }

    public int update_xp(int additional_xp) {
        this.current_xp += additional_xp;

        return leveled_up();
    }

    public int leveled_up() {
        if (this.current_xp < this.xp_to_next_level) {
            return 0;
        }

        this.level++;
        this.current_xp = this.xp_to_next_level - this.current_xp;
        this.xp_to_next_level = this.xp_to_next_level + 100;
        return 1 + leveled_up();
    }
}
