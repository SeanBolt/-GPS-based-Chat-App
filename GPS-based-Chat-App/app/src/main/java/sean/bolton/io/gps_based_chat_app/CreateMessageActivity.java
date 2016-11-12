package sean.bolton.io.gps_based_chat_app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by sean on 12/11/2016.
 */

public class CreateMessageActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createmessage);

        final Button sendMessageButton = (Button) findViewById(R.id.button_sendMessage);
        final EditText messageTextField = (EditText) findViewById(R.id.editText_sendMessage);

        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Disable UI elements during send attempt
                sendMessageButton.setText("Sending Message....");
                sendMessageButton.setEnabled(false);
                messageTextField.setEnabled(false);

                packageMessage();
            }

        });

        //TODO: Capture location and assign to variable


    }

    protected void packageMessage() {
        //TODO: Compile JSON
    }
}
