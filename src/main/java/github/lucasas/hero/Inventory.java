package github.lucasas.hero;

import github.lucasas.items.Item;
import github.lucasas.items.Potion;
import github.lucasas.items.Weapon;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Inventory {
    private final List<Item> items;

    public Inventory() {
        items = new ArrayList<>();
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public boolean containsName(String name) {
        return items.stream()
                .anyMatch(item -> item.getName().equalsIgnoreCase(name));
    }

    public Optional<Weapon> findWeaponByName(String name) {
        return getWeapons().stream()
                .filter(weapon -> weapon.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    public List<Weapon> getWeapons() {
        return items.stream()
                .filter(Weapon.class::isInstance)
                .map(Weapon.class::cast)
                .toList();
    }

    public List<Potion> getPotions() {
        return items.stream()
                .filter(Potion.class::isInstance)
                .map(Potion.class::cast)
                .toList();
    }

    public List<Item> getItems() {
        return items;
    }

    public int size() {
        return items.size();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public double totalValue() {
        return items.stream()
                .mapToDouble(Item::getPrice)
                .sum();
    }
}
