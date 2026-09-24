package github.lucasas.items;

import github.lucasas.combat.Attack;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ItemCatalog {

    public Map<ShopCategory, List<Item>> createShopStock() {
        Map<ShopCategory, List<Item>> stock = new EnumMap<>(ShopCategory.class);
        stock.put(ShopCategory.WEAPONS, createWeapons());
        stock.put(ShopCategory.HEALING_POTIONS, createHealingPotions());
        stock.put(ShopCategory.DAMAGE_POTIONS, createDamagePotions());
        return stock;
    }

    public Optional<Item> findByName(String name) {
        return createShopStock().values().stream()
                .flatMap(List::stream)
                .filter(item -> item.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    private List<Item> createWeapons() {
        return List.of(
                new Weapon("Iron Sword", 45,
                        new Attack("Slash", 7),
                        new Attack("Backhand", 6),
                        new Attack("Thrust", 9)),
                new Weapon("Mjolnir", 95,
                        new Attack("Summon Lightning", 12),
                        new Attack("Thunder Smash", 10),
                        new Attack("Hammer Throw", 8)),
                new Weapon("Necrosword", 160,
                        new Attack("Dark Soul", 15),
                        new Attack("Void Slash", 17),
                        new Attack("Shadow Blast", 13)),
                new Weapon("Odinsword", 260,
                        new Attack("Giant Smash", 20),
                        new Attack("Power Throw", 24),
                        new Attack("Rune Strike", 18)));
    }

    private List<Item> createHealingPotions() {
        return List.of(
                new Potion("Healing I", 20, PotionType.HEALING, 0.30),
                new Potion("Healing II", 38, PotionType.HEALING, 0.55),
                new Potion("Healing III", 65, PotionType.HEALING, 1.00));
    }

    private List<Item> createDamagePotions() {
        return List.of(
                new Potion("Damage I", 28, PotionType.DAMAGE, 0.20),
                new Potion("Damage II", 52, PotionType.DAMAGE, 0.35));
    }
}
