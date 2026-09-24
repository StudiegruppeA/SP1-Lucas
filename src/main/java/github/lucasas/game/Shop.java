package github.lucasas.game;

import github.lucasas.ui.ConsoleUi;
import github.lucasas.hero.Hero;
import github.lucasas.items.Item;
import github.lucasas.items.ShopCategory;
import github.lucasas.items.ItemCatalog;
import github.lucasas.items.Weapon;

import java.util.List;
import java.util.Map;

public class Shop {
    private final ConsoleUi ui;
    private final Hero hero;
    private final Map<ShopCategory, List<Item>> stock;
    private boolean leaveShop;

    public Shop(ConsoleUi ui, Hero hero, ItemCatalog itemCatalog) {
        this.ui = ui;
        this.hero = hero;
        this.stock = itemCatalog.createShopStock();
    }

    public void visit() {
        leaveShop = false;
        ui.header("Butikken");
        ui.print("Købmanden kigger op: \"Velkommen " + hero.getName() + "! Hvad kan jeg friste med?\"");
        while (!leaveShop) {
            Menu menu = new Menu(ui, "Butik: du har " + ui.gold(hero.getGold()));
            for (ShopCategory category : stock.keySet()) {
                menu.add(category.getDisplayName(), () -> browse(category));
            }
            menu.add("Se dit inventory", hero::printInventory);
            menu.add("Forlad butikken", () -> leaveShop = true);
            menu.show();
        }
        ui.print("Du forlader butikken.");
    }

    private void browse(ShopCategory category) {
        Menu menu = new Menu(ui, category.getDisplayName() + " til salg");
        for (Item item : stock.get(category)) {
            menu.add(item.getName() + " | " + item.getDescription() + " | " + ui.gold(item.getPrice()) + ownedMarker(item), () -> buy(item));
        }
        menu.add("Fortryd", () -> { });
        menu.show();
    }

    private String ownedMarker(Item item) {
        return isAlreadyOwned(item) ? "  (ejer allerede)" : "";
    }

    private void buy(Item item) {
        if (isAlreadyOwned(item)) {
            ui.print("Du har allerede " + item.getName() + " i dit inventory.");
            return;
        }
        if (!hero.removeGold(item.getPrice())) {
            return;
        }
        hero.addItem(item);
        ui.print("Du købte " + item.getName() + " for " + ui.gold(item.getPrice()) + " og har " + ui.gold(hero.getGold()) + " tilbage.");
        if (item instanceof Weapon weapon && ui.confirm("Vil du udstyre den med det samme?")) {
            hero.equipWeapon(weapon);
        }
    }

    private boolean isAlreadyOwned(Item item) {
        return item.isUnique() && hero.getInventory().containsName(item.getName());
    }
}
