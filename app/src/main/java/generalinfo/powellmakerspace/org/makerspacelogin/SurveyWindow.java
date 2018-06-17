package generalinfo.powellmakerspace.org.makerspacelogin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class SurveyWindow extends AppCompatActivity {

    Button submitSurveyButton, declineSurveyButton;

    CheckBox friendCheckBox, libraryCheckBox, websiteCheckBox, pvceCheckBox, walkUpCheckBox;
    String learnedAboutMessage;

    DatabaseHelper makerspaceDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_window);

        makerspaceDatabase = new DatabaseHelper(this);

        submitSurveyButton = (Button) findViewById(R.id.submitSurveyButton);
        declineSurveyButton = (Button) findViewById(R.id.declineSurveyButton);

        friendCheckBox = (CheckBox) findViewById(R.id.friendCheckBox);
        libraryCheckBox = (CheckBox) findViewById(R.id.libraryCheckBox);
        websiteCheckBox = (CheckBox) findViewById(R.id.websiteCheckbox);
        pvceCheckBox = (CheckBox) findViewById(R.id.pvceCheckBox);
        walkUpCheckBox = (CheckBox) findViewById(R.id.walkUpCheckBox);


        submitSurveyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Gather Survey Information
                if (friendCheckBox.isChecked())
                    learnedAboutMessage = learnedAboutMessage + "Friend, ";
                if(libraryCheckBox.isChecked())
                    learnedAboutMessage = learnedAboutMessage + "Library, ";
                if(websiteCheckBox.isChecked())
                    learnedAboutMessage = learnedAboutMessage + "Website, ";
                if(pvceCheckBox.isChecked())
                    learnedAboutMessage = learnedAboutMessage + "PVCE, ";
                if(walkUpCheckBox.isChecked())
                    learnedAboutMessage = learnedAboutMessage + "Walk up";

                // Create new Survey Object
                Survey newSurvey = new Survey(learnedAboutMessage);

                // Add Survey to the database
                makerspaceDatabase.createSurvey(newSurvey);

                // Launch Purpose Window
                Intent launchPurposeWindow = new Intent(getApplicationContext(), PurposeWindow.class);
                // Look for passed information - pass it on further
                if (getIntent().hasExtra("org.powellmakerspace.generalinfo.MEMBER_ID")){
                    launchPurposeWindow.putExtra("org.powellmakerspace.generalinfo.MEMBER_ID", getIntent().getExtras().getLong("org.powellmakerspace.generalinfo.MEMBER_ID"));
                }
                // Continue to Purpose Window
                startActivity(launchPurposeWindow);
            }
        });

        declineSurveyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start new intent
                Intent launchPurposeWindow = new Intent(getApplicationContext(), PurposeWindow.class);

                // Look for passed information - pass it on further
                if (getIntent().hasExtra("org.powellmakerspace.generalinfo.MEMBER_ID")){
                    launchPurposeWindow.putExtra("org.powellmakerspace.generalinfo.MEMBER_ID", getIntent().getExtras().getLong("org.powellmakerspace.generalinfo.MEMBER_ID"));
                }

                // Continue to Purpose Window
                startActivity(launchPurposeWindow);
            }
        });
    }
}
