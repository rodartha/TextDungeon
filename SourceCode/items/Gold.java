package items;

import utility.*;
import character.*;

public class Gold{
    int amount;

    public Gold(int amount) {
        this.amount = amount;
    }

    public int get_amount() {
        return this.amount;
    }

    public void add_gold(int amount_to_add) {
        this.amount += amount_to_add;
    }

    public boolean can_remove(int amount_to_remove) {
        return this.amount - amount_to_remove >= 0;
    }

    public boolean remove_gold(int amount_to_remove) {
        if (can_remove(amount_to_remove)) {
            this.amount -= amount_to_remove;
            return true;
        }

        return false;
    }

    public String toString() {
        return "Gold: " + this.amount;
    }
}
