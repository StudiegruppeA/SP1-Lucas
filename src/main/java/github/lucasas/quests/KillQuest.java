package github.lucasas.quests;

import github.lucasas.hero.Hero;

public class KillQuest extends Quest {
    private final String monsterType;

    public KillQuest(String id, String title, String monsterType, int goal, double goldReward, int xpReward) {
        super(id, title, goal, goldReward, xpReward);
        this.monsterType = monsterType;
    }

    @Override
    public int getProgress(Hero hero) {
        return hero.getKillsByMonster().getOrDefault(monsterType, 0);
    }

    @Override
    public String getDescription() {
        return "Dræb " + getGoal() + " x " + monsterType;
    }
}
