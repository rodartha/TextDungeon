import java.util.Scanner; 

public class GameDriver {
    Player player;
    NPC npc;
    Item item;
    State state;
    Scanner scan = new Scanner(System.in);

    public GameDriver() {
        game_startup();
    }

    public void game_startup() {
        game_start_message();
        this.player = new Player();
        // valid types are empty, fight, loot
        this.state = State.EMPTY;

        // FIXME: need a monster generator
        this.npc = new Monster("Ooze", 20, 1);
    }

    public void display_actions() {
        // FIXME this should have something to do with what has happened so far
    }

    public void display_help() {
        System.out.println("Valid Commands for this program are as follows");
        // FIXME: add more
    }

    public void attack() {
    	if (this.state != State.FIGHT) {
    		System.out.println("That action can only be performed when you're in a fight");
    		return;
    	}

    	this.player.attack((Monster)this.npc);
        print_fight_string();
        check_player_dead();
        if (this.npc.get_character_state() == CharacterState.DEAD) {
            this.state = State.LOOT;
            this.player.give_xp(((Monster)this.npc).get_xp_given());
            // Generate loot
        }
    }

    public void flee() {
    	if (this.state != State.FIGHT) {
    		System.out.println("That action can only be performed when you're in a fight");
    		return;
    	}

    	if (this.player.attempt_to_flee((Monster)this.npc)) {
            System.out.println(this.player.get_name() + " Successfully got away from " + this.npc.get_name());
    		this.state = State.EMPTY;
    	} else {
            System.out.println(this.player.get_name() + " Tried to escape but was unsuccessful.");
            int damage = ((Monster)this.npc).get_flee_punishment(this.player);
            System.out.println(this.npc.get_name() + " did " + damage + " damage to " + this.player.get_name() + " as they tried to flee.");
            check_player_dead();
        }
    }

    public void check_player_dead() {
        if (this.player.get_character_state() == CharacterState.DEAD) {
            game_over();
        }
    }

    public void inventory() {
    	this.player.access_inventory();
    }

    public void equip() {
    	if (this.state != State.LOOT) {
    		System.out.println("That action can only performed when there is loot");
    		return;
    	}

    	this.player.attempt_to_equip_new(this.item);
    }

    public void equipment() {
    	this.player.access_equipment();
    }

    public void take() {
    	if (this.state != State.LOOT) {
    		System.out.println("That action can only performed when there is loot");
    		return;
    	}

    	this.player.attempt_to_take(this.item);
    }

    public void move_to_fight() {
    	if (this.state == State.FIGHT) {
    		System.out.println("That action can only be performed outside of combat");
    		return;
    	}
    	else if (this.state == State.LOOT) {
    		System.out.println("Are you sure you want to leave loot behind?");
    		if (!Utils.get_yes_no()) {
    			return;
    		}
    	}

    	// FIXME: need to autogenerate monster here
    	this.state = State.FIGHT;
        begin_fight();
    }

    public void play() {
        System.out.println("You\'ve come to the mouth of a cave.\n");

        String player_input = scan.next();
        System.out.println();

        player_input = player_input.toLowerCase();

        while (!player_input.equals("exit")) {
            if (player_input.equals("help")) {
                display_help();
            } else if (player_input.equals("attack")) {
            	attack();
            } else if (player_input.equals("flee")) {
            	flee();
            } else if (player_input.equals("inventory")) {
            	inventory();
            } else if (player_input.equals("equip")) {
            	equip();
            } else if (player_input.equals("equipment")) {
            	equipment();
            } else if (player_input.equals("take")) {
            	take();
            } else if (player_input.equals("continue")) {
            	move_to_fight();
            } else {
                System.out.println("That was an invalid command, for a list of valid commands, please type \"help\".");
            }

            display_actions();

            player_input = scan.next();
            player_input = player_input.toLowerCase();
        }
    }

    public void begin_fight() {
        System.out.println("      FIGHT!");
        String player_name = this.player.get_name();
        System.out.println(player_name);
        System.out.println(Utils.get_character_string(player_name, ' ') + " vs. ");
        System.out.println(Utils.get_character_string(player_name + " vs. ", ' ') + this.npc.get_name());
        System.out.println();
        print_fight_string();
    }

    public void print_fight_string() {
        String name_plate = this.player.get_name() + "      " + this.npc.get_name();
        String player_health = this.player.get_health_string();
        String monster_health = ((Monster)this.npc).get_health_string();
        int whitespace_size = name_plate.length() - player_health.length() - monster_health.length();
        if (whitespace_size <= 1) {
            whitespace_size = 2;
        }
        System.out.println(Utils.get_character_string(name_plate, '='));
        System.out.println(name_plate);
        System.out.println(player_health + Utils.get_whitespace_of_length(whitespace_size) + monster_health);
    }

    public void game_over() {
        System.out.println("Game Over: " + this.player.get_name() + " has died.");
        System.out.println("Do you want to play again?");
        if (Utils.get_yes_no()) {
            System.out.println("\n");
            game_startup();
            play();
        }
        System.exit(0);
    }

    public void game_start_message() {
        System.out.println("Welcome To Textara Dungeon.\n");
    }

    public static void main(String [] args) {
        GameDriver game = new GameDriver();
        game.play();
    }
}
