package github.lucasas.quests;

import github.lucasas.hero.Hero;

public abstract class Quest {
    private final String id;
    private final String title;
    private final int goal;
    private final double goldReward;
    private final int xpReward;

    protected Quest(String id, String title, int goal, double goldReward, int xpReward) {
        this.id = id;
        this.title = title;
        this.goal = goal;
        this.goldReward = goldReward;
        this.xpReward = xpReward;
    }

    public abstract int getProgress(Hero hero);

    public abstract String getDescription();

    public boolean isCompleted(Hero hero) {
        return getProgress(hero) >= goal;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getGoal() {
        return goal;
    }

    public double getGoldReward() {
        return goldReward;
    }

    public int getXpReward() {
        return xpReward;
    }
}
