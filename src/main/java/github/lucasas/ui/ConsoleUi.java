package github.lucasas.ui;

import java.util.Scanner;
import java.util.regex.Pattern;

public class ConsoleUi {
    private static final int BAR_LENGTH = 20;
    private static final Pattern NUMBER_PATTERN = Pattern.compile("\\d{1,9}");

    private final Scanner scanner;

    public ConsoleUi() {
        this.scanner = new Scanner(System.in);
    }

    public void print(String message) {
        System.out.println(message);
    }

    public void blank() {
        System.out.println();
    }

    public void header(String title) {
        blank();
        print("===== " + title.toUpperCase() + " =====");
    }

    public void divider() {
        print("--------------------------------");
    }

    public String readNonEmptyLine(String prompt) {
        while (true) {
            String input = readLine(prompt);
            if (!input.isEmpty()) {
                return input;
            }
            print("Du skal skrive noget!");
        }
    }

    public int readInt(String prompt, int min, int max) {
        while (true) {
            String input = readLine(prompt);
            if (!NUMBER_PATTERN.matcher(input).matches()) {
                print("'" + input + "' er ikke et tal. Prøv igen.");
                continue;
            }
            int value = Integer.parseInt(input);
            if (value >= min && value <= max) {
                return value;
            }
            print("Skriv et tal mellem " + min + " og " + max + ".");
        }
    }

    public boolean confirm(String prompt) {
        while (true) {
            String input = readLine(prompt + " (j/n)").toLowerCase();
            if (input.equals("j") || input.equals("ja")) {
                return true;
            }
            if (input.equals("n") || input.equals("nej")) {
                return false;
            }
            print("Svar med j eller n.");
        }
    }

    public String gold(double amount) {
        return String.format("%.1f guld", amount);
    }

    public String healthBar(double percentage) {
        int filled = Math.clamp(Math.round(percentage / 100 * BAR_LENGTH), 0, BAR_LENGTH);
        int empty = BAR_LENGTH - filled;
        return "[" + "#".repeat(filled) + "-".repeat(empty) + "]";
    }

    private String readLine(String prompt) {
        System.out.print(prompt + " ");
        return scanner.nextLine().trim();
    }
}
