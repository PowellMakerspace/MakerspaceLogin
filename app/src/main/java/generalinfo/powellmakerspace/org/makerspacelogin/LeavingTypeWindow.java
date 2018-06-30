package generalinfo.powellmakerspace.org.makerspacelogin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LeavingTypeWindow extends AppCompatActivity {

    Button leavingVisitButton;
    Button leavingTourButton;
    DatabaseHelper makerspaceDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaving_type_window);

        // Initiate Leaving Visit and Tour Buttons
        leavingVisitButton = (Button) findViewById(R.id.leavingVisitButton);
        leavingTourButton = (Button) findViewById(R.id.leavingTourButton);

        // Initiate Database
        makerspaceDatabase = new DatabaseHelper(this);

        /**
         * Code to bypass the option for tour logout if no tours are signed in
         */
        if (makerspaceDatabase.getActiveTours().size() == 0){
            Intent launchLeavingVisitWindow = new Intent(getApplicationContext(), LeavingVisitWindow.class);
            startActivity(launchLeavingVisitWindow);
            finish();
        }

        /**
         * Code for button functionality if a tour is signed in
         */
        else {
            // Set button actions
            leavingVisitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent launchLeavingVisitWindow = new Intent(getApplicationContext(), LeavingVisitWindow.class);
                    startActivity(launchLeavingVisitWindow);
                    finish();
                }
            });

            leavingTourButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent launchLeavingTourWindow = new Intent(getApplicationContext(), LeavingTourWindow.class);
                    startActivity(launchLeavingTourWindow);
                    finish();
                }
            });
        }

    }
}
