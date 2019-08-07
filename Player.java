import java.util.*;
import java.lang.Math;

public class Player extends Character{
    Map <String, ArrayList <Item> > inventory;
    PlayerStats stats;
    Map <GearType, Gear> equipment;
    int max_inventory_size;
    int current_inventory_size;
    Gold gold;
    Scanner scan = new Scanner(System.in);
    PlayerLevel level;
    Random random;

    public Player() {
        this.random = new Random();
        System.out.println("Please Enter Your Name:");
        this.name = scan.next();

        this.max_hp = random.nextInt(7) + 18;
        this.max_hp = 22;
        this.current_hp = this.max_hp;
        
        int randomized_strength = random.nextInt(5) + 7;
        int randomized_dexterity = random.nextInt(5) + 7;
        int randomized_intelligence = random.nextInt(5) + 7;
        this.stats = new PlayerStats(randomized_strength, randomized_dexterity, randomized_intelligence);
        
        this.stats = new PlayerStats(10, 10, 10);

        ArrayList <Item> consumables = new ArrayList <Item> ();
        Potion health_potion = new Potion(PotionType.HEALTH, 10, 2);
        consumables.add(health_potion);
        this.current_inventory_size++;
        this.inventory = new HashMap<>();
        this.inventory.put("Consumables", consumables);
        this.inventory.put("Gear", new ArrayList <Item>());
        this.inventory.put("Misc", new ArrayList <Item>());

        this.equipment = new HashMap<>();
        this.equipment.put(GearType.MAINHAND, new Weapon("Wooden Club", WeaponType.MACE, 5, 0, false));
        this.equipment.put(GearType.OFFHAND, null);
        this.equipment.put(GearType.HELMET, null);
        // name, max armor, item level, magic
        this.equipment.put(GearType.CHEST, new Armor("Dirty Rags", GearType.CHEST, 4, 0, false));
        this.equipment.put(GearType.PANTS, new Armor("Dirty Pants", GearType.PANTS, 4, 0, false));
        this.equipment.put(GearType.GLOVES, null);
        this.equipment.put(GearType.BOOTS, null);

        this.max_inventory_size = 20 + (int)Math.floor(stats.get_strength() / 5);

        this.gold = new Gold(10);
        this.level = new PlayerLevel();
        this.character_state = CharacterState.ALIVE;
    }

    public boolean attempt_to_flee(Monster monster) {
        return (this.stats.get_dexterity() >= monster.get_intimidation()) || (random.nextInt(10) >= 9);
    }

    public void update_max_inventory_size() {
        this.max_inventory_size = 20 + (int)Math.floor(this.stats.get_strength() / 5);
    }

    public void access_inventory() {
        while (true) {
            System.out.println(
                get_name() + "\'s Inventory\n" +
                this.gold + '\n' +
                "Inventory Capacity: " + this.current_inventory_size + "/" +this.max_inventory_size + "\n\n" +
                "Would you like to access:\n" +
                "\t1) Gear\n" +
                "\t2) Consumables\n" +
                "\t3) Misc\n" 
            );

            String player_input = scan.next();
            player_input = player_input.toLowerCase();
            if (player_input.equals("1")) {
                if (!view_gear()) {
                    return;
                }
            } else if (player_input.equals("2")) {
                if(!view_consumables()) {
                    return;
                }
            } else if (player_input.equals("3")) {
                if(!view_misc()) {
                    return;
                }
            } else if (player_input.equals("exit")) {
                System.out.println("Are you sure you want to exit the inventory?");
                if (Utils.get_yes_no()) {
                    return;
                }
            } else {
                System.out.println("Valid Responses are 1, 2, 3, and exit.\n\n");
            }
        }
    }

    public boolean view_gear() {
        while (true) {
            System.out.println(get_name() + "\'s Gear:");

            if (this.inventory.get("Gear").size() == 0) {
                System.out.println("Empty.");
            }

            for (int i = 0; i < this.inventory.get("Gear").size(); i++) {
                System.out.print((i + 1) + ") " + this.inventory.get("Gear").get(i).get_name());
                if (i % 4 == 0 && i != 0) {
                    System.out.println('\n');
                }          
            }
            System.out.println();

            String player_input = scan.next();
            player_input = player_input.toLowerCase();
            if (player_input.equals("done")) {
                return true;
            } else if (player_input.equals("exit")) {
                if(Utils.get_yes_no()) {
                    return false;
                }
            } else if (player_input.equals("inspect")) {
                int inspect_number = scan.nextInt();
                System.out.println();
                inspect_number--;
                inspect_gear(inspect_number);
            } else if (player_input.equals("equip")) {
                int equip_number = scan.nextInt();
                equip_number--;
                attempt_to_equip(equip_number);
            } else if (player_input.equals("drop")) {
                int drop_number = scan.nextInt();
                drop_number--;
                drop_item(drop_number, "Gear");
            } else {
                // FIXME write use case message
            }
        }
    }

