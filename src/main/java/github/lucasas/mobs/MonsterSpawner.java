package github.lucasas.mobs;

import github.lucasas.ui.ConsoleUi;

public interface MonsterSpawner {
    Monster spawn(ConsoleUi ui, int level);
}
