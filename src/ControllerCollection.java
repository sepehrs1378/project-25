public class ControllerCollection {
    private static final ControllerCollection ourInstance = new ControllerCollection();
    private static final DataBase dataBase = DataBase.getInstance();

    private ControllerCollection() {

    }

    public static ControllerCollection getInstance() {
        return ourInstance;
    }

    public void main() {
        boolean didExit = false;
        Request request = new Request();
        while (!didExit) {
            request.getNewCommand();
            switch (request.getType()) {
                case CREATE:
                    break;
                case EXIT:
                    didExit = true;
                    break;
                case SHOW:
                    break;
                case SEARCH:
                    break;
                case SAVE:
                    //todo is it needed?
                    break;
                case DELETE:
                    break;
                case ADD:
                    break;
                case REMOVE:
                    break;
                case VALIDATE:
                    break;
                case SELECT:
                    break;
                case HELP:
                    break;
                default:
                    System.out.println("!!!!!! bad input in ControllerCollection.main");
                    System.exit(-1);
            }
        }
    }

    public void show() {

    }

    public void help(){

    }

    public void select(){

    }

    public void validate(){

    }

    public void add(){

    }

    public void
}
