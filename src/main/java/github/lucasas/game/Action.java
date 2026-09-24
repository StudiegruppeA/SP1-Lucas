package github.lucasas.game;

public class Action {
    private final String text;
    private final Runnable runnable;

    public Action(String text, Runnable runnable) {
        this.text = text;
        this.runnable = runnable;
    }

    public String getText() {
        return text;
    }

    public void run() {
        runnable.run();
    }
}
