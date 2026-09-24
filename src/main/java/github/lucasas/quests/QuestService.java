package github.lucasas.quests;

import github.lucasas.hero.Hero;
import github.lucasas.ui.ConsoleUi;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class QuestService {
    private final ConsoleUi ui;
    private final List<Quest> quests;
    private final Set<String> claimedQuestIds;

    public QuestService(ConsoleUi ui, QuestCatalog questCatalog) {
        this.ui = ui;
        this.quests = questCatalog.createQuests();
        this.claimedQuestIds = new HashSet<>();
    }

    public void checkProgress(Hero hero) {
        for (Quest quest : quests) {
            if (isReadyToClaim(quest, hero)) {
                claim(quest, hero);
                checkProgress(hero);
                return;
            }
        }
    }

    public void printQuests(Hero hero) {
        ui.header("Questlog");
        for (Quest quest : quests) {
            printQuest(quest, hero);
        }
        ui.print("Fuldført: " + getCompletedCount() + "/" + getQuestCount());
        ui.divider();
    }

    public void restoreClaimed(String questId) {
        claimedQuestIds.add(questId);
    }

    public Set<String> getClaimedQuestIds() {
        return claimedQuestIds;
    }

    public int getCompletedCount() {
        return claimedQuestIds.size();
    }

    public int getQuestCount() {
        return quests.size();
    }

    private boolean isReadyToClaim(Quest quest, Hero hero) {
        return !isClaimed(quest) && quest.isCompleted(hero);
    }

    private boolean isClaimed(Quest quest) {
        return claimedQuestIds.contains(quest.getId());
    }

    private void claim(Quest quest, Hero hero) {
        claimedQuestIds.add(quest.getId());
        ui.header("Quest fuldført");
        ui.print(quest.getTitle() + " - " + quest.getDescription());
        hero.addGold(quest.getGoldReward());
        hero.addXP(quest.getXpReward());
        ui.divider();
    }

    private void printQuest(Quest quest, Hero hero) {
        String checkbox = isClaimed(quest) ? "[x]" : "[ ]";
        ui.print(checkbox + " " + quest.getTitle() + " - " + quest.getDescription() + " " + progressText(quest, hero));
        if (!isClaimed(quest)) {
            ui.print("      Belønning: " + rewardText(quest));
        }
    }

    private String progressText(Quest quest, Hero hero) {
        int progress = Math.min(quest.getProgress(hero), quest.getGoal());
        return "(" + progress + "/" + quest.getGoal() + ")";
    }

    private String rewardText(Quest quest) {
        return ui.gold(quest.getGoldReward()) + " og " + quest.getXpReward() + " XP";
    }
}
