import java.util.List;

public class View {
    private static View ourInstance = new View();

    public static View getInstance() {
        return ourInstance;
    }

    private View() {
    }

    public void printOutputMessage(OutputMessageType outputMessageType) {
        System.out.println(outputMessageType.getMessage());
    }

    public void printHelp(HelpType helpType) {
        System.out.println(helpType.getMessage());
    }

    public void showInfoOfCards(List<Card> cards) {
        System.out.println("Heroes:");
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i) instanceof Unit) {
                Unit unit = (Unit) cards.get(i);
                if (unit.getHeroOrMinion().equals("Hero")) {
                    System.out.println("1 : Name : " + unit.getCardID() + " - AP : " + unit.getAp() + " - HP : " +
                            unit.getHp() + " - Class : " + unit.getTypeOfAttack() + " - Special power : " +
                            unit.getStringSpecialPower());
                }
            }
        }
        System.out.println("Items:");
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i) instanceof Item) {
                Item item = (Item) cards.get(i);
                System.out.println(i + " : Name : " + item.getItemID() + " - Desc : " + item.getDesc());
            }
        }
        System.out.println("Cards:");
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i) instanceof Spell) {
                Spell spell = (Spell) cards.get(i);
                System.out.println(i + " : Type : Spell - Name : " + spell.getName() + " - MP : " + spell.getMana() +
                        " - Desc : " + spell.getDescription());
            } else if (cards.get(i) instanceof Unit) {
                Unit unit = (Unit) cards.get(i);
                if (unit.getHeroOrMinion().equals("Minion")) {
                    System.out.println(i + " : Type : Minion - Name : " + unit.getName() + " - Class : " +
                            unit.getTypeOfAttack() + " - AP : " + unit.getAp() + " - HP : " + unit.getHp() +
                            " - MP : " + unit.getMana() + " - Special power : " + unit.getStringSpecialPower());
                }
            }
        }
        //todo since this method is general, is it possible to make the code more efficient by putting it in a separate
        //todo Controller class? (i.e. the part that isn't about sout)
    }

    public void printContentsOfAList(List list) {
        System.out.println(list);

    }
    public void showUsers(List<Account> users,String currentUserName){
        for(Account account:users){
            if(!account.getUsername().equals(currentUserName))
                System.out.println(account.getUsername());
        }
    }
    public void showLeaderboard(List<Account> accounts) {
        int counter = 1;
        for (Account account : accounts) {
            System.out.println(counter + "- Username: " + account.getUsername() +
                    " - Wins: " + account.getNumberOfWins());
            counter++;
        }
    }

    public void showCollection(PlayerCollection playerCollection) {

    }

    public void showHeroInfo(Unit hero) {
        System.out.println("Name: " + hero.getId() + " - AP: " + hero.getAp() +
                " - HP: " + hero.getHp() + " - Class: " + hero.getUnitClass() +
                " - Special Power: " + hero.getSpecialPower().getDescription() +
                " - Sell Cost: " + hero.getPrice());
    }

    public void showCardInfo(Card card) {
        //todo
    }

    public void showItemInfo(Item item) {
        if (item instanceof Usable) {
            Usable usable = (Usable) item;
            System.out.println("Name: " + usable.getId() + " - Desc: " +
                    usable.getDescription() + " - Sell Cost: " + usable.getPrice());
            return;
        }
        if (item instanceof Collectable) {
            Collectable collectable = (Collectable) item;
            System.out.println("Name: " + collectable.getId() + " - Desc: " +
                    collectable.getDescription() + " - No Sell Cost: Collectable");
        }
    }

    public void showMatchHistory(Account account){
        //todo
    }
}
