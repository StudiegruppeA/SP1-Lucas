package github.lucasas.save;

import github.lucasas.hero.Hero;
import github.lucasas.hero.HeroClass;
import github.lucasas.items.ItemCatalog;
import github.lucasas.quests.QuestService;
import github.lucasas.ui.ConsoleUi;

import java.util.List;

public class SaveReader {
    private final ConsoleUi ui;
    private final ItemCatalog itemCatalog;

    public SaveReader(ConsoleUi ui, ItemCatalog itemCatalog) {
        this.ui = ui;
        this.itemCatalog = itemCatalog;
    }

    public Hero toHero(List<String[]> rows, QuestService questService) {
        String[] heroRow = findHeroRow(rows);
        if (heroRow == null) {
            return null;
        }
        Hero hero = readHero(heroRow);
        for (String[] row : rows) {
            readRow(row, hero, questService);
        }
        equipWeapon(hero, heroRow[8]);
        return hero;
    }

    private String[] findHeroRow(List<String[]> rows) {
        for (String[] row : rows) {
            if (RowType.HERO.matches(row[0])) {
                return row;
            }
        }
        return null;
    }

    private Hero readHero(String[] row) {
        String name = row[1];
        HeroClass heroClass = HeroClass.valueOf(row[2]);
        int level = Integer.parseInt(row[3]);
        int xp = Integer.parseInt(row[4]);
        int health = Integer.parseInt(row[5]);
        int maxHealth = Integer.parseInt(row[6]);
        double gold = Double.parseDouble(row[7]);

        Hero hero = new Hero(ui, name, heroClass);
        hero.restoreProgress(level, xp, maxHealth, health, gold);
        return hero;
    }

    private void readRow(String[] row, Hero hero, QuestService questService) {
        switch (RowType.valueOf(row[0])) {
            case HERO -> { }
            case ITEM -> readItem(hero, row[1]);
            case KILL -> hero.restoreKills(row[1], Integer.parseInt(row[2]));
            case QUEST -> questService.restoreClaimed(row[1]);
        }
    }

    private void readItem(Hero hero, String itemName) {
        itemCatalog.findByName(itemName).ifPresentOrElse(hero::addItem,
                () -> ui.print("Kendte ikke itemet " + itemName + " det blev sprunget over."));
    }

    private void equipWeapon(Hero hero, String weaponName) {
        hero.getInventory().findWeaponByName(weaponName)
                .ifPresent(hero::restoreEquippedWeapon);
    }
}
