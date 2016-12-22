package sean.bolton.io.gps_based_chat_app;

import java.util.Date;

/**
 * Created by sean bolton on 11/12/2016.
 */

public class ChatMessage {

    public double longitude;
    public double latitude;
    public String messageBody;
    public Date messageDate;

    public ChatMessage() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public ChatMessage(double latitude, double longitude, String messageBody) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.messageBody = messageBody;
        this.messageDate = new Date();
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public Date getMessageDate() {
        return messageDate;
    }
}
