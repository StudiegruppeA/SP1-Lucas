package github.lucasas.items;

import github.lucasas.combat.Attack;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Weapon extends Item {
    private final List<Attack> attacks;
    private final Random random;

    public Weapon(String name, double price, Attack... attacks) {
        super(name, price);
        this.attacks = new ArrayList<>(List.of(attacks));
        this.random = new Random();
    }

    public Attack selectAttack() {
        return attacks.get(random.nextInt(attacks.size()));
    }

    @Override
    public boolean isUnique() {
        return true;
    }

    @Override
    public String getType() {
        return "Våben";
    }

    @Override
    public String getDescription() {
        return "Skade " + getLowestDamage() + "-" + getHighestDamage() + " (" + attacks.size() + " angreb)";
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    private int getLowestDamage() {
        return attacks.stream().min(Comparator.comparingInt(Attack::damage)).get().damage();
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    private int getHighestDamage() {
        return attacks.stream().max(Comparator.comparingInt(Attack::damage)).get().damage();
    }
}