    public void drop_item(int index, String item_type) {
        System.out.println("Are you sure you want to drop this?");
        if (Utils.get_yes_no()) {
            this.inventory.get(item_type).remove(index);
            this.current_inventory_size--;
        }
    }

    public void inspect_gear(int index) {
        System.out.println(this.inventory.get("Gear").get(index));
    }

    public void attempt_to_equip(int index) {
        if (((Gear)this.inventory.get("Gear").get(index)).can_equip(this.stats)) {
            equip(index);
        } else {
            System.out.println("You do not have the required stats to equip this item");
        }
    }

    public boolean attempt_to_equip_new(Item new_equipment) {
        if (new_equipment.get_type() != ItemType.WEAPON && new_equipment.get_type() != ItemType.ARMOR) {
            System.out.println("You can only equip weapons and armor");
            return false;
        }

        if (((Gear)new_equipment).can_equip(this.stats)) {
            if (inventory_full()) {
                System.out.println("You do not have space in your inventory.");
                return false;
            }
            equip_new((Gear)new_equipment);
        } else {
            System.out.println("You do not have the required stats to equip this item");
            return false;
        }
        return true;
    }

    public boolean inventory_full() {
        return this.current_inventory_size == this.max_inventory_size;
    }

    public void attempt_to_take(Item item) {
        if (inventory_full()) {
            System.out.println("You do not have space in your inventory.");
            return;
        }
        if (item.get_type() == ItemType.MISC) {
            this.inventory.get("Misc").add(item);
        } else if (item.get_type() == ItemType.CONSUMABLE) {
            this.inventory.get("Consumables").add(item);
        } else {
            this.inventory.get("Gear").add(item);
        }
        this.current_inventory_size++;
    }

    public void equip_new(Gear new_equipment) {
        if (new_equipment.get_type() == ItemType.WEAPON) {
            if (this.equipment.get(GearType.MAINHAND) == null) {
                this.equipment.put(GearType.MAINHAND, new_equipment);
            } else {
                this.inventory.get("Gear").add(this.equipment.put(GearType.MAINHAND, new_equipment));
                this.current_inventory_size++;
            }
        } else {
            if (this.equipment.get(new_equipment.get_gear_type()) == null) {
                this.equipment.put(new_equipment.get_gear_type(), new_equipment);
            } else {
                this.inventory.get("Gear").add(this.equipment.put(new_equipment.get_gear_type(), new_equipment));
                this.current_inventory_size++;
            }
        }
    }

    public void equip(int index) {
        if (this.inventory.get("Gear").get(index).get_type() == ItemType.WEAPON) {
            Weapon old_weapon = (Weapon)this.equipment.put(GearType.MAINHAND, (Gear)this.inventory.get("Gear").get(index));
            this.inventory.get("Gear").remove(index);
            if (old_weapon != null) {
                this.inventory.get("Gear").add(old_weapon);
                this.current_inventory_size++;
            }
        } else {
            Armor old_armor = (Armor)this.equipment.put(((Gear)this.inventory.get("Gear").get(index)).get_gear_type(), (Gear)this.inventory.get("Gear").get(index));
            this.inventory.get("Gear").remove(index);
            if (old_armor != null) {
                this.inventory.get("Gear").add(old_armor);
                this.current_inventory_size++;
            }
        }
    }

