import java.time.LocalDateTime;

public class ChatMessage {
    private String message;
    private String sender;
    private LocalDateTime time;
    public ChatMessage(String message,String sender){
        this.message = message;
        this.sender = sender;
        time=LocalDateTime.now();
    }

    public String getMessage() {
        return message;
    }

    public String getSender() {
        return sender;
    }

    public LocalDateTime getTime() {
        return time;
    }
}
