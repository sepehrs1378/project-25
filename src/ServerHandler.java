import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonStreamParser;

import java.io.IOException;
import java.net.Socket;

public class ServerHandler extends Thread {
    private String address;
    private int port;

    public ServerHandler(String address, int port) {
        this.address = address;
        this.port = port;
    }

    @Override
    public void run() {
        try {
            Socket socket = new Socket(address, port);
            ClientDB.getInstance().setSocket(socket);
            JsonStreamParser parser = ClientDB.getInstance().getParser();
            JsonObject obj;
            YaGson yaGson = new YaGsonBuilder().setPrettyPrinting().create();
            while (parser.hasNext()) {
                obj = parser.next().getAsJsonObject();
                Response response = yaGson.fromJson(obj.toString(), Response.class);
                switch (response.getResponseType()) {
                    case sendMessage:
                        System.out.println(response.getMessage());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}