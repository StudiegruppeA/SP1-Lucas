package github.lucasas;

import github.lucasas.game.Game;
import github.lucasas.items.ItemCatalog;
import github.lucasas.mobs.MonsterFactory;
import github.lucasas.quests.QuestCatalog;
import github.lucasas.quests.QuestService;
import github.lucasas.save.SaveService;
import github.lucasas.ui.ConsoleUi;

public class HeroMain {
    void main() {
        ConsoleUi ui = new ConsoleUi();
        ItemCatalog itemCatalog = new ItemCatalog();
        Game game = new Game(ui,
                itemCatalog,
                new MonsterFactory(ui),
                new QuestService(ui, new QuestCatalog()),
                new SaveService(ui, itemCatalog));
        game.start();
    }
}
