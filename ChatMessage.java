
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

final class ChatMessage implements Serializable {
    private static final long serialVersionUID = 6898543889087L;

    private String message;
    private int typeOfMessage;

    // Here is where you should implement the chat message object.
    // Variables, Constructors, Methods, etc.

    public ChatMessage(String message, int typeOfMessage) {
        this.message = message;
        this.typeOfMessage = typeOfMessage;
    }

    public String getMessage() {
        return message;
    }

    public int getTypeOfMessage() {
        return typeOfMessage;
    }

    synchronized private void broadcast(String message) {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss ");
        String finalDate = dateFormat.format(date);
        writeMessage(finalDate +  message + "\n");
    }

    private boolean writeMessage(String message) {
        if (getTypeOfMessage() == 1) {
            return false;
        } else

        
    }

    synchronized private void remove(int id) {

    }

    public void run() {

    }

    private void close() {

    }

}