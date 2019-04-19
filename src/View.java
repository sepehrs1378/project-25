import java.util.List;

public class View {
    private static View ourInstance = new View();

    public static View getInstance() {
        return ourInstance;
    }

    private View() {
    }

    public void printError(ErrorType errorType){
        System.out.println(errorType.getMessage());
    }

    public void printHelp(HelpType helpType){
        System.out.println(helpType.getMessage());
    }

    public void showInfoOfCards(List<Card> cards){
        System.out.println("Heroes:");
        for (int i = 0; i < cards.size(); i++){
            if (cards.get(i) instanceof Unit){
                Unit unit = (Unit)cards.get(i);
                if (unit.getHeroOrMinion().equals("Hero")){
                    System.out.println("1 : Name : " + unit.getCardID() + " - AP : " + unit.getAp() + " - HP : " +
                            unit.getHp() + " - Class : " + unit.getTypeOfAttack() + " - Special power : " +
                            unit.getStringSpecialPower());
                }
            }
        }
        System.out.println("Items:");
        for (int i = 0; i < cards.size(); i++){
            if (cards.get(i) instanceof  Item){
                Item item = (Item) cards.get(i);
                System.out.println(i + " : Name : " + item.getItemID() + " - Desc : " + item.getDesc());
            }
        }
        System.out.println("Cards:");
        for (int i = 0; i < cards.size(); i++){
            if (cards.get(i) instanceof  Spell){
                Spell spell = (Spell) cards.get(i);
                System.out.println(i + " : Type : Spell - Name : " + spell.getName() + " - MP : " + spell.getMana() +
                        " - Desc : " + spell.getDescription());
            }else if(cards.get(i) instanceof Unit){
                Unit unit = (Unit) cards.get(i);
                if (unit.getHeroOrMinion().equals("Minion")){
                    System.out.println(i + " : Type : Minion - Name : " + unit.getName() + " - Class : " +
                            unit.getTypeOfAttack() + " - AP : " + unit.getAp() + " - HP : " + unit.getHp() +
                            " - MP : " + unit.getMana() + " - Special power : " + unit.getStringSpecialPower());
                }
            }
        }
        //todo since this method is general, is it possible to make the code more efficient by putting it in a separate
        //todo Controller class?
    }
}



//    Unit unit = (Unit)cards.get(i);
//                    System.out.println("Heroes:");
//                            System.out.println(i + " : Name : " + unit.getCardID() + " - AP : " + unit.getAp() +
//                            " - HP : " + unit.getHp() + " - Class : " + unit.getTypeOfAttack() + "- Special Power: "
//                            + unit.getStringSpecialPower());

 2 : Type : Minion - Name : Dex – Class: Melee - AP : 3 - HP : 5 - MP : 1 Special power : Heal 1 allied minion on spawn
