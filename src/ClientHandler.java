import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import com.google.gson.JsonStreamParser;

import java.io.IOException;
import java.net.Socket;

public class ClientHandler extends Thread {
    private Socket socket;
    private YaGson yaGson = null;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        Connection connection = new Connection(socket);
        NetWorkDB.getInstance().addConnection(connection);
        yaGson = new YaGsonBuilder().setPrettyPrinting().create();
        JsonStreamParser parser = new JsonStreamParser(connection.getInput());
        while (true) {
            System.out.println("*");
            Request request = yaGson.fromJson(connection.getInput(), Request.class);
            switch (request.getRequestType()) {
                case sendMessage:
                    System.out.println("**");
                    System.out.println(request.getMessage());
                    break;
            }
            if (request.getRequestType().equals(RequestType.close))
                break;
        }
        NetWorkDB.getInstance().closeConnection(socket);
    }
}