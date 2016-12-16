package sean.bolton.io.gps_based_chat_app;

/**
 * Created by finnk on 11/5/2016.
 */

public class ChatMessage {

    public double longitude;
    public double latitude;
    public String messageBody;

    public ChatMessage() {

    }

    public ChatMessage(double lati,double longi, String messageBody) {
        longitude = longi;
        latitude = lati;
        this.messageBody = messageBody;
    }



}
