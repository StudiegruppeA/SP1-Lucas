package github.lucasas.items;

import github.lucasas.hero.Hero;
import github.lucasas.mobs.Monster;

public interface PotionEffect {

    void apply(Hero hero, Monster monster, Potion potion);
}
