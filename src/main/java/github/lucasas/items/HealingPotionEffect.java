package github.lucasas.items;

import github.lucasas.ui.ConsoleUi;
import github.lucasas.hero.Hero;
import github.lucasas.mobs.Monster;

public class HealingPotionEffect implements PotionEffect {
    private final ConsoleUi ui;

    public HealingPotionEffect(ConsoleUi ui) {
        this.ui = ui;
    }

    @Override
    public void apply(Hero hero, Monster monster, Potion potion) {
        int healing = (int) Math.round(hero.getMaxHealth() * potion.getEffectPercentage());
        ui.print("Du drikker " + potion.getName() + ".");
        hero.heal(healing);
    }
}
