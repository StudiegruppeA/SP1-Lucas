package github.lucasas;

public class HeroMain {
    void main(String[] args) {
        String name = "Thor";
        double health = 100;
        double maxHealth = 100;
        int level = 10;
        int exp = 4000;
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
    }
}
