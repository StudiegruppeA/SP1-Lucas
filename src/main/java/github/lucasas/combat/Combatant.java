package github.lucasas.combat;

public interface Combatant {

    String getDisplayName();

    int getHealth();

    int getMaxHealth();

    double getHealthPercentage();

    boolean isAlive();

    void takeDamage(int amount);

    Attack selectAttack();

    int damageWith(Attack attack);
}
