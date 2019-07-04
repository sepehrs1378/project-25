import com.google.gson.JsonStreamParser;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ClientDB {
    private static ClientDB ourInstance = new ClientDB();
    private Socket socket;
    private InputStreamReader input;
    private OutputStreamWriter output;
    private JsonStreamParser parser;

    public static ClientDB getInstance() {
        return ourInstance;
    }

    private ClientDB() {
    }

    public InputStreamReader getInput() {
        return input;
    }

    public OutputStreamWriter getOutput() {
        return output;
    }

    public Socket getSocket() {
        return socket;
    }

    public JsonStreamParser getParser() {
        return parser;
    }

    public void setSocket(Socket socket) {
        try {
            this.socket = socket;
            this.input = new InputStreamReader(socket.getInputStream());
            this.output = new OutputStreamWriter(socket.getOutputStream());
            this.parser = new JsonStreamParser(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}