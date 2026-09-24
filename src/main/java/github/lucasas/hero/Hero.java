package github.lucasas.hero;

import github.lucasas.ui.ConsoleUi;
import github.lucasas.combat.Attack;
import github.lucasas.combat.Combatant;
import github.lucasas.items.Item;
import github.lucasas.items.Potion;
import github.lucasas.items.Weapon;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Hero implements Combatant {
    private static final int XP_PER_LEVEL = 100;
    private static final double MAX_HEALTH_GROWTH = 1.15;
    private static final double CRITICAL_HEALTH_LIMIT = 25;

    private final ConsoleUi ui;
    private final String name;
    private final HeroClass heroClass;
    private final Inventory inventory;
    private final Map<String, Integer> killsByMonster;
    private int health;
    private int maxHealth;
    private int level;
    private int xp;
    private double gold;
    private Weapon equippedWeapon;

    public Hero(ConsoleUi ui, String name, HeroClass heroClass) {
        this.ui = ui;
        this.name = name;
        this.heroClass = heroClass;
        this.maxHealth = heroClass.getStartHealth();
        this.health = maxHealth;
        this.level = 1;
        this.xp = 0;
        this.gold = heroClass.getStartGold();
        this.killsByMonster = new LinkedHashMap<>();
        this.inventory = new Inventory();
        this.equippedWeapon = heroClass.getStartWeapon();
        this.inventory.addItem(equippedWeapon);
    }

    public void printCharacterSheet() {
        ui.header("Karakterark");
        ui.print("Navn:            " + name);
        ui.print("Klasse:          " + heroClass.getDisplayName());
        ui.print("Level:           " + level);
        ui.print("Liv:             " + health + "/" + maxHealth + " " + ui.healthBar(getHealthPercentage())
                + " " + String.format("%.1f", getHealthPercentage()) + "%");
        ui.print("XP:              " + xp + "/" + xpForNextLevel());
        ui.print("Guld:            " + ui.gold(gold));
        ui.print("Våben:           " + equippedWeapon.getName() + " (" + equippedWeapon.getDescription() + ")");
        ui.print("Items:           " + inventory.size());
        ui.print("Dræbte monstre:  " + getTotalKills() + killSummary());
        ui.print("Status:          " + (isAlive() ? "I live" : "Død"));
        if (isAlive() && isHealthCritical()) {
            ui.print("ADVARSEL:        Dit liv er kritisk lavt!");
        }
        ui.divider();
    }

    @Override
    public void takeDamage(int amount) {
        health -= amount;
        if (health <= 0) {
            health = 0;
            ui.print(name + " tog " + amount + " skade og faldt om... du er død!");
            return;
        }
        ui.print(name + " tog " + amount + " skade og har nu " + health + "/" + maxHealth + " liv tilbage.");
        if (isHealthCritical()) {
            ui.print("Pas på! Dit liv er under 25%, du burde drikke en healing potion!");
        }
    }

    public void heal(int amount) {
        int missingHealth = maxHealth - health;
        int healedAmount = Math.min(amount, missingHealth);
        health += healedAmount;

        ui.print(name + " blev healet " + healedAmount + " liv og har nu " + health + "/" + maxHealth + " liv.");
    }

    public void addGold(double amount) {
        gold += amount;
        ui.print("Du fik " + ui.gold(amount) + " og har nu " + ui.gold(gold) + ".");
    }

    public boolean removeGold(double amount) {
        if (gold < amount) {
            ui.print("Du mangler " + ui.gold(amount - gold) + " for at have råd til det.");
            return false;
        }
        gold -= amount;
        return true;
    }

    public void addXP(int amount) {
        xp += amount;
        ui.print("Du fik " + amount + " XP (" + xp + "/" + xpForNextLevel() + ").");
        while (xp >= xpForNextLevel()) {
            levelUp();
        }
    }

    public void levelUp() {
        xp -= xpForNextLevel();
        level++;
        int oldMaxHealth = maxHealth;
        maxHealth = (int) Math.round(maxHealth * MAX_HEALTH_GROWTH);
        ui.header("Level up");
        ui.print(name + " er nu level " + level + "!");
        ui.print("Maks liv steg fra " + oldMaxHealth + " til " + maxHealth + ".");
        ui.print("Næste level kræver " + xpForNextLevel() + " XP (du har " + xp + ").");
        ui.divider();
    }

    public boolean isHealthCritical() {
        return getHealthPercentage() < CRITICAL_HEALTH_LIMIT;
    }

    @Override
    public boolean isAlive() {
        return health > 0;
    }

    @Override
    public double getHealthPercentage() {
        return (double) health / maxHealth * 100;
    }

    public void printInventory() {
        ui.header("Inventory");
        if (inventory.isEmpty()) {
            ui.print("Dit inventory er tomt.");
            ui.divider();
            return;
        }
        printItemGroup("Våben:", inventory.getWeapons());
        printItemGroup("Potions:", inventory.getPotions());
        ui.print(inventoryTotal());
        ui.divider();
    }


    public int xpForNextLevel() {
        return XP_PER_LEVEL * level;
    }

    @Override
    public Attack selectAttack() {
        return equippedWeapon.selectAttack();
    }

    @Override
    public int damageWith(Attack attack) {
        double levelBonus = 1 + (level - 1) * 0.1;
        return (int) Math.round(attack.damage() * heroClass.getDamageMultiplier() * levelBonus);
    }

    public void equipWeapon(Weapon weapon) {
        if (weapon == equippedWeapon) {
            ui.print("Du bruger allerede " + weapon.getName() + ".");
            return;
        }
        ui.print("Du skiftede fra " + equippedWeapon.getName() + " til " + weapon.getName() + ".");
        equippedWeapon = weapon;
    }

    public void addItem(Item item) {
        inventory.addItem(item);
    }

    public void restoreProgress(int level, int xp, int maxHealth, int health, double gold) {
        this.level = level;
        this.xp = xp;
        this.maxHealth = maxHealth;
        this.health = health;
        this.gold = gold;
    }

    public void restoreKills(String monsterType, int amount) {
        killsByMonster.put(monsterType, amount);
    }

    public void restoreEquippedWeapon(Weapon weapon) {
        this.equippedWeapon = weapon;
    }

    public void countKill(String monsterType) {
        killsByMonster.merge(monsterType, 1, Integer::sum);
    }

    public int getTotalKills() {
        return killsByMonster.values()
                .stream()
                .mapToInt(Integer::intValue)
                .sum();
    }

    public String killSummary() {
        if (killsByMonster.isEmpty()) {
            return "";
        }
        return killsByMonster.entrySet().stream()
                .map(kill -> kill.getKey() + " x" + kill.getValue())
                .collect(Collectors.joining(", ", " (", ")"));
    }

    public Map<String, Integer> getKillsByMonster() {
        return killsByMonster;
    }

    @Override
    public String getDisplayName() {
        return name + " (level " + level + ")";
    }

    public String getName() {
        return name;
    }

    public HeroClass getHeroClass() {
        return heroClass;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Weapon getEquippedWeapon() {
        return equippedWeapon;
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public int getMaxHealth() {
        return maxHealth;
    }

    public int getLevel() {
        return level;
    }

    public int getXp() {
        return xp;
    }

    public double getGold() {
        return gold;
    }

    private void printItemGroup(String title, List<? extends Item> group) {
        if (group.isEmpty()) {
            return;
        }
        ui.print(title);
        for (Item item : group) {
            ui.print("  - " + item.getName() + " | " + item.getDescription() + equippedMarker(item));
        }
    }

    private String equippedMarker(Item item) {
        return item == equippedWeapon ? "  <-- udstyret" : "";
    }

    private String inventoryTotal() {
        String itemWord = inventory.size() == 1 ? "item" : "items";
        return "I alt " + inventory.size() + " " + itemWord
                + " til en værdi af " + ui.gold(inventory.totalValue()) + ".";
    }
}
