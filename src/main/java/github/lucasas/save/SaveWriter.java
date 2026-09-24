package github.lucasas.save;

import github.lucasas.hero.Hero;
import github.lucasas.items.Item;
import github.lucasas.quests.QuestService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SaveWriter {

    public List<String[]> toRows(Hero hero, QuestService questService) {
        List<String[]> rows = new ArrayList<>();
        rows.add(heroRow(hero));
        rows.addAll(itemRows(hero));
        rows.addAll(killRows(hero));
        rows.addAll(questRows(questService));
        return rows;
    }

    private String[] heroRow(Hero hero) {
        return row(RowType.HERO,
                hero.getName(),
                hero.getHeroClass().name(),
                hero.getLevel(),
                hero.getXp(),
                hero.getHealth(),
                hero.getMaxHealth(),
                hero.getGold(),
                hero.getEquippedWeapon().getName());
    }

    private List<String[]> itemRows(Hero hero) {
        List<String[]> rows = new ArrayList<>();
        for (Item item : hero.getInventory().getItems()) {
            if (!isStartWeapon(hero, item)) {
                rows.add(row(RowType.ITEM, item.getName()));
            }
        }
        return rows;
    }

    private boolean isStartWeapon(Hero hero, Item item) {
        return item == hero.getHeroClass().getStartWeapon();
    }

    private List<String[]> killRows(Hero hero) {
        List<String[]> rows = new ArrayList<>();
        for (Map.Entry<String, Integer> kill : hero.getKillsByMonster().entrySet()) {
            rows.add(row(RowType.KILL, kill.getKey(), kill.getValue()));
        }
        return rows;
    }

    private List<String[]> questRows(QuestService questService) {
        List<String[]> rows = new ArrayList<>();
        for (String questId : questService.getClaimedQuestIds()) {
            rows.add(row(RowType.QUEST, questId));
        }
        return rows;
    }

    private String[] row(RowType type, Object... values) {
        List<String> row = new ArrayList<>();
        row.add(type.name());
        for (Object value : values) {
            row.add(String.valueOf(value));
        }
        return row.toArray(new String[0]);
    }
}
