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

    public void showInfoOfDeadCards(List<Card> cards) {
        System.out.println("Heroes:");
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i) instanceof Unit) {
                Unit unit = (Unit) cards.get(i);
                if (unit.getHeroOrMinion().equals("Hero")) {
                    System.out.println("1 : Name : " + unit.getId() + " - AP : " + unit.getAp() + " - HP : " +
                            unit.getHp() + " - Class : " + unit.getUnitClass() + " - Special power : " +
                            unit.getSpecialPower().getDescription());
                }
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
                            unit.getClass() + " - AP : " + unit.getAp() + " - HP : " + unit.getHp() +
                            " - MP : " + unit.getMana() + " - Special power : " + unit.getSpecialPower().getDescription());
                }
            }
        }
    }

    public void printContentsOfAList(List list) {
        System.out.println(list);
    }

    public void showUsers(List<Account> users, String currentUserName) {
        for (Account account : users) {
            if (!account.getUsername().equals(currentUserName))
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
        System.out.println("Name: " + hero.getId().split("_")[1] + " - AP: " + hero.getAp() +
                " - HP: " + hero.getHp() + " - Class: " + hero.getUnitClass() +
                " - Special Power: " + hero.getSpecialPower().getDescription() +
                " - Sell Cost: " + hero.getPrice());
    }

    public void showMinionInBattle(Unit minion, int[] coordination){
            System.out.println(minion.getId()+" : "+minion.getId().split("_")[1]+", health : "+
                    minion.getHp()+", location:("+coordination[0]+","+coordination[1]+"),power : "
                    +minion.getAp());
    }

    public void showCardInfoHero(Unit hero){
        System.out.println("Hero:");
        System.out.println("Name: "+hero.getName());
        System.out.println("Cost: "+hero.getPrice());
        System.out.println("Desc: "+hero.getDescription());
    }

    public void showCardInfoMinion(Unit unit){
        System.out.println("Minion:");
        System.out.println("Name: "+unit.getName());
        System.out.println("HP: "+unit.getHp()+" AP: "+unit.getAp()+" MP: "+unit.getMana());
        System.out.println("Range: "+unit.getMaxRange());
        //System.out.println("Combo-ability: "+unit.get);
        //todo Combo-ability
        System.out.println("Cost: "+unit.getPrice());
        System.out.println("Desc: "+unit.getDescription());
    }

    public void showCardInfoSpell(Spell spell){
        System.out.println("Spell: ");
        System.out.println("Name: "+spell.getName());
        System.out.println("MP: "+spell.getMana());
        System.out.println("Cost: "+spell.getPrice());
        System.out.println("Desc: "+spell.getDescription());
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

    public void showMatchHistoryTitle(){
        System.out.println("OPPONENT    WIN/LOSS    TIME");
    }

    public void showMatchHistory(String opponentName, String winOrLoss, long seconds,
                                 long minutes, long hours, long days) {
        System.out.print(opponentName + "   " + winOrLoss);
        if (days == 0 && hours == 0 && minutes == 0){
            System.out.println(seconds + "seconds ago");
        }else if (days == 0 && hours == 0){
            System.out.println(minutes + "minutes ago");
        }else if (days == 0){
            System.out.println(hours + "hours ago");
        }else {
            System.out.println(days + "days ago");
        }
    }

    public void showUnitMove(String unitID, int destinationRow, int destinationColumn) {
        System.out.println(unitID + " move to " + destinationRow + " " + destinationColumn);
    }
    public void showGameInfo(Battle battle){
        System.out.println("Mana points of player1 is: "+battle.getPlayer1().getMana());
        System.out.println("Mana points of player2 is: "+battle.getPlayer2().getMana());
        if(battle.getMode().equals(Constants.CLASSIC)){
            System.out.println("HP of player1's hero is: "+battle.getPlayer1().getDeck().getHero().getHp());
            System.out.println("HP of player2's hero is: "+battle.getPlayer2().getDeck().getHero().getHp());
        }else if(battle.getMode().equals(Constants.ONE_FLAG)){
            for(int i=0;i<battle.getBattleGround().getCells().length;i++){
                for(int j=0;j<battle.getBattleGround().getCells()[i].length;j++){
                    if(battle.getBattleGround().getCells()[i][j].getFlags().size()>0)
                    {
                        if(battle.getBattleGround().getCells()[i][j].getUnit().getId().equals(battle.getPlayer1().getPlayerInfo().getPlayerName())){
                            System.out.println("flag is in row "+i+" column "+j+" in hand of player1");
                        }else if(battle.getBattleGround().getCells()[i][j].getUnit().getId().equals(battle.getPlayer1().getPlayerInfo().getPlayerName())){
                            System.out.println("flag is in row "+i+" column "+j+" in hand of player2");
                        }else
                            System.out.println("flag is in row "+i+" column "+j);
                    }
                }
            }
        }else if(battle.getMode().equals(Constants.FLAGS)){
            int rowCounter=1;
            int flagCounter=1;
            for(Cell[] cellRow:battle.getBattleGround().getCells()){
                int columnCounter=1;
                for(Cell cell:cellRow){
                    for(Flag flag:cell.getFlags()){
                        if(cell.getUnit()!=null){
                            System.out.println("flag"+flagCounter+" in row "+rowCounter+
                                    " column "+columnCounter+" "+cell.getUnit().getId());
                        }else {
                            System.out.println("flag"+flagCounter+" in row "+rowCounter+
                                    " column "+columnCounter);
                        }
                        flagCounter++;
                    }
                    columnCounter++;
                }
                rowCounter++;
            }
        }
    }
}
