package github.lucasas.quests;

import java.util.ArrayList;
import java.util.List;

public class QuestCatalog {

    public List<Quest> createQuests() {
        List<Quest> quests = new ArrayList<>();
        quests.add(new KillQuest("zombie_slayer", "Zombie-jæger", "Zombie", 3, 40, 30));
        quests.add(new KillQuest("skeleton_slayer", "Knogleknuser", "Skeleton", 3, 60, 45));
        quests.add(new KillQuest("creeper_hunter", "Sprængstofekspert", "Creeper", 3, 90, 70));
        quests.add(new KillQuest("warden_hunter", "Wardens skræk", "Warden", 1, 200, 150));
        quests.add(new LevelQuest("veteran", "Veteran", 3, 80, 0));
        quests.add(new LevelQuest("legende", "Legende", 6, 250, 0));
        quests.add(new GoldQuest("rig", "Velhaver", 300, 0, 120));
        return quests;
    }
}
