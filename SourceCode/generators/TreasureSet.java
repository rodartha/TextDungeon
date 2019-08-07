package generators;

import utility.*;
import items.*;
import character.*;

import java.util.*;
import java.lang.Math;

// NOTE: this class will hold possible treasures from a monster and will also determine which ones will be dished out.
// as well as possible gold that can be given out
public class TreasureSet {
    ArrayList <TreasureSpec> treasure;
    Random random;
    int max_gold_reward;

    // FIXME:
    public TreasureSet() {
    	this.treasure = new ArrayList <TreasureSpec> ();
    	this.random = new Random();
    }

    public ArrayList <TreasureSpec> get_treasures() {
    	// A monster can drop between 1 and 3 treasures;
    	int num_treasures = random.nextInt(2) + 1;
    	ArrayList <TreasureSpec> granted_treasures = new ArrayList <TreasureSpec> ();

    	while(granted_treasures.size() < num_treasures) {
    		ArrayList <TreasureSpec> possible_treasures = new ArrayList <TreasureSpec> ();
    		Double chance_threshold = Math.random();
    		for (int i = 0; i < this.treasure.size(); i++) {
    			if (this.treasure.get(i).get_chance_picked() >= chance_threshold) {
    				possible_treasures.add(this.treasure.get(i));
    			}
    		}

    		if (possible_treasures.size() > 0) {
    			granted_treasures.add(possible_treasures.get(random.nextInt(possible_treasures.size())));
    		} else {
    			granted_treasures.add(new TreasureSpec(TreasureType.NONE));
    		}
    	}

    	return granted_treasures;
    }

    public int get_gold_reward() {
    	return random.nextInt(this.max_gold_reward - 10) + 10;
    }
}
