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
                case SHOW_INFO_ID:
                    break;
                case SHOW_CARDS:
                    break;
                case HELP:
                    help();
                    break;
                case EXIT:
                    didExit = true;
                    break;
                default:
                    view.printOutputMessage(OutputMessageType.WRONG_COMMAND);
            }
        }
    }

    private void help() {
        view.printHelp(HelpType.CONTROLLER_GRAVEYARD);
    }

    private void showCards() {
        view.showInfoOfDeadCards(dataBase.getCurrentBattle()
                .getPlayerInTurn().getGraveYard().getDeadCards());
    }

    private void showInfoId() {
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

    public void showInfoOfCards(List<Card> cards) {
        view.showInfoOfDeadCards(cards);
    }
}
