package generalinfo.powellmakerspace.org.makerspacelogin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


public class PurposeWindow1 extends AppCompatActivity {

    DatabaseHelper makerspaceDatabase;
    RadioGroup purposeRadioGroup;
    RadioButton purposeRadioButton;
    Button visitArrivingDoneButton;
    String visitPurpose, selectedId;

    Visit newVisit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purpose_window1);

        // Begin Database Helper
        makerspaceDatabase = new DatabaseHelper(this);

        // Cast RadioGroup and Button
        purposeRadioGroup = (RadioGroup) findViewById(R.id.purposeRadioGroup);
        visitArrivingDoneButton = (Button) findViewById(R.id.visitArrivingDoneButton);

        // Click listener for button
        visitArrivingDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Create new visit record
                newVisit = new Visit();

                // Set member id
                if (getIntent().hasExtra("org.powellmakerspace.generalinfo.MEMBER_ID")){
                    newVisit.setMemberID(getIntent().getExtras().getLong("org.powellmakerspace.generalinfo.MEMBER_ID"));
                }
                else{
                    Toast.makeText(getApplicationContext(), "member ID not transferred",Toast.LENGTH_LONG).show();
                }

                // Set current time as arrival time
                newVisit.setArrivalTime(System.currentTimeMillis()/1000);

                // Set Departure time to zero - Flag that indicates currently active
                newVisit.setDepartureTime(0);

                // Find the selected RadioButton and set as purpose
                int selectedId = purposeRadioGroup.getCheckedRadioButtonId();
                purposeRadioButton = (RadioButton) findViewById(selectedId);

                // Set purpose for visit
                newVisit.setVisitPurpose(purposeRadioButton.getText().toString());

                // Add visit record to database
                makerspaceDatabase.createVisit(newVisit);

                // Create notification to welcome guest and let them know they have logged in properly
                Toast welcomeNotification = Toast.makeText(getApplicationContext(), "Welcome to the Powell Makerspace!",Toast.LENGTH_SHORT);
                welcomeNotification.show();

                // Return to the WelcomeWindow
                Intent launchWelcomeWindow = new Intent(getApplicationContext(), WelcomeWindow.class);
                startActivity(launchWelcomeWindow);


            }
        });
    }
}
