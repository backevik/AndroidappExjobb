package examensarbete.diacert_android.Messages;

/**
 * Created by Martin on 2016-04-28.
 */

public class Message {
    private String fromName, message;
    private boolean isSelf;

    public Message() {
    }

    public Message(String message, boolean isSelf) {
        //this.fromName = fromName;
        this.message = message;
        this.isSelf = isSelf;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSelf() {
        return isSelf;
    }

    public void setSelf(boolean isSelf) {
        this.isSelf = isSelf;
    }

}