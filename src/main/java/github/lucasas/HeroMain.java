package github.lucasas;

public class HeroMain {
    void main(String[] args) {
        String name = "Thor";
        double health = 100;
        double maxHealth = 100;
        int level = 1;
        int exp = 6000;
        int money = 156;
        boolean isAlive = true;
        String classType = "Mage";
        String[] inventory = {"Sword", "Hammer", "Health Potion"};

        System.out.println("==== CHARACTER SHEET ====");
        System.out.println("Name: " + name);
        System.out.println("Health: " + health + "/" + maxHealth);
        System.out.println("Level: " + level);
        System.out.println("XP: " + exp);
        System.out.println("Gold: " + money);
        System.out.println("Type: " + classType);
        System.out.println("Alive: " + isAlive);

        System.out.println();
        System.out.println("Inventory (" + inventory.length + " items):");
        for (String item : inventory) {
            System.out.println("- " + item);
        }

        if (exp >= 1000 * level) {
            System.out.println("=== STATUS ===");
            System.out.println("Ready to level up!");
        }

        System.out.println("=== COMBAT ===");
        double damage = 10;
        System.out.println(name + " takes " + damage + " damage!");
        health -= damage;
        System.out.println("Health is now: " + health + "/" + maxHealth);
        if (health > 0) {
            System.out.println(name + " is still alive");
        } else {
            System.out.println(name + " is dead");
        }
    }
}
