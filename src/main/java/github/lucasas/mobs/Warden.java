package github.lucasas.mobs;

import github.lucasas.combat.Attack;
import github.lucasas.ui.ConsoleUi;

import java.util.List;

public class Warden extends Monster {

    public Warden(ConsoleUi ui, int level) {
        super(ui, level, 90, 60, 120);
    }

    @Override
    protected List<Attack> createAttacks() {
        return List.of(
                new Attack("Kampråb", 7),
                new Attack("Kraftslag", 11),
                new Attack("Sonic Boom", 15));
    }

    @Override
    public String getType() {
        return "Warden";
    }
}
