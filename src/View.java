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
}
