public class PlayerStats{
    int strength;
    int dexterity;
    int intelligence;

    public PlayerStats(int strength, int dexterity, int intelligence) {
        this.strength = strength;
        this.dexterity = dexterity;
        this.intelligence = intelligence;
    }

    public int get_strength() {
        return this.strength;
    }

    public void set_strength(int strength) {
        this.strength = strength;
    }

    public void increase_strength(int modifier) {
        this.strength += modifier;
    }

    public void decrease_strength(int modifier) {
        this.strength -= modifier;
    }

    public int get_dexterity() {
        return this.dexterity;
    }

    public void set_dexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public void increase_dexterity(int modifier) {
        this.dexterity += modifier;
    }

    public void decrease_dexterity(int modifier) {
        this.dexterity -= modifier;
    }

    public int get_intelligence() {
        return this.intelligence;
    }

    public void set_intelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public void increase_intelligence(int modifier) {
        this.intelligence += modifier;
    }

    public void decrease_intelligence(int modifier) {
        this.intelligence -= modifier;
    }

    public String toString() {
        return "Strength: " + this.strength + '\n' +
        "Dexterity: " + this.dexterity + '\n' +
        "Intelligence: " + this.intelligence;
    }
}
