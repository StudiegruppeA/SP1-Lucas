package github.lucasas.mobs;

import github.lucasas.combat.Attack;
import github.lucasas.combat.Combatant;
import github.lucasas.ui.ConsoleUi;

import java.util.List;
import java.util.Random;

public abstract class Monster implements Combatant {
    private static final double HEALTH_GROWTH = 0.25;
    private static final double DAMAGE_GROWTH = 0.15;
    private static final double REWARD_GROWTH = 0.30;

    private final ConsoleUi ui;
    private final int level;
    private final int maxHealth;
    private final double damageScale;
    private final double goldReward;
    private final int xpReward;
    private final List<Attack> attacks;
    private final Random random;
    private int health;

    Monster(ConsoleUi ui, int level, int baseHealth, double baseGold, int baseXp) {
        this.ui = ui;
        this.level = level;
        double levelSteps = level - 1;
        this.maxHealth = (int) Math.round(baseHealth * (1 + levelSteps * HEALTH_GROWTH));
        this.health = maxHealth;
        this.damageScale = 1 + levelSteps * DAMAGE_GROWTH;
        this.goldReward = baseGold * (1 + levelSteps * REWARD_GROWTH);
        this.xpReward = (int) Math.round(baseXp * (1 + levelSteps * REWARD_GROWTH));
        this.attacks = createAttacks();
        this.random = new Random();
    }

    abstract List<Attack> createAttacks();

    public abstract String getType();

    @Override
    public Attack selectAttack() {
        return attacks.get(random.nextInt(attacks.size()));
    }

    @Override
    public int damageWith(Attack attack) {
        return (int) Math.round(attack.damage() * damageScale);
    }

    @Override
    public void takeDamage(int amount) {
        health -= amount;
        if (health <= 0) {
            health = 0;
            ui.print(getDisplayName() + " tog " + amount + " skade og faldt om!");
            return;
        }
        ui.print(getDisplayName() + " tog " + amount + " skade og har " + health + "/" + maxHealth + " liv tilbage.");
    }

    @Override
    public boolean isAlive() {
        return health > 0;
    }

    @Override
    public double getHealthPercentage() {
        return (double) health / maxHealth * 100;
    }

    @Override
    public String getDisplayName() {
        return getType() + " (level " + level + ")";
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public int getMaxHealth() {
        return maxHealth;
    }

    public double getGoldReward() {
        return goldReward;
    }

    public int getXpReward() {
        return xpReward;
    }
}
