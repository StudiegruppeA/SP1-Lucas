package github.lucasas.quests;

import github.lucasas.hero.Hero;

public class LevelQuest extends Quest {

    public LevelQuest(String id, String title, int targetLevel, double goldReward, int xpReward) {
        super(id, title, targetLevel, goldReward, xpReward);
    }

    @Override
    public int getProgress(Hero hero) {
        return hero.getLevel();
    }

    @Override
    public String getDescription() {
        return "Nå level " + getGoal();
    }
}
