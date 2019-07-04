import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonStreamParser;

import java.net.Socket;

public class ClientHandler extends Thread {
    private Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        Connection connection = new Connection(socket);
        NetWorkDB.getInstance().addConnection(connection);
        YaGson yaGson = new YaGsonBuilder().setPrettyPrinting().create();
        JsonStreamParser parser = connection.getParser();
        while (true) {
            JsonObject obj = parser.next().getAsJsonObject();
            Request request = yaGson.fromJson(obj.toString(), Request.class);
            handleMultiPlayerCase(request);
            if (request.getRequestType().equals(RequestType.close))
                break;
        }
        NetWorkDB.getInstance().closeConnection(socket);
    }

    private void handleMultiPlayerCase(Request request) {
        switch (request.getRequestType()) {
            case moveUnit:
                break;
            case attackUnit:
                break;
            case endTurn:
                break;
            case insertCard:
                break;
            case useSpecialPower:
                break;
            case useCollectable:
                break;
            case selectUnit:
                break;
            case enterGraveYard:
                break;
            case forfeit:
                break;
        }
    }

    public void handleMultiPlayerCase(){
    }
}