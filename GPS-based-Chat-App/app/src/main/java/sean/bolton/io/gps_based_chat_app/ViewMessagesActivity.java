package sean.bolton.io.gps_based_chat_app;

import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sean on 13/11/2016.
 */

public class ViewMessagesActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    protected ChatMessage chatMessage;
    protected ArrayList<ChatMessage> chatMessageList;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle("Loading...");
        setContentView(R.layout.activity_viewmessages);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.addListenerForSingleValueEvent(messageListener);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    protected void setupTable() {
        final TableLayout tableLayout = (TableLayout) findViewById(R.id.tableLayout);
        int count = 0;

        // Programmatically create a table row for each message
        for (ChatMessage message : this.chatMessageList) {

            TableRow row = new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);
            LinearLayout ll = new LinearLayout(this);
            ll.setOrientation(LinearLayout.VERTICAL);
            TextView one = new TextView(this);
            TextView two = new TextView(this);
            TextView three = new TextView(this);
            one.setTypeface(Typeface.DEFAULT_BOLD);
            Double lat = message.getLatitude();
            Double lng = message.getLongitude();
            Date date = message.getMessageDate();
            one.setText(date.toString());
            two.setText("Longitude: " +lng.toString()+" Latitude: " + lat.toString());
            three.setText(message.getMessageBody());
            ll.addView(one);
            ll.addView(two);
            ll.addView(three);
            row.addView(ll);
            tableLayout.addView(row, count);
            count++;
        }
        this.setTitle("My Messages");
    }

    ValueEventListener messageListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot snapshot) {
            ArrayList<ChatMessage> messagesList = new ArrayList<ChatMessage>();
            for (DataSnapshot messageSnapshot : snapshot.getChildren()) {
                for (DataSnapshot mSnapshot : messageSnapshot.getChildren()) {
                    for (DataSnapshot meSnapshot : mSnapshot.getChildren()) {

                        ChatMessage message = meSnapshot.getValue(ChatMessage.class);
                        messagesList.add(message);
                        Log.e("message", message.getMessageBody());
                    }
                }
            }

            System.out.print(messagesList.toString());
            ViewMessagesActivity.this.chatMessageList = messagesList;
            setupTable();
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            // Getting Post failed, log a message
            Log.w("loadMessage:onCancelled", databaseError.toException());
            // ...
        }
    };

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("ViewMessages Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
