import java.util.List;

public class ControllerBattleCommands {
    private static final DataBase database = DataBase.getInstance();
    private static final View view = View.getInstance();

    private ControllerBattleCommands() {
    }

    public void main() {
        boolean didExit = false;
        Request request = new Request();
        while (!didExit) {
            request.getNewCommand();
            switch (request.getType()) {
                case GAME_INFO:
                    break;
                case SHOW_MINIONS:
                    break;
                case SHOW:
                    break;
                case SELECT:
                    break;
                case MOVE:
                    break;
                case ATTACK:
                    break;
                case USE:
                    break;
                case INSERT:
                    break;
                case END:
                    break;
                case ENTER:
                    break;
                case EXIT:
                    didExit = true;
                    break;
                default:
                    System.out.println("!!!!!! bad input in ControllerBattleCommands.main");
                    System.exit(-1);
            }
        }
    }

    public void showGameInfo(Request request) {

    }

    public void showMinions(Request request) {
        if (request.getCommand().matches("^show my minions$")) {

            return;
        }
        if (request.getCommand().matches("^show opponent minions$")) {

        }
    }

    public void show(Request request) {
        if(request.getCommand().equals("show game info")){
            view.showGameInfo(database.getCurrentBattle());
        }
        else if(request.getCommand().equals("show my minions")){
            List<Unit> minions=database.getCurrentBattle().getBattleGround().getMinionsOfPlayer(database.getCurrentBattle().getPlayerInTurn());
            for(Unit minion:minions){
                view.showMinionInBattle(minion,database.getCurrentBattle().getBattleGround().getCoordinationsOfUnit(minion));
            }
        }
        else if(request.getCommand().equals("show opponent minions")){
            Player player;
            if(database.getCurrentBattle().getPlayerInTurn()==database.getCurrentBattle().getPlayer1())
                player=database.getCurrentBattle().getPlayer2();
            else player=database.getCurrentBattle().getPlayer1();
            List<Unit> minions=database.getCurrentBattle().getBattleGround().getMinionsOfPlayer(player);
            for (Unit minion:minions){
                view.showMinionInBattle(minion,database.getCurrentBattle().getBattleGround().getCoordinationsOfUnit(minion));
            }
        }
        else if(request.getCommand().matches("show card info \\w+")){
            String cardId=request.getCommand().split("\\s+")[3];
            Card card=database.getCurrentBattle().getBattleGround().getCardByID(cardId);
            if(card!=null){
                if(card instanceof Spell)
                {
                    view.showCardInfoSpell((Spell) card);
                }else if(card instanceof Unit){
                    if(((Unit)card).getHeroOrMinion().equals("Minion")){
                        view.showCardInfoMinion((Unit)card);
                    }
                    else if(((Unit)card).getHeroOrMinion().equals("Hero")){
                        view.showCardInfoHero((Unit)card);
                    }
                }
            }else {
                request.setOutputMessageType(outputMessageType.NO_CARD_IN_BATTLEGROUND);
                view.printError(request.getOutputMessageType());
            }
        }
    }

    public void select() {

    }

    public void move() {

    }

    public void attack() {

    }

    public void use() {

    }

    public void insert() {

    }

    public void end(Request request) {
        if (request.getCommand().equals("end game")) {
            if (!database.getCurrentBattle().isBattleFinished()) {
                request.setOutputMessageType(outputMessageType.BATTLE_NOT_FINISHED);
                view.printError(request.getOutputMessageType());
            } else {

            }
            return;
        }
        if (request.getCommand().equals("end turn")) {
            database.getCurrentBattle().endTurn();
            return;
        }
        request.setOutputMessageType(outputMessageType.WRONG_COMMAND);
        view.printError(request.getOutputMessageType());

    }

    public void enter(Request request) {
        if (!request.getCommand().equals("enter graveyard")) {
            request.setOutputMessageType(outputMessageType.WRONG_COMMAND);
            view.printError(request.getOutputMessageType());
        } else {
            ControllerGraveYard.getInstance().main();
        }
    }
}
