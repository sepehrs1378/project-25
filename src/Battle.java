public class Battle {
    private static Battle currentBattle;


    public static Battle getCurrentBattle() {
        return currentBattle;
    }

    public static void setCurrentBattle(Battle currentBattle) {
        Battle.currentBattle = currentBattle;
    }


}
