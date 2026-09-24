package github.lucasas.items;

public enum ShopCategory {
    WEAPONS("Våben"),
    HEALING_POTIONS("Healing potions"),
    DAMAGE_POTIONS("Damage potions");

    private final String displayName;

    ShopCategory(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
