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

    public void show() {

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
                request.setErrorType(ErrorType.BATTLE_NOT_FINISHED);
                view.printError(request.getErrorType());
            } else {
                //todo
            }
            return;
        }
        if (request.getCommand().equals("end turn")) {
            database.getCurrentBattle().endTurn();
            return;
        }
        request.setErrorType(ErrorType.WRONG_COMMAND);
        view.printError(request.getErrorType());

    }

    public void enter(Request request) {
        if (!request.getCommand().equals("enter graveyard")) {
            request.setErrorType(ErrorType.WRONG_COMMAND);
            view.printError(request.getErrorType());
        } else {
            ControllerGraveYard.getInstance().main();
        }
    }
}
