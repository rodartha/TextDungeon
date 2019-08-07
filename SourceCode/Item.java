public abstract class Item {
    String name;
    ItemType type;
    int price;

    public String get_name() {
        return this.name;
    }

    public ItemType get_type() {
        return this.type;
    }

    public int get_price() {
        return this.price;
    }
}
