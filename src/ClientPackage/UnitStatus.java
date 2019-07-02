package ClientPackage;

public enum UnitStatus {
    stand, run, attack, death, spell;

    @Override
    public String toString() {
        switch (this) {
            case run:
                return "run";
            case death:
                return "death";
            case spell:
                return "spell";
            case stand:
                return "stand";
            case attack:
                return "attack";
        }
        return "";
    }
}
