package github.lucasas.mobs;

import github.lucasas.combat.Attack;
import github.lucasas.ui.ConsoleUi;

import java.util.List;

public class Skeleton extends Monster {

    public Skeleton(ConsoleUi ui, int level) {
        super(ui, level, 45, 20, 45);
    }

    @Override
    protected List<Attack> createAttacks() {
        return List.of(
                new Attack("Ribbensknogle", 4),
                new Attack("Pil", 6),
                new Attack("Lynpil", 8)
        );
    }

    @Override
    public String getType() {
        return "Skeleton";
    }
}
