package github.lucasas.items;

import github.lucasas.ui.ConsoleUi;
import github.lucasas.hero.Hero;
import github.lucasas.mobs.Monster;

public class DamagePotionEffect implements PotionEffect {
    private final ConsoleUi ui;

    public DamagePotionEffect(ConsoleUi ui) {
        this.ui = ui;
    }

    @Override
    public void apply(Hero hero, Monster monster, Potion potion) {
        int damage = (int) Math.round(monster.getMaxHealth() * potion.getEffectPercentage());
        ui.print("Du kaster " + potion.getName() + " efter " + monster.getType() + "!");
        monster.takeDamage(damage);
    }
}
