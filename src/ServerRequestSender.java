import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;

import java.io.IOException;
import java.io.OutputStreamWriter;

public class ServerRequestSender extends Thread {
    private Request request;
    private YaGson yaGson;

    public ServerRequestSender(Request request) {
        this.request = request;
    }

    @Override
    public void run() {
        try {
            System.out.println("sent");
            OutputStreamWriter output = ClientDB.getInstance().getOutput();
            yaGson = new YaGsonBuilder().setPrettyPrinting().create();
            yaGson.toJson(request, output);
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*public static void main(String[] args) throws IOException {
        try (OutputStreamWriter writer = new OutputStreamWriter(socket.getOutputStream());
             InputStreamReader reader = new InputStreamReader(socket.getInputStream())) {
            Gson gson = new Gson();
            JsonStreamParser parser = new JsonStreamParser(reader);
            while (inputScanner.hasNextLine()) {
                JsonObject obj = new JsonObject();
                obj.addProperty("text", inputScanner.nextLine());
                gson.toJson(obj, writer);
                writer.flush();

                JsonElement response = parser.next();
            }
        }
    }*/
}