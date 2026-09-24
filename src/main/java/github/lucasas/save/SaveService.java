package github.lucasas.save;

import github.lucasas.hero.Hero;
import github.lucasas.items.ItemCatalog;
import github.lucasas.quests.QuestService;
import github.lucasas.ui.ConsoleUi;

import java.io.IOException;

public class SaveService {
    private final ConsoleUi ui;
    private final CsvFile saveFile;
    private final SaveWriter writer;
    private final SaveReader reader;

    public SaveService(ConsoleUi ui, ItemCatalog itemCatalog) {
        this.ui = ui;
        this.saveFile = new CsvFile("savegame.csv");
        this.writer = new SaveWriter();
        this.reader = new SaveReader(ui, itemCatalog);
    }

    public boolean saveExists() {
        return saveFile.exists();
    }

    public void save(Hero hero, QuestService questService) {
        try {
            saveFile.write(writer.toRows(hero, questService));
            ui.print("Spillet er gemt i " + saveFile.getPath());
        } catch (IOException exception) {
            ui.print("Kunne ikke gemme spillet: " + exception.getMessage());
        }
    }

    public Hero load(QuestService questService) {
        try {
            Hero hero = reader.toHero(saveFile.read(), questService);
            if (hero == null) {
                ui.print("Den gemte fil indeholder ingen helt.");
                return null;
            }
            ui.print("Indlæste " + hero.getName() + " fra den gemte fil.");
            return hero;
        } catch (IOException | IllegalArgumentException | IndexOutOfBoundsException exception) {
            ui.print("Kunne ikke indlæse den gemte fil: " + exception.getMessage());
            return null;
        }
    }

    public void delete() {
        try {
            if (saveFile.delete()) {
                ui.print("Din gemte fil er slettet - din helt er jo død.");
            }
        } catch (IOException exception) {
            ui.print("Kunne ikke slette den gemte fil: " + exception.getMessage());
        }
    }
}
