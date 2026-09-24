package github.lucasas.mobs;

import github.lucasas.combat.Attack;
import github.lucasas.ui.ConsoleUi;

import java.util.List;

public class Creeper extends Monster {

    public Creeper(ConsoleUi ui, int level) {
        super(ui, level, 25, 26, 55);
    }

    @Override
    protected List<Attack> createAttacks() {
        return List.of(new Attack("Hvæs", 2), new Attack("Eksplosion", 14));
    }

    @Override
    public String getType() {
        return "Creeper";
    }
}