    public boolean view_consumables() {
        while (true) {
            System.out.println(get_name() + "\'s Consumables:");

            if (this.inventory.get("Consumables").size() == 0) {
                System.out.println("Empty.");
            }

            for (int i = 0; i < this.inventory.get("Consumables").size(); i++) {
                System.out.print((i + 1) + ") " + this.inventory.get("Consumables").get(i));
                if (i % 4 == 0 && i != 0) {
                    System.out.print('\n');
                } else if (i != 0) {
                    System.out.print(',');
                }          
            }
            System.out.println("\n");

            String player_input = scan.next();
            player_input = player_input.toLowerCase();
            if (player_input.equals("done")) {
                return true;
            } else if (player_input.equals("exit")) {
                System.out.println("Are you sure you want to exit your equipment?");
                if(Utils.get_yes_no()) {
                    return false;
                }
            } else if (player_input.equals("drop")) {
                int index = scan.nextInt();
                index--;
                drop_item(index, "Consumables");
            } else if (player_input.equals("use")) {
                // FIXME
            } else {
                // FIXME add use case messages
            }
        }
    }

    public boolean view_misc() {
        while (true) {
            // FIXME print stuff
            String player_input = scan.next();
            player_input = player_input.toLowerCase();
            if (player_input.equals("done")) {
                return true;
            } else if (player_input.equals("exit")) {
                if (Utils.get_yes_no()) {
                    return false;
                }
            }
        }
    }

    public void access_equipment() {
        while (true) {
            System.out.println('\n' + get_name() + "\'s equipped items:");
            print_gear_piece(GearType.HELMET, "Helmet");
            print_gear_piece(GearType.CHEST, "Chest");
            print_gear_piece(GearType.GLOVES, "Gloves");
            print_gear_piece(GearType.PANTS, "Pants");
            print_gear_piece(GearType.BOOTS, "Boots");
            print_gear_piece(GearType.MAINHAND, "Mainhand");
            System.out.println();

            String player_input = scan.next();
            player_input = player_input.toLowerCase();
            if (player_input.equals("exit")) {
                System.out.println("Are you sure you want to exit equipment?");
                if (Utils.get_yes_no()) {
                    return;
                }
            } else if (player_input.equals("unequip")) {
                player_input = scan.next();
                player_input = player_input.toLowerCase();

                attempt_unequip(player_input);
            } else if (player_input.equals("inspect")) {
                player_input = scan.next();
                player_input = player_input.toLowerCase();

                inspect_piece(player_input);
            } else {
                // FIXME add help thingy
            }
        }
    }

    public void inspect_piece(String gear_piece) {
        GearType piece_type = GearType.OFFHAND;

        if (gear_piece.equals("helmet")) {
            piece_type = GearType.HELMET;
        } else if (gear_piece.equals("chest")) {
            piece_type = GearType.CHEST;
        } else if (gear_piece.equals("gloves")) {
            piece_type = GearType.GLOVES;
        } else if (gear_piece.equals("pants")) {
            piece_type = GearType.PANTS;
        } else if (gear_piece.equals("boots")) {
            piece_type = GearType.BOOTS;
        } else if (gear_piece.equals("mainhand")) {
            piece_type = GearType.MAINHAND;
        } else {
            System.out.println("That is not a valid gear type.");
            return;
        }

        if (this.equipment.get(piece_type) == null) {
            System.out.println("There is nothing in that slot to inspect");
            return;
        }

        System.out.println();
        System.out.println(this.equipment.get(piece_type));
    }

    public void attempt_unequip(String gear_piece) {
        GearType piece_type = GearType.OFFHAND;

        if (gear_piece.equals("helmet")) {
            piece_type = GearType.HELMET;
        } else if (gear_piece.equals("chest")) {
            piece_type = GearType.CHEST;
        } else if (gear_piece.equals("gloves")) {
            piece_type = GearType.GLOVES;
        } else if (gear_piece.equals("pants")) {
            piece_type = GearType.PANTS;
        } else if (gear_piece.equals("boots")) {
            piece_type = GearType.BOOTS;
        } else if (gear_piece.equals("mainhand")) {
            piece_type = GearType.MAINHAND;
        } else {
            System.out.println("That is not a valid gear type.");
            return;
        }

        if (this.equipment.get(piece_type) == null) {
            System.out.println("There is nothing in that slot to unequip");
            return;
        } else if (inventory_full()) {
            System.out.println("There is no room in your inventory for this equipment");
            return;
        }

        this.inventory.get("Gear").add(this.equipment.get(piece_type));
        this.equipment.put(piece_type, null);
        this.current_inventory_size++;
    }

    public void print_gear_piece(GearType gear_type, String type_name) {
        String none_string = "None.\n";
        System.out.print(type_name + ": ");
        if (this.equipment.get(gear_type) == null) {
            System.out.print(none_string);
        } else {
            System.out.print(this.equipment.get(gear_type).get_name() + '\n');
        }
    }

