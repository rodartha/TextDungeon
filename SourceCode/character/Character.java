package character;

import utility.*;
import items.*;

public abstract class Character {
    String name;
    int current_hp;
    int max_hp;
    CharacterState character_state;

    public String get_name() {
        return this.name;
    }

    public boolean check_dead() {
        if (this.current_hp <= 0) {
            this.character_state = CharacterState.DEAD;
            this.current_hp = 0;
            return true;
        }
        return false;
    }

    public int get_hp() {
        return this.current_hp;
    }

    public int get_max_hp() {
        return this.max_hp;
    }

    public void increase_max_hp(int modifier) {
        this.max_hp += modifier;
    }

    public void increase_current_hp(int modifier) {
        this.current_hp = Math.min(this.max_hp, this.current_hp + modifier);
    }

    public boolean decrease_current_hp(int modifier) {
        this.current_hp -= modifier;
        return check_dead();
    }

    public String get_health_string() {
        return this.current_hp + "/" + this.max_hp;
    }

    public CharacterState get_character_state() {
        return this.character_state;
    }

    public void set_character_state(CharacterState new_state) {
        this.character_state = new_state;
    }
}
