package github.lucasas.mobs;

import github.lucasas.ui.ConsoleUi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MonsterFactory {
    private final ConsoleUi ui;
    private final Random random = new Random();
    private final Map<String, MonsterSpawner> spawners = new HashMap<>();
    private final Map<String, Integer> unlockLevels = new HashMap<>();

    public MonsterFactory(ConsoleUi ui) {
        this.ui = ui;
        register("Zombie", 1, Zombie::new);
        register("Skeleton", 2, Skeleton::new);
        register("Creeper", 3, Creeper::new);
        register("Warden", 5, Warden::new);
    }

    public final void register(String type, int minHeroLevel, MonsterSpawner spawner) {
        spawners.put(type, spawner);
        unlockLevels.put(type, minHeroLevel);
    }

    public Monster spawn(int heroLevel) {
        int monsterLevel = Math.max(1, heroLevel + random.nextInt(3) - 1);
        List<String> types = getAvailableTypes(heroLevel);
        String type = types.get(random.nextInt(types.size()));
        return spawnType(type, monsterLevel);
    }

    public Monster spawnType(String type, int monsterLevel) {
        MonsterSpawner spawner = spawners.get(type);
        if (spawner == null) {
            return null;
        }
        return spawner.spawn(ui, monsterLevel);
    }

    public List<String> getAvailableTypes(int heroLevel) {
        return unlockLevels.entrySet().stream()
                .filter(entry -> heroLevel >= entry.getValue())
                .sorted(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .toList();
    }
}
