package github.lucasas.quests;

import github.lucasas.hero.Hero;

public class GoldQuest extends Quest {

    public GoldQuest(String id, String title, int targetGold, double goldReward, int xpReward) {
        super(id, title, targetGold, goldReward, xpReward);
    }

    @Override
    public int getProgress(Hero hero) {
        return (int) hero.getGold();
    }

    @Override
    public String getDescription() {
        return "Hav " + getGoal() + " guld på samme tid";
    }
}
