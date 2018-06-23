package generalinfo.powellmakerspace.org.makerspacelogin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class LeavingConfirmWindow extends AppCompatActivity {

    long visitID;
    Visit visit;
    DatabaseHelper makerspaceDatabase;
    Button leavingConfirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaving_confirm_window);

        if (getIntent().hasExtra("org.powellmakerspace.generalinfo.VISIT_ID")){
            visitID = getIntent().getExtras().getLong("org.powellmakerspace.generalinfo.VISIT_ID");
        }

        // Initialize the database
        makerspaceDatabase = new DatabaseHelper(this);

        // Get visit with visitID
        visit = makerspaceDatabase.getVisit(visitID);

        // Set current time to logout
        visit.setDepartureTime(System.currentTimeMillis()/1000);

        // Initialize button
        leavingConfirmButton = (Button) findViewById(R.id.leavingConfirmButton);
        leavingConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Update Database
                makerspaceDatabase.updateVisit(visit);

                // Display Thank You
                Toast.makeText(getApplicationContext(),"Thank you for visiting!", Toast.LENGTH_LONG).show();

                // Return to start screen
                Intent launchWelcomeWindow = new Intent(getApplicationContext(), WelcomeWindow.class);
                startActivity(launchWelcomeWindow);
            }
        });


    }
}
