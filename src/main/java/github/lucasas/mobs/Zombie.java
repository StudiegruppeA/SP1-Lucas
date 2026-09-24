package github.lucasas.mobs;

import github.lucasas.combat.Attack;
import github.lucasas.ui.ConsoleUi;

import java.util.List;

public class Zombie extends Monster {

    public Zombie(ConsoleUi ui, int level) {
        super(ui, level, 30, 12, 30);
    }

    @Override
    protected List<Attack> createAttacks() {
        return List.of(
                new Attack("Tackle", 3),
                new Attack("Eat", 5),
                new Attack("Kick", 4));
    }

    @Override
    public String getType() {
        return "Zombie";
    }
}
