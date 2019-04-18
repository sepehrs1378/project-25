class ControllerShop {
    private static final View view=View.getInstance();

    private ControllerShop() {

    }

    public void main() {
        boolean didExit = false;
        Request request = new Request();
        request.getNewCommand();
        while (!didExit) {
            switch (request.getType()) {
                case EXIT:
                    didExit = true;
                    break;
                case SHOW:
                    break;
                case SEARCH:
                    break;
                case BUY:
                    break;
                case SELL:
                    break;
                case HELP:
                    break;
                default:
                    System.out.println("!!!!!! bad input in ControllerShop.main");
                    System.exit(-1);
            }
        }
    }

    public void show(Request request) {

    }

    public void sell(Request request){

    }

    public void buy(Request request){

    }

    public void search(Request request){

    }

    public void help(Request request,View view){

    }
}
