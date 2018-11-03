package generalinfo.powellmakerspace.org.makerspacelogin.LoginWindows;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import generalinfo.powellmakerspace.org.makerspacelogin.Classes.Visit;
import generalinfo.powellmakerspace.org.makerspacelogin.MainApplication.DatabaseHelper;
import generalinfo.powellmakerspace.org.makerspacelogin.Classes.Member;
import generalinfo.powellmakerspace.org.makerspacelogin.MainApplication.WelcomeWindow;
import generalinfo.powellmakerspace.org.makerspacelogin.R;

public class MemberConfirmWindow extends AppCompatActivity {

    long member_id;
    DatabaseHelper makerspaceDatabase;
    TextView nameConfirmViewText;
    TextView membershipViewText;
    TextView punchesRemainingTextLabel;
    TextView punchesRemainingViewText;
    Member member;
    Button yesConfirmButton;
    Button noConfirmButton;
    Button updateConfirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_confirm_window);

        //Set up objects in activity
        nameConfirmViewText = (TextView) findViewById(R.id.nameConfirmViewText);
        membershipViewText = (TextView) findViewById(R.id.membershipViewText);
        punchesRemainingViewText = (TextView) findViewById(R.id.punchesRemainingViewText);
        punchesRemainingTextLabel = (TextView) findViewById(R.id.punchesRemainingTextLabel);
        yesConfirmButton = (Button) findViewById(R.id.yesConfirmButton);
        noConfirmButton = (Button) findViewById(R.id.noConfirmButton);
        updateConfirmButton = (Button) findViewById(R.id.updateConfirmButton);


        // Get member id from the last activity
        member_id = getIntent().getLongExtra("org.powellmakerspace.generalinfo.MEMBER_ID",-1);

        // Start Database helper
        makerspaceDatabase = new DatabaseHelper(this);

        // Set the info if there is a member id greater than -1
        if (member_id > -1){
            Log.e("MEMBER ID: ",Long.toString(member_id));
            member = makerspaceDatabase.getMember(member_id);
            nameConfirmViewText.setText(member.getMemberName());
            membershipViewText.setText(member.getMembershipType());

            if(member.getPunchPasses() > -1){
                punchesRemainingTextLabel.setVisibility(View.VISIBLE);
                punchesRemainingViewText.setVisibility(View.VISIBLE);

                if(member.getPunchPasses() == 0){
                    punchesRemainingViewText.setText("Out of Punches, talk to staff.");
                    yesConfirmButton.setEnabled(false);
                }
                else punchesRemainingViewText.setText(Integer.toString(member.getPunchPasses()));

            }
        }
        // Catch if an error occurred
        else{
            Toast errorOccurredMessage = Toast.makeText(getApplicationContext(),"An error occurred, please go back and try again",Toast.LENGTH_SHORT);
            errorOccurredMessage.show();
        }

        // Confirm and move forward
        yesConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Boolean loggedIn = false;

                List<Visit> activeVisits = makerspaceDatabase.getActiveVisits();
                for (Visit visit: activeVisits){
                    if (visit.getMemberID() == member.getMemberID()){
                        loggedIn = true;
                    }
                }

                if (loggedIn == false) {
                    if (member.getPunchPasses() > -1) {
                        int tempPunchNumber = member.getPunchPasses();
                        member.setPunchPasses(tempPunchNumber - 1);
                        makerspaceDatabase.updateMember(member);
                        Toast.makeText(getApplicationContext(), "One punch has been used.", Toast.LENGTH_LONG).show();
                    }

                    // Launch Purpose Window
                    Intent launchPurposeWindow = new Intent(getApplicationContext(), PurposeWindow.class);
                    launchPurposeWindow.putExtra("org.powellmakerspace.generalinfo.MEMBER_ID", member_id);
                    startActivity(launchPurposeWindow);
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(),"You are already Logged In", Toast.LENGTH_LONG).show();
                    Intent launchWelcomeWindow = new Intent(getApplicationContext(), WelcomeWindow.class);
                    startActivity(launchWelcomeWindow);
                    finish();
                }
            }
        });

        // Cancel Confirm and go back
        noConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Launch Login Search Window
                Intent launchLoginSearchWindow = new Intent(getApplicationContext(), LoginSearchWindow.class);
                startActivity(launchLoginSearchWindow);
                finish();
            }
        });

        // Update information by moving to Update Window
        updateConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch Update Window1 and pass in the membership id
                Intent launchUpdateWindow = new Intent(getApplicationContext(), UpdateWindow.class);
                launchUpdateWindow.putExtra("org.powellmakerspace.generalinfo.MEMBER_ID", member_id);
                startActivity(launchUpdateWindow);
                finish();
            }
        });

    }
}
