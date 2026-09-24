package github.lucasas.save;

public enum RowType {
    HERO,
    ITEM,
    KILL,
    QUEST;

    public boolean matches(String type) {
        return name().equals(type);
    }
}
