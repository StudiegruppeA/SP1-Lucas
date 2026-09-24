package github.lucasas.hero;

import github.lucasas.combat.Attack;
import github.lucasas.items.Weapon;

public enum HeroClass {
    WARRIOR("Kriger", 130, 60, 1.0, new Weapon("Rusty Sword", 0,
            new Attack("Slash", 5),
            new Attack("Shield Bash", 4),
            new Attack("Thrust", 6))),
    MAGE("Troldmand", 95, 90, 1.35, new Weapon("Apprentice Staff", 0,
            new Attack("Firebolt", 4),
            new Attack("Frost Shard", 5),
            new Attack("Arcane Zap", 6))),
    ROGUE("Snigmorder", 110, 110, 1.15, new Weapon("Rusty Dagger", 0,
            new Attack("Stab", 4),
            new Attack("Backstab", 7),
            new Attack("Blade Throw", 3)));

    private final String displayName;
    private final int startHealth;
    private final double startGold;
    private final double damageMultiplier;
    private final Weapon startWeapon;

    HeroClass(String displayName, int startHealth, double startGold, double damageMultiplier, Weapon startWeapon) {
        this.displayName = displayName;
        this.startHealth = startHealth;
        this.startGold = startGold;
        this.damageMultiplier = damageMultiplier;
        this.startWeapon = startWeapon;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getStartHealth() {
        return startHealth;
    }

    public double getStartGold() {
        return startGold;
    }

    public double getDamageMultiplier() {
        return damageMultiplier;
    }

    public Weapon getStartWeapon() {
        return startWeapon;
    }

    public String getDescription() {
        return startHealth + " liv, " + (int) startGold + " guld, skade x" + damageMultiplier;
    }
}
