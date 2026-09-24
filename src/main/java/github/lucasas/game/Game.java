package github.lucasas.game;

import github.lucasas.ui.ConsoleUi;
import github.lucasas.hero.Hero;
import github.lucasas.hero.HeroClass;
import github.lucasas.items.ItemCatalog;
import github.lucasas.mobs.MonsterFactory;
import github.lucasas.quests.QuestService;
import github.lucasas.save.SaveService;

public class Game {
    private static final double REST_COST = 25;
    private static final int HEALTH_PER_REST = 40;

    private final ConsoleUi ui;
    private final ItemCatalog itemCatalog;
    private final MonsterFactory monsterFactory;
    private final QuestService questService;
    private final SaveService saveService;
    private Hero hero;
    private Shop shop;
    private Arena arena;
    private HeroClass selectedClass;
    private boolean continueFromSave;
    private boolean running;

    public Game(ConsoleUi ui, ItemCatalog itemCatalog, MonsterFactory monsterFactory, QuestService questService,
                SaveService saveService) {
        this.ui = ui;
        this.itemCatalog = itemCatalog;
        this.monsterFactory = monsterFactory;
        this.questService = questService;
        this.saveService = saveService;
    }

    public void start() {
        printIntro();
        hero = setupHero();
        shop = new Shop(ui, hero, itemCatalog);
        arena = new Arena(ui, hero, monsterFactory, questService);
        hero.printCharacterSheet();
        questService.checkProgress(hero);
        running = true;
        while (running && hero.isAlive()) {
            mainMenu().show();
        }
        if (!hero.isAlive()) {
            printGameOver();
            saveService.delete();
            return;
        }
        printGoodbye();
    }

    private void printIntro() {
        ui.header("Herobuilder");
        ui.print("Velkommen til Herobuilder!");
        ui.print("Byg din helt, løs quests, og overlev så længe du kan i arenaen.");
        ui.divider();
    }

    private Hero setupHero() {
        if (!saveService.saveExists()) {
            return createHero();
        }
        continueFromSave = false;
        new Menu(ui, "Der findes et gemt spil")
                .add("Fortsæt hvor du slap", () -> continueFromSave = true)
                .add("Start et nyt spil", () -> continueFromSave = false)
                .show();
        if (!continueFromSave) {
            return createHero();
        }
        Hero loadedHero = saveService.load(questService);
        if (loadedHero != null) {
            return loadedHero;
        }
        ui.print("Der skete en fejl, starter et nyt spil.");
        return createHero();
    }

    private Hero createHero() {
        String name = ui.readNonEmptyLine("Hvad hedder din helt?").replace(",", " ");
        Menu menu = new Menu(ui, "Vælg din klasse");
        for (HeroClass heroClass : HeroClass.values()) {
            menu.add(heroClass.getDisplayName() + " - " + heroClass.getDescription(),
                    () -> selectedClass = heroClass);
        }
        menu.show();
        ui.blank();
        ui.print(name + " træder frem som " + selectedClass.getDisplayName() + "!");
        return new Hero(ui, name, selectedClass);
    }

    private Menu mainMenu() {
        return new Menu(ui, "Hovedmenu: " + hero.getName() + " (level " + hero.getLevel() + ", "
                + ui.gold(hero.getGold()) + ", quests " + questService.getCompletedCount() + "/"
                + questService.getQuestCount() + ")")
                .add("Gå i arenaen", () -> arena.enter())
                .add("Besøg butikken", () -> shop.visit())
                .add("Se karakterark", hero::printCharacterSheet)
                .add("Se inventory", hero::printInventory)
                .add("Se quests", () -> questService.printQuests(hero))
                .add("Hvil på kroen (" + ui.gold(REST_COST) + " for " + HEALTH_PER_REST + " liv)", this::rest)
                .add("Gem spillet", () -> saveService.save(hero, questService))
                .add("Afslut spillet", this::quit);
    }

    private void rest() {
        ui.header("Kroen");
        if (hero.getHealth() >= hero.getMaxHealth()) {
            ui.print("Kroværten smiler: \"Du har jo fuldt liv, spar dine penge!\"");
            return;
        }
        if (!hero.removeGold(REST_COST)) {
            ui.print("Kroværten peger på døren.");
            return;
        }
        ui.print("Du sover godt og vågner udhvilet.");
        hero.heal(HEALTH_PER_REST);
        ui.print("Du har " + ui.gold(hero.getGold()) + " tilbage.");
    }

    private void quit() {
        if (ui.confirm("Vil du gemme dit spil før du stopper?")) {
            saveService.save(hero, questService);
        }
        running = false;
    }

    private void printGameOver() {
        ui.header("Game over");
        ui.print(hero.getName() + " faldt i arenaen.");
        ui.print("Level nået:         " + hero.getLevel());
        ui.print("Monstre dræbt:      " + hero.getTotalKills() + hero.killSummary());
        ui.print("Quests fuldført:    " + questService.getCompletedCount() + "/" + questService.getQuestCount());
        ui.print("Guld tilbage:       " + ui.gold(hero.getGold()));
        ui.print("Items i inventory:  " + hero.getInventory().size());
        ui.divider();
        ui.print("Tak for spillet, start forfra for at prøve igen!");
    }

    private void printGoodbye() {
        ui.header("Farvel");
        hero.printCharacterSheet();
        questService.printQuests(hero);
        ui.print("Tak for spillet, " + hero.getName() + "!");
    }
}
