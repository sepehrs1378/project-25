import java.util.List;

public class ControllerGraveYard {
    private static ControllerGraveYard ourInstance = new ControllerGraveYard();
    private Request request = Request.getInstance();
    private View view = View.getInstance();
    private DataBase dataBase = DataBase.getInstance();

    public static ControllerGraveYard getInstance() {
        return ourInstance;
    }

    private ControllerGraveYard() {
    }

    public void main() {
        boolean didExit = false;
        while (!didExit) {
            request.getNewCommand();
            switch (request.getType()) {
                case SHOW:
                    show();
                    break;
                case EXIT:
                    didExit = true;
                    break;
                default:
                    System.out.println("!!!!!! bad input in ControllerGraveYard.main");
                    System.exit(-1);
            }
        }
    }

    public void show() {
        if (!request.getCommand().matches("^show cards$") &&
                !request.getCommand().matches("^show info .+$")) {
            request.setOutputMessageType(OutputMessageType.WRONG_COMMAND);
            view.printOutputMessage(request.getOutputMessageType());
            return;
        }
        if (request.getCommand().matches("^show cards$")) {
            view.showInfoOfDeadCards(dataBase.getCurrentBattle().getPlayerInTurn().getGraveYard().getDeadCards());
        }
        if (request.getCommand().matches("^show info .+$")) {
            String[] strings = request.getCommand().split("\\s+");
            String cardId = strings[2];
            Card card = dataBase.getCurrentBattle().getPlayerInTurn().getGraveYard().findCard(cardId);
            if (card instanceof Unit) {
                Unit unit = (Unit) card;
                if (unit.getHeroOrMinion().equals("hero")) {
                    view.showCardInfoHero(unit);
                }
            }
        }
    }

    public void showInfoOfCards(List<Card> cards) {
        view.showInfoOfDeadCards(cards);
    }

}
