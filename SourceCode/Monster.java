import java.lang.Math;

public class Monster extends NPC {
    // FIXME need the loot drop stuff here as well as the damage the moster does etc
    int intimidation;
    int damage;
    int xp_given;

    public Monster(String name, int hp, int monster_level) {
        this.type = NPCType.MONSTER;
        this.name = name;
        this.max_hp = hp;
        this.current_hp = this.max_hp;
        this.intimidation = calculate_intimidation(monster_level);
        this.damage = (int)Math.ceil(1.5 * monster_level + 1);
        this.xp_given = ((monster_level + 1) * 100) + hp;
    }

    public int calculate_intimidation(int monster_level) {
        // FIXME
        return 10;
    }

    public int get_intimidation() {
        return this.intimidation;
    }

    public int get_monster_damage() {
        // FIXME: this is far too basic
        return this.damage;
    }

    public String get_health_string() {
        return this.current_hp + "/" + this.max_hp;
    }

    public int get_xp_given() {
        return this.xp_given;
    }

    public int get_flee_punishment(Player player) {
        // FIXME: better calculation for the damage number
        int flee_punishment = this.damage;

        player.decrease_current_hp(flee_punishment);

        return flee_punishment;
    }
}
