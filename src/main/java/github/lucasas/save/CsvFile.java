package github.lucasas.save;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CsvFile {
    private static final String SEPARATOR = ",";

    private final Path path;

    public CsvFile(String fileName) {
        this.path = Path.of(fileName);
    }

    public boolean exists() {
        return Files.exists(path);
    }

    public void write(List<String[]> rows) throws IOException {
        List<String> lines = new ArrayList<>();
        for (String[] row : rows) {
            lines.add(toLine(row));
        }
        Files.write(path, lines);
    }

    public List<String[]> read() throws IOException {
        List<String[]> rows = new ArrayList<>();
        for (String line : Files.readAllLines(path)) {
            if (!line.isBlank()) {
                rows.add(line.split(SEPARATOR));
            }
        }
        return rows;
    }

    public boolean delete() throws IOException {
        return Files.deleteIfExists(path);
    }

    private String toLine(String[] row) {
        return Arrays.stream(row)
                .map(this::clean)
                .collect(Collectors.joining(SEPARATOR));
    }

    private String clean(String value) {
        return value.replace(SEPARATOR, " ");
    }

    public Path getPath() {
        return path.toAbsolutePath();
    }
}
