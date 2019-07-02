package ClientPackage;

public enum UnitEffectType {
    bloodDrop,
    spawnEffect;

    @Override
    public String toString() {
        switch (this) {
            case bloodDrop:
                return "bloodDrop";
            case spawnEffect:
                return "spawnEffect";
            default:
                System.out.println("unhandled case!!!!!");
        }
        return null;
    }
}
