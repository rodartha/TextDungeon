import java.util.Random;
import java.lang.Math;

 class Weapon extends Gear {
    WeaponType weapon_type;
    int min_attack;
    int max_attack;
    double crit_chance;
    double crit_damage_increase;
    SpecializationType spec_type;
    Random random;

    public Weapon(String name, WeaponType weapon_type, int max_attack, int item_level, boolean magical) {
        this.name = name;
        this.random = new Random();
        // FIXME
        this.gear_type = GearType.MAINHAND;
        this.weapon_type = weapon_type;
        this.max_attack = max_attack;
        // FIXME: logic
        this.min_attack = this.max_attack - (int)(Math.floor(this.max_attack / 5) + 1);
        this.item_level = item_level;
        this.magical = magical;
        if (this.magical) {
            this.magical_attributes = new Attributes(this.item_level, this.type);
        }

        //FIXME shit logic:
        this.min_strength_required = this.item_level;
        this.min_dexterity_required = this.item_level;
        this.min_intelligence_required = this.item_level;

        this.crit_chance = calc_crit_chance();
        this.crit_damage_increase = calc_crit_damage();

        this.price = calculate_price();

        set_specialization_type();
    }

    public void set_specialization_type() {
        if (this.weapon_type == WeaponType.MACE) {
            this.spec_type = SpecializationType.STRENGTH;
        } else if (this.weapon_type == WeaponType.SWORD) {
            this.spec_type = SpecializationType.DEXTERITY;
        } else if (this.weapon_type == WeaponType.AXE) {
            this.spec_type = SpecializationType.STRENGTH;
        }
    }

    public int calculate_price() {
        int modifier = 2;
        if (is_magical()) {
            modifier *= 3;
        }
        return (this.item_level * 2) + 1 + modifier;
    }

    public double calc_crit_chance() {
        // Bounds the crit chance at 50% max
        return (double)(this.item_level + random.nextInt(this.max_attack + 50) + 1) / (double)(2 * (this.item_level + this.max_attack + 75));
    }

    public double calc_crit_damage() {
        return 1.0 + ((double)(this.item_level + random.nextInt(this.max_attack) + 21 + random.nextInt(10)) / 100.0);
    }

    public SpecializationType get_spec_type() {
        return this.spec_type;
    }

    public int get_attack(int modifier) {
        int damage = random.nextInt(this.max_attack) + this.min_attack + modifier;

        if (Math.random() <= this.crit_chance) {
            damage *= this.crit_damage_increase;
        }

        return damage;
    }

    public int get_full_attack() {
        int modifier = 0;

        if (is_magical()) {
            SingleAttribute damage = this.magical_attributes.get_attribute_of_type(AttributeType.DAMAGE);
            if (damage.get_type() == AttributeType.DAMAGE) {
                modifier = damage.get_modifier();
            }
        }

        return get_attack(modifier);
    }

    public double get_crit_chance() {
        return this.crit_chance;
    }

    public double get_crit_damage_increase() {
        return this.crit_damage_increase;
    }

    public int get_min_attack() {
        return this.min_attack;
    }

    public int get_max_attack() {
        return this.max_attack;
    }

    public WeaponType get_weapon_type() {
        return this.weapon_type;
    }

    public String toString() {
        String magical_attributes_string = "";
        if (is_magical()) {
            magical_attributes_string = this.magical_attributes.toString();
        }
        return this.name + '\n'
        + "Attack: " + this.min_attack + '-' + this.max_attack + '\n'
        + "Crit Chance: " + (this.crit_chance * 100) + "%\n"
        + "Crit Damage: " + (this.crit_damage_increase * 100) + "%\n"
        + "Item Level: " + this.item_level + '\n'
        + magical_attributes_string + "\n"
        + "Price: " + get_price();
    }
}