    public void give_xp(int xp) {
        int spendable_points = 0;
        int old_level = this.level.get_level();

        for (int i = 0; i < this.level.update_xp(xp); i++) {
            spendable_points += 2;
        }

        if (spendable_points > 0) {
            level_up(spendable_points, old_level);
        }
    }

    public void level_up(int spendable_points, int old_level) {
        System.out.println("Congratulations, " + this.name + " Leveled up from " + old_level + " to " + this.level.get_level());
        System.out.println(this.name + " has " + spendable_points + " free skill points to spend.\n");
        System.out.println(this.name + "\'s current stats:");
        System.out.println(this.stats + "\n\n");
        System.out.println("How many points would you like to add to the following:");
        // FIXME: may be able to use functors to make this better
        while (true) {
            while (true) {
                System.out.print("Strength: ");
                int player_input = scan.nextInt();
                if (spendable_points - player_input < 0) {
                    System.out.println("You do not have that many points to spend");
                } else {
                    spendable_points = spendable_points - player_input;
                    this.stats.increase_strength(player_input);
                    break;
                }
            }
            if (spendable_points == 0) {
                break;
            }
            System.out.println("You have " + spendable_points + " remaining.");
            while (true) {
                System.out.print("Dexterity: ");
                int player_input = scan.nextInt();
                if (spendable_points - player_input < 0) {
                    System.out.println("You do not have that many points to spend");
                } else {
                    spendable_points = spendable_points - player_input;
                    this.stats.increase_dexterity(player_input);
                    break;
                }
            }
            if (spendable_points == 0) {
                break;
            }
            System.out.println("You have " + spendable_points + " remaining.");
            while (true) {
                System.out.print("Intelligence: ");
                int player_input = scan.nextInt();
                if (spendable_points - player_input < 0) {
                    System.out.println("You do not have that many points to spend");
                } else {
                    spendable_points = spendable_points - player_input;
                    this.stats.increase_intelligence(player_input);
                    break;
                }
            }
            if (spendable_points == 0) {
                break;
            }
            System.out.println("You have " + spendable_points + " remaining.");
            System.out.println("Your current stats are:");
            System.out.println(this.stats);
        }
    }

    public void attack(Monster monster) {
        if (this.stats.get_dexterity() > monster.get_intimidation()) {
            if (player_attacks_first(monster)) { 
                return; 
            }
            if (monster_attacks_first(monster)) {
                return;
            }
        } else if (this.stats.get_dexterity() < monster.get_intimidation()) {
            if (monster_attacks_first(monster)) {
                return;
            }
            if (player_attacks_first(monster)) {
                return;
            }
        } else {
            if (Math.random() <= 0.8) {
                if (player_attacks_first(monster)) { 
                    return; 
                }
                if (monster_attacks_first(monster)) {
                    return;
                }
            } else {
                if (monster_attacks_first(monster)) {
                    return;
                }
                if (player_attacks_first(monster)) {
                    return;
                }
            }
        }
    }

    // FIXME this can be removed and replaced above
    public boolean player_attacks_first(Monster monster) {
        int damage = get_player_damage();
        System.out.println(get_name() + " did " + damage + " to " + monster.get_name());
        if (monster.decrease_current_hp(damage)) {
            return true;
        }
        return false;
    }

    public boolean monster_attacks_first(Monster monster) {
        int damage = monster.get_monster_damage();
        System.out.println(monster.get_name() + " did " + damage + " to " + get_name());
        if (this.decrease_current_hp(damage)) {
            return true;
        }
        return false;
    }

    public int get_player_damage() {
        int weapon_damage = ((Weapon)this.equipment.get(GearType.MAINHAND)).get_full_attack();

        double modifier = 1;

        if (((Weapon)this.equipment.get(GearType.MAINHAND)).get_spec_type() == SpecializationType.STRENGTH) {
            modifier += (double)this.stats.get_strength() / 80.0;
        } else if (((Weapon)this.equipment.get(GearType.MAINHAND)).get_spec_type() == SpecializationType.DEXTERITY) {
            modifier += (double)this.stats.get_dexterity() / 80.0;
        } else if (((Weapon)this.equipment.get(GearType.MAINHAND)).get_spec_type() == SpecializationType.INTELLIGENCE) {
            modifier += (double)this.stats.get_intelligence() / 80.0;
        }

        return (int)Math.ceil(weapon_damage * modifier);
    }

}
