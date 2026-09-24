package github.lucasas.game;

import github.lucasas.items.*;
import github.lucasas.ui.ConsoleUi;
import github.lucasas.combat.Attack;
import github.lucasas.combat.Combatant;
import github.lucasas.hero.Hero;
import github.lucasas.mobs.Monster;
import github.lucasas.mobs.MonsterFactory;
import github.lucasas.quests.QuestService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Arena {
    private final ConsoleUi ui;
    private final Hero hero;
    private final MonsterFactory monsterFactory;
    private final QuestService questService;
    private final Map<PotionType, PotionEffect> potionEffects;
    private boolean leftArena;
    private boolean turnUsed;

    public Arena(ConsoleUi ui, Hero hero, MonsterFactory monsterFactory, QuestService questService) {
        this.ui = ui;
        this.hero = hero;
        this.monsterFactory = monsterFactory;
        this.questService = questService;
        this.potionEffects = new HashMap<>();
        this.potionEffects.put(PotionType.HEALING, new HealingPotionEffect(ui));
        this.potionEffects.put(PotionType.DAMAGE, new DamagePotionEffect(ui));
    }

    public void enter() {
        leftArena = false;
        ui.header("Arenaen");
        ui.print("Portene åbner sig og publikum råber dit navn, " + hero.getName() + "!");
        ui.print("På dit level kan du møde: " + String.join(", ", monsterFactory.getAvailableTypes(hero.getLevel())));
        while (hero.isAlive() && !leftArena) {
            Monster monster = monsterFactory.spawn(hero.getLevel());
            ui.blank();
            ui.print("En " + monster.getDisplayName() + " træder ind i arenaen med " + monster.getMaxHealth() + " liv!");
            fight(monster);
        }
        if (leftArena && hero.isAlive()) {
            ui.print("Du forlader arenaen med " + hero.getHealth() + "/" + hero.getMaxHealth() + " liv.");
        }
    }

    private void fight(Monster monster) {
        while (hero.isAlive() && monster.isAlive() && !leftArena) {
            printRoundStatus(monster);
            heroTurn(monster);
            if (!monster.isAlive()) {
                victory(monster);
                return;
            }
            if (leftArena) {
                ui.print("Du flygtede fra kampen mod " + monster.getDisplayName() + ".");
                return;
            }
            performAttack(monster, hero);
        }
    }

    private void printRoundStatus(Monster monster) {
        ui.divider();
        printStatus(hero);
        printStatus(monster);
    }

    private void printStatus(Combatant combatant) {
        ui.print(combatant.getDisplayName() + ": " + combatant.getHealth() + "/" + combatant.getMaxHealth()
                + " liv " + ui.healthBar(combatant.getHealthPercentage()));
    }

    private void heroTurn(Monster monster) {
        turnUsed = false;
        while (!turnUsed && !leftArena && hero.isAlive() && monster.isAlive()) {
            Menu menu = new Menu(ui, "Din tur");
            menu.add("Angrib med " + hero.getEquippedWeapon().getName(), () -> attack(monster));
            if (!hero.getInventory().getPotions().isEmpty()) {
                menu.add("Brug potion", () -> usePotion(monster));
            }
            menu.add("Skift våben", this::switchWeapon);
            menu.add("Se karakterark", hero::printCharacterSheet);
            menu.add("Se quests", () -> questService.printQuests(hero));
            menu.add("Flygt fra arenaen", this::flee);
            menu.show();
        }
    }

    private void attack(Monster monster) {
        turnUsed = true;
        performAttack(hero, monster);
    }

    private void performAttack(Combatant attacker, Combatant defender) {
        Attack attack = attacker.selectAttack();
        int damage = attacker.damageWith(attack);
        ui.print(attacker.getDisplayName() + " bruger " + attack.name() + "!");
        defender.takeDamage(damage);
    }

    private void usePotion(Monster monster) {
        Menu menu = new Menu(ui, "Vælg en potion");
        for (Potion potion : hero.getInventory().getPotions()) {
            menu.add(potion.getName() + " | " + potion.getDescription(), () -> applyPotion(potion, monster));
        }
        menu.add("Fortryd", () -> { });
        menu.show();
    }

    private void applyPotion(Potion potion, Monster monster) {
        turnUsed = true;
        potionEffects.get(potion.getPotionType()).apply(hero, monster, potion);
        hero.getInventory().removeItem(potion);
    }

    private void switchWeapon() {
        List<Weapon> weapons = hero.getInventory().getWeapons();
        if (weapons.size() == 1) {
            ui.print("Du har kun et våben i dit inventory.");
            return;
        }
        Menu menu = new Menu(ui, "Vælg et våben");
        for (Weapon weapon : weapons) {
            menu.add(weapon.getName() + " | " + weapon.getDescription() + equippedMarker(weapon), () -> hero.equipWeapon(weapon));
        }
        menu.add("Fortryd", () -> { });
        menu.show();
    }

    private String equippedMarker(Weapon weapon) {
        return weapon == hero.getEquippedWeapon() ? "  <-- udstyret" : "";
    }

    private void flee() {
        leftArena = true;
    }

    private void victory(Monster monster) {
        ui.header("Sejr");
        ui.print("Du dræbte " + monster.getDisplayName() + "!");
        hero.countKill(monster.getType());
        hero.addGold(monster.getGoldReward());
        hero.addXP(monster.getXpReward());
        questService.checkProgress(hero);

        new Menu(ui, "Hvad nu?")
                .add("Kæmp videre", () -> ui.print("Du gør dig klar til næste modstander..."))
                .add("Forlad arenaen", this::flee)
                .show();
    }
}
