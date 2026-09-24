package github.lucasas.items;

public class Potion extends Item {
    private final PotionType potionType;
    private final double effectPercentage;

    public Potion(String name, double price, PotionType potionType, double effectPercentage) {
        super(name, price);
        this.potionType = potionType;
        this.effectPercentage = effectPercentage;
    }

    public PotionType getPotionType() {
        return potionType;
    }

    public double getEffectPercentage() {
        return effectPercentage;
    }

    @Override
    public String getType() {
        return "Potion";
    }

    @Override
    public String getDescription() {
        int percentage = (int) Math.round(effectPercentage * 100);
        if (potionType == PotionType.HEALING) {
            return "Healer " + percentage + "% af dit maksimale liv";
        }
        return "Gør " + percentage + "% skade på fjendens maksimale liv";
    }
}
