package sean.bolton.io.gps_based_chat_app;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.location.LocationManager.GPS_PROVIDER;
import static android.location.LocationManager.NETWORK_PROVIDER;


/**
 * Created by sean on 12/11/2016.
 */

public class CreateMessageActivity extends AppCompatActivity implements android.location.LocationListener {

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
                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (location != null) {
                    //Disable UI elements during send attempt
                    sendMessageButton.setText("Sending Message....");
                    sendMessageButton.setEnabled(false);
                    messageTextField.setEnabled(false);
                    CreateMessageActivity.this.location = location;
                    packageMessage();
                    startActivity(new Intent(CreateMessageActivity.this, MainActivity.class));
                } else {
                    if (ActivityCompat.checkSelfPermission(CreateMessageActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(CreateMessageActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, CreateMessageActivity.this);
                }

            }

        });

        //TODO: Capture location and assign to variable

    }

    protected void packageMessage() {
        //TODO:
        Location loc = new Location(GPS_PROVIDER);
        try {
            loc = locationManager.getLastKnownLocation(GPS_PROVIDER);
        } catch (SecurityException e) {
            Log.e("GPS", "exception occured " + e.getMessage());
        } catch (Exception e) {
            Log.e("GPS", "exception occured " + e.getMessage());
        }
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("ChatMessage");
        ChatMessage data = new ChatMessage(loc.getLatitude(), loc.getLongitude(), this.textField.getText().toString());
        myRef.child("messages").push().setValue(data);


    }


    public void onLocationChanged(Location location) {
        if (location != null) {
            Log.v("Location Changed", location.getLatitude() + " and " + location.getLongitude());
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.removeUpdates(this);
        }
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
