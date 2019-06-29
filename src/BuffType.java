public enum BuffType {
    holyBuff,
    negativeArmorBuff,
    poisonBuff,
    weaknessBuff,
    powerBuff,
    infernoBuff,
    disarmBuff,
    stunBuff;

    public String toString() {
        switch (this) {
            case holyBuff:
                return "holyBuff";
            case negativeArmorBuff:
                return "negativeArmorBuff";
            case poisonBuff:
                return "poisonBuff";
            case weaknessBuff:
                return "weaknessBuff";
            case powerBuff:
                return "powerBuff";
            case infernoBuff:
                return "infernoBuff";
            case disarmBuff:
                return "disarmBuff";
            case stunBuff:
                return "stunBuff";
        }
        return null;
    }
}
