package sean.bolton.io.gps_based_chat_app;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.location.LocationManager.NETWORK_PROVIDER;


/**
 * Created by sean on 12/11/2016.
 */

public class CreateMessageActivity extends AppCompatActivity implements android.location.LocationListener{

    private DatabaseReference mDatabase;
    private EditText textField;
    private LocationManager locationManager;
    private Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createmessage);
        final Button sendMessageButton = (Button) findViewById(R.id.button_sendMessage);
        final EditText messageTextField = (EditText) findViewById(R.id.editText_sendMessage);
        this.textField = messageTextField;

        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Disable UI elements during send attempt
                sendMessageButton.setText("Sending Message....");
                sendMessageButton.setEnabled(false);
                messageTextField.setEnabled(false);

                packageMessage();
                startActivity(new Intent(CreateMessageActivity.this, MainActivity.class));
            }

        });

        //TODO: Capture location and assign to variable


    }

    protected void packageMessage() {
        //TODO:
//        getLocation();
//        Location loc = null;
//        try {
//            loc = locationManager.getLastKnownLocation(NETWORK_PROVIDER);
//        }
//        catch (SecurityException e) {
//            Log.e("GPS", "exception occured " + e.getMessage());
//        }
//        catch (Exception e) {
//            Log.e("GPS", "exception occured " + e.getMessage());
//        }
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("messages");
        ChatMessage data = new ChatMessage(53.23, -9.81, this.textField.getText().toString());
        myRef.push().setValue(data);

    }

    protected void getLocation() {
        this.locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try {
            // App 2: Todo:request updates here
            Long timeInterval = new Long(10000);
            Long distance = new Long(5);
            this.locationManager.requestLocationUpdates(NETWORK_PROVIDER, timeInterval, distance, this);
        }
        catch (SecurityException e) {
            Log.e("GPS", "exception occured " + e.getMessage());
        }
        catch (Exception e) {
            Log.e("GPS", "exception occured " + e.getMessage());
        }
    }

    protected void stopLocationUpdates() {
        try {
            locationManager.removeUpdates(this);
        }
        catch (SecurityException e) {
            Log.e("GPS", "exception occured " + e.getMessage());
        }
        catch (Exception e) {
            Log.e("GPS", "exception occured " + e.getMessage());
        }
    }

    public void onLocationChanged(Location location) {
        this.location = location;
    }

    public void onProviderDisabled(String arg0) {
        Log.e("GPS", "provider disabled " + arg0);
    }

    public void onProviderEnabled(String arg0) {
        Log.e("GPS", "provider enabled " + arg0);
    }

    public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
        Log.e("GPS", "status changed to " + arg0 + " [" + arg1 + "]");
    }
}
