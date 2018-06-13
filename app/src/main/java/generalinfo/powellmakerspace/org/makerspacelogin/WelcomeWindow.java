package generalinfo.powellmakerspace.org.makerspacelogin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WelcomeWindow extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_window);

        // Launch arriving window
        Button arrivingButton = (Button) findViewById(R.id.arrivingButton);
        arrivingButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    Intent launchArrivingWindow = new Intent(getApplicationContext(), ArrivingWindow.class);
                    startActivity(launchArrivingWindow);

                }
            }
        );

        //Launch the leaving window.
        Button leavingButton = findViewById(R.id.leavingButton);
        leavingButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent launchLeavingWindow = new Intent(getApplicationContext(), LeavingWindow.class);
                startActivity(launchLeavingWindow);

            }

        });

    }
}
