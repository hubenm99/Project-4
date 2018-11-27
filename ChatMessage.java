
import java.io.Serializable;


final class ChatMessage implements Serializable {
    private static final long serialVersionUID = 6898543889087L;

    private String message;
    private int typeOfMessage;
    private String recipient;

    // Here is where you should implement the chat message object.
    // Variables, Constructors, Methods, etc.

    public ChatMessage(String message, int typeOfMessage, String recipient) {
        this.message = message;
        this.typeOfMessage = typeOfMessage;
        this.recipient = recipient;
    }

    public String getMessage() {
        return message;
    }

    public int getTypeOfMessage() {
        return typeOfMessage;
    }

    public String getRecipient() {
        return recipient;
    }



}